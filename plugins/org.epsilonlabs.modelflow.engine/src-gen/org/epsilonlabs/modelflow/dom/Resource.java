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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Resource#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Resource#getDefinition <em>Definition</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Resource#getDeclared <em>Declared</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResource()
 * @model annotation="exeed classIcon='model' label='return \'Resource \' + self.name;'"
 * @generated
 */
public interface Resource extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResource_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' attribute.
	 * @see #setDefinition(String)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResource_Definition()
	 * @model
	 * @generated
	 */
	String getDefinition();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Resource#getDefinition <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' attribute.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(String value);

	/**
	 * Returns the value of the '<em><b>Declared</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared</em>' attribute.
	 * @see #setDeclared(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResource_Declared()
	 * @model default="true"
	 * @generated
	 */
	Boolean getDeclared();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Resource#getDeclared <em>Declared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared</em>' attribute.
	 * @see #getDeclared()
	 * @generated
	 */
	void setDeclared(Boolean value);

} // Resource
