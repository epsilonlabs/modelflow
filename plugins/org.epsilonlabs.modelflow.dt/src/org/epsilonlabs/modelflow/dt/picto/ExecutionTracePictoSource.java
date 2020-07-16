/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.picto;

import java.util.Map;

import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ui.IEditorPart;
import org.epsilonlabs.modelflow.dt.editor.ExecutionTraceEditor;

/**
 * @author Betty Sanchez
 *
 */
public class ExecutionTracePictoSource extends AbstractModelFlowPictoSource {

	@Override
	protected String getTransformation() {
		return "platform:/plugin/org.epsilonlabs.modelflow.dt/resources/picto/execution/main.egx";
	}

	@Override
	public void showElement(String id, String uri, IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registerExtensions(Map<String, Object> extensionToFactoryMap) {
		extensionToFactoryMap.put("mfexec", new XMIResourceFactoryImpl());
	}

	@Override
	protected boolean supportsEditorType(IEditorPart editorPart) {
		return editorPart instanceof ExecutionTraceEditor;
	}
	
}
