/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/

package org.epsilonlabs.modelflow.execution.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import io.reactivex.subjects.PublishSubject;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractDependencyGraph implements IDependencyGraph {

	protected PublishSubject<GraphState> statusUpdater = PublishSubject.create();
	protected GraphState status = null;
	protected SimpleDirectedWeightedGraph<IGraphNode, DependencyEdge> graph;
	protected Map<String, ITaskNode> tasks = new HashMap<>();
	protected Map<String, IAbstractResourceNode> resources = new HashMap<>();
	
	@Override
	public Collection<IAbstractResourceNode> getResourceNodes() {
		return getGraph().vertexSet().parallelStream()
				.filter(r -> r instanceof IAbstractResourceNode)
				.map(n -> (IAbstractResourceNode) n)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<ITaskNode> getTaskNodes() {
		return getGraph().vertexSet().parallelStream()
				.filter(r -> r instanceof ITaskNode)
				.map(n -> (ITaskNode) n)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<DependencyEdge> getTaskDependencyEdges() {
		return getGraph().edgeSet().parallelStream()
				.filter(e -> e.getKind().equals(DependencyEdge.Kind.TASK))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<DependencyEdge> getTaskResourceDependencyEdges() {
		return getGraph().edgeSet().parallelStream()
				.filter(e -> e.getKind().equals(DependencyEdge.Kind.TASK_RESOURCE))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<DependencyEdge> getResourceDependencyEdges() {
		return getGraph().edgeSet().parallelStream()
				.filter(e -> e.getKind().equals(DependencyEdge.Kind.RESOURCE))
				.collect(Collectors.toList());
	}
	
	@Override
	public GraphState getState() {
		return this.status;
	}
	
	@Override
	public Graph<IGraphNode, DependencyEdge> getGraph() {
		return graph;
	}

}
