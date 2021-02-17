/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.emc.emf.DefaultXMIResource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.EmfHashUtil;

@Definition(name = "epsilon:emf")
public class EpsilonEmfModelResource extends AbstractEpsilonEmfModelResource {

	protected List<File> metamodelFiles = new ArrayList<>();
	protected List<String> metamodelUris = new ArrayList<>();
	protected Boolean reuseUnmodifiedFileBasedMetamodels = true;
	
	protected Optional<File> modelFile = Optional.empty();
	protected Map<Object, Object> saveOptions;
	protected Map<Object, Object> loadOptions;
	
	public EpsilonEmfModelResource() {
		super();
	}
	
	@Override
	public EmfModel getModel() {
		if (model == null) {
			this.model = new EmfModel() {
				@Override
				public void loadModelFromUri() throws EolModelLoadingException {
					//super.loadModelFromUri();
					ResourceSet resourceSet = new ResourceSetImpl();
					
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("model", new DefaultXMIResource.Factory());
					
			        // Check if global package registry contains the EcorePackage
					if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
						EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
					}
					
					determinePackagesFrom(resourceSet);
					
					// Note that AbstractEmfModel#getPackageRegistry() is not usable yet, as modelImpl is not set
					for (EPackage ep : packages) {
						String nsUri = ep.getNsURI();
						if (nsUri == null || nsUri.trim().length() == 0) {
							nsUri = ep.getName();
						}
						resourceSet.getPackageRegistry().put(nsUri, ep);
					}
					resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
					
					Resource model = resourceSet.createResource(modelUri);
					if (this.readOnLoad) {
						try {
							model.load(getLoadOptions());
							if (expand) {
								EcoreUtil.resolveAll(model);
							}
						} catch (IOException e) {
							throw new EolModelLoadingException(e, this);
						}
					}
					modelImpl = model;
					
				}
				
			};
		}
		return (EmfModel) this.model;
	}
	
	@Override
	public void configure(){
		super.configure();
		List<String> mms = metamodelFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList());

		getModel().setReuseUnmodifiedFileBasedMetamodels(reuseUnmodifiedFileBasedMetamodels);
		if (modelFile.isPresent()) {
			getModel().setModelFile(modelFile.get().getAbsolutePath());
		}
		getModel().setMetamodelUris(metamodelUris);
		getModel().setMetamodelFiles(mms);

	}
	
	public File getModelFile() {
		return modelFile.get();
	}
	
	@Param(key="src")
	public void setModelFile(File modelFile) {
		this.modelFile = Optional.of(modelFile);
	}
	
	public Boolean getReuseUnmodifiedFileBasedMetamodels() {
		return reuseUnmodifiedFileBasedMetamodels;
	}
	
	@Param(key="reuse")
	public void setReuseUnmodifiedFileBasedMetamodels(Boolean reuseUnmodifiedFileBasedMetamodels) {
		this.reuseUnmodifiedFileBasedMetamodels = reuseUnmodifiedFileBasedMetamodels;
	}
		
	@Param(key="metamodelUri")
	public void setMetamodelStringUri(String metamodelUri) {
		this.metamodelUris.add(metamodelUri);
	}
	
	@Param(key="metamodelUris")
	public void setMetamodelStringUri(List<String> metamodelUris) {
		metamodelUris.forEach(uri -> this.metamodelUris.add(uri));
	}

	public List<String> getMetamodelUris() {
		return metamodelUris;
	}
	
	@Param(key="metamodelFile")
	public void setMetamodelFileUri(File metamodelFile) {
		this.metamodelFiles.add(metamodelFile);
	}
	
	@Param(key="metamodelFiles")
	public void setMetamodelFileUri(List<File> metamodelFiles) {
		metamodelFiles.forEach(file -> this.metamodelFiles.add(file));
	}
	
	@Param(key="saveOpts")
	public void setSaveOptions(Map<Object, Object> map){
		this.saveOptions = map;
	}
	public Map<Object, Object> getSaveOptions() {
		if (saveOptions!=null && !saveOptions.containsKey(XMLResource.OPTION_FORMATTED)) {
			saveOptions.put(XMLResource.OPTION_FORMATTED, false);
		}
		return saveOptions;
	}
	
	@Param(key="loadOpts")
	public void setLoadOptions(Map<Object, Object> map){
		this.loadOptions = map;
	}
	public Map<Object, Object> getLoadOptions() {
		return loadOptions;
	}
	
	public List<File> getMetamodelFiles() {
		return metamodelFiles;
	}
	
	@Override
	public Optional<Object> loadedHash(){
		return Optional.of(EmfHashUtil.hash(getModel().getResource(), getModelFile(), getExpand()));
	}
	
	@Override
	public Optional<Object> unloadedHash(Object trace){
		return Optional.of(EmfHashUtil.hash(trace, getModelFile(), getExpand()));
	}
	
	@Override
	public void beforeTask() {
		// Do nothing
	}

	@Override
	public void afterTask() {
		// Do nothing
	}
	
	@Override
	public void disposeImpl() {
		super.disposeImpl();
	}
	
	@Override
	public void save() {
		if (getModel().getResource() == null) return;
		try {
			Map<Object, Object> options = getSaveOptions();
			if (!getModel().getMetamodelFileUris().isEmpty()) {
				if (options == null) {
					options = new HashMap<>();					
				}
				options.put(XMLResource.OPTION_SCHEMA_LOCATION, true);
			}
			getModel().getResource().save(options);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}