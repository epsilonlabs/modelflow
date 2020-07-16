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

import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.DomPackage;
import org.epsilonlabs.modelflow.dom.ResourceDependency;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.TaskDependency;
import org.epsilonlabs.modelflow.dom.Workflow;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workflow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl#getTaskDependencies <em>Task Dependencies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl#getResourceDependencies <em>Resource Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkflowImpl extends TaskImpl implements Workflow {
	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<Task> tasks;

	/**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractResource> resources;

	/**
	 * The cached value of the '{@link #getTaskDependencies() <em>Task Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskDependency> taskDependencies;

	/**
	 * The cached value of the '{@link #getResourceDependencies() <em>Resource Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceDependency> resourceDependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkflowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.WORKFLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Task> getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList<Task>(Task.class, this, DomPackage.WORKFLOW__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractResource> getResources() {
		if (resources == null) {
			resources = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, DomPackage.WORKFLOW__RESOURCES);
		}
		return resources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskDependency> getTaskDependencies() {
		if (taskDependencies == null) {
			taskDependencies = new EObjectContainmentEList<TaskDependency>(TaskDependency.class, this, DomPackage.WORKFLOW__TASK_DEPENDENCIES);
		}
		return taskDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceDependency> getResourceDependencies() {
		if (resourceDependencies == null) {
			resourceDependencies = new EObjectContainmentEList<ResourceDependency>(ResourceDependency.class, this, DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES);
		}
		return resourceDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.WORKFLOW__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
			case DomPackage.WORKFLOW__RESOURCES:
				return ((InternalEList<?>)getResources()).basicRemove(otherEnd, msgs);
			case DomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return ((InternalEList<?>)getTaskDependencies()).basicRemove(otherEnd, msgs);
			case DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES:
				return ((InternalEList<?>)getResourceDependencies()).basicRemove(otherEnd, msgs);
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
			case DomPackage.WORKFLOW__TASKS:
				return getTasks();
			case DomPackage.WORKFLOW__RESOURCES:
				return getResources();
			case DomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return getTaskDependencies();
			case DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES:
				return getResourceDependencies();
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
			case DomPackage.WORKFLOW__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends Task>)newValue);
				return;
			case DomPackage.WORKFLOW__RESOURCES:
				getResources().clear();
				getResources().addAll((Collection<? extends AbstractResource>)newValue);
				return;
			case DomPackage.WORKFLOW__TASK_DEPENDENCIES:
				getTaskDependencies().clear();
				getTaskDependencies().addAll((Collection<? extends TaskDependency>)newValue);
				return;
			case DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES:
				getResourceDependencies().clear();
				getResourceDependencies().addAll((Collection<? extends ResourceDependency>)newValue);
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
			case DomPackage.WORKFLOW__TASKS:
				getTasks().clear();
				return;
			case DomPackage.WORKFLOW__RESOURCES:
				getResources().clear();
				return;
			case DomPackage.WORKFLOW__TASK_DEPENDENCIES:
				getTaskDependencies().clear();
				return;
			case DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES:
				getResourceDependencies().clear();
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
			case DomPackage.WORKFLOW__TASKS:
				return tasks != null && !tasks.isEmpty();
			case DomPackage.WORKFLOW__RESOURCES:
				return resources != null && !resources.isEmpty();
			case DomPackage.WORKFLOW__TASK_DEPENDENCIES:
				return taskDependencies != null && !taskDependencies.isEmpty();
			case DomPackage.WORKFLOW__RESOURCE_DEPENDENCIES:
				return resourceDependencies != null && !resourceDependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WorkflowImpl
