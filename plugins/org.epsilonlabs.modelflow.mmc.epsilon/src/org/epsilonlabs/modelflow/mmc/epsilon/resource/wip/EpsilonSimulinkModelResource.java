/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource.wip;
import java.io.File;

import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonResourceFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.AbstractEpsilonCachedModelResource;

public class EpsilonSimulinkModelResource extends AbstractEpsilonCachedModelResource {

	private Boolean showInMatlabEditor = false;
	private Boolean followLinks = false;

	private File workingDir;
	private File file;
	private String libraryPath;
	private String engineJarPath;
	
	@Override
	public SimulinkModel getModel() {
		if (model == null) {
			this.model = new SimulinkModel();
		}
		return (SimulinkModel) this.model;
	}
	
	@Override
	public void configure(){
		/*super.configure();
		List<String> mms = metamodelFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList());

		getModel().setReuseUnmodifiedFileBasedMetamodels(reuseUnmodifiedFileBasedMetamodels);
		getModel().setModelFile(modelFile.getAbsolutePath());
		getModel().setMetamodelUris(metamodelUris);
		getModel().setMetamodelFiles(mms);*/
	}
	
	
	public File getWorkingDir(){
		return workingDir;
	}
	
	@Param(key="workingDir")
	public void setWorkingDir(File workingDir){
		this.workingDir = workingDir;
	}
	
	public Boolean getShowInMatlabEditor(){
		return showInMatlabEditor;
	}
	
	@Param(key="show")
	public void setShowInMatlabEditor(Boolean showInMatlabEditor){
		this.showInMatlabEditor = showInMatlabEditor;
	}
	
	public Boolean getFollowLinks(){
		return followLinks;
	}
	
	@Param(key="expand")
	public void setFollowLinks(Boolean followLinks){
		this.followLinks = followLinks;
	}
	
	public File getFile(){
		return file;
	}
	
	@Param(key="src")
	public void setFile(File file){
		this.file = file;
	}
	
	public String getLibraryPath(){
		return libraryPath;
	}
	
	@Param(key="libraryPath")
	public void setLibraryPath(String libraryPath){
		this.libraryPath = libraryPath;
	}
	
	public String getEngineJarPath(){
		return engineJarPath;
	}
	
	@Param(key="EngineJar")
	public void setEngineJarPath(String engineJarPath){
		this.engineJarPath = engineJarPath;
	}
	
	public static class Factory extends AbstractEpsilonResourceFactory {

		public Factory() {
			super(EpsilonSimulinkModelResource.class);
		}

		@Override
		public String getName() {
			return "simulink";
		}

		@Override
		public void beforeWorkflow() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterWorkflow() {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public Object loadedHash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object unloadedHash(Object trace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTask() {
		// TODO Auto-generated method stub
		
	}

}
