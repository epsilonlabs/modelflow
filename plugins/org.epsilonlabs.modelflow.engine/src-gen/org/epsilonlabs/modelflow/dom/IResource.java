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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A generic resource type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IResource#getDeclared <em>Declared</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getResource()
 * @model
 * @generated
 */
public interface IResource extends IAbstractResource {
	/**
	 * Returns the value of the '<em><b>Declared</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared</em>' attribute.
	 * @see #setDeclared(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getResource_Declared()
	 * @model default="true"
	 * @generated
	 */
	Boolean getDeclared();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IResource#getDeclared <em>Declared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared</em>' attribute.
	 * @see #getDeclared()
	 * @generated
	 */
	void setDeclared(Boolean value);

} // IResource
