/**
 * ******************************************************************************
 * Copyright (c) 2020 The University of York.
 *  
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * ******************************************************************************
 */
package org.epsilonlabs.modelflow.dom.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IDomPackage;
import org.epsilonlabs.modelflow.dom.IProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configurable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Configurable#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Configurable#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Configurable#getDefinition <em>Definition</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class Configurable extends Named implements IConfigurable {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<IProperty> properties;

	/**
	 * The default value of the '{@link #getModuleElement() <em>Module Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleElement()
	 * @generated
	 * @ordered
	 */
	protected static final Object MODULE_ELEMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleElement() <em>Module Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleElement()
	 * @generated
	 * @ordered
	 */
	protected Object moduleElement = MODULE_ELEMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefinition() <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected String definition = DEFINITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Configurable() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IDomPackage.Literals.CONFIGURABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IProperty> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<IProperty>(IProperty.class, this, IDomPackage.CONFIGURABLE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getModuleElement() {
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleElement(Object newModuleElement) {
		Object oldModuleElement = moduleElement;
		moduleElement = newModuleElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.CONFIGURABLE__MODULE_ELEMENT, oldModuleElement, moduleElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinition(String newDefinition) {
		String oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.CONFIGURABLE__DEFINITION, oldDefinition, definition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IDomPackage.CONFIGURABLE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IDomPackage.CONFIGURABLE__PROPERTIES:
				return getProperties();
			case IDomPackage.CONFIGURABLE__MODULE_ELEMENT:
				return getModuleElement();
			case IDomPackage.CONFIGURABLE__DEFINITION:
				return getDefinition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IDomPackage.CONFIGURABLE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends IProperty>)newValue);
				return;
			case IDomPackage.CONFIGURABLE__MODULE_ELEMENT:
				setModuleElement(newValue);
				return;
			case IDomPackage.CONFIGURABLE__DEFINITION:
				setDefinition((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IDomPackage.CONFIGURABLE__PROPERTIES:
				getProperties().clear();
				return;
			case IDomPackage.CONFIGURABLE__MODULE_ELEMENT:
				setModuleElement(MODULE_ELEMENT_EDEFAULT);
				return;
			case IDomPackage.CONFIGURABLE__DEFINITION:
				setDefinition(DEFINITION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IDomPackage.CONFIGURABLE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case IDomPackage.CONFIGURABLE__MODULE_ELEMENT:
				return MODULE_ELEMENT_EDEFAULT == null ? moduleElement != null : !MODULE_ELEMENT_EDEFAULT.equals(moduleElement);
			case IDomPackage.CONFIGURABLE__DEFINITION:
				return DEFINITION_EDEFAULT == null ? definition != null : !DEFINITION_EDEFAULT.equals(definition);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (moduleElement: ");
		result.append(moduleElement);
		result.append(", definition: ");
		result.append(definition);
		result.append(')');
		return result.toString();
	}

} //Configurable
