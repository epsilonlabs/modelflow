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
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.execution.graph.node.IResourceNode;
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
		final EList<WorkflowExecution> executions = trace.getExecutions();
		return executions.get(executions.size()-1);
	}
	
	public synchronized Optional<WorkflowExecution> getPreviousWorkflowExecution(){
		EList<WorkflowExecution> executions = trace.getExecutions();
		if (executions.size()>1) {			
			return Optional.of(executions.get(executions.size()-2));
		} else {
			return Optional.empty();
		}
	}
	
	public synchronized TaskExecution getCurrentTaskExecution(String taskName){
		for (TaskExecution t: getCurrentWorkflowExecution().getTasks()) {
			if (t.getName().equals(taskName)) {
				return t;
			} 
		}
		throw new IllegalStateException("A TaskExecution record should be accessible");
	}
	
	public synchronized Optional<TaskExecution> getPreviousTaskExecution(String taskName){
		Optional<WorkflowExecution> previousWorkflowExecution = getPreviousWorkflowExecution();
		if (previousWorkflowExecution.isPresent()) {
			WorkflowExecution workflowExecution = previousWorkflowExecution.get();
			for (TaskExecution t: workflowExecution.getTasks()) {
				if (t.getName().equals(taskName)) {
					return Optional.of(t);
				} 
			}
		} 
		return Optional.empty();	
	}
	
	public synchronized WorkflowExecution createWorkflowExecution(IWorkflow w) {
		return createWorkflowExecution(w.getName());
	}
	
	public synchronized WorkflowExecution createWorkflowExecution(String workflowName) {
		//Workflow copy = EcoreUtil.copy(w);
		//serializableCopy(copy);
		
		WorkflowExecution currentWorkflowExecution = ExecutionTraceFactoryImpl.eINSTANCE.createWorkflowExecution();
		currentWorkflowExecution.setStamp(workflowName); //TODO use stamp
		trace.getExecutions().add(currentWorkflowExecution);
		return currentWorkflowExecution;
	}
	

	public synchronized TaskExecution createTaskExecution(String task) {
		
		TaskExecution exec = ExecutionTraceFactory.eINSTANCE.createTaskExecution();
		exec.setName(task);

		getCurrentWorkflowExecution().getTasks().add(exec);
		return exec;
	}
	
	public synchronized ResourceSnapshot createResourceSnapshot(IResourceNode res, Object stamp) {
		
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
		
	public synchronized void addTaskInputProperties(String task, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(task);
		taskExecution.getInputProperties().clear();
		for (Entry<String, Object> pair : map.entrySet()) {
			PropertySnapshot propSnapshot = createPropertySnapshot(pair);
			taskExecution.getInputProperties().add(propSnapshot);
		}
	}
	
	public synchronized Optional<ResourceSnapshot> getPastInputResource(String task, String resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot inputModel : taskExecution.get().getInputModels()) {
				if (inputModel.getName().equals(resource)) {
					return Optional.of(inputModel);
				}
			}
		}
		return Optional.empty();
	}
	
	public synchronized Optional<ResourceSnapshot> getPastOutputResource(String task, String resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot outputModel : taskExecution.get().getOutputModels()) {
				if (outputModel.getName().equals(resource)) {
					return Optional.of(outputModel);
				}
			}
		}
		return Optional.empty();
	}

	public synchronized void addTaskOutputProperties(String task, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(task);
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
	
	protected synchronized boolean hasChanged(String task){
		return taskPropertiesHaveChanged(task);
	}
	
	protected synchronized boolean taskPropertiesHaveChanged(String task){
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
