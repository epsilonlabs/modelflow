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
 * A representation of the model object '<em><b>Snapshot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getStamp <em>Stamp</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getTimestamp <em>Timestamp</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getSnapshot()
 * @model abstract="true"
 * @generated
 */
public interface Snapshot extends EObject {
	/**
	 * Returns the value of the '<em><b>Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stamp</em>' attribute.
	 * @see #setStamp(Object)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getSnapshot_Stamp()
	 * @model
	 * @generated
	 */
	Object getStamp();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getStamp <em>Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stamp</em>' attribute.
	 * @see #getStamp()
	 * @generated
	 */
	void setStamp(Object value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Long)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getSnapshot_Timestamp()
	 * @model
	 * @generated
	 */
	Long getTimestamp();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Long value);

} // Snapshot
