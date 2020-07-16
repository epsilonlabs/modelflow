package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.internal.reflect.Instantiator;

public class DefaultSimulinkModel implements SimulinkModel {

	private final String name;
	private final Property<File> modelFile;
	private final Property<File> workingDir;
	private final Property<Boolean> followLinks;

	public DefaultSimulinkModel(String name, Instantiator instantiator, ObjectFactory objectFactory) {
		this.name = name;
		this.modelFile = objectFactory.property(File.class);
		this.workingDir = objectFactory.property(File.class);
		this.followLinks = objectFactory.property(Boolean.class);
		this.followLinks.set(false);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getModelFile() {
		return modelFile.getOrNull();
	}

	public void setModelFile(File file) {
		modelFile.set(file);
	}

	@Override
	public File getWorkingDir() {
		if (this.workingDir.isPresent()) {			
			return this.workingDir.get();
		} else {
			return null;
		}
	}

	@Override
	public void setWorkingDir(File workingDir) {
		this.workingDir.set(workingDir);
	}

	@Override
	public Boolean getFollowLinks() {
		return this.followLinks.get();
	}

	@Override
	public void setFollowLinks(Boolean isExpand) {
		this.followLinks.set(isExpand);
	}

}
