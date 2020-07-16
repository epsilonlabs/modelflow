package org.epsilonlabs.gradle.artifact;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.internal.DefaultPolymorphicDomainObjectContainer;
import org.gradle.internal.reflect.Instantiator;

public class DefaultRuntimeModelContainer extends DefaultPolymorphicDomainObjectContainer<RuntimeModel> implements RuntimeModelContainer {

	public DefaultRuntimeModelContainer(Instantiator instantiator) {
		super(RuntimeModel.class, instantiator);
	}

	@Override
	protected void handleAttemptToAddItemWithNonUniqueName(RuntimeModel m) {
		throw new InvalidUserDataException(String.format("Model with name '%s' added multiple times", m.getName()));
	}

}
