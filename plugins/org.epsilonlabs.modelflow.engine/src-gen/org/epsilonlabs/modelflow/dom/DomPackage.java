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
 * @see org.epsilonlabs.modelflow.dom.DomFactory
 * @model kind="package"
 * @generated
 */
public interface DomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/epsilon/modelflow/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomPackage eINSTANCE = org.epsilonlabs.modelflow.dom.impl.DomPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.NamedImpl <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.NamedImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getNamed()
	 * @generated
	 */
	int NAMED = 1;

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
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.ConfigurableImpl <em>Configurable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.ConfigurableImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getConfigurable()
	 * @generated
	 */
	int CONFIGURABLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE__PROPERTIES = NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE__MODULE_ELEMENT = NAMED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE__DEFINITION = NAMED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Configurable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FEATURE_COUNT = NAMED_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Configurable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OPERATION_COUNT = NAMED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.AbstractTaskImpl <em>Abstract Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.AbstractTaskImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getAbstractTask()
	 * @generated
	 */
	int ABSTRACT_TASK = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__NAME = CONFIGURABLE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__PROPERTIES = CONFIGURABLE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__MODULE_ELEMENT = CONFIGURABLE__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK__DEFINITION = CONFIGURABLE__DEFINITION;

	/**
	 * The number of structural features of the '<em>Abstract Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK_FEATURE_COUNT = CONFIGURABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TASK_OPERATION_COUNT = CONFIGURABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.TaskImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__NAME = ABSTRACT_TASK__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__PROPERTIES = ABSTRACT_TASK__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__MODULE_ELEMENT = ABSTRACT_TASK__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DEFINITION = ABSTRACT_TASK__DEFINITION;

	/**
	 * The feature id for the '<em><b>Produces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__PRODUCES = ABSTRACT_TASK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Consumes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__CONSUMES = ABSTRACT_TASK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Modifies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__MODIFIES = ABSTRACT_TASK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DEPENDENCIES = ABSTRACT_TASK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Dependent Tasks</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DEPENDENT_TASKS = ABSTRACT_TASK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__ENABLED = ABSTRACT_TASK_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Traceable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__TRACEABLE = ABSTRACT_TASK_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Always Execute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__ALWAYS_EXECUTE = ABSTRACT_TASK_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__GUARD = ABSTRACT_TASK_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_OPERATION_COUNT = ABSTRACT_TASK_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl <em>Workflow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.WorkflowImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getWorkflow()
	 * @generated
	 */
	int WORKFLOW = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__NAME = TASK__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__PROPERTIES = TASK__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__MODULE_ELEMENT = TASK__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__DEFINITION = TASK__DEFINITION;

	/**
	 * The feature id for the '<em><b>Produces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__PRODUCES = TASK__PRODUCES;

	/**
	 * The feature id for the '<em><b>Consumes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__CONSUMES = TASK__CONSUMES;

	/**
	 * The feature id for the '<em><b>Modifies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__MODIFIES = TASK__MODIFIES;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__DEPENDENCIES = TASK__DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Dependent Tasks</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__DEPENDENT_TASKS = TASK__DEPENDENT_TASKS;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__ENABLED = TASK__ENABLED;

	/**
	 * The feature id for the '<em><b>Traceable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TRACEABLE = TASK__TRACEABLE;

	/**
	 * The feature id for the '<em><b>Always Execute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__ALWAYS_EXECUTE = TASK__ALWAYS_EXECUTE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__GUARD = TASK__GUARD;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TASKS = TASK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__RESOURCES = TASK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Task Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TASK_DEPENDENCIES = TASK_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Workflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_FEATURE_COUNT = TASK_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Workflow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_OPERATION_COUNT = TASK_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.AbstractResourceImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getAbstractResource()
	 * @generated
	 */
	int ABSTRACT_RESOURCE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__NAME = CONFIGURABLE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__PROPERTIES = CONFIGURABLE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__MODULE_ELEMENT = CONFIGURABLE__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE__DEFINITION = CONFIGURABLE__DEFINITION;

	/**
	 * The number of structural features of the '<em>Abstract Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE_FEATURE_COUNT = CONFIGURABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_RESOURCE_OPERATION_COUNT = CONFIGURABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.ResourceReferenceImpl <em>Resource Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.ResourceReferenceImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getResourceReference()
	 * @generated
	 */
	int RESOURCE_REFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_REFERENCE__RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Aliases</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_REFERENCE__ALIASES = 1;

	/**
	 * The number of structural features of the '<em>Resource Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Resource Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.ResourceImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__PROPERTIES = ABSTRACT_RESOURCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__MODULE_ELEMENT = ABSTRACT_RESOURCE__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DEFINITION = ABSTRACT_RESOURCE__DEFINITION;

	/**
	 * The feature id for the '<em><b>Declared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DECLARED = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_OPERATION_COUNT = ABSTRACT_RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl <em>Derived Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getDerivedResource()
	 * @generated
	 */
	int DERIVED_RESOURCE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__NAME = ABSTRACT_RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__PROPERTIES = ABSTRACT_RESOURCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__MODULE_ELEMENT = ABSTRACT_RESOURCE__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__DEFINITION = ABSTRACT_RESOURCE__DEFINITION;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__REFERENCE = ABSTRACT_RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE__DECLARED = ABSTRACT_RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Derived Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE_FEATURE_COUNT = ABSTRACT_RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Derived Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RESOURCE_OPERATION_COUNT = ABSTRACT_RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.ModelResourceImpl <em>Model Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.ModelResourceImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getModelResource()
	 * @generated
	 */
	int MODEL_RESOURCE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE__NAME = RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE__PROPERTIES = RESOURCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE__MODULE_ELEMENT = RESOURCE__MODULE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE__DEFINITION = RESOURCE__DEFINITION;

	/**
	 * The feature id for the '<em><b>Declared</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE__DECLARED = RESOURCE__DECLARED;

	/**
	 * The number of structural features of the '<em>Model Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Model Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RESOURCE_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.TaskDependencyImpl <em>Task Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.TaskDependencyImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getTaskDependency()
	 * @generated
	 */
	int TASK_DEPENDENCY = 10;

	/**
	 * The feature id for the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DEPENDENCY__TASK = 0;

	/**
	 * The feature id for the '<em><b>Depends On</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DEPENDENCY__DEPENDS_ON = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DEPENDENCY__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Task Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DEPENDENCY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Task Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DEPENDENCY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.epsilonlabs.modelflow.dom.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.epsilonlabs.modelflow.dom.impl.PropertyImpl
	 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 11;

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
	 * The feature id for the '<em><b>Evaluated Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__EVALUATED_VALUE = 2;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Workflow <em>Workflow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workflow</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Workflow
	 * @generated
	 */
	EClass getWorkflow();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Workflow#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Workflow#getTasks()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Tasks();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Workflow#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Workflow#getResources()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_Resources();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Workflow#getTaskDependencies <em>Task Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Task Dependencies</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Workflow#getTaskDependencies()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_TaskDependencies();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Named
	 * @generated
	 */
	EClass getNamed();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Named#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Named#getName()
	 * @see #getNamed()
	 * @generated
	 */
	EAttribute getNamed_Name();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Configurable <em>Configurable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configurable</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Configurable
	 * @generated
	 */
	EClass getConfigurable();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Configurable#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Configurable#getProperties()
	 * @see #getConfigurable()
	 * @generated
	 */
	EReference getConfigurable_Properties();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Configurable#getModuleElement <em>Module Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Element</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Configurable#getModuleElement()
	 * @see #getConfigurable()
	 * @generated
	 */
	EAttribute getConfigurable_ModuleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Configurable#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Configurable#getDefinition()
	 * @see #getConfigurable()
	 * @generated
	 */
	EAttribute getConfigurable_Definition();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Resource</em>'.
	 * @see org.epsilonlabs.modelflow.dom.AbstractResource
	 * @generated
	 */
	EClass getAbstractResource();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.AbstractTask <em>Abstract Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Task</em>'.
	 * @see org.epsilonlabs.modelflow.dom.AbstractTask
	 * @generated
	 */
	EClass getAbstractTask();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Task#getProduces <em>Produces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Produces</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getProduces()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Produces();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Task#getConsumes <em>Consumes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Consumes</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getConsumes()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Consumes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.epsilonlabs.modelflow.dom.Task#getModifies <em>Modifies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modifies</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getModifies()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Modifies();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Task#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guard</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getGuard()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Guard();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Task#getEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getEnabled()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Task#getTraceable <em>Traceable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Traceable</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getTraceable()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Traceable();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Task#getAlwaysExecute <em>Always Execute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Always Execute</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getAlwaysExecute()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_AlwaysExecute();

	/**
	 * Returns the meta object for the reference list '{@link org.epsilonlabs.modelflow.dom.Task#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getDependencies()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_Dependencies();

	/**
	 * Returns the meta object for the reference list '{@link org.epsilonlabs.modelflow.dom.Task#getDependentTasks <em>Dependent Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependent Tasks</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Task#getDependentTasks()
	 * @see #getTask()
	 * @generated
	 */
	EReference getTask_DependentTasks();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.ResourceReference <em>Resource Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Reference</em>'.
	 * @see org.epsilonlabs.modelflow.dom.ResourceReference
	 * @generated
	 */
	EClass getResourceReference();

	/**
	 * Returns the meta object for the reference '{@link org.epsilonlabs.modelflow.dom.ResourceReference#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource</em>'.
	 * @see org.epsilonlabs.modelflow.dom.ResourceReference#getResource()
	 * @see #getResourceReference()
	 * @generated
	 */
	EReference getResourceReference_Resource();

	/**
	 * Returns the meta object for the attribute list '{@link org.epsilonlabs.modelflow.dom.ResourceReference#getAliases <em>Aliases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Aliases</em>'.
	 * @see org.epsilonlabs.modelflow.dom.ResourceReference#getAliases()
	 * @see #getResourceReference()
	 * @generated
	 */
	EAttribute getResourceReference_Aliases();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Resource#getDeclared <em>Declared</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Resource#getDeclared()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Declared();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.DerivedResource <em>Derived Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Derived Resource</em>'.
	 * @see org.epsilonlabs.modelflow.dom.DerivedResource
	 * @generated
	 */
	EClass getDerivedResource();

	/**
	 * Returns the meta object for the reference '{@link org.epsilonlabs.modelflow.dom.DerivedResource#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.epsilonlabs.modelflow.dom.DerivedResource#getReference()
	 * @see #getDerivedResource()
	 * @generated
	 */
	EReference getDerivedResource_Reference();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.DerivedResource#getDeclared <em>Declared</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared</em>'.
	 * @see org.epsilonlabs.modelflow.dom.DerivedResource#getDeclared()
	 * @see #getDerivedResource()
	 * @generated
	 */
	EAttribute getDerivedResource_Declared();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.ModelResource <em>Model Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Resource</em>'.
	 * @see org.epsilonlabs.modelflow.dom.ModelResource
	 * @generated
	 */
	EClass getModelResource();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.TaskDependency <em>Task Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Dependency</em>'.
	 * @see org.epsilonlabs.modelflow.dom.TaskDependency
	 * @generated
	 */
	EClass getTaskDependency();

	/**
	 * Returns the meta object for the reference '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Task</em>'.
	 * @see org.epsilonlabs.modelflow.dom.TaskDependency#getTask()
	 * @see #getTaskDependency()
	 * @generated
	 */
	EReference getTaskDependency_Task();

	/**
	 * Returns the meta object for the reference '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getDependsOn <em>Depends On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Depends On</em>'.
	 * @see org.epsilonlabs.modelflow.dom.TaskDependency#getDependsOn()
	 * @see #getTaskDependency()
	 * @generated
	 */
	EReference getTaskDependency_DependsOn();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.epsilonlabs.modelflow.dom.TaskDependency#getType()
	 * @see #getTaskDependency()
	 * @generated
	 */
	EAttribute getTaskDependency_Type();

	/**
	 * Returns the meta object for class '{@link org.epsilonlabs.modelflow.dom.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Property#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Property#getKey()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.epsilonlabs.modelflow.dom.Property#getEvaluatedValue <em>Evaluated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Evaluated Value</em>'.
	 * @see org.epsilonlabs.modelflow.dom.Property#getEvaluatedValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_EvaluatedValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomFactory getDomFactory();

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
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.WorkflowImpl <em>Workflow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.WorkflowImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getWorkflow()
		 * @generated
		 */
		EClass WORKFLOW = eINSTANCE.getWorkflow();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__TASKS = eINSTANCE.getWorkflow_Tasks();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__RESOURCES = eINSTANCE.getWorkflow_Resources();

		/**
		 * The meta object literal for the '<em><b>Task Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKFLOW__TASK_DEPENDENCIES = eINSTANCE.getWorkflow_TaskDependencies();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.NamedImpl <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.NamedImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getNamed()
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
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.ConfigurableImpl <em>Configurable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.ConfigurableImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getConfigurable()
		 * @generated
		 */
		EClass CONFIGURABLE = eINSTANCE.getConfigurable();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURABLE__PROPERTIES = eINSTANCE.getConfigurable_Properties();

		/**
		 * The meta object literal for the '<em><b>Module Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURABLE__MODULE_ELEMENT = eINSTANCE.getConfigurable_ModuleElement();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURABLE__DEFINITION = eINSTANCE.getConfigurable_Definition();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.AbstractResourceImpl <em>Abstract Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.AbstractResourceImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getAbstractResource()
		 * @generated
		 */
		EClass ABSTRACT_RESOURCE = eINSTANCE.getAbstractResource();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.AbstractTaskImpl <em>Abstract Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.AbstractTaskImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getAbstractTask()
		 * @generated
		 */
		EClass ABSTRACT_TASK = eINSTANCE.getAbstractTask();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.TaskImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Produces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__PRODUCES = eINSTANCE.getTask_Produces();

		/**
		 * The meta object literal for the '<em><b>Consumes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__CONSUMES = eINSTANCE.getTask_Consumes();

		/**
		 * The meta object literal for the '<em><b>Modifies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__MODIFIES = eINSTANCE.getTask_Modifies();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__GUARD = eINSTANCE.getTask_Guard();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__ENABLED = eINSTANCE.getTask_Enabled();

		/**
		 * The meta object literal for the '<em><b>Traceable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__TRACEABLE = eINSTANCE.getTask_Traceable();

		/**
		 * The meta object literal for the '<em><b>Always Execute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__ALWAYS_EXECUTE = eINSTANCE.getTask_AlwaysExecute();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__DEPENDENCIES = eINSTANCE.getTask_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Dependent Tasks</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK__DEPENDENT_TASKS = eINSTANCE.getTask_DependentTasks();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.ResourceReferenceImpl <em>Resource Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.ResourceReferenceImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getResourceReference()
		 * @generated
		 */
		EClass RESOURCE_REFERENCE = eINSTANCE.getResourceReference();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_REFERENCE__RESOURCE = eINSTANCE.getResourceReference_Resource();

		/**
		 * The meta object literal for the '<em><b>Aliases</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_REFERENCE__ALIASES = eINSTANCE.getResourceReference_Aliases();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.ResourceImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Declared</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__DECLARED = eINSTANCE.getResource_Declared();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl <em>Derived Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getDerivedResource()
		 * @generated
		 */
		EClass DERIVED_RESOURCE = eINSTANCE.getDerivedResource();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DERIVED_RESOURCE__REFERENCE = eINSTANCE.getDerivedResource_Reference();

		/**
		 * The meta object literal for the '<em><b>Declared</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DERIVED_RESOURCE__DECLARED = eINSTANCE.getDerivedResource_Declared();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.ModelResourceImpl <em>Model Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.ModelResourceImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getModelResource()
		 * @generated
		 */
		EClass MODEL_RESOURCE = eINSTANCE.getModelResource();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.TaskDependencyImpl <em>Task Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.TaskDependencyImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getTaskDependency()
		 * @generated
		 */
		EClass TASK_DEPENDENCY = eINSTANCE.getTaskDependency();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_DEPENDENCY__TASK = eINSTANCE.getTaskDependency_Task();

		/**
		 * The meta object literal for the '<em><b>Depends On</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_DEPENDENCY__DEPENDS_ON = eINSTANCE.getTaskDependency_DependsOn();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_DEPENDENCY__TYPE = eINSTANCE.getTaskDependency_Type();

		/**
		 * The meta object literal for the '{@link org.epsilonlabs.modelflow.dom.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.epsilonlabs.modelflow.dom.impl.PropertyImpl
		 * @see org.epsilonlabs.modelflow.dom.impl.DomPackageImpl#getProperty()
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

		/**
		 * The meta object literal for the '<em><b>Evaluated Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__EVALUATED_VALUE = eINSTANCE.getProperty_EvaluatedValue();

	}

} //DomPackage
