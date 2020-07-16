/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dt.editor.outline.ModelFlowModuleContentProvider;
import org.epsilonlabs.modelflow.dt.editor.outline.ModelFlowModuleElementLabelProvider;

public class ModelFlowEditor extends EolEditor implements ITextEditor {
	
	public ModelFlowEditor() {
		this.addTemplateContributor(new ModelFlowEditorStaticTemplateContributor());
		ModelFlowEditorTaskTemplateContributor taskTemplateContributor = new ModelFlowEditorTaskTemplateContributor();
		this.addModuleParsedListener(taskTemplateContributor);
		this.addTemplateContributor(taskTemplateContributor);
	}

	@Override
	public SourceViewerConfiguration createSourceViewerConfiguration() {
		return new ModelFlowModuleEditorSourceViewerConfiguration(this);
	}

	@Override
	public List<String> getKeywords() {
		List<String> superKeywords = super.getKeywords();
		List<String> keywords = new ArrayList<>();

		keywords.add("pre");
		keywords.add("post");
		keywords.add("guard");
		keywords.add("param");
		
		keywords.add("task");
		keywords.add("workflow");
		keywords.add("model");
		keywords.add("metamodel");
		keywords.add("abstract");
		keywords.add("extends");
		
		keywords.add("type");
		keywords.add("is");
		keywords.add("as");
		keywords.add("on");
		keywords.add("dependsOn");
		
		keywords.add("in");
		keywords.add("inout");
		keywords.add("out");
		keywords.add("trans");

		keywords.addAll(superKeywords);
		HashSet<String> set = new HashSet<>(keywords);
		return set.stream().collect(Collectors.toList());
	}

	@Override
	public List<String> getBuiltinVariables() {
		Collection<String> superBuiltIns = super.getBuiltinVariables();

		ArrayList<String> builtIn = new ArrayList<>(superBuiltIns.size() + 2);

		builtIn.add("trace");
		builtIn.add("extras");
		builtIn.add("module");
		builtIn.add("baseDir");

		builtIn.addAll(superBuiltIns);

		return builtIn;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new ModelFlowModuleElementLabelProvider();
	}

	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new ModelFlowModuleContentProvider();
	}

	@Override
	public IModelFlowModule createModule() {
		return new ModelFlowModule();
	}
	
}