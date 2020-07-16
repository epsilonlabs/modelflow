package org.epsilonlabs.gradle.artifact;

import org.gradle.api.Namer;
import org.gradle.api.internal.DefaultNamedDomainObjectList;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.util.ConfigureUtil;

import groovy.lang.Closure;

public class DefaultEpsilonConfigurationContainer extends DefaultNamedDomainObjectList<EpsilonConfiguration> implements EpsilonConfigurationContainer {

	public DefaultEpsilonConfigurationContainer(Instantiator instantiator) {
        super(EpsilonConfiguration.class, instantiator, new ConfigurationNamer());
	}

	private static class ConfigurationNamer implements Namer<EpsilonConfiguration> {
        public String determineName(EpsilonConfiguration r) {
            return r.getName();
        }
    }

	@SuppressWarnings("rawtypes") 
	@Override
	public EpsilonConfigurationContainer configure(Closure closure) {
		   return ConfigureUtil.configureSelf(closure, this);
	}

	@Override
    public String getTypeDisplayName() {
        return "config";
    }
}
