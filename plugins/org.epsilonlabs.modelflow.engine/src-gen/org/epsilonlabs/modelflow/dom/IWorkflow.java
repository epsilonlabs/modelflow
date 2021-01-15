/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The workflow definition, container of tasks, resources and dependencies.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IWorkflow#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IWorkflow#getResources <em>Resources</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IWorkflow#getTaskDependencies <em>Task Dependencies</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getWorkflow()
 * @model
 * @generated
 */
public interface IWorkflow extends ITask {
	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ITask}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of contained tasks.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getWorkflow_Tasks()
	 * @model containment="true"
	 * @generated
	 */
	EList<ITask> getTasks();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.IAbstractResource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of contained resources.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getWorkflow_Resources()
	 * @model containment="true"
	 * @generated
	 */
	EList<IAbstractResource> getResources();

	/**
	 * Returns the value of the '<em><b>Task Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ITaskDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of tasks interdependencies.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Task Dependencies</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getWorkflow_TaskDependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ITaskDependency> getTaskDependencies();

} // IWorkflow
