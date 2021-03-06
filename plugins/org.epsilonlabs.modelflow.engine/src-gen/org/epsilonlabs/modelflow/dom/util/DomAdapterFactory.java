/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.epsilonlabs.modelflow.dom.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.epsilonlabs.modelflow.dom.IDomPackage
 * @generated
 */
public class DomAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IDomPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IDomPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomSwitch<Adapter> modelSwitch =
		new DomSwitch<Adapter>() {
			@Override
			public Adapter caseWorkflow(IWorkflow object) {
				return createWorkflowAdapter();
			}
			@Override
			public Adapter caseNamed(INamed object) {
				return createNamedAdapter();
			}
			@Override
			public Adapter caseConfigurable(IConfigurable object) {
				return createConfigurableAdapter();
			}
			@Override
			public Adapter caseAbstractResource(IAbstractResource object) {
				return createAbstractResourceAdapter();
			}
			@Override
			public Adapter caseTask(ITask object) {
				return createTaskAdapter();
			}
			@Override
			public Adapter caseResourceReference(IResourceReference object) {
				return createResourceReferenceAdapter();
			}
			@Override
			public Adapter caseResource(IResource object) {
				return createResourceAdapter();
			}
			@Override
			public Adapter caseDerivedResource(IDerivedResource object) {
				return createDerivedResourceAdapter();
			}
			@Override
			public Adapter caseModelResource(IModelResource object) {
				return createModelResourceAdapter();
			}
			@Override
			public Adapter caseTaskDependency(ITaskDependency object) {
				return createTaskDependencyAdapter();
			}
			@Override
			public Adapter caseProperty(IProperty object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IWorkflow <em>Workflow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IWorkflow
	 * @generated
	 */
	public Adapter createWorkflowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.INamed <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.INamed
	 * @generated
	 */
	public Adapter createNamedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IConfigurable <em>Configurable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IConfigurable
	 * @generated
	 */
	public Adapter createConfigurableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IAbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IAbstractResource
	 * @generated
	 */
	public Adapter createAbstractResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.ITask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.ITask
	 * @generated
	 */
	public Adapter createTaskAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IResourceReference <em>Resource Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IResourceReference
	 * @generated
	 */
	public Adapter createResourceReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IResource
	 * @generated
	 */
	public Adapter createResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IDerivedResource <em>Derived Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IDerivedResource
	 * @generated
	 */
	public Adapter createDerivedResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IModelResource <em>Model Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IModelResource
	 * @generated
	 */
	public Adapter createModelResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.ITaskDependency <em>Task Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.ITaskDependency
	 * @generated
	 */
	public Adapter createTaskDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.epsilonlabs.modelflow.dom.IProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.epsilonlabs.modelflow.dom.IProperty
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DomAdapterFactory
