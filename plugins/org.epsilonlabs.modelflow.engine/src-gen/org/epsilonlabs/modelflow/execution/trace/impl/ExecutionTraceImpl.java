/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.execution.trace.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Execution Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl#getLatest <em>Latest</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl#getFile <em>File</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExecutionTraceImpl extends MinimalEObjectImpl.Container implements ExecutionTrace {
	/**
	 * The cached value of the '{@link #getExecutions() <em>Executions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutions()
	 * @generated
	 * @ordered
	 */
	protected EList<WorkflowExecution> executions;

	/**
	 * The cached value of the '{@link #getLatest() <em>Latest</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLatest()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSnapshot> latest;

	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected String file = FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutionTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionTracePackage.Literals.EXECUTION_TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<WorkflowExecution> getExecutions() {
		if (executions == null) {
			executions = new EObjectContainmentEList<WorkflowExecution>(WorkflowExecution.class, this, ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS);
		}
		return executions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceSnapshot> getLatest() {
		if (latest == null) {
			latest = new EObjectContainmentEList<ResourceSnapshot>(ResourceSnapshot.class, this, ExecutionTracePackage.EXECUTION_TRACE__LATEST);
		}
		return latest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.EXECUTION_TRACE__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS:
				return ((InternalEList<?>)getExecutions()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.EXECUTION_TRACE__LATEST:
				return ((InternalEList<?>)getLatest()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS:
				return getExecutions();
			case ExecutionTracePackage.EXECUTION_TRACE__LATEST:
				return getLatest();
			case ExecutionTracePackage.EXECUTION_TRACE__FILE:
				return getFile();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS:
				getExecutions().clear();
				getExecutions().addAll((Collection<? extends WorkflowExecution>)newValue);
				return;
			case ExecutionTracePackage.EXECUTION_TRACE__LATEST:
				getLatest().clear();
				getLatest().addAll((Collection<? extends ResourceSnapshot>)newValue);
				return;
			case ExecutionTracePackage.EXECUTION_TRACE__FILE:
				setFile((String)newValue);
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
			case ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS:
				getExecutions().clear();
				return;
			case ExecutionTracePackage.EXECUTION_TRACE__LATEST:
				getLatest().clear();
				return;
			case ExecutionTracePackage.EXECUTION_TRACE__FILE:
				setFile(FILE_EDEFAULT);
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
			case ExecutionTracePackage.EXECUTION_TRACE__EXECUTIONS:
				return executions != null && !executions.isEmpty();
			case ExecutionTracePackage.EXECUTION_TRACE__LATEST:
				return latest != null && !latest.isEmpty();
			case ExecutionTracePackage.EXECUTION_TRACE__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
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
		result.append(" (file: ");
		result.append(file);
		result.append(')');
		return result.toString();
	}

} //ExecutionTraceImpl
