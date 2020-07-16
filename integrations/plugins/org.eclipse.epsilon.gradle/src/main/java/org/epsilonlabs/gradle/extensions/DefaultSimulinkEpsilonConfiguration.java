package org.epsilonlabs.gradle.extensions;

import java.io.File;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class DefaultSimulinkEpsilonConfiguration implements SimulinkEpsilonConfiguration {

	private final Property<File> engineJar;
	private final Property<File> libraryPath;
	private final Property<Boolean> hiddenEditor;

	public DefaultSimulinkEpsilonConfiguration(ObjectFactory objectFactory) {
		this.engineJar = objectFactory.property(File.class);
		this.libraryPath = objectFactory.property(File.class);
		this.hiddenEditor = objectFactory.property(Boolean.class);
		this.hiddenEditor.set(true);
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public File getEngineJar() {
		return engineJar.get();
	}

	@Override
	public File getLibraryPath() {
		return libraryPath.get();
	}

	@Override
	public void setEngineJar(File file) {
		engineJar.set(file);
	}

	@Override
	public void setLibraryPath(File file) {
		libraryPath.set(file);
	}

	@Override
	public Boolean getHiddenEditor() {
		return this.hiddenEditor.get();
	}

	@Override
	public void setHiddenEditor(Boolean isExpand) {
		this.hiddenEditor.set(isExpand);
	}

}
