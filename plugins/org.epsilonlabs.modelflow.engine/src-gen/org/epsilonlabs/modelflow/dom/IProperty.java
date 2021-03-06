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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A key value pair of properties.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IProperty#getKey <em>Key</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IProperty#getValue <em>Value</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IProperty#getEvaluatedValue <em>Evaluated Value</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getProperty()
 * @model
 * @generated
 */
public interface IProperty extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The specified property key.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getProperty_Key()
	 * @model
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IProperty#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The specified property value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getProperty_Value()
	 * @model transient="true"
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IProperty#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>Evaluated Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The resolved property value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Evaluated Value</em>' attribute.
	 * @see #setEvaluatedValue(Object)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getProperty_EvaluatedValue()
	 * @model
	 * @generated
	 */
	Object getEvaluatedValue();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IProperty#getEvaluatedValue <em>Evaluated Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evaluated Value</em>' attribute.
	 * @see #getEvaluatedValue()
	 * @generated
	 */
	void setEvaluatedValue(Object value);

} // IProperty
