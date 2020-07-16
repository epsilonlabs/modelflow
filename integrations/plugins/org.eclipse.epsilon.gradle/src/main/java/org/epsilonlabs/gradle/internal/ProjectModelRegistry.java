package org.epsilonlabs.gradle.internal;

import java.util.Set;

public interface ProjectModelRegistry {

	void registerModel(String projectPath, ProjectModel model);

	Set<ProjectModel> getModels(String projectPath);

}
