package org.epsilonlabs.gradle.extensions;

import java.io.File;

import org.epsilonlabs.gradle.artifact.EpsilonConfiguration;

public interface SimulinkEpsilonConfiguration extends EpsilonConfiguration {

	String NAME = "simulink";

	File getEngineJar();
	
	File getLibraryPath();
	
	void setEngineJar(File file);
	
	void setLibraryPath(File file);

	Boolean getHiddenEditor();

	void setHiddenEditor(Boolean isExpand);
	
}
