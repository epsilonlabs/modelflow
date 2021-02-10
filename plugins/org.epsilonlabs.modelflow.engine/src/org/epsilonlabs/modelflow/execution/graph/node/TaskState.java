/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

/*
 * 	SUCCESS_WITH_RETRY, 
	FAILED_AND_SCHEDULING_FAILED, 
	SCHEDULING_FAILED, 
	MISSING_EXTERNAL_DEPENDENCIES;
 */
public enum TaskState { // FAILED?
	// ---, 	---, 			-----, 		RUNNING, 		SUCCESS, 	NOT RUN		, FAILED
	CREATED(0), INITIALIZED(1), RESOLVED(2), EXECUTING(3), EXECUTED(4), SKIPPING(6), SKIPPED(6), FAILED(7);

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
	
	public boolean isNotDone(){
		return this != EXECUTED || this != SKIPPED || this != FAILED;
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