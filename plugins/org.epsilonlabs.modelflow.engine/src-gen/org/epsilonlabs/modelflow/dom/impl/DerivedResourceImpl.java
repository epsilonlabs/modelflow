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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.epsilonlabs.modelflow.dom.DerivedResource;
import org.epsilonlabs.modelflow.dom.DomPackage;
import org.epsilonlabs.modelflow.dom.Resource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Derived Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.DerivedResourceImpl#getDeclared <em>Declared</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DerivedResourceImpl extends AbstractResourceImpl implements DerivedResource {
	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected Resource reference;

	/**
	 * The default value of the '{@link #getDeclared() <em>Declared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclared()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean DECLARED_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getDeclared() <em>Declared</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclared()
	 * @generated
	 * @ordered
	 */
	protected Boolean declared = DECLARED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DerivedResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.DERIVED_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource getReference() {
		if (reference != null && reference.eIsProxy()) {
			InternalEObject oldReference = (InternalEObject)reference;
			reference = (Resource)eResolveProxy(oldReference);
			if (reference != oldReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomPackage.DERIVED_RESOURCE__REFERENCE, oldReference, reference));
			}
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource basicGetReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReference(Resource newReference) {
		Resource oldReference = reference;
		reference = newReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.DERIVED_RESOURCE__REFERENCE, oldReference, reference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getDeclared() {
		return declared;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclared(Boolean newDeclared) {
		Boolean oldDeclared = declared;
		declared = newDeclared;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.DERIVED_RESOURCE__DECLARED, oldDeclared, declared));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomPackage.DERIVED_RESOURCE__REFERENCE:
				if (resolve) return getReference();
				return basicGetReference();
			case DomPackage.DERIVED_RESOURCE__DECLARED:
				return getDeclared();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DomPackage.DERIVED_RESOURCE__REFERENCE:
				setReference((Resource)newValue);
				return;
			case DomPackage.DERIVED_RESOURCE__DECLARED:
				setDeclared((Boolean)newValue);
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
			case DomPackage.DERIVED_RESOURCE__REFERENCE:
				setReference((Resource)null);
				return;
			case DomPackage.DERIVED_RESOURCE__DECLARED:
				setDeclared(DECLARED_EDEFAULT);
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
			case DomPackage.DERIVED_RESOURCE__REFERENCE:
				return reference != null;
			case DomPackage.DERIVED_RESOURCE__DECLARED:
				return DECLARED_EDEFAULT == null ? declared != null : !DECLARED_EDEFAULT.equals(declared);
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
		result.append(" (declared: ");
		result.append(declared);
		result.append(')');
		return result.toString();
	}

} //DerivedResourceImpl
