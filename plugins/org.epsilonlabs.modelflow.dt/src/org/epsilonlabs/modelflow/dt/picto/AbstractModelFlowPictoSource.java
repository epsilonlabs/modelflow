/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.picto;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoFactory;
import org.eclipse.epsilon.picto.source.EglPictoSource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.epsilonlabs.modelflow.dom.impl.DomPackage;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePackage;
import org.epsilonlabs.modelflow.management.trace.ManagementTracePackage;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractModelFlowPictoSource extends EglPictoSource {

	
	@Override
	public Picto getRenderingMetadata(IEditorPart editorPart) {
		Picto metadata = PictoFactory.eINSTANCE.createPicto();
		metadata.setFormat("egx");
		metadata.setTransformation(getTransformation());
		return metadata;
	}
	
	protected abstract String getTransformation();

	@Override
	protected Resource getResource(IEditorPart editorPart) {
		IPath file = waitForPath(editorPart);
		if (file == null) return null;
		
		//TODO
		ResourceSet resourceSet = new ResourceSetImpl();
		Registry packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(DomPackage.eNS_URI, DomPackage.eINSTANCE);
		packageRegistry.put(ExecutionTracePackage.eNS_URI, ExecutionTracePackage.eINSTANCE);
		packageRegistry.put(ManagementTracePackage.eNS_URI, ManagementTracePackage.eINSTANCE);
		
		Map<String, Object> extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		registerExtensions(extensionToFactoryMap);

		Resource resource = resourceSet.createResource(URI.createFileURI(file.toOSString()));		
		try {
			resource.load(null);
			return resource;
		}
		catch (IOException e) {
			ModelFlowPlugin.error(e.getMessage());
			return null;
		}
	}
	
	protected abstract void registerExtensions(Map<String, Object> extensionToFactoryMap);

	@Override
	protected IFile getFile(IEditorPart editorPart) {
		IEditorInput editorInput = editorPart.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput input = (IFileEditorInput) editorInput;
			return input.getFile();
		}
		ModelFlowPlugin.error("PictoSource.getFile() returned null");
		return null;
	}

}
