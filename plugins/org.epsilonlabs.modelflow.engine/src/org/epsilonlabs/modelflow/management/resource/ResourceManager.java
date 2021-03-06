/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IDerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceManager implements IResourceManager {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceManager.class);	

	/** 
	 * This method is called when the node has been marked ready for execution.
	 */
	@Override
	public void processResourcesBeforeExecution(ITaskNode tNode, IModelFlowContext ctx) throws MFRuntimeException {
		// Create empty list of models for task to accept 
		List<IModelWrapper> list = new ArrayList<>();
		
		ExecutionTraceUpdater updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		// Prepare task execution trace
		final TaskExecution tExec = updater.getCurrentTaskExecution(tNode);
		tExec.getInputModels().clear();

		DependencyGraphHelper helper = new DependencyGraphHelper(ctx.getDependencyGraph());
		// For all the resources connected to this task node
		for (IAbstractResourceNode entry : helper.getResourceNodes(tNode)) {
			IAbstractResourceNode rNode = entry;
			ResourceKind kind = helper.getResourceKindForTask(rNode, tNode);
			// If of type ModelResource 
			if (rNode instanceof IModelResourceNode) {
				handleModelResourceBeforeExecution(tNode, ctx, list, updater, tExec, (IModelResourceNode) rNode, kind);
			} 
			// If of type DerivedResource 
			else if (rNode instanceof DerivedResourceNode) {
				handleDerivedResourceBeforeExecution(ctx, list, (DerivedResourceNode) rNode, kind);
			}
		}
		
		// List to Array 
		IModelWrapper[] iModelResources = list.toArray(new IModelWrapper[0]);
		// Assign models to task for review 
		tNode.getTaskInstance().acceptModels(iModelResources);
	}

	protected void handleModelResourceBeforeExecution(ITaskNode tNode, IModelFlowContext ctx, List<IModelWrapper> list,
			ExecutionTraceUpdater updater, final TaskExecution tExec, IModelResourceNode rNode, ResourceKind kind)
			throws MFRuntimeException {
		// Get (if already created) or create resource
		IModelResourceInstance<?> r = ctx.getTaskRepository().getResourceRepository().getOrCreate(rNode, ctx);
		// FIXME move alias logic to IModelWrapper
		// Add edge aliases
		new DependencyGraphHelper(ctx.getDependencyGraph()).getAliasFor(rNode, tNode).forEach(r::setAlias);
		// TODO... Add resource default alias
		// Call Resource Type Before method
		r.beforeTask();
		
		// Load resource as indicated by the resource kind (in/out..)
		IModelFlowProfiler profiler = ctx.getProfiler();
		profiler.start(IMeasurable.Stage.LOAD, rNode, ctx);
		IModelWrapper mRes = new ResourceLoader(kind,r, rNode).load(tNode);
		profiler.stop(IMeasurable.Stage.LOAD, rNode, ctx);

		// Add model to list of models for task to accept
		list.add(mRes);
		// If model is of used as input (input or in/out) 
		if (kind.isInout() || kind.isInput()) {					
			// Store input model hash in execution trace  
			ResourceSnapshot snapshot = updater.createResourceSnapshot(rNode.getModelElement(), r.loadedHash().get());
			tExec.getInputModels().add(snapshot);
			// Also record the current model snapshot in trace latest resources 
			updater.addResourceToLatest(snapshot);
		}
	}

	protected void handleDerivedResourceBeforeExecution(IModelFlowContext ctx, List<IModelWrapper> list,
			IDerivedResourceNode derRes, ResourceKind kind) {
		// Exclusively input 
		if (kind.isInput()) {
			// Locate its value from previous executions 
			Object derived = ctx.getTaskRepository().getResourceRepository().getDerived(derRes);
			// Wrap as model resource
			IModelWrapper mRes = new ModelWrapper(kind,derRes.getModelElement(), derived);
			// Add to list of models for task to accept
			list.add(mRes);
		}
	}
	
	/** 
	 * This method is called after the task has been executed.
	 * @throws MFRuntimeException 
	 */
	@Override
	public void processResourcesAfterExecution(ITaskNode tNode, IModelFlowContext ctx) throws MFRuntimeException {
		
		// Prepare task execution trace 
		ExecutionTraceUpdater updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		final TaskExecution tExec = updater.getCurrentTaskExecution(tNode);
		tExec.getOutputModels().clear();
		
		// For all the resources connected to this task node 
		DependencyGraphHelper helper = new DependencyGraphHelper(ctx.getDependencyGraph());
		for (IAbstractResourceNode e : helper.getResourceNodes(tNode)) {
			ResourceKind kind = helper.getResourceKindForTask(e, tNode);
			IAbstractResourceNode value = e;
			// If of type ModelResourceNode 
			if (value instanceof IModelResourceNode) {
				handleModelResourceAfterExecution(tNode, ctx, updater, tExec, kind, value);
			}
			// If type DerivedResource
			else if (value instanceof DerivedResourceNode) {
				handleDerivedResourceAfterExecution(tNode, ctx, kind, value);
			}
		}
	}

	protected void handleModelResourceAfterExecution(ITaskNode tNode, IModelFlowContext ctx, ExecutionTraceUpdater updater,
			final TaskExecution tExec, ResourceKind kind, IAbstractResourceNode value) throws MFRuntimeException {
		IModelResourceNode resourceNode = (IModelResourceNode) value;
		ResourceRepository repo = ctx.getTaskRepository().getResourceRepository();
		IModelResourceInstance<?> resource = null;
		// Get resource from resource repository 
		Optional<IModelResourceInstance<?>> opResource = repo.get(resourceNode);
		if (opResource.isPresent()) {							
			resource = opResource.get();

			// If output or in/out
			if (kind.isOutput() || kind.isInout()) {
					
					// Save output model between tasks. This is ignored for readOnly ones 
					LOG.debug("Saving {}", resourceNode.getName());
					resource.save();
					
					// Store output model hash in execution trace 
					ResourceSnapshot snapshot = updater.createResourceSnapshot(resourceNode.getModelElement(), resource.loadedHash().get());
					tExec.getOutputModels().add(snapshot);
					// Also record the current model snapshot in trace latest resources 
					updater.addResourceToLatest(snapshot);					
			}
			// Check if this task uses the resource for its last time 
			boolean finalUse = ctx.getExecutionGraph().isLastUseOf(resourceNode, tNode, ctx.getDependencyGraph());
			if (finalUse) {
				// Dispose resource 
				IModelFlowProfiler profiler = ctx.getProfiler();
				LOG.debug("Disposing {}", resourceNode.getName());
				profiler.start(IMeasurable.Stage.DISPOSE, resourceNode, ctx);
				resource.dispose();
				profiler.stop(IMeasurable.Stage.DISPOSE, resourceNode, ctx);
			} 
			resource.afterTask();
		} else {
			throw new IllegalStateException("resource " + resourceNode.getName() + " should have been created previously");
		}
	}

	protected void handleDerivedResourceAfterExecution(ITaskNode tNode, IModelFlowContext ctx, ResourceKind kind,
			IAbstractResourceNode value) {
		// Exclusively Output
		if (kind.isOutput()) {			
			DerivedResourceNode derivedNode = (DerivedResourceNode) value;
			// Identify property name
			String propertyName = derivedNode.getName().split("_")[1];
			// If result
			Object result = null;
			if ("trace".equals(propertyName)){
				result = tNode.getTaskInstance().getTrace();
			// If other
			} else {
				result = ctx.getParamManager().getOutputParameterHandler(tNode).get(propertyName);
			} 
			// If present, save value in repository
			if (result != null) {				
				ctx.getTaskRepository().getResourceRepository().addDerived(derivedNode, result);
			}
		}
	}
	
}
