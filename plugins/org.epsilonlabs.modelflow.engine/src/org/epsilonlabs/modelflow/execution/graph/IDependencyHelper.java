/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph;

import java.util.Collection;
import java.util.Set;

import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

/**
 * @author bea
 *
 */
public interface IDependencyHelper {

	/**
	 * Gets the input resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the input resource nodes
	 */
	Collection<IAbstractResourceNode> getInputResourceNodes(ITaskNode taskNode);

	/**
	 * Gets the output resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the output resource nodes
	 */
	Collection<IAbstractResourceNode> getOutputResourceNodes(ITaskNode taskNode);

	/**
	 * Gets the resource nodes.
	 *
	 * @param taskNode the task node
	 * @return the resource nodes
	 */
	Collection<IAbstractResourceNode> getResourceNodes(ITaskNode taskNode);

	/**
	 * Gets the alias for.
	 *
	 * @param rNode the r node
	 * @param tNode the t node
	 * @return the alias for
	 */
	Collection<String> getAliasFor(IAbstractResourceNode rNode, ITaskNode tNode);

	/**
	 * Used by.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	Set<ITaskNode> usedBy(IAbstractResourceNode node);

	/**
	 * Gets the resource kind for task.
	 *
	 * @param resourceNode the resource node
	 * @param taskNode the task node
	 * @return the resource kind for task
	 */
	ResourceKind getResourceKindForTask(IAbstractResourceNode resourceNode, ITaskNode taskNode);

	/**
	 * Checks for derived output dependencies.
	 *
	 * @param tNode the t node
	 * @return true, if successful
	 */
	boolean hasDerivedOutputDependencies(ITaskNode tNode);

}