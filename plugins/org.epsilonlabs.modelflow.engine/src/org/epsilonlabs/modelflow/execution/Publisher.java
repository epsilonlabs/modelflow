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
import io.reactivex.subjects.PublishSubject;

public class Publisher implements IModelFlowPublisher {

	protected PublishSubject<TaskUpdate> tasks = PublishSubject.create();
	
	@Override
	public void taskState(String taskName, TaskState state) {
		this.tasks.onNext(new TaskUpdate() {
			@Override
			public TaskState getState() {
				return state;
			}
			@Override
			public String getName() {
				return taskName;
			}
		});
	}
	
	@Override
	public Observable<TaskUpdate> forTasks() {
		return tasks;
	}

	@Override
	public void resourceState(String name, Object state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dependencyGraph(GraphState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executionGraph(GraphState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduler(GraphState state) {
		// TODO Auto-generated method stub
		
	}

}