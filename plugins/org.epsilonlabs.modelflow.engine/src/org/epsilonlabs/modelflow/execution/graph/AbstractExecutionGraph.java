/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.exception.MFExecutionGraphExeption;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.control.ExecutionStage;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.util.GraphizPrinter;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractExecutionGraph implements IExecutionGraph {

	protected Graph<ITaskNode, ExecutionEdge> graph;
	protected Map<String, ITaskNode> tasks = new HashMap<>();
	protected GraphState state;

	@Override
	public IExecutionGraph build(IModelFlowContext ctx) throws MFExecutionGraphExeption {
		IModelFlowProfiler profiler = ctx.getProfiler();
		profiler.start(ExecutionStage.EXECUTION_GRAPH, null, ctx);
		IExecutionGraph eg = buildImpl(ctx);
		profiler.stop(ExecutionStage.EXECUTION_GRAPH, null, ctx);
		return eg;
	}

	protected abstract IExecutionGraph buildImpl(IModelFlowContext ctx) throws MFExecutionGraphExeption;
	
	@Override
	public List<ITaskNode> getTasks() {
		return getGraph().vertexSet().parallelStream().filter(r -> r instanceof ITaskNode).map(n -> n)
				.collect(Collectors.toList());
	}

	@Override
	public List<ITaskNode> getPredecessorTasks(ITaskNode task) {
		if (this.getState().isPopulated()) {
			return Graphs.predecessorListOf(this.graph, task);
		}
		throw new IllegalStateException("Execution Graph has not been populated yet");
	}

	@Override
	public List<ITaskNode> getSuccessorTasks(ITaskNode task) {
		if (this.getState().isPopulated()) {
			return Graphs.successorListOf(this.graph, task);
		}
		throw new IllegalStateException("Execution Graph has not been populated yet");
	}

	// FIXME check algorithm to detect if last time use of resource.
	// It should consider potential parallel executions as well.

	/**
	 * Looks for tasks that use this resource and checks whether they are in the
	 * process of being executed that is, their execute method has been called and
	 * therefore this is the last use.
	 * 
	 * FIXME: this could be a bug as it could be that in a process of asking a few
	 * moments after we could have seen that another task instantiated the resource.
	 * It should not be calculated based on states but rather on the execution graph
	 * 
	 * The final disposal of the resource should take into account tasks that have
	 * not yet been executed, or those which are executing or have been executed to
	 * determine whether it can be safely disposed.
	 */

	


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
	public synchronized GraphState getState() {
		return this.state;
	}

	protected synchronized void setState(GraphState state) {
		this.state = state;
	}


}
