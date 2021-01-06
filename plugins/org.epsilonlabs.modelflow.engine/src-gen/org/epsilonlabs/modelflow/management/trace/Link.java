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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Link#getType <em>Type</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Link#getOperation <em>Operation</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Link#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getLink()
 * @model annotation="exeed classIcon='link'"
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getLink_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.Link#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' attribute.
	 * @see #setOperation(String)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getLink_Operation()
	 * @model
	 * @generated
	 */
	String getOperation();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.Link#getOperation <em>Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' attribute.
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.management.trace.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getLink_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

} // Link
