/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.compile.context;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.dom.ITask;

/**
 * @author Betty Sanchez
 *
 */
public class EMFModelFlowCompilationContext extends ModelFlowCompilationContext implements IEMFModelFlowCompilationContext {

	protected Map<String, ModuleElement> resourceToRuleMap = new HashMap<>();
	protected Map<String, ModuleElement> taskToRuleMap = new HashMap<>();

	public EMFModelFlowCompilationContext(IModelFlowModule module) {
		super(module);
	}
	
	@Override
	public void registerResourceModelElement(IResource r, ModuleElement declaration) {
		resourceToRuleMap.put(r.getName(), declaration);
	}
	
	@Override
	public void registerTaskModelElement(ITask r, ModuleElement declaration) {
		taskToRuleMap.put(r.getName(), declaration);
	}
	
	@Override
	public ModuleElement getTaskModuleElement(ITask task){
		return taskToRuleMap.get(task.getName());
	}
	
	@Override
	public ModuleElement getResourceModuleElement(IResource resource){
		return resourceToRuleMap.get(resource.getName());
	}

}
