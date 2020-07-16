package org.epsilonlabs.gradle.artifact;

public interface SimulinkRuntimeModel extends RuntimeCachedModel {

	public static final String ID = "SimulinkRuntime";
	
	Boolean getFollowReferences();
	
	void setIsFollowReferences(Boolean followReferences);
}
