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

import org.epsilonlabs.modelflow.dom.IDerivedResource;
import org.epsilonlabs.modelflow.dom.IDomPackage;
import org.epsilonlabs.modelflow.dom.IResource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Derived Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.DerivedResource#getReference <em>Reference</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.DerivedResource#getDeclared <em>Declared</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DerivedResource extends AbstractResource implements IDerivedResource {
	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected IResource reference;

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
	protected DerivedResource() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IDomPackage.Literals.DERIVED_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IResource getReference() {
		if (reference != null && reference.eIsProxy()) {
			InternalEObject oldReference = (InternalEObject)reference;
			reference = (IResource)eResolveProxy(oldReference);
			if (reference != oldReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IDomPackage.DERIVED_RESOURCE__REFERENCE, oldReference, reference));
			}
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResource basicGetReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReference(IResource newReference) {
		IResource oldReference = reference;
		reference = newReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.DERIVED_RESOURCE__REFERENCE, oldReference, reference));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.DERIVED_RESOURCE__DECLARED, oldDeclared, declared));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IDomPackage.DERIVED_RESOURCE__REFERENCE:
				if (resolve) return getReference();
				return basicGetReference();
			case IDomPackage.DERIVED_RESOURCE__DECLARED:
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
			case IDomPackage.DERIVED_RESOURCE__REFERENCE:
				setReference((IResource)newValue);
				return;
			case IDomPackage.DERIVED_RESOURCE__DECLARED:
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
			case IDomPackage.DERIVED_RESOURCE__REFERENCE:
				setReference((IResource)null);
				return;
			case IDomPackage.DERIVED_RESOURCE__DECLARED:
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
			case IDomPackage.DERIVED_RESOURCE__REFERENCE:
				return reference != null;
			case IDomPackage.DERIVED_RESOURCE__DECLARED:
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

} //DerivedResource
