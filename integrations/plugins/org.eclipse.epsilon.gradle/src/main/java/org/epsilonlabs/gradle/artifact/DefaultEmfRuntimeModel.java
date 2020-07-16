package org.epsilonlabs.gradle.artifact;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class DefaultEmfRuntimeModel implements EmfRuntimeModel {

	private final Property<String> name;
	private final Property<String> alias;
	private final Property<Boolean> readOnLoad;
	private final Property<Boolean> storeOnDisposal;
	private final Property<Boolean> isCachingEnabled;

	public DefaultEmfRuntimeModel(
	        String name,
	        ObjectFactory objectFactory
	) {
		this.name = objectFactory.property(String.class);
        this.name.set(name);
        this.alias = objectFactory.property(String.class);
        this.alias.set("");
        this.readOnLoad = objectFactory.property(Boolean.class);
        this.readOnLoad.set(true);
        this.storeOnDisposal = objectFactory.property(Boolean.class);
        this.storeOnDisposal.set(false);
        this.isCachingEnabled = objectFactory.property(Boolean.class);
        this.isCachingEnabled.set(false);
	}
	
	@Override
	public String getName() {
		return this.name.get();
	}

	@Override
	public String getAlias() {
		return this.alias.get();
	}

	@Override
	public void setAlias(String alias) {
		this.alias.set(alias);
	}

	@Override
	public Boolean getReadOnLoad() {
		return this.readOnLoad.get();
	}

	@Override
	public void setReadOnLoad(Boolean readOnLoad) {
		this.readOnLoad.set(readOnLoad);
	}

	@Override
	public Boolean getStoreOnDisposal() {
		return this.storeOnDisposal.get();
	}

	@Override
	public void setStoreOnDisposal(Boolean storeOnDisposal) {
		this.storeOnDisposal.set(storeOnDisposal);
	}

	@Override
	public Boolean getIsCachingEnabled() {
		return this.isCachingEnabled.get();
	}

	@Override
	public void setIsCachingEnabled(Boolean storeOnDisposal) {
		this.isCachingEnabled.set(storeOnDisposal);
	}
	
}