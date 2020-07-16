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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage
 * @generated
 */
public interface ExecutionTraceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExecutionTraceFactory eINSTANCE = org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Execution Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Trace</em>'.
	 * @generated
	 */
	ExecutionTrace createExecutionTrace();

	/**
	 * Returns a new object of class '<em>Resource Snapshot</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Snapshot</em>'.
	 * @generated
	 */
	ResourceSnapshot createResourceSnapshot();

	/**
	 * Returns a new object of class '<em>Property Snapshot</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Snapshot</em>'.
	 * @generated
	 */
	PropertySnapshot createPropertySnapshot();

	/**
	 * Returns a new object of class '<em>Workflow Execution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Workflow Execution</em>'.
	 * @generated
	 */
	WorkflowExecution createWorkflowExecution();

	/**
	 * Returns a new object of class '<em>Task Execution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Task Execution</em>'.
	 * @generated
	 */
	TaskExecution createTaskExecution();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExecutionTracePackage getExecutionTracePackage();

} //ExecutionTraceFactory
