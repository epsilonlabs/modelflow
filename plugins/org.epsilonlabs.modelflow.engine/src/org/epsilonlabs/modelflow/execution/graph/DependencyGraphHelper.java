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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge.Kind;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

/**
 * The Class DependencyGraphHelper.
 *
 * @author Betty Sanchez
 */
public class DependencyGraphHelper implements IDependencyHelper{

	/** The graph. */
	protected final IDependencyGraph graph; 
	
	/**
	 * Instantiates a new dependency graph helper.
	 *
	 * @param graph the graph
	 */
	public DependencyGraphHelper(IDependencyGraph graph){
		this.graph = graph;
	}
	
	/**
	 * Gets the input resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the input resource nodes
	 */
	public Collection<IAbstractResourceNode> getInputResourceNodes(ITaskNode taskNode) {
		return getInputResourceEdges(taskNode).stream()
				.map(ie->(IAbstractResourceNode)ie.getSource())
				.collect(Collectors.toSet());
	}
	

	/**
	 * Gets the output resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the output resource nodes
	 */
	public Collection<IAbstractResourceNode> getOutputResourceNodes(ITaskNode taskNode) {
		return getOutputResourceEdges(taskNode).stream()
				.map(oe->(IAbstractResourceNode)oe.getTarget())
				.collect(Collectors.toSet());
	}

	/**
	 * Gets the resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the resource nodes
	 */
	public Collection<IAbstractResourceNode> getResourceNodes(ITaskNode taskNode) {
		List<IAbstractResourceNode> nodes = new ArrayList<>();
		nodes.addAll(getInoutResourceNodes(taskNode));
		nodes.addAll(getInputResourceNodes(taskNode));
		nodes.addAll(getOutputResourceNodes(taskNode));
		return nodes;
	}
	
	/**
	 * Gets the alias for.
	 *
	 * @param rNode the r node
	 * @param tNode the t node
	 * @return the alias for
	 */
	public Collection<String> getAliasFor(IAbstractResourceNode rNode, ITaskNode tNode){
		return graph.getGraph().edgesOf(tNode).parallelStream()
				.filter(e->e.getKind().equals(DependencyEdge.Kind.TASK_RESOURCE))
				.filter(e->e.getSource().equals(rNode) || e.getTarget().equals(rNode))
				.map(DependencyEdge::getAliases)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}
	
	/**
	 * Gets the input resource edges.
	 *
	 * @param taskNode the task node
	 * @return the input resource edges
	 */
	protected Collection<DependencyEdge> getInputResourceEdges(ITaskNode taskNode) {
		return graph.getGraph().incomingEdgesOf(taskNode).stream().filter(ie ->
				ie.getKind().equals(Kind.TASK_RESOURCE)
				&& graph.getGraph().outgoingEdgesOf(taskNode).stream().noneMatch(oe-> oe.getTarget().equals( ie.getSource())))
				.collect(Collectors.toSet());
	}

	/**
	 * Gets the output resource edges.
	 *
	 * @param taskNode the task node
	 * @return the output resource edges
	 */
	protected Collection<DependencyEdge> getOutputResourceEdges(ITaskNode taskNode) {
		return graph.getGraph().outgoingEdgesOf(taskNode).stream().filter(oe ->
				// targets a resource
				oe.getKind().equals(Kind.TASK_RESOURCE)
				// there is an incoming edge with the same resource 
				&& graph.getGraph().incomingEdgesOf(taskNode).stream().noneMatch(ie-> ie.getSource().equals( oe.getTarget())))
				.collect(Collectors.toSet());
	}

	/**
	 * Gets the inout resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the inout resource nodes
	 */
	public Collection<IAbstractResourceNode> getInoutResourceNodes(ITaskNode taskNode) {
		return graph.getGraph().outgoingEdgesOf(taskNode).stream().filter(oe ->
				// targets a resource 
				oe.getKind().equals(Kind.TASK_RESOURCE)
				// there is no incoming edge with the same resource as source
				&& graph.getGraph().incomingEdgesOf(taskNode).stream().anyMatch(ie-> ie.getSource().equals( oe.getTarget())))
				// incoming edge source 
				.map(oe->(IAbstractResourceNode)oe.getTarget())
				.collect(Collectors.toSet());
	}

	/**
	 * Used by.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	public Set<ITaskNode> usedBy(IAbstractResourceNode node){
		Set<ITaskNode> taskList = new HashSet<>();
		graph.getGraph().edgesOf(node).stream().forEach(e->{
			ITaskNode element = null;
			if (e.getSource() instanceof ITaskNode) {
				element = (ITaskNode) e.getSource();
			} else if (e.getTarget() instanceof ITaskNode) {
				element = (ITaskNode) e.getTarget();
			}
			if (element != null) {
				taskList.add(element);
			}
		});
		return taskList;
	}
	
	/**
	 * Gets the resource kind for task.
	 *
	 * @param resourceNode the resource node
	 * @param taskNode the task node
	 * @return the resource kind for task
	 */
	public ResourceKind getResourceKindForTask(IAbstractResourceNode resourceNode, ITaskNode taskNode) {
		if (getOutputResourceNodes(taskNode).contains(resourceNode)) {
			return ResourceKind.OUTPUT;
		}
		if (getInputResourceNodes(taskNode).contains(resourceNode)) {
			return ResourceKind.INPUT;
		}
		if (getInoutResourceNodes(taskNode).contains(resourceNode)) {
			return ResourceKind.INOUT;
		}

		return null;
	}
	
	/**
	 * Checks for derived output dependencies.
	 *
	 * @param tNode the t node
	 * @return true, if successful
	 */
	public boolean hasDerivedOutputDependencies(ITaskNode tNode) {
		return getOutputResourceNodes(tNode).parallelStream()
		.filter(node -> node instanceof DerivedResourceNode)
		.findAny().isPresent();
	}
	
}
