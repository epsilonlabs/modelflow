/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public interface IMeasurable {

	enum Stage {
		DEPENDENCY_GRAPH, 
		EXECUTION_GRAPH,
		EXECUTION_PROCESS,
		TASK_EXECUTION_PROCESS,
		EXECUTION,
		LOAD, 
		DISPOSE,
		// Task
		PROCESS_INPUTS, 
		PROCESS_MODELS_BEFORE_EXECUTION, 
		ATOMIC_EXECUTION, 
		PROCESS_OUTPUTS, 
		END_TO_END_TRACES, 
		PROCESS_MODELS_AFTER_EXECUTION
		;
		
		
		public Stage getParent() {
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
			return Arrays.asList(Stage.values()).stream().map(Stage::name).collect(Collectors.toList());
			
		}
	}
	
	String getName();
	
	Stage getStage();
		
	Class<? extends IGraphNode> getType();
	
	IMeasurable getParent();

	/**
	 * @return
	 */
	IGraphNode getNode();
	
	
}
