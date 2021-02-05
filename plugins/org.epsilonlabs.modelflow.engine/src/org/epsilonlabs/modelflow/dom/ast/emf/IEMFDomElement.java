/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDomElement.
 *
 * @author Betty Sanchez
 * @param <T> the generic type
 */
public interface IEMFDomElement<T> {

	/**
	 * Gets the dom elements.
	 *
	 * @return the dom elements
	 */
	Collection<T> getDomElements();

}
