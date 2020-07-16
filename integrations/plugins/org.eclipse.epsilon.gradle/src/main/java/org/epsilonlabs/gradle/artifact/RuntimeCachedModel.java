package org.epsilonlabs.gradle.artifact;

public interface RuntimeCachedModel extends RuntimeModel {
		
	Boolean getIsCachingEnabled();
	
	void setIsCachingEnabled(Boolean storeOnDisposal);
	
}
