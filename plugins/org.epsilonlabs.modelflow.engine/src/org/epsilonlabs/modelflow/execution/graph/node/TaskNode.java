/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.emf.RuleUtil;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.ExecutionStage;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.epsilonlabs.modelflow.management.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskNode extends AbstractTaskNode { 

	private static final Logger LOG = LoggerFactory.getLogger(TaskNode.class);
	
	protected ITask taskDefintion;
	
	public TaskNode(ITask task) {
		super((ITaskModuleElement)task.getModuleElement(), task.getName());
		this.taskDefintion = task;
	}
	
	public TaskNode(ITaskModuleElement task, String name) {
		super(task, name);
	}
	
	public ITask getTaskDefintion() {
		return taskDefintion;
	}
	
	public void setTaskDefintion(ITask taskDefintion) {
		this.taskDefintion = taskDefintion;
	}
	
	@Override
	protected ITaskNode createSubNode(ITaskModuleElement declaration, String name) {
		return new TaskNode(declaration, name);
	}
	
	protected List<IModel> getForEachModels(IModelFlowContext ctx) throws MFRuntimeException{
		List<IModel> models = new ArrayList<>();
		final IDependencyGraph dg = ctx.getScheduler().getDependencyGraph();
		// For all the resources connected to this task node
		for (IAbstractResourceNode entry : dg.getResourceNodes(this)) {
			IAbstractResourceNode resNode = entry;
			ResourceKind kind = dg.getResourceKindForTask(resNode, this);
			// If of type ModelResource 
			if (resNode instanceof IModelResourceNode && (kind.isInout() || kind.isInput())) {
				IModelResourceNode rNode = (IModelResourceNode) resNode;
				IModelResourceInstance<?> r = ctx.getTaskRepository().getResourceRepository().getOrCreate(rNode, ctx);
				ctx.getScheduler().getDependencyGraph().getAliasFor(rNode, this).forEach(r::setAlias);
				if (r.get() instanceof IModel) {					
					new ResourceLoader(kind,r, rNode).load();
					// Add model to list of models for task to accept
					models.add((IModel) r.get());
				}
			}
		}
		return models;
	}
	
	protected void resolveTask(IModelFlowContext ctx) throws MFRuntimeException {		
		if (parentNode instanceof TaskNode) {
			final ITask parentTask = ((TaskNode)parentNode).getTaskDefintion();
			EcoreUtil.Copier copier = new EcoreUtil.Copier(); 
			final ITask copy = (ITask) copier.copy(parentTask); //Improve copy
			copier.copyReferences();
			copy.setName(getName());
			taskDefintion = copy;
			((IWorkflow)parentTask.eContainer()).getTasks().add(taskDefintion);
			
			try {
				RuleUtil.setupConfigurableParameters(ctx, copy, taskDeclaration);
			} catch (Exception e) {
				throw new MFRuntimeException(e);
			}
		}
	}
	
	@Override
	protected void postFor(IModelFlowContext ctx) {
		if (parentNode instanceof TaskNode) {
			final ITask task = ((TaskNode)parentNode).getTaskDefintion();
			final EList<ITask> taskList = ((IWorkflow) task.eContainer()).getTasks();
			final Iterator<ITask> taskIterator = taskList.iterator();
			List<ITask> toRemove = new ArrayList<>();
			while (taskIterator.hasNext()) {
				final ITask next = taskIterator.next();
				if (next.getName().equals(task.getName())) {
					toRemove.add(next);
				}
			}
			toRemove.forEach(taskList::remove);
		}
	}
	
	@Override
	protected ExecutableBlock<Boolean> getGuard() {
		if (this.taskDeclaration != null) {
			return super.getGuard();
		} else if (this.taskDefintion != null) {
			// TODO fixme
		}
		return null;
	}

	@Override
	protected boolean isEnabled() {
		return (taskDeclaration != null && taskDeclaration.isEnabled()) || (taskDefintion != null && taskDefintion.getEnabled());
	}
	
	@Override
	public String getDefinition() {
		if (taskDeclaration != null) {
			return super.getDefinition();
		} else if (taskDefintion != null) {
			return taskDefintion.getDefinition();
		}
		throw new IllegalStateException("Should build the workflow from model or by parsing the program");
	}
	
	/**
	 * @param ctx
	 * @param conservativeExecutionHelper
	 * @return
	 */
	protected boolean shouldExecuteBasedOnTrace(IModelFlowContext ctx) {
		boolean execute = true;
		
		if (conservativeExecutionHelper.hasBeenPreviouslyExecuted()) { // There is a previous execution trace
			
			if ( !(taskDeclaration.isAlwaysExecute() || taskInstance.isAlwaysExecute()) 
					/*&& ! (dependencyGraphHelper.hasDerivedOutputDependencies(parentNode)) */)
			{
				boolean inputsChanged = conservativeExecutionHelper.haveInputPropertiesChanged() 
						|| conservativeExecutionHelper.haveInputModelsChanged();
				if (ctx.isProtectResources()) {					
					int shouldExecuteOutput = shouldExecuteBasedOnOutputs(ctx);
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
				} else {
					return inputsChanged;
				}
			}
		}
		return execute;
	}

	/**
	 * @param ctx
	 * @param conservativeExecutionHelper
	 * @return 0 ignore, 1 must execute, -1 must not execute
	 */
	protected int shouldExecuteBasedOnOutputs(IModelFlowContext ctx) {
		// Determine which outputs have changed
		boolean outputsChanged = conservativeExecutionHelper.haveOutputPropertiesChanged() 
				|| conservativeExecutionHelper.haveOutputModelsChanged();
		if (ctx.isInteractive()) {
			if (outputsChanged) {
				// List them and prompt
				String msg = String.format("The outputs of task %s have been modified from the previous execution.", getName());
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
	 * @param conservativeExecutionHelper
	 */
	protected void noNeedToExecute(IModelFlowContext ctx) {
		// -- SKIPPING --
		skip();
		
		// Copy Execution Trace 
		conservativeExecutionHelper.copyFromPrevious();
		
		// End-To-End Traces
		processManagementTraces(ctx); // Do nothing for the time being
		
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

	protected void safelyDispose(IModelFlowContext ctx) {
		if (parentNode == null) {
			new DependencyGraphHelper(ctx.getScheduler().getDependencyGraph()).getResourceNodes(this).stream()
			.filter(r->r instanceof IModelResourceNode)
			.forEach(r -> {
				IModelResourceNode resource = (IModelResourceNode)r;
				boolean finalUse = ctx.getScheduler().isLastUseOf(r.getName(), getName());
				if (finalUse) {
					/** Dispose resource */
					LOG.debug("Disposing {}", resource.getName());
					try {
						Optional<IModelResourceInstance<?>> optional = ctx.getTaskRepository().getResourceRepository().get(resource);
						optional.ifPresent(res -> {
							if (res.isLoaded()) {
								/*
								 * TODO Ensure this is the correct behaviour of safely dispose,
								 * Maybe also remove from repository
								 */
								LOG.debug("Disposing {}", res.getName());
								IModelFlowProfiler profiler = ctx.getProfiler();
								profiler.start(ExecutionStage.DISPOSE, r, ctx);
								res.dispose();
								profiler.stop(ExecutionStage.DISPOSE, r, ctx);
							}
						});
					} catch (MFRuntimeException e) {
						throw new IllegalStateException(e);
					}
				} 
			});
		}
	}
	
	@Override
	protected boolean isTrace(){
		if (this.taskDeclaration != null) {
			return super.isTrace();
		} else {
			return this.taskDefintion.getTraceable();
		}
	}
	
	
	
	@Override
	public Set<String> getResourceAliases(String resourceNode) {
		if (taskDeclaration != null) {
			return super.getResourceAliases(resourceNode);
		} else {			
			final ArrayList<IResourceReference> list = new ArrayList<>();
			list.addAll(taskDefintion.getConsumes());
			list.addAll(taskDefintion.getProduces());
			list.addAll(taskDefintion.getModifies());
			final Optional<IResourceReference> optional = list.stream().filter(r->r.getResource().getName().equals(resourceNode)).findAny();
			if (optional.isPresent()) {
				return optional.get().getAliases().stream().collect(Collectors.toSet());
			}
			return Collections.emptySet();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(taskDefintion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof TaskNode)) {
			return false;
		}
		TaskNode other = (TaskNode) obj;
		return Objects.equals(taskDefintion, other.taskDefintion);
	}
	
	
	
}