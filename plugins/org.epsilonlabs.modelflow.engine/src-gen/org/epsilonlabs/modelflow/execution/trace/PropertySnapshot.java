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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Snapshot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.PropertySnapshot#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getPropertySnapshot()
 * @model annotation="exeed classIcon='parameter' label='return self.key + \'=\' + self.stamp;'"
 * @generated
 */
public interface PropertySnapshot extends Snapshot {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getPropertySnapshot_Key()
	 * @model
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.PropertySnapshot#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

} // PropertySnapshot
