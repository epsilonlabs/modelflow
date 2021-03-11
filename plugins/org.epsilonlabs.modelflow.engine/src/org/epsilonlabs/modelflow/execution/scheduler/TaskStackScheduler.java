package org.epsilonlabs.modelflow.execution.scheduler;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.util.Multimap;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.factory.IInstanceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ModuleElementTaskFactory;
import org.epsilonlabs.modelflow.dom.ast.IModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.TaskDependencyExpression;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.ExecutionStage;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.ModuleElementDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskModuleElementNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;


public class TaskStackScheduler extends AbstractScheduler {

	protected Set<ITaskModuleElement> dispatchedTasks;
	protected Set<TaskModuleElementNode> dispatchedNodes;
	protected Deque<ITaskModuleElement> pending;
	protected Collection<ITaskModuleElement> tasks;
	protected Multimap<String, ITaskModuleElement> dispatchedTasksPerModel;
	
	protected IDependencyGraph dg;
	
	public TaskStackScheduler(){
		this.dg = new ModuleElementDependencyGraph();
	}
	
	@Override
	public WorkflowExecution execute(IModelFlowContext ctx) throws MFExecutionException {
		updater = new ExecutionTraceUpdater(ctx.getExecutionTrace()); 
		try {
			ctx.getProfiler().start(ExecutionStage.EXECUTION_PROCESS, null, ctx);
			try {
				executeAll(ctx);
			} catch (MFRuntimeException e) {
				throw new MFExecutionException(e);
			}
			return updater.getCurrentWorkflowExecution();
		} finally {
			ctx.getProfiler().stop(ExecutionStage.EXECUTION_PROCESS, null, ctx);
		}
	}
	
	@Override
	public WorkflowExecution execute(String target, IModelFlowContext ctx) throws MFExecutionException {
		updater = new ExecutionTraceUpdater(ctx.getExecutionTrace()); 
		try {
			ctx.getProfiler().start(ExecutionStage.EXECUTION_PROCESS, null, ctx);
			try {
				executeTarget(target, ctx);
			} catch (MFRuntimeException e) {
				throw new MFExecutionException(e);
			}
			return updater.getCurrentWorkflowExecution();
		} finally {
			ctx.getProfiler().stop(ExecutionStage.EXECUTION_PROCESS, null, ctx);
		}
	}
	
	public Collection<ITaskModuleElement> getDispatchedTasksPerModel(String model) {
		return dispatchedTasksPerModel.get(model);
	}
	
	public void executeAll(IModelFlowContext ctx) throws MFRuntimeException {
		setup(ctx);

		tasks = ctx.getModule().getCompilationContext().getTaskDeclarations();
		tasks.stream().findFirst().ifPresent(pending::addFirst);

		while (dispatchedTasks.size() < tasks.size()) {
			while (!pending.isEmpty()) {
				attemptDispatch(ctx);
			}
			Optional<ITaskModuleElement> nextTask = tasks.stream().filter(t -> !dispatchedTasks.contains(t)).findAny();
			nextTask.ifPresent(pending::addFirst);
		}
	}	

	public void executeTarget(String target, IModelFlowContext ctx) throws MFRuntimeException {
		setup(ctx);

		tasks = ctx.getModule().getCompilationContext().getTaskDeclarations();
		Optional<ITaskModuleElement> optionalTargetTask = tasks.stream()
				.filter(t -> t.getNameExpression().getName().equals(target)).findFirst();
		if (optionalTargetTask.isPresent()) {
			pending.addFirst(optionalTargetTask.get());
		}
		while (!pending.isEmpty()) {
			attemptDispatch(ctx);
		}
	}
	
	protected void setup(IModelFlowContext ctx) {
		dispatchedTasks = new HashSet<>();
		dispatchedNodes = new HashSet<>();
		pending = new ArrayDeque<>();
		dispatchedTasksPerModel = new Multimap<>();
		tasks = ctx.getModule().getCompilationContext().getTaskDeclarations();
	}
	
	protected void attemptDispatch(IModelFlowContext ctx) throws MFRuntimeException {
		ITaskModuleElement currentTask = pending.peekFirst();
		AtomicBoolean canExecute = new AtomicBoolean(true);

		checkDependencies(currentTask, canExecute);
		modelCheck(currentTask, canExecute, currentTask.getInputs(), ITaskModuleElement::getOutputs);
		modelCheck(currentTask, canExecute, currentTask.getInputs(), ITaskModuleElement::getInouts);
		modelCheck(currentTask, canExecute, currentTask.getInouts(), ITaskModuleElement::getOutputs);

		if (canExecute.get()) {
			dispatch(ctx);
			
		}
	}

	protected void dispatch(IModelFlowContext ctx) throws MFRuntimeException {
		ITaskModuleElement currentTask;
		currentTask = pending.removeFirst();
		System.out.println("Dispatching " + currentTask.getNameExpression().getName());
		dispatchedTasks.add(currentTask);
		final TaskModuleElementNode node = new TaskModuleElementNode(currentTask);
		dispatchedNodes.add(node);
		super.executeTask(ctx, node);
	}

	protected void checkDependencies(ITaskModuleElement currentTask, AtomicBoolean canExecute)
			throws MFRuntimeException {
		if (!currentTask.getDependsOn().isEmpty()) {
			for (TaskDependencyExpression dep : currentTask.getDependsOn()) {
				final Optional<ITaskModuleElement> findAny = tasks.stream()
						.filter(t -> t.getNameExpression().getName().equals(dep.getTarget().getName())).findAny();
				if (findAny.isPresent()) {
					final ITaskModuleElement o = findAny.get();
					if (!dispatchedTasks.contains(o)) {
						canExecute.set(false);
						addToPendingStack(o);
					}
				} else {
					System.out.println("Dependency not found!");
				}
			}
		}
	}

	protected void modelCheck(ITaskModuleElement currentTask, AtomicBoolean canExecute,
			Collection<IModelCallExpression> source,
			Function<ITaskModuleElement, Collection<IModelCallExpression>> targets) throws MFRuntimeException {
		if (!source.isEmpty()) {
			for (IModelCallExpression mIn : currentTask.getInputs()) {
				List<ITaskModuleElement> before = tasks.stream()
						.filter(t -> targets.apply(t).stream()
								.anyMatch(o -> o.getModel().getName().equals(mIn.getModel().getName())))
						.collect(Collectors.toList());
				for (ITaskModuleElement b : before) {
					if (!dispatchedTasks.contains(b)) {
						canExecute.set(false);
						addToPendingStack(b);
					}
				}
			}
		}
	}

	protected void addToPendingStack(ITaskModuleElement d) {
		if (!pending.contains(d)) {
			pending.addFirst(d);
		} else {
			pending.remove(d);
			pending.addFirst(d);
		}
	}

	protected void updateMapRegistry(ITaskModuleElement task) {
		task.getInputs().stream().map(m->m.getModel().getName()).forEach(m -> dispatchedTasksPerModel.put(m, task));
		task.getOutputs().stream().map(m->m.getModel().getName()).forEach(m -> dispatchedTasksPerModel.put(m, task));
		task.getInouts().stream().map(m->m.getModel().getName()).forEach(m -> dispatchedTasksPerModel.put(m, task));
	}

	@Override
	public IExecutionGraph getExecutionGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDependencyGraph getDependencyGraph() {
		return dg;
	}
	
	@Override
	public void build(IModelFlowContext context) throws Exception {
		this.dg.build(context);
	}	
	

	/**
	 * @return the dispatchedNodes
	 */
	public Set<ITaskNode> getDispatchedNodes() {
		return dispatchedNodes.stream().map(ITaskNode.class::cast).collect(Collectors.toSet());
	}
	
	/**
	 * @return the dispatchedNodes
	 */
	public Map<String, ITaskNode> getAllDispatchedNodes() {
		final Map<String, ITaskNode> all = dispatchedNodes.stream()
				.map(ITaskNode::getSubNodes)
				.map(Map::entrySet)
				.flatMap(Set::stream)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		dispatchedNodes.stream().forEach(e -> all.put(e.getName(), e));
		return all;
	}
	/**
	 * @return the dispatchedTasks
	 */
	public Set<ITaskModuleElement> getDispatchedTasks() {
		return dispatchedTasks;
	}
	/**
	 * @return the pending
	 */
	public Deque<ITaskModuleElement> getPending() {
		return pending;
	}
	
	/**
	 * @return the tasks
	 */
	public Collection<ITaskModuleElement> getTasks() {
		return tasks;
	}

	@Override
	public IInstanceFactory<ITaskInstance, ITaskNode> getTaskInstanceFactory() {
		return new ModuleElementTaskFactory();
	}

	@Override
	public IInstanceFactory<IModelResourceInstance<?>, IModelResourceNode> getModelInstanceFactory() {
		return null ; //FIXME
	}
}
