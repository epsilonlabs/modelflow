/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.management.trace.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.epsilonlabs.modelflow.management.trace.ManagementOperation;
import org.epsilonlabs.modelflow.management.trace.ManagementTracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Management Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.impl.ManagementOperationImpl#getManagementOperation <em>Management Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManagementOperationImpl extends LinkImpl implements ManagementOperation {
	/**
	 * The default value of the '{@link #getManagementOperation() <em>Management Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManagementOperation()
	 * @generated
	 * @ordered
	 */
	protected static final String MANAGEMENT_OPERATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getManagementOperation() <em>Management Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManagementOperation()
	 * @generated
	 * @ordered
	 */
	protected String managementOperation = MANAGEMENT_OPERATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManagementOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ManagementTracePackage.Literals.MANAGEMENT_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getManagementOperation() {
		return managementOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setManagementOperation(String newManagementOperation) {
		String oldManagementOperation = managementOperation;
		managementOperation = newManagementOperation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ManagementTracePackage.MANAGEMENT_OPERATION__MANAGEMENT_OPERATION, oldManagementOperation, managementOperation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ManagementTracePackage.MANAGEMENT_OPERATION__MANAGEMENT_OPERATION:
				return getManagementOperation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ManagementTracePackage.MANAGEMENT_OPERATION__MANAGEMENT_OPERATION:
				setManagementOperation((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ManagementTracePackage.MANAGEMENT_OPERATION__MANAGEMENT_OPERATION:
				setManagementOperation(MANAGEMENT_OPERATION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ManagementTracePackage.MANAGEMENT_OPERATION__MANAGEMENT_OPERATION:
				return MANAGEMENT_OPERATION_EDEFAULT == null ? managementOperation != null : !MANAGEMENT_OPERATION_EDEFAULT.equals(managementOperation);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (managementOperation: ");
		result.append(managementOperation);
		result.append(')');
		return result.toString();
	}

} //ManagementOperationImpl
