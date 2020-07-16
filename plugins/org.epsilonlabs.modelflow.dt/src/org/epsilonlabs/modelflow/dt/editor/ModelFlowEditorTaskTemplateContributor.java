/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.IModuleParseListener;
import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.epsilon.common.module.IModule;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.eclipse.jface.text.templates.Template;

public class ModelFlowEditorTaskTemplateContributor implements IAbstractModuleEditorTemplateContributor, IModuleParseListener {

	@Override
	public void moduleParsed(AbstractModuleEditor editor, IModule module) {
		if (module instanceof IModelFlowModule) {
			//IModelFlowModule module = (IModelFlowModule) _module;
			//if (_editor instanceof ModelFlowEditor) {
				//List<TaskModel> factories = module.getTaskDefinitions().getTypes();
				//List<String> parameters = factories.stream().map(f->f.getProperties()).flatMap(f->f.stream()).distinct().collect(Collectors.toList());
				//ModelFlowEditor editor = ((ModelFlowEditor) _editor);
				//editor.addTaskKeywords(parameters);
				//editor.createSourceViewerConfiguration();
			//}			
		}
	}

	@Override
	public List<Template> getTemplates() {
		// depending on the element type: TODO
		// - get available tasks and add them to the template registry
		// - get available task properties and add them to the template registry
		return Collections.emptyList();
	}

}
