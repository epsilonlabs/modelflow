package org.epsilonlabs.gradle.artifact;

import javax.inject.Inject;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.internal.DefaultPolymorphicDomainObjectContainer;
import org.gradle.internal.reflect.Instantiator;

public class DefaultModelContainer extends DefaultPolymorphicDomainObjectContainer<Model> implements ModelContainer {

	@Inject
	public DefaultModelContainer(Instantiator instantiator) {
		super(Model.class, instantiator);
	}

	@Override
	protected void handleAttemptToAddItemWithNonUniqueName(Model m) {
		throw new InvalidUserDataException(String.format("Model with name '%s' added multiple times", m.getName()));
	}

}
