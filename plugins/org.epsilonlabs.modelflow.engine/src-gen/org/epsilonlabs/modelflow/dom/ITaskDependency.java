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
 *   <li>{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getTask <em>Task</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getTaskDependency()
 * @model
 * @generated
 */
public interface ITaskDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The task
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Task</em>' reference.
	 * @see #setTask(ITask)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getTaskDependency_Task()
	 * @model
	 * @generated
	 */
	ITask getTask();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getTask <em>Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(ITask value);

	/**
	 * Returns the value of the '<em><b>Depends On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The task it dependends on
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Depends On</em>' reference.
	 * @see #setDependsOn(ITask)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getTaskDependency_DependsOn()
	 * @model
	 * @generated
	 */
	ITask getDependsOn();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getDependsOn <em>Depends On</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depends On</em>' reference.
	 * @see #getDependsOn()
	 * @generated
	 */
	void setDependsOn(ITask value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type of their dependency
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getTaskDependency_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ITaskDependency#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // ITaskDependency
