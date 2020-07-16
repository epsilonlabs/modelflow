/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.execution.trace.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.epsilonlabs.modelflow.execution.trace.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExecutionTraceFactoryImpl extends EFactoryImpl implements ExecutionTraceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExecutionTraceFactory init() {
		try {
			ExecutionTraceFactory theExecutionTraceFactory = (ExecutionTraceFactory)EPackage.Registry.INSTANCE.getEFactory(ExecutionTracePackage.eNS_URI);
			if (theExecutionTraceFactory != null) {
				return theExecutionTraceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExecutionTraceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionTraceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ExecutionTracePackage.EXECUTION_TRACE: return createExecutionTrace();
			case ExecutionTracePackage.RESOURCE_SNAPSHOT: return createResourceSnapshot();
			case ExecutionTracePackage.PROPERTY_SNAPSHOT: return createPropertySnapshot();
			case ExecutionTracePackage.WORKFLOW_EXECUTION: return createWorkflowExecution();
			case ExecutionTracePackage.TASK_EXECUTION: return createTaskExecution();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExecutionTrace createExecutionTrace() {
		ExecutionTraceImpl executionTrace = new ExecutionTraceImpl();
		return executionTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceSnapshot createResourceSnapshot() {
		ResourceSnapshotImpl resourceSnapshot = new ResourceSnapshotImpl();
		return resourceSnapshot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PropertySnapshot createPropertySnapshot() {
		PropertySnapshotImpl propertySnapshot = new PropertySnapshotImpl();
		return propertySnapshot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WorkflowExecution createWorkflowExecution() {
		WorkflowExecutionImpl workflowExecution = new WorkflowExecutionImpl();
		return workflowExecution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TaskExecution createTaskExecution() {
		TaskExecutionImpl taskExecution = new TaskExecutionImpl();
		return taskExecution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExecutionTracePackage getExecutionTracePackage() {
		return (ExecutionTracePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExecutionTracePackage getPackage() {
		return ExecutionTracePackage.eINSTANCE;
	}

} //ExecutionTraceFactoryImpl
