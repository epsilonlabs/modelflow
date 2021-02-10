/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.dom.ITask;

/**
 * The Interface IModelFlowCompilationContext.
 *
 * @author Betty Sanchez
 */
public interface IEMFModelFlowCompilationContext extends IModelFlowCompilationContext {

	/**
	 * Register resource model element.
	 *
	 * @param r the r
	 * @param declaration the declaration
	 */
	void registerResourceModelElement(IResource r, ModuleElement declaration);

	/**
	 * Register task model element.
	 *
	 * @param r the r
	 * @param declaration the declaration
	 */
	void registerTaskModelElement(ITask r, ModuleElement declaration);

	/**
	 * Gets the task module element.
	 *
	 * @param task the task
	 * @return the task module element
	 */
	ModuleElement getTaskModuleElement(ITask task);

	/**
	 * Gets the resource module element.
	 *
	 * @param resource the resource
	 * @return the resource module element
	 */
	ModuleElement getResourceModuleElement(IResource resource);




}
