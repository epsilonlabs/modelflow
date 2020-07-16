/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor.outline;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleContentProvider;
import org.epsilonlabs.modelflow.ModelFlowModule;

public class ModelFlowModuleContentProvider extends ErlModuleContentProvider {
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		List<ModuleElement> visible = super.getVisibleChildren(moduleElement);
		
		if (moduleElement.getClass() == ModelFlowModule.class) {
			ModelFlowModule module = (ModelFlowModule) moduleElement;
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredPre());
			visible.addAll(module.getCompilationContext().getResourceDeclarations());
			visible.addAll(module.getCompilationContext().getTaskDeclarations()); 
			visible.addAll(module.getDeclaredPost());
			visible.addAll(module.getDeclaredOperations());
		}
		
		return visible;
	}
	
}
