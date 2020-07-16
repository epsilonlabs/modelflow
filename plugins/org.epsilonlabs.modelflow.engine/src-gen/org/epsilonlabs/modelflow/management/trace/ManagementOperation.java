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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Management Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.ManagementOperation#getManagementOperation <em>Management Operation</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getManagementOperation()
 * @model annotation="exeed classIcon='operation' label='return self.link + \':\' + self.managementOperation;'"
 * @generated
 */
public interface ManagementOperation extends Link {
	/**
	 * Returns the value of the '<em><b>Management Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Management Operation</em>' attribute.
	 * @see #setManagementOperation(String)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getManagementOperation_ManagementOperation()
	 * @model
	 * @generated
	 */
	String getManagementOperation();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.ManagementOperation#getManagementOperation <em>Management Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Management Operation</em>' attribute.
	 * @see #getManagementOperation()
	 * @generated
	 */
	void setManagementOperation(String value);

} // ManagementOperation
