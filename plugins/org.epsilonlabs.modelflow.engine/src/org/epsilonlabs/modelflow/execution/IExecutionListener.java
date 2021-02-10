/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.scheduler.IScheduler;

/**
 * The listener interface for receiving IExecution events.
 * The class that is interested in processing a IExecution
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIExecutionListener<code> method. When
 * the IExecution event occurs, that object's appropriate
 * method is invoked.
 *
 * @author Betty Sanchez
 */
public interface IExecutionListener {

	/**
	 * Executing.
	 *
	 * @param scheduler the scheduler
	 * @param task the task
	 */
	void executing(IScheduler scheduler, ITaskNode task);

	/**
	 * Execution threw.
	 *
	 * @param scheduler the scheduler
	 * @param node the node
	 * @param e the e
	 */
	void executionThrew(IScheduler scheduler, ITaskNode node, Throwable e);

	/**
	 * Finished execution.
	 *
	 * @param scheduler the scheduler
	 * @param task the task
	 */
	void finishedExecution(IScheduler scheduler, ITaskNode task);

	/**
	 * Preparing for execution.
	 *
	 * @param scheduler the scheduler
	 * @param task the task
	 */
	void preparingForExecution(IScheduler scheduler, ITaskNode task);

}
