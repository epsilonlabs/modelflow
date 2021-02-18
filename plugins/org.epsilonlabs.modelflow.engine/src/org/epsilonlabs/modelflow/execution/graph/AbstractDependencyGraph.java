/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/

package org.epsilonlabs.modelflow.execution.graph;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.util.GraphizPrinter;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.jgrapht.Graph;

import io.reactivex.subjects.PublishSubject;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractDependencyGraph implements IDependencyGraph {

	protected PublishSubject<GraphState> statusUpdater = PublishSubject.create();
	protected GraphState status = null;
	
	protected Graph<IGraphNode, DependencyEdge> graph;
	protected Map<String, ITaskNode> tasks = new ConcurrentHashMap<>();
	protected Map<String, IAbstractResourceNode> resources = new ConcurrentHashMap<>();
	
		
	protected AbstractDependencyGraph() {
		updateStatus(GraphState.INSTANTIATED);
	}
	
	protected void updateStatus(GraphState status) {
		this.status = status;
		this.statusUpdater.onNext(this.status);
		if (this.status.equals(GraphState.POPULATED)) {
			this.statusUpdater.onComplete();
		}
	}
	
	@Override
	public IDependencyGraph build(IModelFlowContext ctx) throws MFDependencyGraphException {
		IModelFlowProfiler profiler = ctx.getProfiler();
		profiler.start(IMeasurable.Stage.DEPENDENCY_GRAPH, null, ctx);
		IDependencyGraph dg = buildImpl(ctx);
		profiler.stop(IMeasurable.Stage.DEPENDENCY_GRAPH, null, ctx);
		return dg;
	}
	
	protected abstract IDependencyGraph buildImpl(IModelFlowContext ctx) throws MFDependencyGraphException;
	
	@Override
	public Collection<IAbstractResourceNode> getResourceNodes() {
		return getGraph().vertexSet().parallelStream()
				.filter(r -> r instanceof IAbstractResourceNode)
				.map(IAbstractResourceNode.class::cast)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<ITaskNode> getTaskNodes() {
		return getGraph().vertexSet().parallelStream()
				.filter(r -> r instanceof ITaskNode)
				.map(ITaskNode.class::cast)
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
	
	@Override
	public void subscribe(IModelFlowPublisher publisher) {
		statusUpdater.subscribe(state -> publisher.dependencyGraph(getState()));
	}
	
	@Override
	public String toString() {
		GraphizPrinter<IGraphNode, DependencyEdge> printer = new GraphizPrinter<>(getGraph());
		return printer.toDot().toString();
	}

	@Override
	public Collection<IAbstractResourceNode> getInputResourceNodes(ITaskNode taskNode) {
		return new DependencyGraphHelper(this).getInputResourceNodes(taskNode);
	}

	@Override
	public Collection<IAbstractResourceNode> getOutputResourceNodes(ITaskNode taskNode) {
		return new DependencyGraphHelper(this).getOutputResourceNodes(taskNode);
	}

	@Override
	public Collection<IAbstractResourceNode> getResourceNodes(ITaskNode taskNode) {
		return new DependencyGraphHelper(this).getResourceNodes(taskNode);
	}

	@Override
	public Collection<String> getAliasFor(IAbstractResourceNode rNode, ITaskNode tNode) {
		return new DependencyGraphHelper(this).getAliasFor(rNode, tNode);
	}

	@Override
	public Set<ITaskNode> usedBy(IAbstractResourceNode node) {
		return new DependencyGraphHelper(this).usedBy(node);
	}

	@Override
	public ResourceKind getResourceKindForTask(IAbstractResourceNode resourceNode, ITaskNode taskNode) {
		return new DependencyGraphHelper(this).getResourceKindForTask(resourceNode, taskNode);
	}
	
	@Override
	public boolean hasDerivedOutputDependencies(ITaskNode tNode) {
		return new DependencyGraphHelper(this).hasDerivedOutputDependencies(tNode);
	}

}
