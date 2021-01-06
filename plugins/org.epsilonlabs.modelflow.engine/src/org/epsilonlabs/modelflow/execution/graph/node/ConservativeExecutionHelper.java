/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraphHelper;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.PropertySnapshot;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConservativeExecutionHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ConservativeExecutionHelper.class);

	protected ITaskNode task;
	protected IModelFlowContext ctx;

	protected ExecutionTraceUpdater updater;
	protected TaskExecution currentTaskEecution;
	protected TaskExecution previousTaskExecution;

	public ConservativeExecutionHelper(ITaskNode task, IModelFlowContext ctx) {
		this.task = task;
		this.ctx = ctx;
	}

	/* CHECKER METHODS */

	public boolean hasBeenPreviouslyExecuted() {
		updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		Optional<TaskExecution> optionalPastTaskExecution = updater.getPreviousTaskExecution(task);
		boolean present = optionalPastTaskExecution.isPresent();
		if (present) {			
			currentTaskEecution = updater.getCurrentTaskExecution(task);
			previousTaskExecution = optionalPastTaskExecution.get();
		}
		return present;
	}

	public boolean haveInputPropertiesChanged() {
		EList<PropertySnapshot> previousPropertiesHashes = previousTaskExecution.getInputProperties();
		Map<String, Object> inputProperties = task.getInputParams().getHashes();
		return !equivalent(inputProperties, previousPropertiesHashes);
	}

	public List<String> getChangedInputProperties() {
		EList<PropertySnapshot> previousPropertiesHashes = previousTaskExecution.getInputProperties();
		Map<String, Object> inputProperties = task.getInputParams().getHashes();
		return getChangedProperties(inputProperties, previousPropertiesHashes);
	}

	public boolean haveOutputPropertiesChanged() {
		EList<PropertySnapshot> previousPropertiesHashes = previousTaskExecution.getOutputProperties();
		Map<String, Object> outputProperties = task.getOutputParams().getHashesFromTrace(previousTaskExecution);
		return !equivalent(outputProperties, previousPropertiesHashes);
	}
	
	public List<String> getChangedOutputProperties() {
		EList<PropertySnapshot> previousPropertiesHashes = previousTaskExecution.getOutputProperties();
		Map<String, Object> outputProperties = task.getOutputParams().getHashesFromTrace(previousTaskExecution);
		return getChangedProperties(outputProperties, previousPropertiesHashes);
	}

	public boolean haveInputModelsChanged() {
		return resourcesChanged(true);
	}

	public boolean haveOutputModelsChanged() {
		return resourcesChanged(false);
	}

	// TODO check also model properties ?
	protected boolean resourcesChanged(boolean input) {
		Collection<IAbstractResourceNode> nodes;
		DependencyGraphHelper helper = new DependencyGraphHelper(ctx.getDependencyGraph());
		if (input) {
			nodes = helper.getInputResourceNodes(task);
		} else {
			nodes = helper.getOutputResourceNodes(task);
		}
		for (IAbstractResourceNode r : nodes) {
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
					// What if past stamp is null
					try {
						IResource<?> iResource = this.ctx.getTaskRepository().getResourceRepository()
								.getOrCreate((IModelResourceNode) r, ctx);
						Object hash;
						if (!input || !iResource.isLoaded()) {
							/* Reuse past trace to identify resources to compute hashes for */
							hash = iResource.unloadedHash(pastStamp);
						} else {
							/* Compute from in-memory resource */
							// FIXME for output resources we shouldn't use the loaded model because we are
							// about to change it
							hash = iResource.loadedHash();
						}
						if (!pastStamp.equals(hash)) {
							LOG.debug("Hash of {} changed from {} to {}", resource.getName(), pastStamp, hash);
							return true;
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
		return false;
	}

	public void copyFromPrevious() {
		TaskExecution prev = previousTaskExecution;

		Collection<PropertySnapshot> inProps = EcoreUtil.copyAll(prev.getInputProperties());
		currentTaskEecution.getInputProperties().addAll(inProps);
		Collection<PropertySnapshot> outProps = EcoreUtil.copyAll(prev.getOutputProperties());
		currentTaskEecution.getOutputProperties().addAll(outProps);

		Collection<ResourceSnapshot> inModels = EcoreUtil.copyAll(prev.getInputModels());
		currentTaskEecution.getInputModels().addAll(inModels);
		Collection<ResourceSnapshot> outModels = EcoreUtil.copyAll(prev.getOutputModels());
		currentTaskEecution.getOutputModels().addAll(outModels);
	}

	private static boolean equivalent(Map<String, Object> currentProperties,
			EList<PropertySnapshot> previousProperties) {
		boolean sameNumberOfElements = previousProperties.size() == currentProperties.size();
		boolean allElementsEqual = previousProperties.stream().allMatch(e -> {
			String key = e.getName();
			return currentProperties.containsKey(key) && e.getStamp().equals(currentProperties.get(key));
		});
		return sameNumberOfElements && allElementsEqual;
	}

	private static List<String> getChangedProperties(Map<String, Object> currentProperties,
			EList<PropertySnapshot> previousProperties) {
		boolean sameNumberOfElements = previousProperties.size() == currentProperties.size();
		if (sameNumberOfElements) {
			return previousProperties.parallelStream().filter(e -> {
				String key = e.getName();
				return currentProperties.containsKey(key) && !e.getStamp().equals(currentProperties.get(key));
			}).map(PropertySnapshot::getName).collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException();
		}
	}

}