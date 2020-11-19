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
 * A representation of the model object '<em><b>Derived Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A derived resource type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.DerivedResource#getReference <em>Reference</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.DerivedResource#getDeclared <em>Declared</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getDerivedResource()
 * @model
 * @generated
 */
public interface DerivedResource extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The reference to generic resource.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(Resource)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getDerivedResource_Reference()
	 * @model
	 * @generated
	 */
	Resource getReference();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.DerivedResource#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(Resource value);

	/**
	 * Returns the value of the '<em><b>Declared</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The reference to generic resource.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared</em>' attribute.
	 * @see #setDeclared(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getDerivedResource_Declared()
	 * @model default="false"
	 * @generated
	 */
	Boolean getDeclared();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.DerivedResource#getDeclared <em>Declared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared</em>' attribute.
	 * @see #getDeclared()
	 * @generated
	 */
	void setDeclared(Boolean value);

} // DerivedResource
