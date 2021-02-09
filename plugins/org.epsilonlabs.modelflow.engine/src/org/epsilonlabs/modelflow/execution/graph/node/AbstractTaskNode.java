/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph.node;

import static org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement.TASK_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.ast.ForEachModuleElement;
import org.epsilonlabs.modelflow.dom.ast.IModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.management.param.ITaskParameterManager;
import org.epsilonlabs.modelflow.management.resource.IResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class AbstractTaskNode implements ITaskNode {
	
	private static final Logger LOG = LoggerFactory.getLogger(ITaskNode.class);

	protected ITaskInstance taskInstance;
	protected ITaskModuleElement taskDeclaration;
	protected String name;
	protected TaskState state;

	protected ITaskNode parentNode = null;
	protected Map<String, ITaskNode> subNodes = Maps.newHashMap();
	protected ConservativeExecutionHelper conservativeExecutionHelper;

	protected final CompletableSubject completable = CompletableSubject.create();
	protected final PublishSubject<TaskState> statusUpdater = PublishSubject.create();
	
	protected abstract ITaskNode createSubNode(ITaskModuleElement declaration, String name);
	
	public AbstractTaskNode(ITaskModuleElement declaration) {
		this(declaration, declaration.getName());
	}
	protected AbstractTaskNode(ITaskModuleElement declaration, String name) {
		this.taskDeclaration = declaration; 
		this.name = name;
	}
	
	@Override
	public Completable getObservable() {
		return completable;
	}
	
	@Override
	public void subscribe(IModelFlowPublisher pub){
		statusUpdater.subscribe(status -> pub.taskState(getName(), status));
	}
	
	protected synchronized void setState(TaskState state){
		if (state != null) {			
			final String stateName = state.name();
			LOG.debug("Task {} is {}", getName(), stateName);
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
	}
	
	@Override
	public synchronized TaskState getState() {
		return this.state;
	}
	
	@Override
	public ITaskInstance getTaskInstance() {
		return this.taskInstance;
	}

	public void setInstance(ITaskInstance instance) {
		this.taskInstance = instance;
	}
	
	@Override
	public Map<String, ITaskNode> getSubNodes() {
		return subNodes;
	}
	
	@Override
	public String getDefinition() {
		return taskDeclaration.getType().getName();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ITaskModuleElement getModuleElement() {
		return taskDeclaration;
	}
	
	/** Unique Task identifiers by name */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ITaskNode && getName().equals(((ITaskNode)obj).getName());
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	protected abstract boolean shouldExecuteBasedOnTrace(IModelFlowContext ctx);
	protected abstract void noNeedToExecute(IModelFlowContext ctx);
	protected abstract void safelyDispose(IModelFlowContext ctx);
	
	// NEW

	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		if (taskDeclaration != null && taskDeclaration.isGenerator() && getParentNode() == null) {
			try {
				// Evaluate forEach
				attemptForEachExecution(ctx);
			} finally {
				postFor(ctx);
			}
		} else {
			attemptIndividualExecution(ctx);
		}		
	}
	protected void postFor(IModelFlowContext ctx) {
		
	}
	protected List<IModel> getForEachModels(){
		final List<IModelCallExpression> inputs = taskDeclaration.getInputs();
		final List<IModelCallExpression> inouts = taskDeclaration.getInouts();
		return Collections.emptyList();
	}
	
	public void attemptForEachExecution(IModelFlowContext ctx) throws MFRuntimeException {
		final ForEachModuleElement forEachModuleElement = taskDeclaration.getForEach();
		try {
			ctx.getModelRepository().addModels(getForEachModels());
			forEachModuleElement.execute(ctx);
		} catch (Exception e) {
			final String msg = "Exception when evaluating forEach statement of task %s";
			final String formatted = String.format(msg, taskDeclaration.getName());
			throw new MFRuntimeException(formatted, forEachModuleElement);
		} finally {
			ctx.getModelRepository().dispose();			
		}
		final Iterator<Object> iterator = forEachModuleElement.getIterator();
		final Parameter iteratorParameter = forEachModuleElement.getIteratorParameter();
		for (int loop = 1; iterator.hasNext(); loop++) {
			try {
				final Object next = iterator.next();
				Variable selfVar = new Variable(iteratorParameter.getName(), next, iteratorParameter.getType(ctx));
				Variable loopCountVar = new Variable("loopCount", loop, EolPrimitiveType.Integer, true);
				String subTaskName = String.valueOf(loop);
				final Optional<Entry<NameExpression, ModuleElement>> optionalTaskName = taskDeclaration.getParameters().entrySet().stream().filter(p->p.getKey().getName().equals(TASK_NAME)).findAny();
				if (optionalTaskName.isPresent()) {
					Object val = optionalTaskName.get().getValue();
					if (val instanceof IExecutableModuleElement) {
						try {
							final Object result = ((IExecutableModuleElement) val).execute(ctx);
							if (result instanceof String) {
								subTaskName = (String) result;
							} 
						} catch (Exception e) {
							// Do not change the name, ignore this error for now 
						}
					}
				}
				final String taskName = taskDeclaration.getName() + "@" + subTaskName;
				Variable taskNameVar = new Variable(TASK_NAME, taskName, EolPrimitiveType.String, true);
				ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, forEachModuleElement, selfVar, loopCountVar,
						taskNameVar);
				try {
					
					final ITaskNode node = createSubNode(taskDeclaration, taskName);
					node.setParentNode(this);
					subNodes.put(taskName, node);
					if (node instanceof AbstractTaskNode) {
						ExecutionTraceUpdater updater = new ExecutionTraceUpdater(ctx.getExecutionTrace()); 
						final TaskExecution exec = updater.createTaskExecution(taskName);
						ctx.getOutputStream().printf("%n>>Executing: %s%n", taskName);
						// While we are in the local context
						((AbstractTaskNode)node).attemptIndividualExecution(ctx);
						ctx.getOutputStream().printf("%n--Status: %s%n", exec.getEndState());

					}
				} finally {
					ctx.getFrameStack().leaveLocal(forEachModuleElement);
				}
			} catch (EolRuntimeException e) {
				throw new MFRuntimeException(e);
			}
		}
	}
	

	public void attemptIndividualExecution(IModelFlowContext ctx) throws MFRuntimeException {
		if (isEnabled() && isGuardOk(ctx)) {
			
			taskInstance = ctx.getTaskRepository().create(this, ctx);
			taskInstance.validateParameters();
			
			conservativeExecutionHelper = new ConservativeExecutionHelper(this, ctx);
			
			// Assume it will execute
			boolean execute = shouldExecuteBasedOnTrace(ctx);
			if (execute) {
				doExecute(ctx);
			} else {
				noNeedToExecute(ctx);
			}
		} else { // Guard failed or task is disabled
			skip();			
		}
	}

	
	protected boolean isEnabled() {
		return taskDeclaration.isEnabled();
	}
	
	protected ExecutableBlock<Boolean> getGuard(){
		return this.taskDeclaration.getGuard();
	}
	
	@Override
	public void setParentNode(ITaskNode parent) {
		this.parentNode = parent;
	}
	
	@Override
	public ITaskNode getParentNode() {
		return parentNode;
	}
	
	// TODO Check if iterator variable from forEach is already present.
	protected boolean isGuardOk(IModelFlowContext ctx) throws MFRuntimeException {
		ExecutableBlock<Boolean> guard = getGuard();
		if (guard != null) {
			List<Variable> variables = new ArrayList<>(2);
			variables.add(Variable.createReadOnlyVariable("self", taskInstance));
			ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, guard, variables.toArray(new Variable[0]));
			try {
				return guard.execute(ctx);
			} catch (Exception e) {
				final String msg = "Exception when evaluating guard of task %s";
				final String formatted = String.format(msg, taskDeclaration.getName());
				throw new MFRuntimeException(formatted, guard);
			} finally {
				ctx.getFrameStack().leaveLocal(guard);
			}
		}
		return true;
	}
	
	
	protected void skip() {
		setState(TaskState.SKIPPED);
		LOG.info("Skipping {}", getName());
	}
	
	@Override
	public Set<String> getResourceAliases(String resourceNode) {
		final ArrayList<IModelCallExpression> list = new ArrayList<>();
		list.addAll(taskDeclaration.getInputs());
		list.addAll(taskDeclaration.getOutputs());
		list.addAll(taskDeclaration.getInouts());
		final Optional<IModelCallExpression> optional = list.stream().filter(r->r.getName().equals(resourceNode)).findAny();
		if (optional.isPresent()) {
			return optional.get().getAliases().stream().map(NameExpression::getName).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}
	
	protected void doExecute(IModelFlowContext ctx) throws MFRuntimeException {
		
		IResourceManager manager = ctx.getResourceManager(); 
		ITaskParameterManager pManager = ctx.getParamManager();
		/*
		 * TODO if any derived outputs are contributed, 
		 * then either check its stamp has not changed or 
		 * re-execute  
		 */
		
		// Register inputs in execution trace
		try {
			ctx.getProfiler().start(IMeasurable.Stage.PROCESS_INPUTS, this, ctx);
			pManager.processInputs(this, ctx);
		} finally {
			ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_INPUTS, this, ctx);
		}
		// Assign Models Before Execution
		try {
			ctx.getProfiler().start(IMeasurable.Stage.PROCESS_MODELS_BEFORE_EXECUTION, this, ctx);
			manager.processResourcesBeforeExecution(this, ctx);
		} finally {			
			ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_MODELS_BEFORE_EXECUTION, this, ctx);
		}
		setState(TaskState.RESOLVED);		
		
		// -- EXECUTING --
		// Cleanup if necessary
		taskInstance.beforeExecute();
		
		// Execute 
		try {
			setState(TaskState.EXECUTING);
			ctx.getProfiler().start(IMeasurable.Stage.ATOMIC_EXECUTION, this, ctx);
			this.taskInstance.execute(ctx);
			setState(TaskState.EXECUTED);
		} finally {
			ctx.getProfiler().stop(IMeasurable.Stage.ATOMIC_EXECUTION, this, ctx);
		}

		ctx.getFrameStack().put(
			Variable.createReadOnlyVariable(getName(), taskInstance)
		);
		
		
		// Cleanup if necessary 
		this.taskInstance.afterExecute();
		
		// -- POST PROCESSING -- 
		
		// Record outputs in execution trace
		try {
			ctx.getProfiler().start(IMeasurable.Stage.PROCESS_OUTPUTS, this, ctx);
			pManager.processOutputs(this, ctx);
		} finally {
			ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_OUTPUTS, this, ctx);
		}

		// Traces
		processManagementTraces(ctx);
		
		// Process Models After Execution
		try {
			ctx.getProfiler().start(IMeasurable.Stage.PROCESS_MODELS_AFTER_EXECUTION, this, ctx);
			manager.processResourcesAfterExecution(this, ctx);
		} finally {
			ctx.getProfiler().stop(IMeasurable.Stage.PROCESS_MODELS_AFTER_EXECUTION, this, ctx);
		}
	}
	
	protected boolean isTrace(){
		return this.taskDeclaration.isTrace();
	}
	
	protected void processManagementTraces(IModelFlowContext ctx) {
		boolean endToEndTracing = ctx.isEndToEndTracing();
		boolean traceable = isTrace();
		if (endToEndTracing && traceable) {
			if (!getState().isSkpped()) {
				// Check if task produced traces
				this.taskInstance.getTrace().ifPresent(traces -> {
					try {
						ctx.getProfiler().start(IMeasurable.Stage.END_TO_END_TRACES, this, ctx);
						ManagementTrace fullTrace = ctx.getManagementTrace();
						ManagementTraceUpdater traceUpdater = new ManagementTraceUpdater(fullTrace, getName());
						traceUpdater.update(traces);
					} finally {
						ctx.getProfiler().stop(IMeasurable.Stage.END_TO_END_TRACES, this, ctx);
					}
				});
			} else {
				// Should remain the same
			}
		}
	}
	
}
