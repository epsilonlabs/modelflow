/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	protected List<ResourceRule> resourceDeclarations = new ArrayList<>();
	protected List<TaskRule> taskDeclarations = new ArrayList<>();
	protected List<ParameterDeclaration> parameterDeclarations = new ArrayList<>();


	protected Map<String, ModuleElement> resourceToRuleMap = new HashMap<>();
	protected Map<String, ModuleElement> taskToRuleMap = new HashMap<>();

	public ModelFlowCompilationContext(IModelFlowModule module) {
		this.module = module;
	}
	
	@Override
	public List<ResourceRule> getResourceDeclarations() {
		return resourceDeclarations;
	}

	@Override
	public void addResourceDeclaration(ResourceRule resourceDeclarations) {
		this.resourceDeclarations.add(resourceDeclarations);
	}
	
	@Override
	public List<TaskRule> getTaskDeclarations() {
		return taskDeclarations;
	}

	@Override
	public void addTaskDeclaration(TaskRule taskRule) {
		this.taskDeclarations.add(taskRule);
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
	public List<ParameterDeclaration> getParameterDeclarations() {
		return parameterDeclarations;
	}
	
	@Override
	public void addParameterDeclaration(ParameterDeclaration parameterDeclaration) {
		this.parameterDeclarations.add(parameterDeclaration);
	}

}
