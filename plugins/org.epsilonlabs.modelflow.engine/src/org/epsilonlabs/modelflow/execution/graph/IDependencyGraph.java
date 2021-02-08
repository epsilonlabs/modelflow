/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import java.util.Collection;

import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.jgrapht.Graph;

/**
 * The Interface IDependencyGraph.
 */
public interface IDependencyGraph extends IDependencyHelper{
	
	/**
	 * Builds the.
	 *
	 * @param context the context
	 * @return the dependency graph
	 * @throws MFDependencyGraphException the MF dependency graph exception
	 */
	IDependencyGraph build(IModelFlowContext context) throws MFDependencyGraphException;

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	GraphState getState();
	
	/**
	 * Gets the task nodes.
	 *
	 * @return the task nodes
	 */
	Collection<ITaskNode> getTaskNodes();
	
	/**
	 * Gets the resource nodes.
	 *
	 * @return the resource nodes
	 */
	Collection<IAbstractResourceNode> getResourceNodes();
	
	/**
	 * Gets the graph.
	 *
	 * @return the graph
	 */
	Graph<IGraphNode, DependencyEdge> getGraph();
	
	/**
	 * Gets the resource dependency edges.
	 *
	 * @return the resource dependency edges
	 */
	Collection<DependencyEdge> getResourceDependencyEdges();

	/**
	 * Gets the task resource dependency edges.
	 *
	 * @return the task resource dependency edges
	 */
	Collection<DependencyEdge> getTaskResourceDependencyEdges();

	/**
	 * Gets the task dependency edges.
	 *
	 * @return the task dependency edges
	 */
	Collection<DependencyEdge> getTaskDependencyEdges();	

	/**
	 * @param publisher
	 */
	void subscribe(IModelFlowPublisher publisher);
	
}
