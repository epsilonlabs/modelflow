package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;

public interface SimulinkModel extends Model {

	public static final String ID = "Simulink";
	
	@InputFile
	public File getModelFile();
	
	public void setModelFile(File modelFile);
	
	@InputFile
	public File getWorkingDir();
	
	public void setWorkingDir(File workingDir);

	@Optional
	@Input
	public Boolean getFollowLinks();

	public void setFollowLinks(Boolean isExpand);
	
	
}