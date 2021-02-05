/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph.wip;

import java.util.Collection;
import java.util.Set;

import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

/**
 * @author bea
 *
 */
public interface ITaskNodeHelper {

	
	Collection<IAbstractResourceNode> getInputResourceNodes(ITaskNode taskNode);

	Collection<IAbstractResourceNode> getOutputResourceNodes(ITaskNode taskNode);

	Collection<IAbstractResourceNode> getAllResourceNodes(ITaskNode taskNode);

	
	Collection<String> getAliasFor(IAbstractResourceNode rNode, ITaskNode tNode);

	
	Collection<DependencyEdge> getInputResourceEdges(ITaskNode taskNode);

	Collection<DependencyEdge> getOutputResourceEdges(ITaskNode taskNode);
	
	Collection<IAbstractResourceNode> getInoutResourceNodes(ITaskNode taskNode);

	
	Set<ITaskNode> usedBy(IAbstractResourceNode node);

	ResourceKind getResourceKindForTask(IAbstractResourceNode resourceNode, ITaskNode taskNode);

	
	boolean hasDerivedOutputDependencies(ITaskNode tNode);

	
	Collection<DependencyEdge> getResourceEdge(IResourceNode res);
	

}