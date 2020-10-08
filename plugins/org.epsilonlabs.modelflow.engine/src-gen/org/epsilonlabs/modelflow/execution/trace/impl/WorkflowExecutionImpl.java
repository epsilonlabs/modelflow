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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.epsilonlabs.modelflow.dom.Workflow;

import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workflow Execution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl#getWorkflow <em>Workflow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkflowExecutionImpl extends StatefulImpl implements WorkflowExecution {
	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskExecution> tasks;

	/**
	 * The cached value of the '{@link #getWorkflow() <em>Workflow</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkflow()
	 * @generated
	 * @ordered
	 */
	protected Workflow workflow;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkflowExecutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionTracePackage.Literals.WORKFLOW_EXECUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskExecution> getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList<TaskExecution>(TaskExecution.class, this, ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Workflow getWorkflow() {
		return workflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkflow(Workflow newWorkflow, NotificationChain msgs) {
		Workflow oldWorkflow = workflow;
		workflow = newWorkflow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW, oldWorkflow, newWorkflow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWorkflow(Workflow newWorkflow) {
		if (newWorkflow != workflow) {
			NotificationChain msgs = null;
			if (workflow != null)
				msgs = ((InternalEObject)workflow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW, null, msgs);
			if (newWorkflow != null)
				msgs = ((InternalEObject)newWorkflow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW, null, msgs);
			msgs = basicSetWorkflow(newWorkflow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW, newWorkflow, newWorkflow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW:
				return basicSetWorkflow(null, msgs);
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
			case ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS:
				return getTasks();
			case ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW:
				return getWorkflow();
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
			case ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends TaskExecution>)newValue);
				return;
			case ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW:
				setWorkflow((Workflow)newValue);
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
			case ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS:
				getTasks().clear();
				return;
			case ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW:
				setWorkflow((Workflow)null);
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
			case ExecutionTracePackage.WORKFLOW_EXECUTION__TASKS:
				return tasks != null && !tasks.isEmpty();
			case ExecutionTracePackage.WORKFLOW_EXECUTION__WORKFLOW:
				return workflow != null;
		}
		return super.eIsSet(featureID);
	}

} //WorkflowExecutionImpl
