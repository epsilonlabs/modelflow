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

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonResourceFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.EmfHashUtil;

public class EpsilonEmfModelResource extends AbstractEpsilonEmfModelResource {

	protected List<File> metamodelFiles = new ArrayList<>();
	protected List<String> metamodelUris = new ArrayList<>();
	protected Boolean reuseUnmodifiedFileBasedMetamodels = true;
	
	protected Optional<File> modelFile = Optional.empty();
	
	public EpsilonEmfModelResource() {
		super();
	}
	
	@Override
	public EmfModel getModel() {
		if (model == null) {
			this.model = new EmfModel();
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
	

	private Map<Object, Object> saveOptions = new HashMap<>();
	
	@Param(key="saveOpts")
	public void setSaveOptions(Map<Object, Object> map){
		this.saveOptions = map;
	}
	public Map<Object, Object> getSaveOptions() {
		return saveOptions;
	}
	
	public List<File> getMetamodelFiles() {
		return metamodelFiles;
	}
	
	public static class Factory extends AbstractEpsilonResourceFactory {

		public Factory() {
			super(EpsilonEmfModelResource.class);
		}

		@Override
		public String getName() {
			return "emf";
		}

		@Override
		public void beforeWorkflow() {
			// Do nothing
		}

		@Override
		public void afterWorkflow() {
			// Do nothing
		}
		
	}	
	
	@Override
	public Object loadedHash(){
		return EmfHashUtil.hash(getModel().getResource(), getModelFile(), getExpand());
	}
	
	@Override
	public Object unloadedHash(Object trace){
		return EmfHashUtil.hash(trace, getModelFile(), getExpand());
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
				options = new HashMap<>(getSaveOptions());
				options.put(XMLResource.OPTION_SCHEMA_LOCATION, true);
			}
			getModel().getResource().save(options);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	@Override
	public void register(IModelIndexer indexer) throws Exception {
		
		// METAMODELS
		boolean mmParserExists = indexer.getMetaModelParsers().stream()
				.anyMatch(e -> e instanceof EMFMetaModelResourceFactory);
		if (!mmParserExists) {			
			try {
				LOG.debug("Registering {}", EMFMetaModelResourceFactory.class.getSimpleName());
				indexer.addMetaModelResourceFactory(new EMFMetaModelResourceFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		indexer.registerMetamodels(this.getMetamodelFiles().toArray(new File[0]));

		// MODEL
		boolean mParserExists = indexer.getModelParsers().stream()
				.anyMatch(e -> e instanceof EMFModelResourceFactory);
		if (!mParserExists) {	
			try {
				LOG.debug("Registering {}", EMFModelResourceFactory.class.getSimpleName());
				indexer.addModelResourceFactory(new EMFModelResourceFactory());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LocalFile localFolder = new LocalFile();
		localFolder.init(this.getModelFile().getAbsolutePath(), indexer);
		localFolder.run();
		indexer.addVCSManager(localFolder, true);
		indexer.requestImmediateSync();
	}
	*/
	
}