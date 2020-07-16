/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.execution.trace;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stateful</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.Stateful#getEndState <em>End State</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getStateful()
 * @model abstract="true"
 * @generated
 */
public interface Stateful extends EObject {
	/**
	 * Returns the value of the '<em><b>End State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End State</em>' attribute.
	 * @see #setEndState(String)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getStateful_EndState()
	 * @model
	 * @generated
	 */
	String getEndState();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.Stateful#getEndState <em>End State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End State</em>' attribute.
	 * @see #getEndState()
	 * @generated
	 */
	void setEndState(String value);

} // Stateful
