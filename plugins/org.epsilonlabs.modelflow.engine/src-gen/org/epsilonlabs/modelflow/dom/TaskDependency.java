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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An explicit task-task dependency
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getTask <em>Task</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency()
 * @model
 * @generated
 */
public interface TaskDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The task
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Task</em>' reference.
	 * @see #setTask(Task)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_Task()
	 * @model
	 * @generated
	 */
	Task getTask();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getTask <em>Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(Task value);

	/**
	 * Returns the value of the '<em><b>Depends On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The task it dependends on
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Depends On</em>' reference.
	 * @see #setDependsOn(Task)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_DependsOn()
	 * @model
	 * @generated
	 */
	Task getDependsOn();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getDependsOn <em>Depends On</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depends On</em>' reference.
	 * @see #getDependsOn()
	 * @generated
	 */
	void setDependsOn(Task value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type of their dependency
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // TaskDependency
