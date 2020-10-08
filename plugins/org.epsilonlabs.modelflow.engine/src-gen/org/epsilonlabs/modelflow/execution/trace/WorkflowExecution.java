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

import org.epsilonlabs.modelflow.dom.Workflow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workflow Execution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution#getWorkflow <em>Workflow</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getWorkflowExecution()
 * @model annotation="exeed classIcon='operation' label='return \'Workflow\' \n+ \' (\' + self.endState +\') \';'"
 * @generated
 */
public interface WorkflowExecution extends Stateful {
	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.TaskExecution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getWorkflowExecution_Tasks()
	 * @model containment="true"
	 * @generated
	 */
	EList<TaskExecution> getTasks();

	/**
	 * Returns the value of the '<em><b>Workflow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workflow</em>' containment reference.
	 * @see #setWorkflow(Workflow)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getWorkflowExecution_Workflow()
	 * @model containment="true"
	 * @generated
	 */
	Workflow getWorkflow();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution#getWorkflow <em>Workflow</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workflow</em>' containment reference.
	 * @see #getWorkflow()
	 * @generated
	 */
	void setWorkflow(Workflow value);

} // WorkflowExecution
