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
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTraceFactory
 * @model kind="package"
 * @generated
 */
public interface ManagementTracePackage extends EPackage {
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
	String eNS_URI = "http://eclipse.org/epsilon/modelflow/management/trace/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "managementTrace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ManagementTracePackage eINSTANCE = org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceImpl <em>Management Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getManagementTrace()
	 * @generated
	 */
	int MANAGEMENT_TRACE = 0;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGEMENT_TRACE__TASKS = 0;

	/**
	 * The number of structural features of the '<em>Management Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGEMENT_TRACE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Management Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGEMENT_TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.TaskTraceImpl <em>Task Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.TaskTraceImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getTaskTrace()
	 * @generated
	 */
	int TASK_TRACE = 1;

	/**
	 * The feature id for the '<em><b>Task</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_TRACE__TASK = 0;

	/**
	 * The feature id for the '<em><b>Traces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_TRACE__TRACES = 1;

	/**
	 * The number of structural features of the '<em>Task Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_TRACE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Task Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.TraceImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 2;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__SOURCES = 0;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__TARGETS = 1;

	/**
	 * The feature id for the '<em><b>Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__LINK = 2;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ElementImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ROLE = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ModelElementImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__RESOURCE = ELEMENT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__ROLE = ELEMENT__ROLE;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__ELEMENT_ID = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ModelElementPropertyImpl <em>Model Element Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ModelElementPropertyImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getModelElementProperty()
	 * @generated
	 */
	int MODEL_ELEMENT_PROPERTY = 5;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY__RESOURCE = MODEL_ELEMENT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY__ROLE = MODEL_ELEMENT__ROLE;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY__ELEMENT_ID = MODEL_ELEMENT__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY__PROPERTY = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY__VALUE = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Model Element Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Model Element Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_PROPERTY_OPERATION_COUNT = MODEL_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.FileElementImpl <em>File Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.FileElementImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getFileElement()
	 * @generated
	 */
	int FILE_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ELEMENT__RESOURCE = ELEMENT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ELEMENT__ROLE = ELEMENT__ROLE;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ELEMENT__REGION = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>File Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>File Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ELEMENT_OPERATION_COUNT = ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.RegionImpl <em>Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.RegionImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getRegion()
	 * @generated
	 */
	int REGION = 7;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__OFFSET = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__LENGTH = 1;

	/**
	 * The number of structural features of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.LinkImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getLink()
	 * @generated
	 */
	int LINK = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__OPERATION = 1;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__PROPERTIES = 2;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.management.trace.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.management.trace.impl.PropertyImpl
	 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 9;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.ManagementTrace <em>Management Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Management Trace</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTrace
	 * @generated
	 */
	EClass getManagementTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.management.trace.ManagementTrace#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTrace#getTasks()
	 * @see #getManagementTrace()
	 * @generated
	 */
	EReference getManagementTrace_Tasks();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.TaskTrace <em>Task Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Trace</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.TaskTrace
	 * @generated
	 */
	EClass getTaskTrace();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.TaskTrace#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.TaskTrace#getTask()
	 * @see #getTaskTrace()
	 * @generated
	 */
	EAttribute getTaskTrace_Task();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.management.trace.TaskTrace#getTraces <em>Traces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.TaskTrace#getTraces()
	 * @see #getTaskTrace()
	 * @generated
	 */
	EReference getTaskTrace_Traces();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.management.trace.Trace#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sources</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Trace#getSources()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Sources();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.management.trace.Trace#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Targets</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Trace#getTargets()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Targets();

	/**
	 * Returns the meta object for the containment reference '{@link org.epsilonlabs.modelflow.management.trace.Trace#getLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Link</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Trace#getLink()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Link();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Element#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Element#getResource()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Resource();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Element#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Element#getRole()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Role();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.ModelElement#getElementId <em>Element Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Id</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ModelElement#getElementId()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_ElementId();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty <em>Model Element Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element Property</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ModelElementProperty
	 * @generated
	 */
	EClass getModelElementProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getProperty()
	 * @see #getModelElementProperty()
	 * @generated
	 */
	EAttribute getModelElementProperty_Property();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getValue()
	 * @see #getModelElementProperty()
	 * @generated
	 */
	EAttribute getModelElementProperty_Value();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.FileElement <em>File Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Element</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.FileElement
	 * @generated
	 */
	EClass getFileElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.epsilonlabs.modelflow.management.trace.FileElement#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Region</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.FileElement#getRegion()
	 * @see #getFileElement()
	 * @generated
	 */
	EReference getFileElement_Region();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.Region <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Region</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Region
	 * @generated
	 */
	EClass getRegion();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Region#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Region#getOffset()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_Offset();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Region#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Region#getLength()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_Length();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Link#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Link#getType()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Link#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Link#getOperation()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_Operation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.management.trace.Link#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Link#getProperties()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Properties();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.management.trace.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Property#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Property#getKey()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.management.trace.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.epsilonlabs.modelflow.management.trace.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ManagementTraceFactory getManagementTraceFactory();

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
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceImpl <em>Management Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getManagementTrace()
		 * @generated
		 */
		EClass MANAGEMENT_TRACE = eINSTANCE.getManagementTrace();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANAGEMENT_TRACE__TASKS = eINSTANCE.getManagementTrace_Tasks();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.TaskTraceImpl <em>Task Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.TaskTraceImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getTaskTrace()
		 * @generated
		 */
		EClass TASK_TRACE = eINSTANCE.getTaskTrace();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_TRACE__TASK = eINSTANCE.getTaskTrace_Task();

		/**
		 * The meta object literal for the '<em><b>Traces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_TRACE__TRACES = eINSTANCE.getTaskTrace_Traces();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.TraceImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__SOURCES = eINSTANCE.getTrace_Sources();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__TARGETS = eINSTANCE.getTrace_Targets();

		/**
		 * The meta object literal for the '<em><b>Link</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__LINK = eINSTANCE.getTrace_Link();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ElementImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__RESOURCE = eINSTANCE.getElement_Resource();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__ROLE = eINSTANCE.getElement_Role();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ModelElementImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getModelElement()
		 * @generated
		 */
		EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

		/**
		 * The meta object literal for the '<em><b>Element Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT__ELEMENT_ID = eINSTANCE.getModelElement_ElementId();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.ModelElementPropertyImpl <em>Model Element Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ModelElementPropertyImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getModelElementProperty()
		 * @generated
		 */
		EClass MODEL_ELEMENT_PROPERTY = eINSTANCE.getModelElementProperty();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT_PROPERTY__PROPERTY = eINSTANCE.getModelElementProperty_Property();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT_PROPERTY__VALUE = eINSTANCE.getModelElementProperty_Value();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.FileElementImpl <em>File Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.FileElementImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getFileElement()
		 * @generated
		 */
		EClass FILE_ELEMENT = eINSTANCE.getFileElement();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_ELEMENT__REGION = eINSTANCE.getFileElement_Region();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.RegionImpl <em>Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.RegionImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getRegion()
		 * @generated
		 */
		EClass REGION = eINSTANCE.getRegion();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__OFFSET = eINSTANCE.getRegion_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__LENGTH = eINSTANCE.getRegion_Length();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.LinkImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__TYPE = eINSTANCE.getLink_Type();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK__OPERATION = eINSTANCE.getLink_Operation();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__PROPERTIES = eINSTANCE.getLink_Properties();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.management.trace.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.management.trace.impl.PropertyImpl
		 * @see org.epsilonlabs.modelflow.management.trace.impl.ManagementTracePackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__KEY = eINSTANCE.getProperty_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

	}

} //ManagementTracePackage
