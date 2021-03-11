/**
 * 
 */
package org.epsilonlabs.modelflow.management.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.control.ExecutionStage;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskModuleElementNode;
import org.epsilonlabs.modelflow.execution.scheduler.IScheduler;
import org.epsilonlabs.modelflow.execution.scheduler.TaskStackScheduler;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.repository.ResourceRepository;

/**
 * This class is intended to be an improvement over the ResourceManager. 
 * Analysis must be done on a task instance basis rather than the task node, as a node may have multiple task instances.
 */
public class TaskResourceManager implements IResourceManager {

	protected ITaskInstance taskInstance;
	protected String taskInstanceName;
	
	// TODO standardise
	protected TaskModuleElementNode taskNode; //ITaskNode
	protected TaskStackScheduler scheduler; //IScheduler

	protected ExecutionTraceUpdater updater;
	protected IModelFlowContext ctx;
	protected ResourceRepository resourceRepository;

	public void processResourcesBeforeExecution(ITaskNode taskNode, IModelFlowContext ctx) throws MFRuntimeException {
		new TaskResourceManager(taskNode, ctx).processResourcesBeforeExecution();
	}
	
	public void processResourcesAfterExecution(ITaskNode taskNode, IModelFlowContext ctx) throws MFRuntimeException {
		new TaskResourceManager(taskNode, ctx).processResourcesBeforeExecution();
	}

	public TaskResourceManager() {}
	
	protected TaskResourceManager(ITaskNode taskNode, IModelFlowContext ctx){
		this.taskNode = (TaskModuleElementNode) taskNode;
		this.taskInstance = taskNode.getTaskInstance();
		this.ctx = ctx;
		this.updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		this.resourceRepository = ctx.getTaskRepository().getResourceRepository();
		final IScheduler iScheduler = ctx.getScheduler();
		if (iScheduler instanceof TaskStackScheduler) {
			this.scheduler = (TaskStackScheduler) iScheduler;
		} else {
			throw new UnsupportedOperationException("Wrong scheduler");
		}
	}
	
	/** 
	 * This method identifies the models that are to be passed to the task instance before it executes. 
	 * 
	 */
	protected void processResourcesBeforeExecution() throws MFRuntimeException {
		// Prepare
		List<IModelWrapper> list = new ArrayList<>();
		final TaskExecution tExec = updater.getCurrentTaskExecution(taskInstanceName);
		tExec.getInputModels().clear();
		
		for (Entry<ResourceKind, Collection<IModelResourceNode>> entry : taskNode.getModels().entrySet()) {
			final ResourceKind kind = entry.getKey();
			for (IModelResourceNode node : entry.getValue()) {				
				handleModelResourceBeforeExecution(list, tExec, node, kind);
			}
		}
		
		// Assign
		IModelWrapper[] iModelResources = list.toArray(new IModelWrapper[0]);
		taskInstance.acceptModels(iModelResources);
	}

	protected void handleModelResourceBeforeExecution(List<IModelWrapper> list, final TaskExecution tExec, IModelResourceNode resourceNode, ResourceKind kind)
			throws MFRuntimeException {
		IModelResourceInstance<?> resourceInstance = resourceRepository.getOrCreate(resourceNode, ctx);
		taskNode.getResourceAliases(resourceNode.getName()).forEach(resourceInstance::setAlias);
		
		resourceInstance.beforeTask();
		
		// Load resource as indicated by the resource kind (in/out..)
		IModelFlowProfiler profiler = ctx.getProfiler();
		profiler.start(ExecutionStage.LOAD, resourceNode, ctx);
		IModelWrapper mRes = new ResourceLoader(kind, resourceInstance, resourceNode).load();
		profiler.stop(ExecutionStage.LOAD, resourceNode, ctx);

		// Add model to list of models for task to accept
		list.add(mRes);
		
		// If model is of used as input (input or in/out) 
		if (kind.isInout() || kind.isInput()) {					
		
			// Store input model hash in execution trace  
			final Optional<Object> loadedHash = resourceInstance.loadedHash();
			Object hash = "";
			if (loadedHash.isPresent()) {				
				hash = loadedHash.get();
			} else {
				// What do we do if no hash is provided?
			}
			ResourceSnapshot snapshot = updater.createResourceSnapshot(resourceNode, hash);
			tExec.getInputModels().add(snapshot);
			
			// Also record the current model snapshot in trace latest resources 
			updater.addResourceToLatest(snapshot);
		}
	}
	
	/** 
	 * This method is called after the task has been executed.
	 * @throws MFRuntimeException 
	 */
	protected void processResourcesAfterExecution() throws MFRuntimeException {		
		// Prepare task execution trace 
		final TaskExecution tExec = updater.getCurrentTaskExecution(taskInstanceName);
		tExec.getOutputModels().clear();
		
		for (Entry<ResourceKind, Collection<IModelResourceNode>> entry : taskNode.getModels().entrySet()) {
			final ResourceKind kind = entry.getKey();
			for (IModelResourceNode node : entry.getValue()) {				
				handleModelResourceAfterExecution(tExec, node, kind);
			}
		}
	}

	protected void handleModelResourceAfterExecution(TaskExecution tracedExecution, IAbstractResourceNode node,  ResourceKind kind) throws MFRuntimeException {
		IModelResourceNode modelNode = (IModelResourceNode) node;
		
		// Get resource from resource repository 
		Optional<IModelResourceInstance<?>> opResource = resourceRepository.get(modelNode);
		if (opResource.isPresent()) {							
			IModelResourceInstance<?> resource = opResource.get();

			// If output or in/out
			if (kind.isOutput() || kind.isInout()) {
					
					// Save output model between tasks. This is ignored for readOnly ones 
					resource.save();
					
					// Store output model hash in execution trace 
					final Optional<Object> loadedHash = resource.loadedHash();
					Object hash = "";
					if (loadedHash.isPresent()) {
						hash = loadedHash.get();
					} else {
						// TODO Whad do we do if not available?
					}
					ResourceSnapshot snapshot = updater.createResourceSnapshot(modelNode, hash);
					tracedExecution.getOutputModels().add(snapshot);
					// Also record the current model snapshot in trace latest resources 
					updater.addResourceToLatest(snapshot);					
			}
			// Check if this task uses the resource for its last time 
			boolean finalUse = ctx.getScheduler().isLastUseOf(modelNode.getName(), taskInstanceName);
			if (finalUse) {
				// Dispose resource 
				IModelFlowProfiler profiler = ctx.getProfiler();
				profiler.start(ExecutionStage.DISPOSE, modelNode, ctx);
				resource.dispose();
				profiler.stop(ExecutionStage.DISPOSE, modelNode, ctx);
			}
			resource.afterTask();
		} else {
			throw new IllegalStateException("resource " + modelNode.getName() + " should have been created previously");
		}
	}
	
}

