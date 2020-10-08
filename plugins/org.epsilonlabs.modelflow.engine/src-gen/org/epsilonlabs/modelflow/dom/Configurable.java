/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configurable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Configurable#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Configurable#getModuleElement <em>Module Element</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getConfigurable()
 * @model abstract="true"
 * @generated
 */
public interface Configurable extends Named {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getConfigurable_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Element</em>' attribute.
	 * @see #setModuleElement(Object)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getConfigurable_ModuleElement()
	 * @model transient="true"
	 * @generated
	 */
	Object getModuleElement();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Configurable#getModuleElement <em>Module Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Element</em>' attribute.
	 * @see #getModuleElement()
	 * @generated
	 */
	void setModuleElement(Object value);

} // Configurable
