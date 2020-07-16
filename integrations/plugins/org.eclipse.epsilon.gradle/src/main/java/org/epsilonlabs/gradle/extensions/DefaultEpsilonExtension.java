package org.epsilonlabs.gradle.extensions;

import org.epsilonlabs.gradle.artifact.EpsilonConfigurationContainer;
import org.epsilonlabs.gradle.artifact.ModelContainer;
import org.gradle.api.Action;

public class DefaultEpsilonExtension implements EpsilonExtension {

	private final ModelContainer models;
	private final EpsilonConfigurationContainer config;

	public DefaultEpsilonExtension(ModelContainer models, EpsilonConfigurationContainer config) {
		this.models = models;
		this.config = config;
	}

	@Override
	public ModelContainer getModels() {
		return this.models;
	}

	@Override
	public void models(Action<? super ModelContainer> configure) {
		configure.execute(this.models);
	}

	@Override
	public EpsilonConfigurationContainer getConfig() {
		return this.config;
	}

	@Override
	public void config(Action<? super EpsilonConfigurationContainer> configure) {
		configure.execute(this.config);
	}
	
}
