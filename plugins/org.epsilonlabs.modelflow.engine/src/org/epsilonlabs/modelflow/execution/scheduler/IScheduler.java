/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.scheduler;

import java.util.List;

import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.factory.IInstanceFactory;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.IExecutionListener;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;

/**
 * An executor.
 *
 * @author Betty Sanchez
 */
public interface IScheduler {

	/**
	 * Execute from context.
	 *
	 * @param ctx 	ModelFlow module context
	 * @return The execution trace
	 * @throws MFExecutionException the MF execution exception
	 */
	WorkflowExecution execute(IModelFlowContext ctx) throws MFExecutionException;
	
	/**
	 * Execute target from context.
	 *
	 * @param target the target
	 * @param ctx 	ModelFlow module context
	 * @return The execution trace
	 * @throws MFExecutionException the MF execution exception
	 */
	WorkflowExecution execute(String target, IModelFlowContext ctx) throws MFExecutionException;
	
	/**
	 * Add execution Listener.
	 *
	 * @param listener the listener
	 */
	void addExecutionListener(IExecutionListener listener);
	
	/**
	 * Get execution listeners.
	 *
	 * @return list of execution listeners
	 */
	List<IExecutionListener> getExecutionListeners();

	/**
	 * Gets the execution graph.
	 *
	 * @return the execution graph
	 */
	IExecutionGraph getExecutionGraph();

	/**
	 * Gets the dependency graph.
	 *
	 * @return the dependency graph
	 */
	IDependencyGraph getDependencyGraph();	
	
	/**
	 * Builds the.
	 *
	 * @param context the context
	 * @throws Exception the exception
	 */
	void build(IModelFlowContext context) throws Exception;

	/**
	 * Checks if is last use of.
	 *
	 * @param resourceNode the resource node
	 * @param here the here
	 * @param dg the dg
	 * @return true, if is last use of
	 */
	default boolean isLastUseOf(String resourceName, String taskName) {
		return false;
	}
	
	IInstanceFactory<ITaskInstance, ITaskNode> getTaskInstanceFactory();
	
	IInstanceFactory<IModelResourceInstance<?>, IModelResourceNode> getModelInstanceFactory();
	
}
