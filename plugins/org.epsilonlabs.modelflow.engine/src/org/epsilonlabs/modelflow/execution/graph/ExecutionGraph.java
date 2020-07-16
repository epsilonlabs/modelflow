/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.exception.MFExecutionGraphExeption;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge.Kind;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.graph.util.GraphizPrinter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionGraph implements IExecutionGraph {

	private static final Logger LOG = LoggerFactory.getLogger(ExecutionGraph.class);

	protected DirectedAcyclicGraph<ITaskNode, ExecutionEdge> graph;
	protected Map<String, ITaskNode> tasks = new HashMap<>();
	protected GraphState state;
		
	public ExecutionGraph() {
		this.graph = new DirectedAcyclicGraph<>(ExecutionEdge.class);
		setState(GraphState.CREATED);
	}
	
	/**
	 * Builds the execution graph for a given dependency graph extracted from the context
	 */
	@Override
	public IExecutionGraph build(IModelFlowContext ctx) throws MFExecutionGraphExeption {
		IDependencyGraph dg = ctx.getDependencyGraph();
		
		// TODO Manage changed resources
		
		// Add Task Nodes
		addTaskNodes(dg);
		// Add Edges from Task Dependencies
		addEdgesFromTaskDependencies(dg);
		// Add Edges from Task-Resource Dependencies
		addEdgesFromTaskResourceDependencies(dg);
		
		// Clean Up
		cleanupMultipleEdges();
		//removeHarmfulTasks(); //FIXME is this necessary? 
		
		setState(GraphState.POPULATED);
		LOG.info("ExecutionGraph populated: \n{}", this);
		return this;

	}
	
	@Override
	public List<ITaskNode> getTasks() {
		return getGraph().vertexSet().parallelStream()
				.filter(r -> r instanceof ITaskNode)
				.map(n -> n)
				.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		GraphizPrinter<ITaskNode, ExecutionEdge> printer = new GraphizPrinter<>(this.graph);
		return printer.toDot().toString();
	}
	
	@Override
	public Graph<ITaskNode, ExecutionEdge> getGraph() {
		return this.graph;
	}
	
	@Override
	public List<ITaskNode> getPredecessorTasks(ITaskNode task){
		if (this.getState().isPopulated()) {
			return Graphs.predecessorListOf(this.graph, task);	
		}
		throw new IllegalStateException("Execution Graph has not been populated yet");
	}
	
	@Override
	public List<ITaskNode> getSuccessorTasks(ITaskNode task){
		if (this.getState().isPopulated()) {
			return Graphs.successorListOf(this.graph, task);	
		}
		throw new IllegalStateException("Execution Graph has not been populated yet");
	}
	
	// FIXME check algorithm to detect if last time use of resource. 
	// It should consider potential parallel executions as well.
	
	/**
	 *  Looks for tasks that use this resource and checks whether they are in the process of being executed
	 *  that is, their execute method has been called and therefore this is the last use.
	 *  
	 *  FIXME: this could be a bug as it could be that in a process of asking a few moments after we could have 
	 *  seen that another task instantiated the resource.
	 *  It should not be calculated based on states but rather on the execution graph
	 *  
	 *  The final disposal of the resource should take into account tasks that have not yet been executed, 
	 *  or those which are executing or have been executed to determine whether it can be safely disposed.   
	 */
	
	@Override
	public 	boolean isLastUseOf(IAbstractResourceNode resourceNode, ITaskNode here, IDependencyGraph dg){
		return new DependencyGraphHelper(dg).usedBy(resourceNode).parallelStream()
				.filter(tn -> !tn.equals(here)).allMatch(tn-> 
					tn.getState().getVal() >= TaskState.INITIALIZED.getVal()
				);
	}
	
	protected void addTaskNodes(IDependencyGraph dg) {
		dg.getTaskNodes().forEach(tn -> {
			getGraph().addVertex(tn);
			this.tasks.put(tn.getName(), tn);
		});
	}
	
	/**
	 * 
	 * @param dg
	 */
	protected void addEdgesFromTaskDependencies(IDependencyGraph dg){
		dg.getTaskDependencyEdges().stream()
			.filter(d -> d.getKind().equals(Kind.TASK))
			// For a found dependency edge where the source node B depends on target node A, 
			// we create an execution edge where the target A executes before source B. 
			.forEach(d -> addEdge((ITaskNode) d.getTarget(), (ITaskNode) d.getSource()));
	}
	
	protected void addEdgesFromTaskResourceDependencies(IDependencyGraph dg){
		DependencyGraphHelper helper = new DependencyGraphHelper(dg);
		dg.getTaskNodes().stream().forEach(t1 ->	
			dg.getTaskNodes().stream().filter(t2 -> !t2.equals(t1))
			.forEach(t2 -> {
				// FOR EACH COMBINATION OF TASKS 
				if (helper.getInputResourceNodes(t2).stream().anyMatch(i -> 
					      helper.getInoutResourceNodes(t1).stream().anyMatch(io -> io.equals(i)) 
					   || helper.getOutputResourceNodes(t1).stream().anyMatch(o -> o.equals(i)))) 
				{
					addEdge(t1, t2);
				}
				if (helper.getInoutResourceNodes(t2).stream().anyMatch(t2io -> 
					   helper.getInoutResourceNodes(t1).stream().anyMatch(o -> o.equals(t2io))
					|| helper.getOutputResourceNodes(t1).stream().anyMatch(o -> o.equals(t2io)))) 
				{
					addEdge(t1, t2);
				}
			})
		);
	}
	
	protected ExecutionEdge addEdge(ITaskNode sourceVertex, ITaskNode targetVertex) {
		LOG.debug("Adding edge : {} -> {}", sourceVertex.getName(), targetVertex.getName());
		// Only allow one edge per source/target node combination
		if (getGraph().getAllEdges(sourceVertex, targetVertex).isEmpty()) {
			return this.graph.addEdge(sourceVertex, targetVertex);
		} 
		Optional<ExecutionEdge> findFirst = getGraph().getAllEdges(sourceVertex, targetVertex).stream().findFirst();
		if (findFirst.isPresent()) {
			return findFirst.get();	
		}
		return null;
	}

	protected void cleanupMultipleEdges() {
		TopologicalOrderIterator<ITaskNode, ExecutionEdge> iterator = new TopologicalOrderIterator<>(this.graph);
		while (iterator.hasNext()) {
			ITaskNode node = iterator.next();
			Collection<ExecutionEdge> edgesToRemove = new ArrayList<>();
			getGraph().outgoingEdgesOf(node).forEach(o -> {
				AllDirectedPaths<ITaskNode, ExecutionEdge> shortest = new AllDirectedPaths<>(this.graph);
				ITaskNode target = getGraph().getEdgeTarget(o);
				List<GraphPath<ITaskNode, ExecutionEdge>> allPaths = shortest.getAllPaths(node, target, false,
						Integer.MAX_VALUE);
				if (allPaths.size() > 1) {
					AtomicInteger min = new AtomicInteger(-1);
					allPaths.forEach(p -> {
						if (min.get() < 0 || p.getLength() < min.get())
							min.set(p.getLength());

					});
					allPaths.stream().filter(p -> p.getLength() == min.get())
							.forEach(p -> edgesToRemove.addAll(p.getEdgeList()));
				}
			});
			getGraph().removeAllEdges(edgesToRemove);
		}
	}
	
	/**
	 * --------------------- GRAPH WRAPPING UTILS ---------------------
	 */
	
	protected void addEdge(ITaskNode source, ITaskNode target, ExecutionEdge dependencyEdge) {
		try {
			this.graph.addEdge(source, target, dependencyEdge);
		} catch (Exception e) {
			String msg = String.format("Unable to create edge : %s -- %s", source.getName(), target.getName());
			LOG.error(msg, e);
		}
	}

	@Override
	public synchronized GraphState getState() {
		return this.state;
	}

	protected synchronized void setState(GraphState state){
		this.state = state;
	}

	@Override
	public void reset() {
		this.graph = new DirectedAcyclicGraph<>(ExecutionEdge.class);
		setState(GraphState.CREATED);
		this.tasks.clear();
	}
	
}
