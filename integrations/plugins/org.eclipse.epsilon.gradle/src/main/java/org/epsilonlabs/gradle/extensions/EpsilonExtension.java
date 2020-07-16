package org.epsilonlabs.gradle.extensions;

import org.epsilonlabs.gradle.artifact.EpsilonConfigurationContainer;
import org.epsilonlabs.gradle.artifact.ModelContainer;
import org.gradle.api.Action;

public interface EpsilonExtension {

	String NAME = "epsilon";

	ModelContainer getModels();

	void models(Action<? super ModelContainer> configure);
	
	EpsilonConfigurationContainer getConfig();
	
	void config(Action<? super EpsilonConfigurationContainer> configure);
}
