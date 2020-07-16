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
 * A representation of the model object '<em><b>Resource Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getType <em>Type</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getSource <em>Source</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResourceDependency()
 * @model annotation="exeed classIcon='transition' label='return self.source.name + \' depends \' + self.target.name;'"
 * @generated
 */
public interface ResourceDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResourceDependency_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(AbstractResource)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResourceDependency_Source()
	 * @model
	 * @generated
	 */
	AbstractResource getSource();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(AbstractResource value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(AbstractResource)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getResourceDependency_Target()
	 * @model
	 * @generated
	 */
	AbstractResource getTarget();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.ResourceDependency#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(AbstractResource value);

} // ResourceDependency
