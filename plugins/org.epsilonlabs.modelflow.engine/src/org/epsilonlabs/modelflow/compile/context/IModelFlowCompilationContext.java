/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import java.util.Collection;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.Resource;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.ast.ParameterDeclaration;
import org.epsilonlabs.modelflow.dom.ast.ResourceRule;
import org.epsilonlabs.modelflow.dom.ast.TaskRule;

/**
 * The Interface IModelFlowCompilationContext.
 *
 * @author Betty Sanchez
 */
public interface IModelFlowCompilationContext extends IEolCompilationContext {

	
	/**
	 * Gets the resource declarations.
	 *
	 * @return the resource declarations
	 */
	Collection<ResourceRule> getResourceDeclarations();

	/**
	 * Gets the task declarations.
	 *
	 * @return the task declarations
	 */
	Collection<TaskRule> getTaskDeclarations();
	
	/**
	 * Gets the parameter declarations.
	 *
	 * @return the parameter declarations
	 */
	Collection<ParameterDeclaration> getParameterDeclarations();
	
	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	IModelFlowModule getModule();

	/**
	 * Register resource model element.
	 *
	 * @param r the r
	 * @param declaration the declaration
	 */
	void registerResourceModelElement(Resource r, ModuleElement declaration);

	/**
	 * Register task model element.
	 *
	 * @param r the r
	 * @param declaration the declaration
	 */
	void registerTaskModelElement(Task r, ModuleElement declaration);

	/**
	 * Gets the task module element.
	 *
	 * @param task the task
	 * @return the task module element
	 */
	ModuleElement getTaskModuleElement(Task task);

	/**
	 * Gets the resource module element.
	 *
	 * @param resource the resource
	 * @return the resource module element
	 */
	ModuleElement getResourceModuleElement(Resource resource);




}
