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
import org.eclipse.epsilon.dt.exeed.ExeedEditor;
import org.eclipse.ui.IEditorPart;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowPictoSource extends AbstractModelFlowPictoSource {

	@Override
	protected boolean supportsEditorType(IEditorPart editorPart) {
		return editorPart instanceof ExeedEditor;
	}

	@Override
	protected String getTransformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showElement(String id, String uri, IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registerExtensions(Map<String, Object> extensionToFactoryMap) {
		extensionToFactoryMap.put("mf", new XMIResourceFactoryImpl());
	}
	

}
