/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import java.util.List;

import org.epsilonlabs.modelflow.exception.MFExecutionGraphExeption;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.jgrapht.Graph;

/**
 * The Interface IExecutionGraph.
 */
public interface IExecutionGraph {
	
	/*
	 * --------------------------------
	 * Graph descriptors
	 * --------------------------------
	 */

	/**
	 * Gets the graph.
	 *
	 * @return the graph
	 */
	Graph<ITaskNode, ExecutionEdge> getGraph();
	
	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	List<ITaskNode> getTasks();
	
	/*
	 * --------------------------------
	 * task execution helpers
	 * --------------------------------
	 */

	/**
	 * Gets the predecessor tasks.
	 *
	 * @param task the task
	 * @return the predecessor tasks
	 */
	List<ITaskNode> getPredecessorTasks(ITaskNode task);

	/**
	 * Gets the successor tasks.
	 *
	 * @param task the task
	 * @return the successor tasks
	 */
	List<ITaskNode> getSuccessorTasks(ITaskNode task);
	
	/**
	 * Builds the.
	 *
	 * @param context the context
	 * @return the i execution graph
	 * @throws MFExecutionGraphExeption the MF execution graph exeption
	 */
	/*
	 * --------------------------------
	 * Build Graph
	 * --------------------------------
	 */
	IExecutionGraph build(IModelFlowContext context) throws MFExecutionGraphExeption;

	/*
	 * --------------------------------
	 * State
	 * --------------------------------
	 */
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	GraphState getState();
	
}
