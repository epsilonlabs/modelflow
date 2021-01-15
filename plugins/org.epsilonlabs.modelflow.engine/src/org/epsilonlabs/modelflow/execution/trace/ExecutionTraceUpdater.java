/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.trace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.api.factory.ModuleElementHandler;
import org.epsilonlabs.modelflow.execution.graph.node.IResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionTraceUpdater {

	private static final Logger LOG = LoggerFactory.getLogger(ExecutionTraceUpdater.class);

	private final ExecutionTrace trace;
	
	public ExecutionTraceUpdater(ExecutionTrace trace) {
		this.trace = trace;
	}
	
	public ExecutionTrace getTrace() {
		return trace;
	}
	
	public synchronized WorkflowExecution getCurrentWorkflowExecution(){
		return trace.getExecutions().get(trace.getExecutions().size()-1);
	}
	
	public synchronized Optional<WorkflowExecution> getPreviousWorkflowExecution(){
		EList<WorkflowExecution> executions = trace.getExecutions();
		if (executions.size()>1) {			
			return Optional.of(executions.get(executions.size()-2));
		} else {
			return Optional.empty();
		}
	}
	
	public synchronized TaskExecution getCurrentTaskExecution(ITaskNode task){
		for (TaskExecution t: getCurrentWorkflowExecution().getTasks()) {
			if (t.getName().equals(task.getTaskDefinition().getName())) {
				return t;
			} 
		}
		throw new IllegalStateException("A TaskExecution record should be accessible");
	}
	
	public synchronized Optional<TaskExecution> getPreviousTaskExecution(ITaskNode task){
		Optional<WorkflowExecution> previousWorkflowExecution = getPreviousWorkflowExecution();
		if (previousWorkflowExecution.isPresent()) {
			WorkflowExecution workflowExecution = previousWorkflowExecution.get();
			for (TaskExecution t: workflowExecution.getTasks()) {
				if (t.getName().equals(task.getTaskDefinition().getName())) {
					return Optional.of(t);
				} 
			}
		} 
		return Optional.empty();	
	}
	
	public synchronized WorkflowExecution createWorkflowExecution(IWorkflow w) {
		//Workflow copy = EcoreUtil.copy(w);
		//serializableCopy(copy);
		
		WorkflowExecution currentWorkflowExecution = ExecutionTraceFactoryImpl.eINSTANCE.createWorkflowExecution();
		currentWorkflowExecution.setStamp(w.getName()); //TODO use stamp
		trace.getExecutions().add(currentWorkflowExecution);
		return currentWorkflowExecution;
	}
	

	public synchronized TaskExecution createTaskExecution(ITask task) {
		
		TaskExecution exec = ExecutionTraceFactory.eINSTANCE.createTaskExecution();
		exec.setName(task.getName());

		getCurrentWorkflowExecution().getTasks().add(exec);
		return exec;
	}
	
	protected synchronized void serializableCopy(ITask task) {
		Object guard = task.getGuard();
		if (guard instanceof ModuleElement) {
			ModuleElementHandler wrapper = new ModuleElementHandler((ModuleElement)guard);
			task.setGuard(wrapper);
		}
		for (IProperty p : task.getProperties()) {
			serializableCopy(p);
		}
	}
	
	protected synchronized void serializableCopy(IResource resource) {
		for (IProperty p : resource.getProperties()) {
			serializableCopy(p);
		}
	}
	
	protected synchronized void serializableCopy(IProperty p) {
		Object value = p.getValue();
		if (value instanceof ModuleElement) {
			ModuleElementHandler wrapper = new ModuleElementHandler((ModuleElement)value);
			p.setValue(wrapper);
		}
	}
	
	protected synchronized void serializableCopy(IWorkflow w) {
		for (ITask t : w.getTasks()) {
			serializableCopy(t);
		}
		for (IAbstractResource r : w.getResources()) {
			if (r instanceof IResource) {				
				serializableCopy((IResource) r);
			}
		}
		for (IProperty p : w.getProperties()) {
			serializableCopy(p);
		}		
	}

	public synchronized ResourceSnapshot createResourceSnapshot(IResource res, Object stamp) {
	
		ResourceSnapshot snapshot = ExecutionTraceFactory.eINSTANCE.createResourceSnapshot();
		snapshot.setName(res.getName());
		snapshot.setTimestamp(System.nanoTime());
		snapshot.setStamp(stamp);
		LOG.debug("Stamp of {} is {}",res.getName(), stamp);
		return snapshot;
	}
	
	public synchronized void addResourceToLatest(ResourceSnapshot snapshot){
		// Find the resource with same identifier 
		List<ResourceSnapshot> toRemove=new ArrayList<>();
		trace.getLatest()
			.parallelStream()
			.filter(r-> r.getName().equals(snapshot.getName()))
			.forEach(toRemove::add);
		toRemove.parallelStream().forEach(r-> trace.getLatest().remove(r));
		
		// Make a copy and save it in latest 
		ResourceSnapshot copy = EcoreUtil.copy(snapshot);
		trace.getLatest().add(copy);
	}
		
	public synchronized void addTaskInputProperties(ITaskNode node, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(node);
		taskExecution.getInputProperties().clear();
		for (Entry<String, Object> pair : map.entrySet()) {
			PropertySnapshot propSnapshot = createPropertySnapshot(pair);
			taskExecution.getInputProperties().add(propSnapshot);
		}
	}
	
	public synchronized Optional<ResourceSnapshot> getPastInputResource(ITaskNode task, IResourceNode resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot inputModel : taskExecution.get().getInputModels()) {
				if (inputModel.getName().equals(resource.getInternal().getName())) {
					return Optional.of(inputModel);
				}
			}
		}
		return Optional.empty();
	}
	
	public synchronized Optional<ResourceSnapshot> getPastOutputResource(ITaskNode task, IResourceNode resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot outputModel : taskExecution.get().getOutputModels()) {
				if (outputModel.getName().equals(resource.getInternal().getName())) {
					return Optional.of(outputModel);
				}
			}
		}
		return Optional.empty();
	}

	public synchronized void addTaskOutputProperties(ITaskNode node, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(node);
		taskExecution.getOutputProperties().clear();
		for (Entry<String, Object> pair : map.entrySet()) {
			PropertySnapshot propSnapshot = createPropertySnapshot(pair);
			taskExecution.getOutputProperties().add(propSnapshot);			
		}
	}

	protected synchronized PropertySnapshot createPropertySnapshot(Entry<String, Object> pair){
		PropertySnapshot propSnapshot = ExecutionTraceFactory.eINSTANCE.createPropertySnapshot();
		propSnapshot.setTimestamp(System.nanoTime());
		propSnapshot.setName(pair.getKey());
		propSnapshot.setStamp(pair.getValue());
		return propSnapshot;
	}
	
	protected synchronized boolean hasChanged(ITaskNode task){
		return taskPropertiesHaveChanged(task);
	}
	
	protected synchronized boolean taskPropertiesHaveChanged(ITaskNode task){
		Optional<TaskExecution> previousTaskExecution = getPreviousTaskExecution(task);
		if (previousTaskExecution.isPresent()) {
			TaskExecution current = getCurrentTaskExecution(task);
			TaskExecution previous = previousTaskExecution.get();
			return current.getInputModels().equals(previous.getInputModels()); // TODO fixme
		} else {			
			return true;
		}
	}

	
}
