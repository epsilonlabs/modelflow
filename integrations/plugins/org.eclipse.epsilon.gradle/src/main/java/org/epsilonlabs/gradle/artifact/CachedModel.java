package org.epsilonlabs.gradle.artifact;

import org.gradle.api.tasks.InputFile;

public interface CachedModel extends Model {
	
	@InputFile
	public Boolean getCachingEnabled();
	
	public void setCachingEnabled(Boolean modelFile);
}
