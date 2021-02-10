/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.management.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.TaskTrace#getTask <em>Task</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.TaskTrace#getTraces <em>Traces</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTaskTrace()
 * @model annotation="exeed classIcon='operation' label='return self.task.name + \' trace\';'"
 * @generated
 */
public interface TaskTrace extends EObject {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' attribute.
	 * @see #setTask(String)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTaskTrace_Task()
	 * @model
	 * @generated
	 */
	String getTask();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.TaskTrace#getTask <em>Task</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' attribute.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(String value);

	/**
	 * Returns the value of the '<em><b>Traces</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.management.trace.Trace}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTaskTrace_Traces()
	 * @model containment="true"
	 * @generated
	 */
	EList<Trace> getTraces();

} // TaskTrace
