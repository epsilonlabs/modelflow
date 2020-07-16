/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.PropertySnapshot;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.management.param.TaskParamManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncrementalTaskNodeChecker {

	private static final Logger LOG = LoggerFactory.getLogger(IncrementalTaskNodeChecker.class);

	protected ITaskNode task;
	protected IModelFlowContext ctx;
	protected ExecutionTrace trace;
	protected TaskParamManager paramManager;
	protected ExecutionTraceUpdater updater;
	protected Optional<TaskExecution> previousTaskExecution;
	protected TaskExecution currentTaskEecution;
	protected Map<String, Object> inputProperties;
	protected Map<String, Object> outputProperties;
	
	public IncrementalTaskNodeChecker(ITaskNode task, IModelFlowContext ctx){
		this.task = task;
		this.ctx = ctx;
		this.trace = ctx.getExecutionTrace();
		this.paramManager = ctx.getParamManager();
		this.updater = new ExecutionTraceUpdater(trace);
		this.previousTaskExecution = updater.getPreviousTaskExecution(task);
		this.currentTaskEecution = updater.getCurrentTaskExecution(task);
	}
	
	/** CHECKER METHODS */
	
	public boolean haveInputPropertiesChanged() {
		if (previousTaskExecution.isPresent()) {				
			TaskExecution previousTask = previousTaskExecution.get();
			EList<PropertySnapshot> previousPropertiesHashes = previousTask.getInputProperties();
			inputProperties = task.getInputParams().getHashes();
			return !equivalent(inputProperties, previousPropertiesHashes);
		}
		/** FIRST TIME OR CHANGE */
		return true; 
	}
	
	public boolean haveOutputPropertiesChanged() {
		if (previousTaskExecution.isPresent()) {				
			TaskExecution previousTask = previousTaskExecution.get();
			EList<PropertySnapshot> previousPropertiesHashes = previousTask.getOutputProperties();
			outputProperties = task.getOutputParams().getHashesFromTrace(previousTask);
			return !equivalent(outputProperties, previousPropertiesHashes);
		}
		/** FIRST TIME OR CHANGE */
		return false; 
	}
	
	public boolean haveInputModelsChanged() {
		return resourcesChanged(true);
	}
	
	public boolean haveOutputModelsChanged() {
		return resourcesChanged(false);
	}
	
	//TODO check also model properties ? 
	protected boolean resourcesChanged(boolean input) {
		if (previousTaskExecution.isPresent()) {
			Collection<IAbstractResourceNode> nodes;
			DependencyGraphHelper helper = new DependencyGraphHelper(ctx.getDependencyGraph());
			if (input) {
				nodes = helper.getInputResourceNodes(task);
			} else {
				nodes = helper.getOutputResourceNodes(task);
			}
			for (IAbstractResourceNode r : nodes){
				if (r instanceof IModelResourceNode) {	
					IModelResourceNode resource = (IModelResourceNode) r;
					Optional<ResourceSnapshot> pastResource;
					if (input) {
						pastResource = updater.getPastInputResource(task, resource);
					} else {
						pastResource = updater.getPastOutputResource(task, resource);
					}
					if (pastResource.isPresent()) {						
						Object pastStamp = pastResource.get().getStamp();
						try {
							Optional<IResource<?>> optionalIResource = this.ctx.getTaskRepository().getResourceRepository().get((IModelResourceNode)r);
							if (optionalIResource.isPresent()) {								
								IResource<?> iResource = optionalIResource.get();
								Object hash;
								if (!input || !iResource.isLoaded()) {
									/** Reuse past trace to identify resources to compute hashes for*/ 
									hash = iResource.unloadedHash(pastStamp);
								} else {
									/** Compute from in-memory resource */
									// FIXME for output resources we shouldn't use the loaded model because we are about to change it
									hash = iResource.loadedHash();
								}
								if (!hash.equals(pastStamp)) {
									LOG.debug("Hash of {} changed from {} to {}", resource.getName(), pastStamp, hash);	
									return true;
								}
							}
						} catch (MFRuntimeException e) {
							throw new IllegalStateException(e.getCause());
						}
					} else {
						LOG.debug("No previous record of {}", resource.getName());
						return input;
					}
				}
			}
		} else {
			/** FIRST TIME */
			return input;
		}
		return false; 
	}
	
	public void copyFromPrevious(){
		if (previousTaskExecution.isPresent()) {
			TaskExecution prev = previousTaskExecution.get();
			
			Collection<PropertySnapshot> inProps = EcoreUtil.copyAll(prev.getInputProperties());
			currentTaskEecution.getInputProperties().addAll(inProps);
			Collection<PropertySnapshot> outProps = EcoreUtil.copyAll(prev.getOutputProperties());
			currentTaskEecution.getOutputProperties().addAll(outProps);
			
			Collection<ResourceSnapshot> inModels = EcoreUtil.copyAll(prev.getInputModels());
			currentTaskEecution.getInputModels().addAll(inModels);
			Collection<ResourceSnapshot> outModels = EcoreUtil.copyAll(prev.getOutputModels());
			currentTaskEecution.getOutputModels().addAll(outModels);
		}
	}
	
	private static boolean equivalent(Map<String, Object> currentProperties, EList<PropertySnapshot> previousProperties) {
		boolean sameNumberOfElements = previousProperties.size() == currentProperties.size();
		boolean allElementsEqual = previousProperties.stream().allMatch(e -> {
			String key = e.getKey();
			return currentProperties.containsKey(key) && e.getStamp().equals(currentProperties.get(key));
		});
		return sameNumberOfElements && allElementsEqual;
	}
	
}