/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.tests.common;

import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.exception.MFUnknownTaskException;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;

public class ExecutionTraceAnalyser {
	
	private final ExecutionTrace trace; 
	
	public ExecutionTraceAnalyser(ExecutionTrace trace) {
		this.trace = trace;
	}

	public boolean allTasksExecuted(){
		return allTasksWithState(TaskState.EXECUTED);
	}
	
	public boolean allTasksSkipped(){
		return allTasksWithState(TaskState.SKIPPED);
	}
	
	public boolean allTasksWithState(TaskState state){
		
		WorkflowExecution wf = new ExecutionTraceUpdater(trace).getCurrentWorkflowExecution();
		return wf.getTasks().stream().allMatch(t->t.getEndState().equalsIgnoreCase(state.name()));
	}
	
	public Collection<TaskExecution> getAllTaskExecutions(){
		WorkflowExecution wf = new ExecutionTraceUpdater(trace).getCurrentWorkflowExecution();
		return wf.getTasks();
	}
	
	public boolean taskWithState(String taskName, TaskState state) throws MFUnknownTaskException {
		Optional<TaskExecution> task = getAllTaskExecutions().stream().filter(t->t.getName().equalsIgnoreCase(taskName)).findFirst();
		if (task.isPresent()) {
			String endState = task.get().getEndState();
			if (endState.equalsIgnoreCase(state.name())) {
				return true;
			} else {
				String msg= String.format("Task %s expected to %s but was %s", taskName, state.name(), endState);
				throw new MFUnknownTaskException(msg);
			}
		} else {			
			throw new MFUnknownTaskException("unknown task " + taskName);
		}
	}
	
	public String taskState(String taskName) throws MFUnknownTaskException {
		Optional<TaskExecution> task = getAllTaskExecutions().stream().filter(t->t.getName().equalsIgnoreCase(taskName)).findFirst();
		if (task.isPresent()) {
			return task.get().getEndState();
		} else {			
			throw new MFUnknownTaskException("unknown task " + taskName);
		}
	}
}
