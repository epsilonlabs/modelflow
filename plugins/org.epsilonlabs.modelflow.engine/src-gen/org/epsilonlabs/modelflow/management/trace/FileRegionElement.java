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
 * A representation of the model object '<em><b>File Region Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.FileRegionElement#getOffset <em>Offset</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.FileRegionElement#getLength <em>Length</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getFileRegionElement()
 * @model annotation="exeed classIcon='object' label='return self.offset + \':\' + self.length;'"
 * @generated
 */
public interface FileRegionElement extends FileElement {
	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(int)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getFileRegionElement_Offset()
	 * @model
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.FileRegionElement#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getFileRegionElement_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.FileRegionElement#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

} // FileRegionElement
