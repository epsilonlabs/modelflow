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
 * A representation of the model object '<em><b>Task Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getType <em>Type</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getBefore <em>Before</em>}</li>
 *   <li>{@link org.epsilonlabs.modelflow.dom.TaskDependency#getAfter <em>After</em>}</li>
 * </ul>
 *
 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency()
 * @model annotation="exeed classIcon='transition' label='return self.before.name + \' depends \' + self.after.name;'"
 * @generated
 */
public interface TaskDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Before</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.epsilonlabs.modelflow.dom.Task#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Before</em>' reference.
	 * @see #setBefore(Task)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_Before()
	 * @see org.epsilonlabs.modelflow.dom.Task#getDependencies
	 * @model opposite="dependencies"
	 * @generated
	 */
	Task getBefore();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getBefore <em>Before</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Before</em>' reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(Task value);

	/**
	 * Returns the value of the '<em><b>After</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>After</em>' reference.
	 * @see #setAfter(Task)
	 * @see org.epsilonlabs.modelflow.dom.DomPackage#getTaskDependency_After()
	 * @model
	 * @generated
	 */
	Task getAfter();

	/**
	 * Sets the value of the '{@link org.epsilonlabs.modelflow.dom.TaskDependency#getAfter <em>After</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>After</em>' reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(Task value);

} // TaskDependency
