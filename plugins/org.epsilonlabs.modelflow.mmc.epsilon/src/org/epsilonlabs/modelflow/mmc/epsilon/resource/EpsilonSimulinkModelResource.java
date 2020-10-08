/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEnginePool;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonResourceFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
	public void configure() {
		super.configure();
		getModel().setLibraryPath(libraryPath);
		getModel().setEngineJarPath(engineJarPath);
		getModel().setFile(file);
		getModel().setCloseOnDispose(true);
	}

	public File getWorkingDir() {
		return workingDir;
	}

	@Param(key = "workingDir")
	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
	}

	public Boolean getShowInMatlabEditor() {
		return showInMatlabEditor;
	}

	@Param(key = "show")
	public void setShowInMatlabEditor(Boolean showInMatlabEditor) {
		this.showInMatlabEditor = showInMatlabEditor;
	}

	public Boolean getFollowLinks() {
		return followLinks;
	}

	@Param(key = "expand")
	public void setFollowLinks(Boolean followLinks) {
		this.followLinks = followLinks;
	}

	public File getFile() {
		return file;
	}

	@Param(key = "src")
	public void setFile(File file) {
		this.file = file;
	}

	public String getLibraryPath() {
		return libraryPath;
	}

	@Param(key = "library")
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}

	public String getEngineJarPath() {
		return engineJarPath;
	}

	@Param(key = "engine")
	public void setEngineJarPath(String engineJarPath) {
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
			// Do nothing
		}

		@Override
		public void afterWorkflow() {
			// Do nothing

		}
	}


	@Override
	public Object loadedHash() {
		try {
			MatlabEngine engine = getModel().getEngine(); //loaded engine
			String simulinkModelName = (file.exists()) ? file.getAbsolutePath() : getModel().getSimulinkModelName();
			return engine.evalWithResult("Simulink.MDLInfo('?').ModelVersion", simulinkModelName);
		} catch (MatlabException e) {
			return null;
		}
	}

	@Override
	public Object unloadedHash(Object trace) {
		return extractRevisionFromSlx();
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
		try {
			getModel().getEngine().close();
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// extractRevisionWithEngine(); // Matlab took (ms): 5038
		long end = System.currentTimeMillis();
		System.out.println("Matlab took (ms): " + (end - start));

		start = System.currentTimeMillis();
		// extractRevisionFromSlx(); // Zip took (ms): 44

		end = System.currentTimeMillis();
		System.out.println("Zip took (ms): " + (end - start));
	}
	
	/*
	 * For Checksum alternatives
	 * @see https://uk.mathworks.com/help/simulink/slref/simulink.blockdiagram.getchecksum.html
	 */

	/**
	 * Implementation that returns the model revision by invoking the
	 * Simulink.MDLinfo function through the MATLAB engine
	 * 
	 * @return
	 */
	private String extractRevisionWithEngine() {
		try {
			MatlabEnginePool pool;
			if (libraryPath !=null && engineJarPath !=null) {				
				pool = MatlabEnginePool.getInstance(libraryPath, engineJarPath);
			} else {
				pool = MatlabEnginePool.getInstance();
			}
			MatlabEngine engine = pool.getMatlabEngine();
			return (String) getModel().getEngine().evalWithResult("Simulink.MDLInfo('?').ModelVersion", file);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Implementation that returns the model revision by extracting it from the Slx
	 * compressed format
	 * 
	 * @return
	 */
	protected String extractRevisionFromSlx() {
		String revision = null;
		if (file.exists()) {		
			try (ZipFile zipFile = new ZipFile(file)) {
				Path outputTmp = Files.createTempDirectory("mflowSim");
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					if (entry.getName().equals("metadata/coreProperties.xml")) {
						File entryDestination = new File(outputTmp.toFile(), entry.getName());
						entryDestination.getParentFile().mkdirs();
						try (InputStream in = zipFile.getInputStream(entry);
								OutputStream out = new FileOutputStream(entryDestination)) {
							IOUtils.copy(in, out);
							DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder dBuilder;
							dBuilder = dbFactory.newDocumentBuilder();
							Document doc = dBuilder.parse(entryDestination);
							NodeList elementsByTagName = doc.getElementsByTagName("cp:revision");
							revision = elementsByTagName.item(0).getChildNodes().item(0).getNodeValue();
							break;
						}
					}
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
		return revision;
	}

}
