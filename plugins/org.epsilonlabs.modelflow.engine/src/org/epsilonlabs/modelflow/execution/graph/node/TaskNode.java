/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.api.IResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.management.param.TaskInputPropertyHandler;
import org.epsilonlabs.modelflow.management.param.TaskOutputPropertyHandler;
import org.epsilonlabs.modelflow.management.param.TaskParamManager;
import org.epsilonlabs.modelflow.management.resource.ResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.PublishSubject;

public class TaskNode implements ITaskNode { 

	private static final Logger LOG = LoggerFactory.getLogger(TaskNode.class);
	
	protected ITask taskDefintion;
	protected ITaskInstance taskInstance;
	protected TaskState state;

	protected final CompletableSubject completable = CompletableSubject.create();

	protected final PublishSubject<TaskState> statusUpdater = PublishSubject.create();

	private TaskInputPropertyHandler taskInputs;
	private TaskOutputPropertyHandler taskOutputs;
	
	public TaskNode(ITask task) {
		this.taskDefintion = task;
		setState(TaskState.CREATED);
	}
	
	@Override
	public Completable getObservable() {
		return completable;
	}
	
	@Override
	public void subscribe(IModelFlowPublisher pub){
		statusUpdater.subscribe(status -> pub.taskState(this.taskDefintion.getName(), status));
	}
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		LOG.debug("Called execute for {}", getTaskDefinition().getName());
		
		// Instantiate task and populate
		this.taskInstance = ctx.getTaskRepository().create(this, ctx);
		setState(TaskState.INITIALIZED);
		
		/*
		 * Check if it should execute 
		 * i.e. task is enabled && guard is OK 
		 */
		if (shouldExecute(ctx)) {
			// Prepare for execution 
			this.taskInstance.validateParameters();
			// Assume it will execute
			boolean execute = true;
			
			ConservativeExecutionHelper conservativeHelper = new ConservativeExecutionHelper(this, ctx);
			if (conservativeHelper.hasBeenPreviouslyExecuted()) { // There is a previous execution trace
				DependencyGraphHelper dependencyGraphHelper = new DependencyGraphHelper(ctx.getDependencyGraph());
				
				if ( !(taskInstance.isAlwaysExecute() || taskDefintion.getAlwaysExecute()) 
						&& ! (dependencyGraphHelper.hasDerivedOutputDependencies(this)) )
				{
					boolean inputsChanged = conservativeHelper.haveInputPropertiesChanged() 
							|| conservativeHelper.haveInputModelsChanged();
					int shouldExecuteOutput = shouldExecuteBasedOnOutput(ctx, conservativeHelper);
					switch (shouldExecuteOutput) {
					case 0:
						execute = inputsChanged;
						break;
					case -1:
						if (inputsChanged) {
							// WARN!
						}
						execute = false;
						break;
					default:
						break;
					}					
				}
			}
			if (execute) {
				doExecute(ctx);
			} else {
				noNeedToExecute(ctx, conservativeHelper);
			}
		}
		else { // Guard failed or task is disabled
			skip();			
		}
	}

	/**
	 * @param ctx
	 * @param conservativeHelper
	 * @return 0 ignore, 1 must execute, -1 must not execute
	 */
	protected int shouldExecuteBasedOnOutput(IModelFlowContext ctx, ConservativeExecutionHelper conservativeHelper) {
		// Determine which outputs have changed
		boolean outputsChanged = conservativeHelper.haveOutputPropertiesChanged() 
				|| conservativeHelper.haveOutputModelsChanged();
		if (ctx.isInteractive()) {
			if (outputsChanged) {
				// List them and prompt
				String msg = String.format("The outputs of task %s have been modified from the previous execution.", getTaskDefinition().getName());
				String instructions = "Would you like to discard these changes and continue with the execution?\n"
						+ "1 - discard changes and continue with execution\n"
						+ "0 - only execute if inputs have changed\n"
						+ "-1 - keep changes and abort execution";
				List<Integer> validOptions = Arrays.asList(-1, 0, 1);
				Integer selected = 2;
				while(!validOptions.contains(selected)) {
					selected = ctx.getUserInput().promptInteger(msg + "\n\n" + instructions, 0);	
				}					
			} 
			return 0;
		} else {
			// We should not execute if outputs have changed since they are protected, if they are not we execute to restore consistency
			if (ctx.isProtectResources()) {
				return outputsChanged ? -1 : 0; 
				
			} else {
				return outputsChanged ? 1 : 0;
			}
		}
	}

	/**
	 * Actions taken when the task does not need to execute
	 * @param ctx 	the ModelFlow context
	 * @param incremental
	 */
	protected void noNeedToExecute(IModelFlowContext ctx, ConservativeExecutionHelper incremental) {
		// -- SKIPPING --
		skip();
		
		// Copy Execution Trace 
		incremental.copyFromPrevious();
		
		// End-To-End Traces
		processEndToEndTraces(ctx); // Do nothing for the time being
		
		// Check if tasks depend on my execution results
		/*if (new DependencyGraphHelper(ctx.getDependencyGraph()).hasDerivedOutputDependencies(this)) {
			ExecutionTrace trace = ctx.getExecutionTrace();
			EList<TaskExecution> tasks = trace.getExecutions().get(-1).getTasks();
			tasks.stream().filter(t->t.getTask().getName().equals(this.getName())).map(TaskExecution::getOutputProperties).;
		}*/
		
		//Safely dispose no longer used models
		safelyDispose(ctx);
		
		ctx.getFrameStack().put(
			Variable.createReadOnlyVariable(getName(), this)
		);
	}

	/**
	 * 
	 * @param ctx
	 * @param manager
	 * @param pManager
	 * @throws MFRuntimeException
	 */
	protected void doExecute(IModelFlowContext ctx)
			throws MFRuntimeException {
		
		ResourceManager manager = ctx.getResourceManager(); 
		TaskParamManager pManager = ctx.getParamManager();
		/*
		 * TODO if any derived outputs are contributed, 
		 * then either check its stamp has not changed or 
		 * re-execute  
		 */
		
		// Register inputs in execution trace
		ctx.getProfiler().start(IMeasurable.Stage.PROCESS_INPUTS, this, ctx);
		pManager.processInputs(this, ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_INPUTS, this, ctx);

		// Assign Models Before Execution
		ctx.getProfiler().start(IMeasurable.Stage.PROCESS_MODELS_BEFORE_EXECUTION, this, ctx);
		manager.processResourcesBeforeExecution(this, ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_MODELS_BEFORE_EXECUTION, this, ctx);

		
		setState(TaskState.RESOLVED);
		
		// -- EXECUTING --
		
		// Execute 
		setState(TaskState.EXECUTING);
		ctx.getProfiler().start(IMeasurable.Stage.EXECUTION, this, ctx);
		this.taskInstance.execute(ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.EXECUTION, this, ctx);
		setState(TaskState.EXECUTED);

		ctx.getFrameStack().put(
			Variable.createReadOnlyVariable(getName(), taskInstance)
		);
		
		// Cleanup if necessary 
		ctx.getProfiler().start(IMeasurable.Stage.CLEANUP, this, ctx);
		this.taskInstance.afterExecute();
		ctx.getProfiler().stop(IMeasurable.Stage.CLEANUP, this, ctx);
		
		// -- POST PROCESSING -- 
		
		// Record outputs in execution trace
		ctx.getProfiler().start(IMeasurable.Stage.PROCESS_OUTPUTS, this, ctx);
		pManager.processOutputs(this, ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_OUTPUTS, this, ctx);

		// Traces
		ctx.getProfiler().start(IMeasurable.Stage.END_TO_END_TRACES, this, ctx);
		processEndToEndTraces(ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.END_TO_END_TRACES, this, ctx);
		
		// Process Models After Execution
		ctx.getProfiler().start(IMeasurable.Stage.PROCESS_MODELS_AFTER_EXECUTION, this, ctx);
		manager.processResourcesAfterExecution(this, ctx);
		ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_MODELS_AFTER_EXECUTION, this, ctx);
	}

	protected void safelyDispose(IModelFlowContext ctx) {
		new DependencyGraphHelper(ctx.getDependencyGraph()).getResourceNodes(this).stream()
			.filter(r->r instanceof IModelResourceNode)
			.forEach(r -> {
				IModelResourceNode resource = (IModelResourceNode)r;
				boolean finalUse = ctx.getExecutionGraph().isLastUseOf(resource, this, ctx.getDependencyGraph());
				if (finalUse) {
					/** Dispose resource */
					LOG.debug("Disposing {}", resource.getName());
					try {
						Optional<IResourceInstance<?>> optional = ctx.getTaskRepository().getResourceRepository().get(resource);
						optional.ifPresent(res -> {
							if (res.isLoaded()) {
								/*
								 * TODO Ensure this is the correct behaviour of safely dispose,
								 * Maybe also remove from repository
								 */
								res.dispose();
							}
						});
					} catch (MFRuntimeException e) {
						throw new IllegalStateException(e);
					}
				} 
			});
	}
	
	public boolean shouldExecute(IModelFlowContext ctx) throws MFRuntimeException {
		return this.taskDefintion.getEnabled() && isGuardSatisfied(ctx);
	}

	public void skip() {
		setState(TaskState.SKIPPED);
		LOG.info("Skipping {}", taskDefintion.getName());
	}

	protected void processEndToEndTraces(IModelFlowContext ctx) {
		boolean endToEndTracing = ctx.isEndToEndTracing();
		boolean traceable = this.taskDefintion.getTraceable();
		if (endToEndTracing && traceable) {
			if (!getState().isSkpped()) {
				// Check if task produced traces
				this.taskInstance.getTrace().ifPresent(traces -> {
					ManagementTrace trace = ctx.getManagementTrace();
					ManagementTraceUpdater traceUpdater = new ManagementTraceUpdater(trace, this.taskDefintion);
					traceUpdater.update(traces);
				});
			} else {
				// Should remain the same
			}
		}
	}
	
	protected Boolean isGuardSatisfied(IModelFlowContext ctx) throws MFRuntimeException{
  		Object guard = this.taskDefintion.getGuard();
		Boolean ok = true;
		if (guard instanceof String) {
			ok = evaluateStringGuard(ctx, guard, ok);
		} else if (guard instanceof Boolean) {
			ok = (Boolean) guard;
		} else if (guard instanceof IExecutableModuleElement) {
			IExecutableModuleElement exp = (IExecutableModuleElement) guard;
			Variable self = Variable.createReadOnlyVariable("self", taskInstance);
			ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, exp, self);
			try {				
				Object result = exp.execute(ctx);
				if (result instanceof Boolean) {
					ok = (Boolean) result;
				}
				ctx.getFrameStack().leaveLocal(exp);
			} catch (Exception e) {
				ctx.getFrameStack().leaveLocal(exp);
				throw new MFRuntimeException(String.format("Exception when evaluating guard of task %s",taskDefintion.getName()), e);
			}
		}
		return ok;
	}

	protected Boolean evaluateStringGuard(IModelFlowContext ctx, Object guard, Boolean ok)
			throws MFRuntimeException {
		String stringGuard = (String) guard;
		if (!stringGuard.trim().isEmpty()) {
			EolModule eolModule = new EolModule();
			eolModule.getContext().setFrameStack(ctx.getFrameStack());
			eolModule.getContext().getFrameStack().put(
				Variable.createReadOnlyVariable("self", taskInstance)
			);
			try {
				if (!stringGuard.contains(";") && !stringGuard.trim().startsWith("return")) {
					stringGuard = "return " + stringGuard + ";";
				}
				eolModule.parse(stringGuard);
				ok = (Boolean) eolModule.execute();
			} catch (Exception e) {
				throw new MFRuntimeException("Invalid guard", e);
			}
		}
		return ok;
	}
		
	@Override
	public synchronized TaskState getState() {
		return this.state;
	}
	
	@Override
	public String getName() {
		return this.taskDefintion.getName();
	}
	
	@Override
	public String getType() {
		return this.taskDefintion.getDefinition();
	}

	@Override
	public ITask getTaskDefinition() {
		return this.taskDefintion;
	}
	
	@Override
	public ITaskInstance getTaskInstance() {
		if (getState().hasBeenInitialised()) {			
			return this.taskInstance;
		}
		throw new IllegalStateException("Task has not been initialised yet"); 
	}

	@Override
	public void setInstance(ITaskInstance instance) {
		this.taskInstance = instance;
	}
	
	protected synchronized void setState(TaskState state){
		LOG.debug("Task {} is {}", getTaskDefinition().getName(), state.name());
		this.statusUpdater.onNext(state);
		this.state = state;
		switch (state) {
		case EXECUTED:
		case SKIPPED:
			this.statusUpdater.onComplete();
			this.completable.onComplete();
			break;
		default: 
			break;
		}

	}
	
	@Override
	public TaskInputPropertyHandler getInputParams() {
		if (this.taskInputs == null) {
			this.taskInputs = new TaskInputPropertyHandler(taskInstance);
		}
		return this.taskInputs;
	}
	
	@Override
	public TaskOutputPropertyHandler getOutputParams() {
		if (this.taskOutputs == null) {
			this.taskOutputs = new TaskOutputPropertyHandler(taskInstance);
		}
		return this.taskOutputs;
	}
	
	/** Unique Task identifiers by name */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof TaskNode && getName().equals(((TaskNode)obj).getName());
	}
	
	@Override
	public String toString() {
		return this.getName();
	}


}