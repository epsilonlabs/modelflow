/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import org.epsilonlabs.modelflow.execution.graph.GraphState;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;

import io.reactivex.Observable;

/**
 * The Interface IModelFlowPublisher.
 */
public interface IModelFlowPublisher {
	
	/**
	 * The Interface TaskUpdate.
	 */
	public interface TaskUpdate {
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		String getName();
		
		/**
		 * Gets the state.
		 *
		 * @return the state
		 */
		TaskState getState();
	}
	

	/**
	 * Task state.
	 *
	 * @param taskName the task name
	 * @param state the state
	 */
	void taskState(String taskName, TaskState state);

	/**
	 * Resource state.
	 *
	 * @param name the name
	 * @param state the state
	 */
	void resourceState(String name, Object state);

	
	void dependencyGraph(GraphState state);
	
	void executionGraph(GraphState state);
	
	void scheduler(GraphState state);

	/**
	 * @return
	 */
	Observable<TaskUpdate> forTasks();



}
