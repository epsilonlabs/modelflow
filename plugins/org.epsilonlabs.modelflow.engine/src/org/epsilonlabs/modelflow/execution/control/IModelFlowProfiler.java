/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * The Interface IModelFlowProfiler.
 *
 * @author Betty Sanchez
 */
public interface IModelFlowProfiler {

	/**
	 * Dispose.
	 */
	void dispose();

	/**
	 * Report.
	 *
	 * @param context the context
	 */
	void report(IModelFlowContext context);
	
	/**
	 * Start.
	 *
	 * @param stage the stage
	 * @param ast the ast
	 * @param context the context
	 */
	void start(ExecutionStage stage, IGraphNode ast, IModelFlowContext context);
	
	/**
	 * Stop.
	 *
	 * @param stage the stage
	 * @param ast the ast
	 * @param context the context
	 */
	void stop(ExecutionStage stage, IGraphNode ast, IModelFlowContext context);
	
	/**
	 * Sets the timeout.
	 *
	 * @param timeout the new timeout
	 */
	void setTimeout(long timeout);
	
	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	long getTimeout();
	
	/**
	 * Sets the time unit.
	 *
	 * @param unit the new time unit
	 */
	void setTimeUnit(TimeUnit unit);
	
	/**
	 * Gets the time unit.
	 *
	 * @return the time unit
	 */
	TimeUnit getTimeUnit();
		
	/**
	 * Track.
	 */
	void track();

	/**
	 * Gets the memory tracker.
	 *
	 * @return the memory tracker
	 */
	IMemoryTracker getMemoryTracker();

}
