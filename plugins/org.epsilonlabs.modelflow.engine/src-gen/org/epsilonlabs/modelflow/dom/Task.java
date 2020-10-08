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
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getProduces <em>Produces</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getConsumes <em>Consumes</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getModifies <em>Modifies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getDefinition <em>Definition</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getTraceable <em>Traceable</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getAlwaysExecute <em>Always Execute</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.Task#getGuard <em>Guard</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask()
 * @model annotation="exeed classIcon='operation' label='return self.name +\' (\'+  self.definition.parent.shortId + \':\' + self.definition.shortId + \')\';'"
 * @generated
 */
public interface Task extends AbstractTask {
	/**
	 * Returns the value of the '<em><b>Produces</b></em>' containment reference list.
	 * The list contents are of type {@link org.epsilonlabs.modelflow.dom.ResourceReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * Returns the value of the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' attribute.
	 * @see #setDefinition(String)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Definition()
	 * @model
	 * @generated
	 */
	String getDefinition();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.Task#getDefinition <em>Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' attribute.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(String value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * It is bidirectional and its opposite is '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getBefore <em>Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' reference list.
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTask_Dependencies()
	 * @see org.epsilonlabs.modelflow.dom.TaskDependency#getBefore
	 * @model opposite="before"
	 * @generated
	 */
	EList<TaskDependency> getDependencies();

} // Task
