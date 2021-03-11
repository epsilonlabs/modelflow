/**
 * 
 */
package org.epsilonlabs.modelflow.execution.control;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ExecutionStage {
	DEPENDENCY_GRAPH, 
	EXECUTION_GRAPH,
	EXECUTION, // Module
	EXECUTION_PROCESS, // Scheduler
	TASK_EXECUTION_PROCESS, // Task
	// Task
	LOAD, 
	DISPOSE,
	PROCESS_INPUTS, 
	PROCESS_MODELS_BEFORE_EXECUTION, 
	ATOMIC_EXECUTION, 
	PROCESS_OUTPUTS, 
	END_TO_END_TRACES, 
	PROCESS_MODELS_AFTER_EXECUTION;
	
	
	public ExecutionStage getParent() {
		switch (this) {
		case EXECUTION:
			return null;
		case DEPENDENCY_GRAPH:
		case EXECUTION_GRAPH:
		case EXECUTION_PROCESS:
			return EXECUTION;
		case TASK_EXECUTION_PROCESS:
			return EXECUTION_PROCESS;
		case LOAD:
			return PROCESS_MODELS_BEFORE_EXECUTION;
		case DISPOSE:
			return PROCESS_MODELS_AFTER_EXECUTION;
		default:
			return TASK_EXECUTION_PROCESS;
		}
	}
	
	public static List<String> names(){
		return Arrays.asList(ExecutionStage.values()).stream().map(ExecutionStage::name).collect(Collectors.toList());
		
	}
}