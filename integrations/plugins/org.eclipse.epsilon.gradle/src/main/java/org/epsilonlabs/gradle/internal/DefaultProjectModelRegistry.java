package org.epsilonlabs.gradle.internal;

import java.util.Set;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

public class DefaultProjectModelRegistry implements ProjectModelRegistry {

	private final SetMultimap<String, ProjectModel> modelsByProject = LinkedHashMultimap.create();

	@Override
	public Set<ProjectModel> getModels(String projectPath) {
		return modelsByProject.get(projectPath);
	}

	@Override
	public void registerModel(String projectPath, ProjectModel publication) {
		modelsByProject.put(projectPath, publication);
	}
}
