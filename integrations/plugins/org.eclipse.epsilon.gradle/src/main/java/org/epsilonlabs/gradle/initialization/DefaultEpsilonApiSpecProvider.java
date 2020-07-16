package org.epsilonlabs.gradle.initialization;

import java.util.Set;

import org.gradle.initialization.GradleApiSpecProvider;
import org.gradle.internal.impldep.com.google.common.collect.ImmutableSet;

public class DefaultEpsilonApiSpecProvider extends GradleApiSpecProvider.SpecAdapter implements GradleApiSpecProvider {

    /*@Override
    public Set<Class<?>> getExportedClasses() {
        return ImmutableSet.<Class<?>>of(
            FastStringServiceFactory.class,
            DefaultFastStringServiceFactory.class
        );
    }
    
    @Override
    public Set<String> getExportedResources() {
        return ImmutableSet.of(
            "META-INF/groovy/org.codehaus.groovy.runtime.ExtensionModule",
            "META-INF/services/org.apache.groovy.json.FastStringServiceFactory"
        );
    }*/

    @Override
    public Set<String> getExportedPackages() {
        return ImmutableSet.of(
            "org.epsilonlabs.gradle",
            "org.epsilonlabs.gradle.tasks",
            "org.epsilonlabs.gradle.artifact");
    }

    @Override
    public Set<String> getExportedResourcePrefixes() {
        return ImmutableSet.of(
            "META-INF/gradle-plugins", 
            "META-INF/services"
        );
    }

    @Override
    public Spec get() {
        return this;
    }
}
