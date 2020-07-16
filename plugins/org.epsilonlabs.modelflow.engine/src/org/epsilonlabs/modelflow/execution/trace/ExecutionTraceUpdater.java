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
import org.epsilonlabs.modelflow.dom.Resource;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.Workflow;
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
	
	public WorkflowExecution getCurrentWorkflowExecution(){
		return trace.getExecutions().get(trace.getExecutions().size()-1);
	}
	
	public Optional<WorkflowExecution> getPreviousWorkflowExecution(){
		EList<WorkflowExecution> executions = trace.getExecutions();
		if (executions.size()>1) {			
			return Optional.of(executions.get(executions.size()-2));
		} else {
			return Optional.empty();
		}
	}
	
		
	public TaskExecution getCurrentTaskExecution(ITaskNode task){
		for (TaskExecution t: getCurrentWorkflowExecution().getTasks()) {
			if (t.getTask().getName().equals(task.getTaskDefinition().getName())) {
				return t;
			} 
		}
		throw new IllegalStateException("A TaskExecution record should be accessible");
	}
	
	public Optional<TaskExecution> getPreviousTaskExecution(ITaskNode task){
		Optional<WorkflowExecution> previousWorkflowExecution = getPreviousWorkflowExecution();
		if (previousWorkflowExecution.isPresent()) {
			WorkflowExecution workflowExecution = previousWorkflowExecution.get();
			for (TaskExecution t: workflowExecution.getTasks()) {
				if (t.getTask().getName().equals(task.getTaskDefinition().getName())) {
					return Optional.of(t);
				} 
			}
			throw new IllegalStateException("A TaskExecution record should be accessible");
		} else {
			return Optional.empty();
		}
	}
	
	public WorkflowExecution createWorkflowExecution(Workflow w) {
		Workflow copy = EcoreUtil.copy(w);
		
		WorkflowExecution currentWorkflowExecution = ExecutionTraceFactoryImpl.eINSTANCE.createWorkflowExecution();
		currentWorkflowExecution.setWorkflow(copy);
		trace.getExecutions().add(currentWorkflowExecution);
		return currentWorkflowExecution;
	}
	

	public TaskExecution createTaskExecution(Task task) {
		Task copy = EcoreUtil.copy(task);

		TaskExecution exec = ExecutionTraceFactory.eINSTANCE.createTaskExecution();
		exec.setTask(copy);

		getCurrentWorkflowExecution().getTasks().add(exec);
		return exec;
	}

	public ResourceSnapshot createResourceSnapshot(Resource res, Object stamp) {
		Resource copy = EcoreUtil.copy(res);

		ResourceSnapshot snapshot = ExecutionTraceFactory.eINSTANCE.createResourceSnapshot();
		snapshot.setResource(copy);
		snapshot.setTimestamp(System.nanoTime());
		snapshot.setStamp(stamp);
		LOG.debug("Stamp of {} is {}",res.getName(), stamp);
		return snapshot;
	}
	
	public void addResourceToLatest(ResourceSnapshot snapshot){
		// Find the resource with same identifier 
		List<ResourceSnapshot> toRemove=new ArrayList<>();
		trace.getLatest()
			.parallelStream()
			.filter(r-> r.getResource().getName().equals(snapshot.getResource().getName()))
			.forEach(toRemove::add);
		toRemove.parallelStream().forEach(r-> trace.getLatest().remove(r));
		
		// Make a copy and save it in latest 
		ResourceSnapshot copy = EcoreUtil.copy(snapshot);
		trace.getLatest().add(copy);
	}
		
	public void addTaskInputProperties(ITaskNode node, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(node);
		taskExecution.getInputProperties().clear();
		for (Entry<String, Object> pair : map.entrySet()) {
			PropertySnapshot propSnapshot = createPropertySnapshot(pair);
			taskExecution.getInputProperties().add(propSnapshot);
		}
	}
	
	public Optional<ResourceSnapshot> getPastInputResource(ITaskNode task, IResourceNode resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot inputModel : taskExecution.get().getInputModels()) {
				if (inputModel.getResource().getName().equals(resource.getInternal().getName())) {
					return Optional.of(inputModel);
				}
			}
		}
		return Optional.empty();
	}
	
	public Optional<ResourceSnapshot> getPastOutputResource(ITaskNode task, IResourceNode resource){
		Optional<TaskExecution> taskExecution = getPreviousTaskExecution(task);
		if (taskExecution.isPresent()) {
			for (ResourceSnapshot outputModel : taskExecution.get().getOutputModels()) {
				if (outputModel.getResource().getName().equals(resource.getInternal().getName())) {
					return Optional.of(outputModel);
				}
			}
		}
		return Optional.empty();
	}

	public void addTaskOutputProperties(ITaskNode node, Map<String, Object> map){
		TaskExecution taskExecution = getCurrentTaskExecution(node);
		taskExecution.getOutputProperties().clear();
		for (Entry<String, Object> pair : map.entrySet()) {
			PropertySnapshot propSnapshot = createPropertySnapshot(pair);
			taskExecution.getOutputProperties().add(propSnapshot);			
		}
	}

	protected PropertySnapshot createPropertySnapshot(Entry<String, Object> pair){
		PropertySnapshot propSnapshot = ExecutionTraceFactory.eINSTANCE.createPropertySnapshot();
		propSnapshot.setTimestamp(System.nanoTime());
		propSnapshot.setKey(pair.getKey());
		propSnapshot.setStamp(pair.getValue());
		return propSnapshot;
	}
	
	protected boolean hasChanged(ITaskNode task){
		return taskPropertiesHaveChanged(task);
	}
	
	protected boolean taskPropertiesHaveChanged(ITaskNode task){
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
