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
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.execution.trace.Named;
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
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getInputModels <em>Input Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getOutputModels <em>Output Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getInputProperties <em>Input Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl#getOutputProperties <em>Output Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskExecutionImpl extends StatefulImpl implements TaskExecution {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExecutionTracePackage.TASK_EXECUTION__NAME, oldName, name));
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
			case ExecutionTracePackage.TASK_EXECUTION__NAME:
				return getName();
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				return getInputModels();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				return getOutputModels();
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				return getInputProperties();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				return getOutputProperties();
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
			case ExecutionTracePackage.TASK_EXECUTION__NAME:
				setName((String)newValue);
				return;
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
			case ExecutionTracePackage.TASK_EXECUTION__NAME:
				setName(NAME_EDEFAULT);
				return;
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
			case ExecutionTracePackage.TASK_EXECUTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_MODELS:
				return inputModels != null && !inputModels.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_MODELS:
				return outputModels != null && !outputModels.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__INPUT_PROPERTIES:
				return inputProperties != null && !inputProperties.isEmpty();
			case ExecutionTracePackage.TASK_EXECUTION__OUTPUT_PROPERTIES:
				return outputProperties != null && !outputProperties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Named.class) {
			switch (derivedFeatureID) {
				case ExecutionTracePackage.TASK_EXECUTION__NAME: return ExecutionTracePackage.NAMED__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Named.class) {
			switch (baseFeatureID) {
				case ExecutionTracePackage.NAMED__NAME: return ExecutionTracePackage.TASK_EXECUTION__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TaskExecutionImpl
