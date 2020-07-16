package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;

public interface EmfModel extends Model {
	
	public static final String ID = "EMF";
	
/*	@InputFiles
	public FileCollection getMetamodelFiles();
	
	public void setMetamodelFiles(FileCollection metamodelFiles);
*/
	@InputFile
	public File getMetamodelFile();
	
	public void setMetamodelFile(File metamodelFile);

	@Optional
	@Input
	public boolean getIsReuseUnmodifiedFileBasedMetamodels();

	public void setReuseUnmodifiedFileBasedMetamodels(boolean reuseUnmodifiedFileBasedMetamodels);

	@Optional
	@Input
	public Boolean getIsExpand();

	public void setIsExpand(Boolean isExpand);
	
}