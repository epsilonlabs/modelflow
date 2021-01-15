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
 * A representation of the model object '<em><b>Configurable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A named element configurable through a set of properties.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IConfigurable#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IConfigurable#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.IConfigurable#getDefinition <em>Definition</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getConfigurable()
 * @model abstract="true"
 * @generated
 */
public interface IConfigurable extends INamed {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.IProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of configurable properties.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getConfigurable_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<IProperty> getProperties();

	/**
	 * Returns the value of the '<em><b>Module Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * FIXME: A reference to a module element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Module Element</em>' attribute.
	 * @see #setModuleElement(Object)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getConfigurable_ModuleElement()
	 * @model transient="true"
	 * @generated
	 */
	Object getModuleElement();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IConfigurable#getModuleElement <em>Module Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Element</em>' attribute.
	 * @see #getModuleElement()
	 * @generated
	 */
	void setModuleElement(Object value);

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of the entity that configures this configurable element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Definition</em>' attribute.
	 * @see #setDefinition(String)
	 * @see org.epsilonlabs.modelflow.dom.IDomPackage#getConfigurable_Definition()
	 * @model
	 * @generated
	 */
	String getDefinition();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.IConfigurable#getDefinition <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' attribute.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(String value);

} // IConfigurable
