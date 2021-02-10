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

import org.epsilonlabs.modelflow.dom.IDomPackage;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getProduces <em>Produces</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getModifies <em>Modifies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getDependentTasks <em>Dependent Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getTraceable <em>Traceable</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getAlwaysExecute <em>Always Execute</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.impl.Task#getGuard <em>Guard</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Task extends Configurable implements ITask {
	/**
	 * The cached value of the '{@link #getProduces() <em>Produces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduces()
	 * @generated
	 * @ordered
	 */
	protected EList<IResourceReference> produces;

	/**
	 * The cached value of the '{@link #getConsumes() <em>Consumes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsumes()
	 * @generated
	 * @ordered
	 */
	protected EList<IResourceReference> consumes;

	/**
	 * The cached value of the '{@link #getModifies() <em>Modifies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifies()
	 * @generated
	 * @ordered
	 */
	protected EList<IResourceReference> modifies;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ITaskDependency> dependencies;

	/**
	 * The cached value of the '{@link #getDependentTasks() <em>Dependent Tasks</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependentTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<ITaskDependency> dependentTasks;

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
	protected Task() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IDomPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IResourceReference> getProduces() {
		if (produces == null) {
			produces = new EObjectContainmentEList<IResourceReference>(IResourceReference.class, this, IDomPackage.TASK__PRODUCES);
		}
		return produces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IResourceReference> getConsumes() {
		if (consumes == null) {
			consumes = new EObjectContainmentEList<IResourceReference>(IResourceReference.class, this, IDomPackage.TASK__CONSUMES);
		}
		return consumes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IResourceReference> getModifies() {
		if (modifies == null) {
			modifies = new EObjectContainmentEList<IResourceReference>(IResourceReference.class, this, IDomPackage.TASK__MODIFIES);
		}
		return modifies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITaskDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectResolvingEList<ITaskDependency>(ITaskDependency.class, this, IDomPackage.TASK__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITaskDependency> getDependentTasks() {
		if (dependentTasks == null) {
			dependentTasks = new EObjectResolvingEList<ITaskDependency>(ITaskDependency.class, this, IDomPackage.TASK__DEPENDENT_TASKS);
		}
		return dependentTasks;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.TASK__ENABLED, oldEnabled, enabled));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.TASK__TRACEABLE, oldTraceable, traceable));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.TASK__ALWAYS_EXECUTE, oldAlwaysExecute, alwaysExecute));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IDomPackage.TASK__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IDomPackage.TASK__PRODUCES:
				return ((InternalEList<?>)getProduces()).basicRemove(otherEnd, msgs);
			case IDomPackage.TASK__CONSUMES:
				return ((InternalEList<?>)getConsumes()).basicRemove(otherEnd, msgs);
			case IDomPackage.TASK__MODIFIES:
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
			case IDomPackage.TASK__PRODUCES:
				return getProduces();
			case IDomPackage.TASK__CONSUMES:
				return getConsumes();
			case IDomPackage.TASK__MODIFIES:
				return getModifies();
			case IDomPackage.TASK__DEPENDENCIES:
				return getDependencies();
			case IDomPackage.TASK__DEPENDENT_TASKS:
				return getDependentTasks();
			case IDomPackage.TASK__ENABLED:
				return getEnabled();
			case IDomPackage.TASK__TRACEABLE:
				return getTraceable();
			case IDomPackage.TASK__ALWAYS_EXECUTE:
				return getAlwaysExecute();
			case IDomPackage.TASK__GUARD:
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
			case IDomPackage.TASK__PRODUCES:
				getProduces().clear();
				getProduces().addAll((Collection<? extends IResourceReference>)newValue);
				return;
			case IDomPackage.TASK__CONSUMES:
				getConsumes().clear();
				getConsumes().addAll((Collection<? extends IResourceReference>)newValue);
				return;
			case IDomPackage.TASK__MODIFIES:
				getModifies().clear();
				getModifies().addAll((Collection<? extends IResourceReference>)newValue);
				return;
			case IDomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends ITaskDependency>)newValue);
				return;
			case IDomPackage.TASK__DEPENDENT_TASKS:
				getDependentTasks().clear();
				getDependentTasks().addAll((Collection<? extends ITaskDependency>)newValue);
				return;
			case IDomPackage.TASK__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case IDomPackage.TASK__TRACEABLE:
				setTraceable((Boolean)newValue);
				return;
			case IDomPackage.TASK__ALWAYS_EXECUTE:
				setAlwaysExecute((Boolean)newValue);
				return;
			case IDomPackage.TASK__GUARD:
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
			case IDomPackage.TASK__PRODUCES:
				getProduces().clear();
				return;
			case IDomPackage.TASK__CONSUMES:
				getConsumes().clear();
				return;
			case IDomPackage.TASK__MODIFIES:
				getModifies().clear();
				return;
			case IDomPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				return;
			case IDomPackage.TASK__DEPENDENT_TASKS:
				getDependentTasks().clear();
				return;
			case IDomPackage.TASK__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case IDomPackage.TASK__TRACEABLE:
				setTraceable(TRACEABLE_EDEFAULT);
				return;
			case IDomPackage.TASK__ALWAYS_EXECUTE:
				setAlwaysExecute(ALWAYS_EXECUTE_EDEFAULT);
				return;
			case IDomPackage.TASK__GUARD:
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
			case IDomPackage.TASK__PRODUCES:
				return produces != null && !produces.isEmpty();
			case IDomPackage.TASK__CONSUMES:
				return consumes != null && !consumes.isEmpty();
			case IDomPackage.TASK__MODIFIES:
				return modifies != null && !modifies.isEmpty();
			case IDomPackage.TASK__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case IDomPackage.TASK__DEPENDENT_TASKS:
				return dependentTasks != null && !dependentTasks.isEmpty();
			case IDomPackage.TASK__ENABLED:
				return ENABLED_EDEFAULT == null ? enabled != null : !ENABLED_EDEFAULT.equals(enabled);
			case IDomPackage.TASK__TRACEABLE:
				return TRACEABLE_EDEFAULT == null ? traceable != null : !TRACEABLE_EDEFAULT.equals(traceable);
			case IDomPackage.TASK__ALWAYS_EXECUTE:
				return ALWAYS_EXECUTE_EDEFAULT == null ? alwaysExecute != null : !ALWAYS_EXECUTE_EDEFAULT.equals(alwaysExecute);
			case IDomPackage.TASK__GUARD:
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

} //Task
