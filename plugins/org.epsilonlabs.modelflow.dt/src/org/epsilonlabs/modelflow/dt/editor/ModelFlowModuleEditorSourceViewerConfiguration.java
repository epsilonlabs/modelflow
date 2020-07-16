/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditorSourceViewerConfiguration;

public class ModelFlowModuleEditorSourceViewerConfiguration extends AbstractModuleEditorSourceViewerConfiguration {

	public ModelFlowModuleEditorSourceViewerConfiguration(ModelFlowEditor editor) {
		super(editor);
		scanner = new ModelFlowModuleEditorScanner(editor);			
	}

}
