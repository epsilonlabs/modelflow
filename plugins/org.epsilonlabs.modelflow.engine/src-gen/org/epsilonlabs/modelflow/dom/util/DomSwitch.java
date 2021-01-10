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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.epsilonlabs.modelflow.dom.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.epsilonlabs.modelflow.dom.IDomPackage
 * @generated
 */
public class DomSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IDomPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomSwitch() {
		if (modelPackage == null) {
			modelPackage = IDomPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IDomPackage.WORKFLOW: {
				IWorkflow workflow = (IWorkflow)theEObject;
				T result = caseWorkflow(workflow);
				if (result == null) result = caseTask(workflow);
				if (result == null) result = caseConfigurable(workflow);
				if (result == null) result = caseNamed(workflow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.NAMED: {
				INamed named = (INamed)theEObject;
				T result = caseNamed(named);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.CONFIGURABLE: {
				IConfigurable configurable = (IConfigurable)theEObject;
				T result = caseConfigurable(configurable);
				if (result == null) result = caseNamed(configurable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.ABSTRACT_RESOURCE: {
				IAbstractResource abstractResource = (IAbstractResource)theEObject;
				T result = caseAbstractResource(abstractResource);
				if (result == null) result = caseConfigurable(abstractResource);
				if (result == null) result = caseNamed(abstractResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.TASK: {
				ITask task = (ITask)theEObject;
				T result = caseTask(task);
				if (result == null) result = caseConfigurable(task);
				if (result == null) result = caseNamed(task);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.RESOURCE_REFERENCE: {
				IResourceReference resourceReference = (IResourceReference)theEObject;
				T result = caseResourceReference(resourceReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.RESOURCE: {
				IResource resource = (IResource)theEObject;
				T result = caseResource(resource);
				if (result == null) result = caseAbstractResource(resource);
				if (result == null) result = caseConfigurable(resource);
				if (result == null) result = caseNamed(resource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.DERIVED_RESOURCE: {
				IDerivedResource derivedResource = (IDerivedResource)theEObject;
				T result = caseDerivedResource(derivedResource);
				if (result == null) result = caseAbstractResource(derivedResource);
				if (result == null) result = caseConfigurable(derivedResource);
				if (result == null) result = caseNamed(derivedResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.MODEL_RESOURCE: {
				IModelResource modelResource = (IModelResource)theEObject;
				T result = caseModelResource(modelResource);
				if (result == null) result = caseResource(modelResource);
				if (result == null) result = caseAbstractResource(modelResource);
				if (result == null) result = caseConfigurable(modelResource);
				if (result == null) result = caseNamed(modelResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.TASK_DEPENDENCY: {
				ITaskDependency taskDependency = (ITaskDependency)theEObject;
				T result = caseTaskDependency(taskDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IDomPackage.PROPERTY: {
				IProperty property = (IProperty)theEObject;
				T result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Workflow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Workflow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWorkflow(IWorkflow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamed(INamed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configurable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configurable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurable(IConfigurable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractResource(IAbstractResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTask(ITask object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceReference(IResourceReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResource(IResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Derived Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Derived Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerivedResource(IDerivedResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelResource(IModelResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskDependency(ITaskDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(IProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DomSwitch
