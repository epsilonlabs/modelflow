package org.epsilonlabs.gradle.artifact;

import org.gradle.api.Named;

public interface RuntimeModel extends Named {
	
	String getAlias();
	
	void setAlias(String alias);

	Boolean getReadOnLoad();
	
	void setReadOnLoad(Boolean readOnLoad);
	
	Boolean getStoreOnDisposal();
	
	void setStoreOnDisposal(Boolean storeOnDisposal);
	
}
