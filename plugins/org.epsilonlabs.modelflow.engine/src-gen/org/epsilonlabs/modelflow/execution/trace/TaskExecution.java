/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.execution.trace;

import org.eclipse.emf.common.util.EList;

import org.epsilonlabs.modelflow.dom.Task;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Execution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputModels <em>Input Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputModels <em>Output Models</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputProperties <em>Input Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputProperties <em>Output Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getTask <em>Task</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution()
 * @model annotation="exeed classIcon='operation' label='return self.task.name \n+ \' (\' + self.endState +\') \';'"
 * @generated
 */
public interface TaskExecution extends Stateful {
	/**
	 * Returns the value of the '<em><b>Input Models</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Models</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution_InputModels()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceSnapshot> getInputModels();

	/**
	 * Returns the value of the '<em><b>Output Models</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Models</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution_OutputModels()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceSnapshot> getOutputModels();

	/**
	 * Returns the value of the '<em><b>Input Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.PropertySnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution_InputProperties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertySnapshot> getInputProperties();

	/**
	 * Returns the value of the '<em><b>Output Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.PropertySnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution_OutputProperties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertySnapshot> getOutputProperties();

	/**
	 * Returns the value of the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' containment reference.
	 * @see #setTask(Task)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getTaskExecution_Task()
	 * @model containment="true"
	 * @generated
	 */
	Task getTask();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getTask <em>Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' containment reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(Task value);

} // TaskExecution
