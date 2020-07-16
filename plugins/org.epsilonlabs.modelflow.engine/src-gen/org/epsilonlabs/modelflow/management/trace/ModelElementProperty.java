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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Element Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getProperty <em>Property</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getModelElementProperty()
 * @model annotation="exeed classIcon='parameter' label='return self.container.name + \'::\' + self.elementId + \'::\' + self.property;'"
 * @generated
 */
public interface ModelElementProperty extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see #setProperty(String)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getModelElementProperty_Property()
	 * @model
	 * @generated
	 */
	String getProperty();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getProperty <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getModelElementProperty_Value()
	 * @model
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.ModelElementProperty#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

} // ModelElementProperty
