/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.param.TaskInputPropertyHandler;
import org.epsilonlabs.modelflow.management.param.TaskOutputPropertyHandler;

/**
 * The Interface ITaskNode.
 */
public interface ITaskNode extends IGraphNode {

	/**
	 *  
	 * Covers the whole execution process of a single task unit.
	 *
	 * @param context the context
	 * @throws MFRuntimeException the MF runtime exception
	 */
	void execute(IModelFlowContext context) throws MFRuntimeException;

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	TaskState getState();

	/**
	 * Gets the task definition.
	 *
	 * @return the task definition
	 */
	Task getTaskDefinition();

	/**
	 * Sets the instance.
	 *
	 * @param task the new instance
	 */
	void setInstance(ITask task);
	
	/**
	 * Gets the task instance.
	 *
	 * @return the task instance
	 */
	ITask getTaskInstance();

	/**
	 * Gets the input params.
	 *
	 * @return the input params
	 */
	TaskInputPropertyHandler getInputParams();

	/**
	 * Gets the output params.
	 *
	 * @return the output params
	 */
	TaskOutputPropertyHandler getOutputParams();

}
