/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.picto;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoFactory;
import org.eclipse.epsilon.picto.source.GraphvizSource;
import org.eclipse.ui.IEditorPart;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.dom.impl.DomPackageImpl;
import org.epsilonlabs.modelflow.dt.editor.ModelFlowEditor;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowEditorPictoSource extends GraphvizSource {

	protected Resource workflow;
	
	public ModelFlowEditorPictoSource() {
		ResourceSet rs = new ResourceSetImpl();		
		Registry packageRegistry = rs.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(DomPackageImpl.eNS_URI, DomPackageImpl.eINSTANCE);
		workflow = rs.createResource(URI.createURI("wf"));
	}
	
	@Override
	public String getFormat() {
		return "egx";
	}

	@Override
	public String getFileExtension() {
		return "mflow";
	}
	
	@Override
	public Picto getRenderingMetadata(IEditorPart editorPart) {
		Picto metadata = PictoFactory.eINSTANCE.createPicto();
		metadata.setTransformation("platform:/resource/org.epsilonlabs.modelflow.dt/resources/picto.egx");
		metadata.setFormat(getFormat());
		return metadata;
	}
	
	@Override
	public boolean supportsEditorType(IEditorPart editorPart) {
		boolean ok = editorPart instanceof ModelFlowEditor;
		if (ok) {
			ModelFlowEditor mfEditor=(ModelFlowEditor) editorPart;
			mfEditor.addModuleParsedListener(
				(editor, module) -> {
					workflow.getContents().clear();
					workflow.getContents().add(((IModelFlowModule) module).getWorkflow());
				}
			);
		}
		return ok;
	}
		
	@Override
	public Resource getResource(IEditorPart editorPart) {
		return workflow;
	}
	
}
