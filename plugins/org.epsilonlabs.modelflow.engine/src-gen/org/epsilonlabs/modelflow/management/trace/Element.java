/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.management.trace;

import org.eclipse.emf.ecore.EObject;

import org.epsilonlabs.modelflow.dom.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Element#getContainer <em>Container</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface Element extends EObject {
	/**
	 * Returns the value of the '<em><b>Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container</em>' containment reference.
	 * @see #setContainer(Resource)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getElement_Container()
	 * @model containment="true"
	 * @generated
	 */
	Resource getContainer();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.Element#getContainer <em>Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' containment reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(Resource value);

} // Element
