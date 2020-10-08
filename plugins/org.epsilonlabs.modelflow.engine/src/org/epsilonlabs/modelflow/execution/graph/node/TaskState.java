/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

/*
 * 	SUCCESS, 
	SUCCESS_WITH_RETRY, 
	FAILED, 
	FAILED_AND_SCHEDULING_FAILED, 
	SCHEDULING_FAILED, 
	NOT_RUN, 
	MISSING_EXTERNAL_DEPENDENCIES;
 */
public enum TaskState { // FAILED?
	CREATED(0), INITIALIZED(1), RESOLVED(2), EXECUTING(3), EXECUTED(4), SKIPPED(6);

	private int val;

	TaskState(int val) {
		this.val = val;
	}

	public int getVal() {
		return this.val;
	}
	
	public boolean isCreated(){
		return this == CREATED;
	}
	
	public boolean hasBeenCreated(){
		return this.val >= CREATED.val;
	}
	
	public boolean isInitialised(){
		return this == INITIALIZED;
	}
	
	public boolean hasBeenInitialised(){
		return this.val >= INITIALIZED.val;
	}
	
	public boolean isResolved(){
		return this == RESOLVED;
	}
	
	public boolean hasBeenResolved(){
		return this.val >= RESOLVED.val;
	}
	
	public boolean isExecuting(){
		return this == EXECUTING;
	}
	
	public boolean isExecuted(){
		return this == EXECUTED;
	}
	
	public boolean hasBeenExecuted(){
		return this.val >= EXECUTED.val;
	}
	
	public boolean isSkpped(){
		return this == SKIPPED;
	}
	
	public boolean hasBeenSkipped(){
		return this.val >= SKIPPED.val;
	}

}