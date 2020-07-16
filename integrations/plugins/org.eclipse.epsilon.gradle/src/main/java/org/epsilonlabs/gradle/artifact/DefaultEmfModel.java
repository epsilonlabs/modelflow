package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class DefaultEmfModel implements EmfModel {

	//private FileCollection metamodelFiles;
	private final Property<String> name;
	private final Property<File> modelFile;
	private final Property<File> metamodelFile;
	private final Property<Boolean> reuseUnmodifiedFileBasedMetamodels;
	private final Property<Boolean> isExpand;

	public DefaultEmfModel(String name, ObjectFactory objectFactory) {
		this.name = objectFactory.property(String.class);
		this.name.set(name);
		this.modelFile = objectFactory.property(File.class);
		this.metamodelFile = objectFactory.property(File.class);
		// this.metamodelFiles = objectFactory.property(FileCollection.class);
		// this.metamodelFiles.set();
		this.reuseUnmodifiedFileBasedMetamodels = objectFactory.property(Boolean.class);
		this.reuseUnmodifiedFileBasedMetamodels.set(true);
		this.isExpand = objectFactory.property(Boolean.class);
		this.isExpand.set(false);
	}

	@Override
	public String getName() {
		return this.name.get();
	}

	@Override
	public File getModelFile() {
		return this.modelFile.getOrNull();
	}

	public void setModelFile(File file) {
		this.modelFile.set(file);
	}
	
	@Override
	public File getMetamodelFile() {
		return this.metamodelFile.getOrNull();
	}

	public void setMetamodelFile(File file) {
		this.metamodelFile.set(file);
	}

	/*@Override
	@PathSensitive(PathSensitivity.RELATIVE)
    @InputFiles
	public FileCollection getMetamodelFiles() {
		return this.metamodelFiles;
	}

	@Override
	public void setMetamodelFiles(FileCollection metamodelFiles) {
		this.metamodelFiles = metamodelFiles;
	}*/

	@Override
	public boolean getIsReuseUnmodifiedFileBasedMetamodels() {
		return this.reuseUnmodifiedFileBasedMetamodels.get();
	}

	@Override
	public void setReuseUnmodifiedFileBasedMetamodels(boolean reuseUnmodifiedFileBasedMetamodels) {
		this.reuseUnmodifiedFileBasedMetamodels.set(reuseUnmodifiedFileBasedMetamodels);
	}

	@Override
	public Boolean getIsExpand() {
		return this.isExpand.get();
	}

	@Override
	public void setIsExpand(Boolean isExpand) {
		this.isExpand.set(isExpand);
	}

}