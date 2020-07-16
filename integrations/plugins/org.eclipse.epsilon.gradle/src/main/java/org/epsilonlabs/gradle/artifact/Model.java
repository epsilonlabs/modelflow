package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.Named;
import org.gradle.api.tasks.InputFile;

public interface Model extends Named {
		
	@InputFile
	File getModelFile();
	
	void setModelFile(File modelFile);
}
