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

import org.epsilonlabs.modelflow.dom.Task;

import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.execution.trace.PropertySnapshot;
import org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Execution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getInputModels <em>Input Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getOutputModels <em>Output Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getInputProperties <em>Input Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getOutputProperties <em>Output Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getTask <em>Task</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskExecutionImpl extends StatefulImpl implements TaskExecution {
	/**
	 * The cached value of the '{@link #getInputModels() <em>Input Models</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputModels()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSnapshot> inputModels;

	/**
	 * The cached value of the '{@link #getOutputModels() <em>Output Models</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputModels()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceSnapshot> outputModels;

	/**
	 * The cached value of the '{@link #getInputProperties() <em>Input Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertySnapshot> inputProperties;

	/**
	 * The cached value of the '{@link #getOutputProperties() <em>Output Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertySnapshot> outputProperties;

	/**
	 * The cached value of the '{@link #getTask() <em>Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTask()
	 * @generated
	 * @ordered
	 */
	protected Task task;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskExecutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionTracePackage.Literals.TASK_EXECUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceSnapshot> getInputModels() {
		if (inputModels == null) {
			inputModels = new EObjectContainmentEList<ResourceSnapshot>(ResourceSnapshot.class, this, ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS);
		}
		return inputModels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceSnapshot> getOutputModels() {
		if (outputModels == null) {
			outputModels = new EObjectContainmentEList<ResourceSnapshot>(ResourceSnapshot.class, this, ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS);
		}
		return outputModels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PropertySnapshot> getInputProperties() {
		if (inputProperties == null) {
			inputProperties = new EObjectContainmentEList<PropertySnapshot>(PropertySnapshot.class, this, ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES);
		}
		return inputProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PropertySnapshot> getOutputProperties() {
		if (outputProperties == null) {
			outputProperties = new EObjectContainmentEList<PropertySnapshot>(PropertySnapshot.class, this, ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES);
		}
		return outputProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Task getTask() {
		return task;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTask(Task newTask, NotificationChain msgs) {
		Task oldTask = task;
		task = newTask;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.TASK_EXECUTION__TASK, oldTask, newTask);
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
	public void setTask(Task newTask) {
		if (newTask != task) {
			NotificationChain msgs = null;
			if (task != null)
				msgs = ((InternalEObject)task).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExecutionTracePackage.TASK_EXECUTION__TASK, null, msgs);
			if (newTask != null)
				msgs = ((InternalEObject)newTask).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExecutionTracePackage.TASK_EXECUTION__TASK, null, msgs);
			msgs = basicSetTask(newTask, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.TASK_EXECUTION__TASK, newTask, newTask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				return ((InternalEList<?>)getInputModels()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				return ((InternalEList<?>)getOutputModels()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				return ((InternalEList<?>)getInputProperties()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				return ((InternalEList<?>)getOutputProperties()).basicRemove(otherEnd, msgs);
			case ExecutionTracePackage.TASK_EXECUTION__TASK:
				return basicSetTask(null, msgs);
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
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				return getInputModels();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				return getOutputModels();
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				return getInputProperties();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				return getOutputProperties();
			case ExecutionTracePackage.TASK_EXECUTION__TASK:
				return getTask();
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
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				getInputModels().clear();
				getInputModels().addAll((Collection<? extends ResourceSnapshot>)newValue);
				return;
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				getOutputModels().clear();
				getOutputModels().addAll((Collection<? extends ResourceSnapshot>)newValue);
				return;
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				getInputProperties().clear();
				getInputProperties().addAll((Collection<? extends PropertySnapshot>)newValue);
				return;
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				getOutputProperties().clear();
				getOutputProperties().addAll((Collection<? extends PropertySnapshot>)newValue);
				return;
			case ExecutionTracePackage.TASK_EXECUTION__TASK:
				setTask((Task)newValue);
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
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				getInputModels().clear();
				return;
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				getOutputModels().clear();
				return;
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				getInputProperties().clear();
				return;
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				getOutputProperties().clear();
				return;
			case ExecutionTracePackage.TASK_EXECUTION__TASK:
				setTask((Task)null);
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
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				return inputModels != null && !inputModels.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				return outputModels != null && !outputModels.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				return inputProperties != null && !inputProperties.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				return outputProperties != null && !outputProperties.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__TASK:
				return task != null;
		}
		return super.eIsSet(featureID);
	}

} //TaskExecutionImpl
