/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.IPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.management.param.TaskInputPropertyHandler;
import org.epsilonlabs.modelflow.management.param.TaskOutputPropertyHandler;
import org.epsilonlabs.modelflow.management.param.TaskParamManager;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.epsilonlabs.modelflow.management.resource.ResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.subjects.PublishSubject;

public class TaskNode implements ITaskNode { 

	private static final Logger LOG = LoggerFactory.getLogger(TaskNode.class);
	
	protected Task taskDefintion;
	protected ITask taskInstance;
	protected IDependencyGraph dependencyGraph;
	protected TaskState state;
	protected PublishSubject<TaskState> statusUpdater = PublishSubject.create();

	protected Collection<ImmutablePair<ResourceKind, IAbstractResourceNode>> dependentResources;
	protected Collection<ITaskNode> dependentTasks;
	protected Collection<IAbstractResourceNode> inouts;
	protected Collection<IAbstractResourceNode> outputs;
	protected Collection<IAbstractResourceNode> inputs;

	private TaskInputPropertyHandler taskInputs;
	private TaskOutputPropertyHandler taskOutputs;

	
	public TaskNode(Task task, IDependencyGraph graph) {
		this.taskDefintion = task;
		setState(TaskState.CREATED);
		this.dependencyGraph = graph;
	}
	
	@Override
	public void subscribe(IPublisher pub){
		statusUpdater.subscribe(status -> pub.taskState(this.taskDefintion.getName(), status));
	}
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		// Locate Utilities
		ResourceManager manager = ctx.getResourceManager(); 
		TaskParamManager pManager = ctx.getParamManager();
		
		IncrementalTaskNodeChecker incremental = new IncrementalTaskNodeChecker(this, ctx);
		
		// Instantiate and configure basic properties
		this.taskInstance = ctx.getTaskRepository().create(this, ctx);
		setState(TaskState.INITIALIZED);
		

		/*
		 * Check if it should execute 
		 * i.e. task is enabled && guard is OK 
		 */
		if (shouldExecute(ctx)) {
			
			// Prepare for execution 
			this.taskInstance.validateParameters();
			
			boolean execute = true;
			// If input properties changed, continue 
			boolean outputsChanged = incremental.haveOutputPropertiesChanged() || incremental.haveOutputModelsChanged();
			
			boolean derivedOutputDependencies = new DependencyGraphHelper(this.dependencyGraph).hasDerivedOutputDependencies(this);
			if (derivedOutputDependencies || incremental.haveInputPropertiesChanged() || incremental.haveInputModelsChanged() || outputsChanged) {
				// If output models and properties have not been externally changed continue 
				if (outputsChanged) {
					execute = decideIfShouldExecute(ctx, execute);
				}
				if (execute) {
					// Go ahead with execution
					doExecute(ctx, manager, pManager);
				}
			} else {
				execute = false;
			}
			// Past Execution was the same 
			if (!execute){
				noNeedToExecute(ctx, incremental);
			}
		} 
		// Guard failed or task disabled  
		else {
			// -- SKIPPING --
			skip();			
		}
	}

	protected boolean decideIfShouldExecute(IModelFlowContext ctx, boolean execute) {
		if (ctx.isInteractive()) {
			String msg = String.format("The outputs of task %s have been modified%n", getTaskDefinition().getName());
			ctx.getOutputStream().print(msg);
			
			String result = null;
			while ( ! ("N".equalsIgnoreCase(result) || "Y".equalsIgnoreCase(result)) ) {							
				result = ctx.getUserInput().prompt("Would you like to discard these changes and continue with the execution? %n"
						+ "Y - discard changes and continue %n"
						+ "N - keep changes and abort execution", 
						null);
			}
			LOG.debug("Continuing with execution");
			execute = "Y".equalsIgnoreCase(result);
			
		} else {
			if (ctx.isProtectResources()){
				execute = false;
				LOG.debug("Aborting execution of {}", getTaskDefinition().getName());
			} else {
				LOG.debug("Going ahead with execution of {}", getTaskDefinition().getName());
			}
		}
		return execute;
	}

	/**
	 * Actions taken when the task does not need to execute
	 * @param ctx 	the ModelFlow context
	 * @param incremental
	 */
	protected void noNeedToExecute(IModelFlowContext ctx, IncrementalTaskNodeChecker incremental) {
		// -- SKIPPING --
		skip();
		
		// Copy Execution Trace 
		incremental.copyFromPrevious();
		
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
	protected void doExecute(IModelFlowContext ctx, ResourceManager manager, TaskParamManager pManager)
			throws MFRuntimeException {
		/*
		 * TODO if any derived outputs are contributed, 
		 * then either check its stamp has not changed or 
		 * re-execute  
		 */
			
		// Register inputs in execution trace
		pManager.processInputs(this, ctx);
		
		// Assign Models Before Execution
		manager.processResourcesBeforeExecution(this, ctx);
		
		setState(TaskState.RESOLVED);
		
		// -- EXECUTING --
		
		// Execute 
		setState(TaskState.EXECUTING);
		this.taskInstance.execute(ctx);
		setState(TaskState.EXECUTED);
		
		ctx.getFrameStack().put(
			Variable.createReadOnlyVariable(getName(), taskInstance)
		);
		
		// Cleanup if necessary 
		this.taskInstance.afterExecute();
		
		// -- POST PROCESSING -- 
		
		// Record outputs in execution trace
		pManager.processOutputs(this, ctx);
		
		// Traces
		processTraces(ctx);
		
		// Process Models After Execution
		manager.processResourcesAfterExecution(this, ctx);
	}

	protected void safelyDispose(IModelFlowContext ctx) {
		new DependencyGraphHelper(this.dependencyGraph).getResourceNodes(this).stream()
			.filter(r->r instanceof IModelResourceNode)
			.forEach(r -> {
				IModelResourceNode resource = (IModelResourceNode)r;
				boolean finalUse = ctx.getExecutionGraph().isLastUseOf(resource, this, ctx.getDependencyGraph());
				if (finalUse) {
					/** Dispose resource */
					LOG.debug("Disposing {}", resource.getName());
					try {
						Optional<IResource<?>> optional = ctx.getTaskRepository().getResourceRepository().get(resource);
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

	protected void processTraces(IModelFlowContext ctx) {
		boolean endToEndTracing = ctx.isEndToEndTracing();
		boolean traceable = this.taskDefintion.getTraceable();
		if (endToEndTracing && traceable && !getState().isSkpped()) {
			// Check if task produced traces
			this.taskInstance.getTrace().ifPresent(traces -> {
				ManagementTrace trace = ctx.getManagementTrace();
				ManagementTraceUpdater traceUpdater = new ManagementTraceUpdater(trace, this.taskDefintion);
				traceUpdater.update(traces);
			});
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
	public Task getTaskDefinition() {
		return this.taskDefintion;
	}
	
	@Override
	public ITask getTaskInstance() {
		if (getState().hasBeenInitialised()) {			
			return this.taskInstance;
		}
		throw new IllegalStateException("Task has not been initialised yet"); 
	}

	@Override
	public void setInstance(ITask instance) {
		this.taskInstance = instance;
	}
	
	protected synchronized void setState(TaskState state){
		this.statusUpdater.onNext(state);
		this.state = state;
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