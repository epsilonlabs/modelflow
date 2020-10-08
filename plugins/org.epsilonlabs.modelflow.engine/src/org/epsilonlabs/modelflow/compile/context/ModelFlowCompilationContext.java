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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.Resource;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.ast.ParameterDeclaration;
import org.epsilonlabs.modelflow.dom.ast.ResourceRule;
import org.epsilonlabs.modelflow.dom.ast.TaskRule;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowCompilationContext extends EolCompilationContext implements IModelFlowCompilationContext {

	protected IModelFlowModule module;

	protected Collection<ResourceRule> resourceDeclarations = new ArrayList<>();
	protected Collection<TaskRule> taskDeclarations = new ArrayList<>();
	protected Collection<ParameterDeclaration> parameterDeclarations = new ArrayList<>();


	protected Map<String, ModuleElement> resourceToRuleMap = new HashMap<>();
	protected Map<String, ModuleElement> taskToRuleMap = new HashMap<>();

	public ModelFlowCompilationContext(IModelFlowModule module) {
		this.module = module;
	}
	
	@Override
	public Collection<ResourceRule> getResourceDeclarations() {
		return resourceDeclarations;
	}
	
	@Override
	public Collection<TaskRule> getTaskDeclarations() {
		return taskDeclarations;
	}
	
	@Override
	public IModelFlowModule getModule() {
		return module;
	}

	@Override
	public void registerResourceModelElement(Resource r, ModuleElement declaration) {
		resourceToRuleMap.put(r.getName(), declaration);
	}
	
	@Override
	public void registerTaskModelElement(Task r, ModuleElement declaration) {
		taskToRuleMap.put(r.getName(), declaration);
	}
	
	@Override
	public ModuleElement getTaskModuleElement(Task task){
		return taskToRuleMap.get(task.getName());
	}
	
	@Override
	public ModuleElement getResourceModuleElement(Resource resource){
		return resourceToRuleMap.get(resource.getName());
	}

	@Override
	public Collection<ParameterDeclaration> getParameterDeclarations() {
		return parameterDeclarations;
	}

}
