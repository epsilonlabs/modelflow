package org.epsilonlabs.gradle.artifact;

import java.io.File;

import org.gradle.api.Buildable;

public interface ModelArtifact extends Buildable {

	File getFile();

    void builtBy(Object... tasks);
    
}
