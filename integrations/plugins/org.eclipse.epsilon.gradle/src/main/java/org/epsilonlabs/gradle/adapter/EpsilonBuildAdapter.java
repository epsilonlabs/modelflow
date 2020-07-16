package org.epsilonlabs.gradle.adapter;

import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;

/**
 * A {@link BuildListener} adapter class for receiving build events. The methods in this class are empty. This class
 * exists as convenience for creating listener objects.
 */
public class EpsilonBuildAdapter implements BuildListener {

	@Override
	public void buildFinished(BuildResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildStarted(Gradle arg0) {
		// TODO Auto-generated method stub
		
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
