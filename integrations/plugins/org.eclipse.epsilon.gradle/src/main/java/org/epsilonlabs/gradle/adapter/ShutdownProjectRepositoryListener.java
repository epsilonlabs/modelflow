package org.epsilonlabs.gradle.adapter;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;

public class ShutdownProjectRepositoryListener implements BuildListener {

	private static ShutdownProjectRepositoryListener INSTANCE = null;
	
	private ModelRepository repository;
	
	private ShutdownProjectRepositoryListener() {}

	public static void activate(Project project, ModelRepository repository) {
		if (INSTANCE == null) {
			INSTANCE = new ShutdownProjectRepositoryListener();
			INSTANCE.repository = repository;
			project.getGradle().addBuildListener(INSTANCE);
		}
	}

	@Override
	public void buildFinished(BuildResult arg0) {
		if (arg0.getFailure() != null) {
			for (IModel model : repository.getModels()) {
				model.setStoredOnDisposal(false);
			}
		}
		repository.dispose();
	}

	@Override
	public void buildStarted(Gradle arg0) {
		
	}

	@Override
	public void projectsEvaluated(Gradle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectsLoaded(Gradle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void settingsEvaluated(Settings arg0) {
		// TODO Auto-generated method stub
		
	}	
	
}