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
 * A representation of the model object '<em><b>Timed</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.Timed#getStart <em>Start</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.Timed#getEnd <em>End</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTimed()
 * @model abstract="true"
 * @generated
 */
public interface Timed extends EObject {
	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see #setStart(long)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTimed_Start()
	 * @model
	 * @generated
	 */
	long getStart();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.Timed#getStart <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' attribute.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(long value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' attribute.
	 * @see #setEnd(long)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTimed_End()
	 * @model
	 * @generated
	 */
	long getEnd();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.Timed#getEnd <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(long value);

} // Timed
