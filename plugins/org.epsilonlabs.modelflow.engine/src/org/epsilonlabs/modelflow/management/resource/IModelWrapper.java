/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import java.util.Collection;

import org.epsilonlabs.modelflow.dom.IAbstractResource;

/**
 * The Interface IModelWrapper.
 */
public interface IModelWrapper {

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	Object getModel();
	
	/**
	 * Gets the resource kind.
	 *
	 * @return the resource kind
	 */
	ResourceKind getResourceKind();

	/**
	 * Gets the aliases.
	 *
	 * @return the aliases
	 */
	Collection<String> getAliases();
	
	/**
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	IAbstractResource getResource();
}
