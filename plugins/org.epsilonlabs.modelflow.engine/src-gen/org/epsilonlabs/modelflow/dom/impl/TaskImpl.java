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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.epsilonlabs.modelflow.dom.DomPackage;
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
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getDependentTasks <em>Dependent Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getTraceable <em>Traceable</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getAlwaysExecute <em>Always Execute</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.TaskImpl#getGuard <em>Guard</em>}</li>
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
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskDependency> dependencies;

	/**
	 * The cached value of the '{@link #getDependentTasks() <em>Dependent Tasks</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependentTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskDependency> dependentTasks;

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
	 * The default value of the '{@link #getAlwaysExecute() <em>Always Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlwaysExecute()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ALWAYS_EXECUTE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getAlwaysExecute() <em>Always Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlwaysExecute()
	 * @generated
	 * @ordered
	 */
	protected Boolean alwaysExecute = ALWAYS_EXECUTE_EDEFAULT;

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
	public Boolean getAlwaysExecute() {
		return alwaysExecute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlwaysExecute(Boolean newAlwaysExecute) {
		Boolean oldAlwaysExecute = alwaysExecute;
		alwaysExecute = newAlwaysExecute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TASK__ALWAYS_EXECUTE, oldAlwaysExecute, alwaysExecute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectResolvingEList<TaskDependency>(TaskDependency.class, this, DomPackage.TASK__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskDependency> getDependentTasks() {
		if (dependentTasks == null) {
			dependentTasks = new EObjectResolvingEList<TaskDependency>(TaskDependency.class, this, DomPackage.TASK__DEPENDENT_TASKS);
		}
		return dependentTasks;
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
			case DomPackage.TASK__DEPENDENCIES:
				return getDependencies();
			case DomPackage.TASK__DEPENDENT_TASKS:
				return getDependentTasks();
			case DomPackage.TASK__ENABLED:
				return getEnabled();
			case DomPackage.TASK__TRACEABLE:
				return getTraceable();
			case DomPackage.TASK__ALWAYS_EXECUTE:
				return getAlwaysExecute();
			case DomPackage.TASK__GUARD:
				return getGuard();
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
			case DomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends TaskDependency>)newValue);
				return;
			case DomPackage.TASK__DEPENDENT_TASKS:
				getDependentTasks().clear();
				getDependentTasks().addAll((Collection<? extends TaskDependency>)newValue);
				return;
			case DomPackage.TASK__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case DomPackage.TASK__TRACEABLE:
				setTraceable((Boolean)newValue);
				return;
			case DomPackage.TASK__ALWAYS_EXECUTE:
				setAlwaysExecute((Boolean)newValue);
				return;
			case DomPackage.TASK__GUARD:
				setGuard(newValue);
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
			case DomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				return;
			case DomPackage.TASK__DEPENDENT_TASKS:
				getDependentTasks().clear();
				return;
			case DomPackage.TASK__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case DomPackage.TASK__TRACEABLE:
				setTraceable(TRACEABLE_EDEFAULT);
				return;
			case DomPackage.TASK__ALWAYS_EXECUTE:
				setAlwaysExecute(ALWAYS_EXECUTE_EDEFAULT);
				return;
			case DomPackage.TASK__GUARD:
				setGuard(GUARD_EDEFAULT);
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
			case DomPackage.TASK__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case DomPackage.TASK__DEPENDENT_TASKS:
				return dependentTasks != null && !dependentTasks.isEmpty();
			case DomPackage.TASK__ENABLED:
				return ENABLED_EDEFAULT == null ? enabled != null : !ENABLED_EDEFAULT.equals(enabled);
			case DomPackage.TASK__TRACEABLE:
				return TRACEABLE_EDEFAULT == null ? traceable != null : !TRACEABLE_EDEFAULT.equals(traceable);
			case DomPackage.TASK__ALWAYS_EXECUTE:
				return ALWAYS_EXECUTE_EDEFAULT == null ? alwaysExecute != null : !ALWAYS_EXECUTE_EDEFAULT.equals(alwaysExecute);
			case DomPackage.TASK__GUARD:
				return GUARD_EDEFAULT == null ? guard != null : !GUARD_EDEFAULT.equals(guard);
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
		result.append(" (enabled: ");
		result.append(enabled);
		result.append(", traceable: ");
		result.append(traceable);
		result.append(", alwaysExecute: ");
		result.append(alwaysExecute);
		result.append(", guard: ");
		result.append(guard);
		result.append(')');
		return result.toString();
	}

} //TaskImpl
