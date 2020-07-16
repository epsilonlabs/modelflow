/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import java.util.List;

import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
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
	
}
