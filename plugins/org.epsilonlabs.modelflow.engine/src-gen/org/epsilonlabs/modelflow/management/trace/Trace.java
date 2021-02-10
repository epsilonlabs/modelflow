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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Trace#getSources <em>Sources</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Trace#getTargets <em>Targets</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.management.trace.Trace#getLink <em>Link</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTrace()
 * @model annotation="exeed classIcon='transition'"
 * @generated
 */
public interface Trace extends EObject {
	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.management.trace.Element}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTrace_Sources()
	 * @model containment="true"
	 *        annotation="exeed featureLabel='source'"
	 * @generated
	 */
	EList<Element> getSources();

	/**
	 * Returns the value of the '<em><b>Targets</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.management.trace.Element}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targets</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTrace_Targets()
	 * @model containment="true"
	 *        annotation="exeed featureLabel='return \'target\';'"
	 * @generated
	 */
	EList<Element> getTargets();

	/**
	 * Returns the value of the '<em><b>Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' containment reference.
	 * @see #setLink(Link)
	 * @see org.epsilonlabs.modelflow.management.trace.ManagementTracePackage#getTrace_Link()
	 * @model containment="true"
	 * @generated
	 */
	Link getLink();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.management.trace.Trace#getLink <em>Link</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link</em>' containment reference.
	 * @see #getLink()
	 * @generated
	 */
	void setLink(Link value);

} // Trace
