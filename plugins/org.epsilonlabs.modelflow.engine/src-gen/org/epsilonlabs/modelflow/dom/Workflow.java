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
 *   <li>{@link org.epsilonlabs.modelflow.dom.Workflow#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Workflow#getResources <em>Resources</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Workflow#getTaskDependencies <em>Task Dependencies</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getWorkflow()
 * @model
 * @generated
 */
public interface Workflow extends Task {
	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.Task}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of contained tasks.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getWorkflow_Tasks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Task> getTasks();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.AbstractResource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of contained resources.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getWorkflow_Resources()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractResource> getResources();

	/**
	 * Returns the value of the '<em><b>Task Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.TaskDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of tasks interdependencies.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Task Dependencies</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getWorkflow_TaskDependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<TaskDependency> getTaskDependencies();

} // Workflow
