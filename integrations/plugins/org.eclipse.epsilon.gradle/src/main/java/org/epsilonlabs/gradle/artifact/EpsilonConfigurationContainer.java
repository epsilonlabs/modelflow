package org.epsilonlabs.gradle.artifact;

import org.gradle.api.NamedDomainObjectList;
import org.gradle.util.Configurable;

public interface EpsilonConfigurationContainer extends NamedDomainObjectList<EpsilonConfiguration>,
		Configurable<EpsilonConfigurationContainer> {

}
