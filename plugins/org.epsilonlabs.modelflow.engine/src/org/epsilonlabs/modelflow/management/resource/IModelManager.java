/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import org.epsilonlabs.modelflow.exception.MFResourceInstantiationException;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModelManager.
 *
 * @param <T> the generic type
 */
public interface IModelManager<T> {	

	/**
	 * Register.
	 *
	 * @param res the res
	 */
	void register(IModelResourceNode res);

	/**
	 * Gets the model as inout.
	 *
	 * @param r the r
	 * @return the model as inout
	 * @throws MFResourceInstantiationException the MF resource instantiation exception
	 */
	T getModelAsInout(IModelResourceNode r) throws MFResourceInstantiationException;

	/**
	 * Gets the model as output.
	 *
	 * @param r the r
	 * @return the model as output
	 * @throws MFResourceInstantiationException the MF resource instantiation exception
	 */
	T getModelAsOutput(IModelResourceNode r) throws MFResourceInstantiationException;

	/**
	 * Gets the model as input.
	 *
	 * @param r the r
	 * @return the model as input
	 * @throws MFResourceInstantiationException the MF resource instantiation exception
	 */
	T getModelAsInput(IModelResourceNode r) throws MFResourceInstantiationException;

	/**
	 * Dispose.
	 *
	 * @param r the r
	 */
	void dispose(IModelResourceNode r);

	/**
	 * Reset.
	 */
	void reset();
}
