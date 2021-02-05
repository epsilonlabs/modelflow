/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.ast.IModelModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ParameterDeclaration;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowCompilationContext extends EolCompilationContext implements IModelFlowCompilationContext {

	protected IModelFlowModule module;

	protected Collection<IModelModuleElement> resourceDeclarations = new ArrayList<>();
	protected Collection<ITaskModuleElement> taskDeclarations = new ArrayList<>();
	protected Collection<ParameterDeclaration> parameterDeclarations = new ArrayList<>();

	public ModelFlowCompilationContext(IModelFlowModule module) {
		this.module = module;
	}
	
	@Override
	public Collection<IModelModuleElement> getResourceDeclarations() {
		return resourceDeclarations;
	}
	
	@Override
	public Collection<ITaskModuleElement> getTaskDeclarations() {
		return taskDeclarations;
	}
	
	@Override
	public IModelFlowModule getModule() {
		return module;
	}

	@Override
	public Collection<ParameterDeclaration> getParameterDeclarations() {
		return parameterDeclarations;
	}

}
