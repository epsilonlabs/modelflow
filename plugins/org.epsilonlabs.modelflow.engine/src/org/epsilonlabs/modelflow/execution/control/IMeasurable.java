/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public interface IMeasurable {

	enum Stage {
		DEPENDENCY_GRAPH("Workflow"), EXECUTION_GRAPH("Workflow"),EXECUTION_PROCESS("Workflow"),
		TASK_EXECUTION_PROCESS("Workflow"),
		LOAD("Model"), DISPOSE("Model"),
		// Task
		PROCESS_INPUTS, 
		PROCESS_MODELS_BEFORE_EXECUTION, 
		EXECUTION, 
		PROCESS_OUTPUTS, 
		CLEANUP, 
		END_TO_END_TRACES, 
		PROCESS_MODELS_AFTER_EXECUTION
		;
		
		String group;
		
		Stage(String group){
			this.group = group;
		}
		Stage(){
			this.group= "Task";
		}
		
		public String getGroup(){
			return group;
		}
	}
	
	String getNode();
	
	Stage getStage();
	
	Class<? extends IGraphNode> getType();
	
	IMeasurable getParent();
}
