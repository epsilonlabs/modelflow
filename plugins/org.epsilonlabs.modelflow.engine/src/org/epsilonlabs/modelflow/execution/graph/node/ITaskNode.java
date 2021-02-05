/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

import io.reactivex.Completable;

/**
 * The Interface ITaskNode.
 */
public interface ITaskNode extends IGraphNode {

	/**
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
	ITask getTaskElement();
	

	/**
	 * Gets the task definition.
	 *
	 * @return the task definition
	 */
	ModuleElement getModuleElement();
	
	/**
	 * Gets the observable.
	 *
	 * @return the observable
	 */
	Completable getObservable();
	
}
