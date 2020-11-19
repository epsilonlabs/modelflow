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
 * A representation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A task.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getProduces <em>Produces</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getModifies <em>Modifies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getDependentTasks <em>Dependent Tasks</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getTraceable <em>Traceable</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getAlwaysExecute <em>Always Execute</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getGuard <em>Guard</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask()
 * @model
 * @generated
 */
public interface Task extends AbstractTask {
	/**
	 * Returns the value of the '<em><b>Produces</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ResourceReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of resource references to be produced.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Produces</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Produces()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceReference> getProduces();

	/**
	 * Returns the value of the '<em><b>Consumes</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ResourceReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of resource references to be consumed.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Consumes</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Consumes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceReference> getConsumes();

	/**
	 * Returns the value of the '<em><b>Modifies</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ResourceReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of resource references to be modified.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Modifies</em>' containment reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Modifies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ResourceReference> getModifies();

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A condition that must be valid for the task to execute.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Guard</em>' attribute.
	 * @see #setGuard(Object)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Guard()
	 * @model transient="true"
	 * @generated
	 */
	Object getGuard();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Task#getGuard <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' attribute.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Object value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the task is enabled.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Enabled()
	 * @model default="true"
	 * @generated
	 */
	Boolean getEnabled();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Task#getEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(Boolean value);

	/**
	 * Returns the value of the '<em><b>Traceable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the task should be traced.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Traceable</em>' attribute.
	 * @see #setTraceable(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Traceable()
	 * @model default="true"
	 * @generated
	 */
	Boolean getTraceable();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Task#getTraceable <em>Traceable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traceable</em>' attribute.
	 * @see #getTraceable()
	 * @generated
	 */
	void setTraceable(Boolean value);

	/**
	 * Returns the value of the '<em><b>Always Execute</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the task shall always be executed even when inputs do not change.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Always Execute</em>' attribute.
	 * @see #setAlwaysExecute(Boolean)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_AlwaysExecute()
	 * @model default="false"
	 * @generated
	 */
	Boolean getAlwaysExecute();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Task#getAlwaysExecute <em>Always Execute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Always Execute</em>' attribute.
	 * @see #getAlwaysExecute()
	 * @generated
	 */
	void setAlwaysExecute(Boolean value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.TaskDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of task dependencies to be executed beforehand.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dependencies</em>' reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Dependencies()
	 * @model
	 * @generated
	 */
	EList<TaskDependency> getDependencies();

	/**
	 * Returns the value of the '<em><b>Dependent Tasks</b></em>' reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.TaskDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of tasks that depend on this task.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dependent Tasks</em>' reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_DependentTasks()
	 * @model derived="true"
	 * @generated
	 */
	EList<TaskDependency> getDependentTasks();

} // Task
