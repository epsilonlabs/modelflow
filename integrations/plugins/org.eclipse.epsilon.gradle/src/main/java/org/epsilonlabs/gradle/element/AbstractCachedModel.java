package org.epsilonlabs.gradle.element;

import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.gradle.api.tasks.Input;

public abstract class AbstractCachedModel extends AbstractModel {

	protected Boolean isCachingEnabled = true;

	@Input
	public Boolean getIsCachingEnabled() {
		return isCachingEnabled;
	}

	public void setIsCachingEnabled(Boolean isCachingEnabled) {
		this.isCachingEnabled = isCachingEnabled;
	}
	
	protected IModel setup(IModel model) {
		CachedModel cachedModel = (CachedModel) model;
		cachedModel.setCachingEnabled(getIsCachingEnabled());
		
		return (IModel) super.setup(cachedModel);
	}

}
