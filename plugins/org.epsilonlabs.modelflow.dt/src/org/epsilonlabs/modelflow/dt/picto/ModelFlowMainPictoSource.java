/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.picto;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IEditorPart;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.impl.DomPackageImpl;
import org.epsilonlabs.modelflow.dt.editor.ModelFlowEditor;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowMainPictoSource extends AbstractModelFlowPictoSource {

	protected Map<IFile, Resource> graphs = new HashMap<>();

	@Override
	protected String getTransformation() {
		return "platform:/plugin/org.epsilonlabs.modelflow.dt/resources/picto/dependency/main.egx";
	}

	@Override
	public void showElement(String id, String uri, IEditorPart editor) {
		// Pending
	}

	@Override
	protected void registerExtensions(Map<String, Object> extensionToFactoryMap) {
	}

	@Override
	protected Resource getResource(IEditorPart editorPart) {
		IFile file = waitForFile(editorPart);
		if (file == null) {
			return null;
		} else {
			return graphs.get(file);
		}
	}

	@Override
	protected IFile waitForFile(IEditorPart editorPart) {
		IFile file = getFile(editorPart);

		ModelFlowModule module = new ModelFlowModule();
		
		try (Scanner sc = new Scanner(file.getContents())){
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) {
				sb.append(String.format("%s%n", sc.nextLine()));
			}			
			try {
				module.parse(sb.toString());
				module.compile();
				Workflow w = module.getWorkflow();
				if (w != null) {
					ResourceSet resourceSet = new ResourceSetImpl();
					Registry packageRegistry = resourceSet.getPackageRegistry();
					packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
					packageRegistry.put(DomPackageImpl.eNS_URI, DomPackageImpl.eINSTANCE);

					String uriLocation = String.format("picto://%s", file.getFullPath());
					Resource r = resourceSet.createResource(URI.createURI(uriLocation));
					r.getContents().add(w);
					graphs.put(file, r);
				}
			} catch (Exception e) { }
		} catch (CoreException e) { }	
		return file;
	}

	@Override
	public boolean supportsEditorType(IEditorPart editorPart) {
		return editorPart instanceof ModelFlowEditor;
	}

}
