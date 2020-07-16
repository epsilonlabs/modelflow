/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.jface.text.templates.Template;

public class ModelFlowEditorStaticTemplateContributor implements IAbstractModuleEditorTemplateContributor {
	
	List<Template> templates;
	
	@Override
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = new ArrayList<>();
			templates.add(new Template("pre", "block executed before the rules", "", "pre ${name} {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("post", "block executed after the rules", "", "post ${name} {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("task", "task", "",  
					"task ${classname} is ${task_type} {\r\n" + 
					"\t guard : \r\n" +
					"\t config {\r\n" +
					"\t\t ${cursor}\r\n" + 
					"\t}\r\n}",false));
		}
		return templates;
	}
}
