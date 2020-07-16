package org.epsilonlabs.gradle.artifact.internal;

import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.RuntimeModel;
import org.gradle.api.Project;

public interface Factory {

	public IModel get(Model model, RuntimeModel runtime, Project project);
	
	public IModel get(Model model, Project project);
	
}
