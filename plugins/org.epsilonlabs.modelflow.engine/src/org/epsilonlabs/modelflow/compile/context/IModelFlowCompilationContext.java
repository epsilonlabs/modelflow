/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import java.util.Collection;

import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.ast.IModelModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ParameterDeclaration;

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
	Collection<IModelModuleElement> getResourceDeclarations();

	/**
	 * Gets the task declarations.
	 *
	 * @return the task declarations
	 */
	Collection<ITaskModuleElement> getTaskDeclarations();
	
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


}
