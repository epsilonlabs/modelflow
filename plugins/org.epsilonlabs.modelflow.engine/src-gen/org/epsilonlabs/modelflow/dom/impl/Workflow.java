/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IDomPackage;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.IWorkflow;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workflow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Workflow#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Workflow#getResources <em>Resources</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Workflow#getTaskDependencies <em>Task Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Workflow extends Task implements IWorkflow {
	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<ITask> tasks;

	/**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbstractResource> resources;

	/**
	 * The cached value of the '{@link #getTaskDependencies() <em>Task Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ITaskDependency> taskDependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Workflow() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IDomPackage.Literals.WORKFLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITask> getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList<ITask>(ITask.class, this, IDomPackage.WORKFLOW__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAbstractResource> getResources() {
		if (resources == null) {
			resources = new EObjectContainmentEList<IAbstractResource>(IAbstractResource.class, this, IDomPackage.WORKFLOW__RESOURCES);
		}
		return resources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITaskDependency> getTaskDependencies() {
		if (taskDependencies == null) {
			taskDependencies = new EObjectContainmentEList<ITaskDependency>(ITaskDependency.class, this, IDomPackage.WORKFLOW__TASK_DEPENDENCIES);
		}
		return taskDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IDomPackage.WORKFLOW__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
			case IDomPackage.WORKFLOW__RESOURCES:
				return ((InternalEList<?>)getResources()).basicRemove(otherEnd, msgs);
			case IDomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return ((InternalEList<?>)getTaskDependencies()).basicRemove(otherEnd, msgs);
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
			case IDomPackage.WORKFLOW__TASKS:
				return getTasks();
			case IDomPackage.WORKFLOW__RESOURCES:
				return getResources();
			case IDomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return getTaskDependencies();
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
			case IDomPackage.WORKFLOW__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends ITask>)newValue);
				return;
			case IDomPackage.WORKFLOW__RESOURCES:
				getResources().clear();
				getResources().addAll((Collection<? extends IAbstractResource>)newValue);
				return;
			case IDomPackage.WORKFLOW__TASK_DEPENDENCIES:
				getTaskDependencies().clear();
				getTaskDependencies().addAll((Collection<? extends ITaskDependency>)newValue);
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
			case IDomPackage.WORKFLOW__TASKS:
				getTasks().clear();
				return;
			case IDomPackage.WORKFLOW__RESOURCES:
				getResources().clear();
				return;
			case IDomPackage.WORKFLOW__TASK_DEPENDENCIES:
				getTaskDependencies().clear();
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
			case IDomPackage.WORKFLOW__TASKS:
				return tasks != null && !tasks.isEmpty();
			case IDomPackage.WORKFLOW__RESOURCES:
				return resources != null && !resources.isEmpty();
			case IDomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return taskDependencies != null && !taskDependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //Workflow
