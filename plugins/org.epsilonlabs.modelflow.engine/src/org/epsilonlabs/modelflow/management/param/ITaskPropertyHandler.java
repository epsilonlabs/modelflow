/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param;

import java.util.Map;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;

/**
 * The Interface ITaskPropertyHandler.
 *
 * @author Betty Sanchez
 */
public interface ITaskPropertyHandler {

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	ITaskInstance getTask();

	/**
	 * Checks for any.
	 *
	 * @return the boolean
	 */
	Boolean hasAny();

	/**
	 * Gets the.
	 *
	 * @return the map
	 */
	Map<String, Object> get();

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 */
	Object get(String key);

	/**
	 * Gets the hashes.
	 *
	 * @return the hashes
	 */
	Map<String, Object> getHashes();

}