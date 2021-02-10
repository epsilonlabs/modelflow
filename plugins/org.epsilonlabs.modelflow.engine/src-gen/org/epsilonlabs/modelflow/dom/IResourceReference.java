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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An resource reference that may use aliases.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IResourceReference#getResource <em>Resource</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IResourceReference#getAliases <em>Aliases</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getResourceReference()
 * @model
 * @generated
 */
public interface IResourceReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The reference to an abstract resource.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource</em>' reference.
	 * @see #setResource(IAbstractResource)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getResourceReference_Resource()
	 * @model
	 * @generated
	 */
	IAbstractResource getResource();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IResourceReference#getResource <em>Resource</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' reference.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(IAbstractResource value);

	/**
	 * Returns the value of the '<em><b>Aliases</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of aliases to be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Aliases</em>' attribute list.
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getResourceReference_Aliases()
	 * @model
	 * @generated
	 */
	EList<String> getAliases();

} // IResourceReference
