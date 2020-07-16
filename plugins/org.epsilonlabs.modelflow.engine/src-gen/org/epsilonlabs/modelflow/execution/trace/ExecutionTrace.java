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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Execution Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getLatest <em>Latest</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getFile <em>File</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getExecutionTrace()
 * @model annotation="exeed classIcon='clock'"
 * @generated
 */
public interface ExecutionTrace extends EObject {
	/**
	 * Returns the value of the '<em><b>Executions</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Executions</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getExecutionTrace_Executions()
	 * @model containment="true"
	 * @generated
	 */
	EList<WorkflowExecution> getExecutions();

	/**
	 * Returns the value of the '<em><b>Latest</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Latest</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getExecutionTrace_Latest()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceSnapshot> getLatest();

	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage#getExecutionTrace_File()
	 * @model
	 * @generated
	 */
	String getFile();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(String value);

} // ExecutionTrace
