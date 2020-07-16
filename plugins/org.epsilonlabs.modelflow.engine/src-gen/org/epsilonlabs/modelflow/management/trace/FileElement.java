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
 * A representation of the model object '<em><b>File Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.FileElement#getRegion <em>Region</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getFileElement()
 * @model annotation="exeed classIcon='text' label='return \'@\'+self.region.offset + \':\' + self.region.length;'"
 * @generated
 */
public interface FileElement extends Element {
	/**
	 * Returns the value of the '<em><b>Region</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Region</em>' containment reference.
	 * @see #setRegion(Region)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getFileElement_Region()
	 * @model containment="true"
	 * @generated
	 */
	Region getRegion();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.FileElement#getRegion <em>Region</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Region</em>' containment reference.
	 * @see #getRegion()
	 * @generated
	 */
	void setRegion(Region value);

} // FileElement
