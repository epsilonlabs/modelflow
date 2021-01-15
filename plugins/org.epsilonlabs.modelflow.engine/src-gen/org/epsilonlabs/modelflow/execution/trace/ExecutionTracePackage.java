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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTraceFactory
 * @model kind="package"
 * @generated
 */
public interface ExecutionTracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "trace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/epsilon/modelflow/exec/trace/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "executionTrace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExecutionTracePackage eINSTANCE = org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl <em>Execution Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getExecutionTrace()
	 * @generated
	 */
	int EXECUTION_TRACE = 0;

	/**
	 * The feature id for the '<em><b>Executions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE__EXECUTIONS = 0;

	/**
	 * The feature id for the '<em><b>Latest</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE__LATEST = 1;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE__FILE = 2;

	/**
	 * The number of structural features of the '<em>Execution Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Execution Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.StatefulImpl <em>Stateful</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.StatefulImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getStateful()
	 * @generated
	 */
	int STATEFUL = 1;

	/**
	 * The feature id for the '<em><b>End State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEFUL__END_STATE = 0;

	/**
	 * The number of structural features of the '<em>Stateful</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEFUL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Stateful</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEFUL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.NamedImpl <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.NamedImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getNamed()
	 * @generated
	 */
	int NAMED = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.SnapshotImpl <em>Snapshot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.SnapshotImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getSnapshot()
	 * @generated
	 */
	int SNAPSHOT = 3;

	/**
	 * The feature id for the '<em><b>Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNAPSHOT__STAMP = 0;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNAPSHOT__TIMESTAMP = 1;

	/**
	 * The number of structural features of the '<em>Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNAPSHOT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNAPSHOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.ResourceSnapshotImpl <em>Resource Snapshot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ResourceSnapshotImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getResourceSnapshot()
	 * @generated
	 */
	int RESOURCE_SNAPSHOT = 4;

	/**
	 * The feature id for the '<em><b>Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SNAPSHOT__STAMP = SNAPSHOT__STAMP;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SNAPSHOT__TIMESTAMP = SNAPSHOT__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SNAPSHOT__NAME = SNAPSHOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Resource Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SNAPSHOT_FEATURE_COUNT = SNAPSHOT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SNAPSHOT_OPERATION_COUNT = SNAPSHOT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.PropertySnapshotImpl <em>Property Snapshot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.PropertySnapshotImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getPropertySnapshot()
	 * @generated
	 */
	int PROPERTY_SNAPSHOT = 5;

	/**
	 * The feature id for the '<em><b>Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SNAPSHOT__STAMP = SNAPSHOT__STAMP;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SNAPSHOT__TIMESTAMP = SNAPSHOT__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SNAPSHOT__NAME = SNAPSHOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SNAPSHOT_FEATURE_COUNT = SNAPSHOT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Property Snapshot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SNAPSHOT_OPERATION_COUNT = SNAPSHOT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl <em>Workflow Execution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getWorkflowExecution()
	 * @generated
	 */
	int WORKFLOW_EXECUTION = 6;

	/**
	 * The feature id for the '<em><b>End State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION__END_STATE = STATEFUL__END_STATE;

	/**
	 * The feature id for the '<em><b>Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION__STAMP = STATEFUL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION__TIMESTAMP = STATEFUL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION__TASKS = STATEFUL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Workflow Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION_FEATURE_COUNT = STATEFUL_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Workflow Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_EXECUTION_OPERATION_COUNT = STATEFUL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl <em>Task Execution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl
	 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getTaskExecution()
	 * @generated
	 */
	int TASK_EXECUTION = 7;

	/**
	 * The feature id for the '<em><b>End State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__END_STATE = STATEFUL__END_STATE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__NAME = STATEFUL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Input Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__INPUT_MODELS = STATEFUL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__OUTPUT_MODELS = STATEFUL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Input Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__INPUT_PROPERTIES = STATEFUL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Output Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION__OUTPUT_PROPERTIES = STATEFUL_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Task Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION_FEATURE_COUNT = STATEFUL_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Task Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_EXECUTION_OPERATION_COUNT = STATEFUL_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace <em>Execution Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Trace</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTrace
	 * @generated
	 */
	EClass getExecutionTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getExecutions <em>Executions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Executions</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getExecutions()
	 * @see #getExecutionTrace()
	 * @generated
	 */
	EReference getExecutionTrace_Executions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getLatest <em>Latest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Latest</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getLatest()
	 * @see #getExecutionTrace()
	 * @generated
	 */
	EReference getExecutionTrace_Latest();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.ExecutionTrace#getFile()
	 * @see #getExecutionTrace()
	 * @generated
	 */
	EAttribute getExecutionTrace_File();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.Stateful <em>Stateful</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stateful</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Stateful
	 * @generated
	 */
	EClass getStateful();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.execution.trace.Stateful#getEndState <em>End State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End State</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Stateful#getEndState()
	 * @see #getStateful()
	 * @generated
	 */
	EAttribute getStateful_EndState();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Named
	 * @generated
	 */
	EClass getNamed();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.execution.trace.Named#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Named#getName()
	 * @see #getNamed()
	 * @generated
	 */
	EAttribute getNamed_Name();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.Snapshot <em>Snapshot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snapshot</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Snapshot
	 * @generated
	 */
	EClass getSnapshot();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getStamp <em>Stamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stamp</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Snapshot#getStamp()
	 * @see #getSnapshot()
	 * @generated
	 */
	EAttribute getSnapshot_Stamp();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.execution.trace.Snapshot#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.Snapshot#getTimestamp()
	 * @see #getSnapshot()
	 * @generated
	 */
	EAttribute getSnapshot_Timestamp();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot <em>Resource Snapshot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Snapshot</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.ResourceSnapshot
	 * @generated
	 */
	EClass getResourceSnapshot();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.PropertySnapshot <em>Property Snapshot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Snapshot</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.PropertySnapshot
	 * @generated
	 */
	EClass getPropertySnapshot();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution <em>Workflow Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workflow Execution</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.WorkflowExecution
	 * @generated
	 */
	EClass getWorkflowExecution();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.WorkflowExecution#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.WorkflowExecution#getTasks()
	 * @see #getWorkflowExecution()
	 * @generated
	 */
	EReference getWorkflowExecution_Tasks();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution <em>Task Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Execution</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.TaskExecution
	 * @generated
	 */
	EClass getTaskExecution();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputModels <em>Input Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Models</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputModels()
	 * @see #getTaskExecution()
	 * @generated
	 */
	EReference getTaskExecution_InputModels();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputModels <em>Output Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Models</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputModels()
	 * @see #getTaskExecution()
	 * @generated
	 */
	EReference getTaskExecution_OutputModels();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputProperties <em>Input Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Properties</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.TaskExecution#getInputProperties()
	 * @see #getTaskExecution()
	 * @generated
	 */
	EReference getTaskExecution_InputProperties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputProperties <em>Output Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Properties</em>'.
	 * @see org.epsilonlabs.modelflow.execution.trace.TaskExecution#getOutputProperties()
	 * @see #getTaskExecution()
	 * @generated
	 */
	EReference getTaskExecution_OutputProperties();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExecutionTraceFactory getExecutionTraceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl <em>Execution Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getExecutionTrace()
		 * @generated
		 */
		EClass EXECUTION_TRACE = eINSTANCE.getExecutionTrace();

		/**
		 * The meta object literal for the '<em><b>Executions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_TRACE__EXECUTIONS = eINSTANCE.getExecutionTrace_Executions();

		/**
		 * The meta object literal for the '<em><b>Latest</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_TRACE__LATEST = eINSTANCE.getExecutionTrace_Latest();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_TRACE__FILE = eINSTANCE.getExecutionTrace_File();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.StatefulImpl <em>Stateful</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.StatefulImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getStateful()
		 * @generated
		 */
		EClass STATEFUL = eINSTANCE.getStateful();

		/**
		 * The meta object literal for the '<em><b>End State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATEFUL__END_STATE = eINSTANCE.getStateful_EndState();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.NamedImpl <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.NamedImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getNamed()
		 * @generated
		 */
		EClass NAMED = eINSTANCE.getNamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED__NAME = eINSTANCE.getNamed_Name();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.SnapshotImpl <em>Snapshot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.SnapshotImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getSnapshot()
		 * @generated
		 */
		EClass SNAPSHOT = eINSTANCE.getSnapshot();

		/**
		 * The meta object literal for the '<em><b>Stamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SNAPSHOT__STAMP = eINSTANCE.getSnapshot_Stamp();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SNAPSHOT__TIMESTAMP = eINSTANCE.getSnapshot_Timestamp();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.ResourceSnapshotImpl <em>Resource Snapshot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ResourceSnapshotImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getResourceSnapshot()
		 * @generated
		 */
		EClass RESOURCE_SNAPSHOT = eINSTANCE.getResourceSnapshot();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.PropertySnapshotImpl <em>Property Snapshot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.PropertySnapshotImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getPropertySnapshot()
		 * @generated
		 */
		EClass PROPERTY_SNAPSHOT = eINSTANCE.getPropertySnapshot();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl <em>Workflow Execution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.WorkflowExecutionImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getWorkflowExecution()
		 * @generated
		 */
		EClass WORKFLOW_EXECUTION = eINSTANCE.getWorkflowExecution();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW_EXECUTION__TASKS = eINSTANCE.getWorkflowExecution_Tasks();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl <em>Task Execution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.TaskExecutionImpl
		 * @see org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTracePackageImpl#getTaskExecution()
		 * @generated
		 */
		EClass TASK_EXECUTION = eINSTANCE.getTaskExecution();

		/**
		 * The meta object literal for the '<em><b>Input Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_EXECUTION__INPUT_MODELS = eINSTANCE.getTaskExecution_InputModels();

		/**
		 * The meta object literal for the '<em><b>Output Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_EXECUTION__OUTPUT_MODELS = eINSTANCE.getTaskExecution_OutputModels();

		/**
		 * The meta object literal for the '<em><b>Input Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_EXECUTION__INPUT_PROPERTIES = eINSTANCE.getTaskExecution_InputProperties();

		/**
		 * The meta object literal for the '<em><b>Output Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_EXECUTION__OUTPUT_PROPERTIES = eINSTANCE.getTaskExecution_OutputProperties();

	}

} //ExecutionTracePackage
