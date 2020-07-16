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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.epsilonlabs.modelflow.dom.DomPackage;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.ResourceReference;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.TaskDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getProduces <em>Produces</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getModifies <em>Modifies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getTraceable <em>Traceable</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskImpl extends AbstractTaskImpl implements Task {
	/**
	 * The cached value of the '{@link #getProduces() <em>Produces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduces()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceReference> produces;

	/**
	 * The cached value of the '{@link #getConsumes() <em>Consumes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsumes()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceReference> consumes;

	/**
	 * The cached value of the '{@link #getModifies() <em>Modifies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifies()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceReference> modifies;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> properties;

	/**
	 * The default value of the '{@link #getGuard() <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected static final Object GUARD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Object guard = GUARD_EDEFAULT;

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
	 * The default value of the '{@link #getEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ENABLED_EDEFAULT = Boolean.TRUE;

	/**
	 * The cached value of the '{@link #getEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
	protected Boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getTraceable() <em>Traceable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean TRACEABLE_EDEFAULT = Boolean.TRUE;

	/**
	 * The cached value of the '{@link #getTraceable() <em>Traceable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraceable()
	 * @generated
	 * @ordered
	 */
	protected Boolean traceable = TRACEABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskDependency> dependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceReference> getProduces() {
		if (produces == null) {
			produces = new EObjectContainmentEList<ResourceReference>(ResourceReference.class, this, DomPackage.TASK__PRODUCES);
		}
		return produces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceReference> getConsumes() {
		if (consumes == null) {
			consumes = new EObjectContainmentEList<ResourceReference>(ResourceReference.class, this, DomPackage.TASK__CONSUMES);
		}
		return consumes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ResourceReference> getModifies() {
		if (modifies == null) {
			modifies = new EObjectContainmentEList<ResourceReference>(ResourceReference.class, this, DomPackage.TASK__MODIFIES);
		}
		return modifies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGuard(Object newGuard) {
		Object oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TASK__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Property> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, DomPackage.TASK__PROPERTIES);
		}
		return properties;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TASK__DEFINITION, oldDefinition, definition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnabled(Boolean newEnabled) {
		Boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TASK__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getTraceable() {
		return traceable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTraceable(Boolean newTraceable) {
		Boolean oldTraceable = traceable;
		traceable = newTraceable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TASK__TRACEABLE, oldTraceable, traceable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectWithInverseResolvingEList<TaskDependency>(TaskDependency.class, this, DomPackage.TASK__DEPENDENCIES, DomPackage.TASK_DEPENDENCY__BEFORE);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.TASK__DEPENDENCIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependencies()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.TASK__PRODUCES:
				return ((InternalEList<?>)getProduces()).basicRemove(otherEnd, msgs);
			case DomPackage.TASK__CONSUMES:
				return ((InternalEList<?>)getConsumes()).basicRemove(otherEnd, msgs);
			case DomPackage.TASK__MODIFIES:
				return ((InternalEList<?>)getModifies()).basicRemove(otherEnd, msgs);
			case DomPackage.TASK__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case DomPackage.TASK__DEPENDENCIES:
				return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
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
			case DomPackage.TASK__PRODUCES:
				return getProduces();
			case DomPackage.TASK__CONSUMES:
				return getConsumes();
			case DomPackage.TASK__MODIFIES:
				return getModifies();
			case DomPackage.TASK__PROPERTIES:
				return getProperties();
			case DomPackage.TASK__GUARD:
				return getGuard();
			case DomPackage.TASK__DEFINITION:
				return getDefinition();
			case DomPackage.TASK__ENABLED:
				return getEnabled();
			case DomPackage.TASK__TRACEABLE:
				return getTraceable();
			case DomPackage.TASK__DEPENDENCIES:
				return getDependencies();
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
			case DomPackage.TASK__PRODUCES:
				getProduces().clear();
				getProduces().addAll((Collection<? extends ResourceReference>)newValue);
				return;
			case DomPackage.TASK__CONSUMES:
				getConsumes().clear();
				getConsumes().addAll((Collection<? extends ResourceReference>)newValue);
				return;
			case DomPackage.TASK__MODIFIES:
				getModifies().clear();
				getModifies().addAll((Collection<? extends ResourceReference>)newValue);
				return;
			case DomPackage.TASK__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case DomPackage.TASK__GUARD:
				setGuard(newValue);
				return;
			case DomPackage.TASK__DEFINITION:
				setDefinition((String)newValue);
				return;
			case DomPackage.TASK__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case DomPackage.TASK__TRACEABLE:
				setTraceable((Boolean)newValue);
				return;
			case DomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends TaskDependency>)newValue);
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
			case DomPackage.TASK__PRODUCES:
				getProduces().clear();
				return;
			case DomPackage.TASK__CONSUMES:
				getConsumes().clear();
				return;
			case DomPackage.TASK__MODIFIES:
				getModifies().clear();
				return;
			case DomPackage.TASK__PROPERTIES:
				getProperties().clear();
				return;
			case DomPackage.TASK__GUARD:
				setGuard(GUARD_EDEFAULT);
				return;
			case DomPackage.TASK__DEFINITION:
				setDefinition(DEFINITION_EDEFAULT);
				return;
			case DomPackage.TASK__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case DomPackage.TASK__TRACEABLE:
				setTraceable(TRACEABLE_EDEFAULT);
				return;
			case DomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
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
			case DomPackage.TASK__PRODUCES:
				return produces != null && !produces.isEmpty();
			case DomPackage.TASK__CONSUMES:
				return consumes != null && !consumes.isEmpty();
			case DomPackage.TASK__MODIFIES:
				return modifies != null && !modifies.isEmpty();
			case DomPackage.TASK__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case DomPackage.TASK__GUARD:
				return GUARD_EDEFAULT == null ? guard != null : !GUARD_EDEFAULT.equals(guard);
			case DomPackage.TASK__DEFINITION:
				return DEFINITION_EDEFAULT == null ? definition != null : !DEFINITION_EDEFAULT.equals(definition);
			case DomPackage.TASK__ENABLED:
				return ENABLED_EDEFAULT == null ? enabled != null : !ENABLED_EDEFAULT.equals(enabled);
			case DomPackage.TASK__TRACEABLE:
				return TRACEABLE_EDEFAULT == null ? traceable != null : !TRACEABLE_EDEFAULT.equals(traceable);
			case DomPackage.TASK__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
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
		result.append(" (guard: ");
		result.append(guard);
		result.append(", definition: ");
		result.append(definition);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", traceable: ");
		result.append(traceable);
		result.append(')');
		return result.toString();
	}

} //TaskImpl
