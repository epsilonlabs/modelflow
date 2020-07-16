package org.epsilonlabs.gradle.adapter;

import org.gradle.BuildListener;
import org.gradle.BuildResult;
import org.gradle.api.Project;
import org.gradle.api.ProjectEvaluationListener;
import org.gradle.api.ProjectState;
import org.gradle.api.Task;
import org.gradle.api.artifacts.DependencyResolutionListener;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.execution.TaskExecutionListener;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskState;
import org.gradle.initialization.BuildCompletionListener;
import org.gradle.internal.buildevents.BuildStartedTime;
import org.gradle.internal.time.Clock;
import org.gradle.profile.BuildProfile;
import org.gradle.profile.ContinuousOperation;
import org.gradle.profile.ProfileEventAdapter;
import org.gradle.profile.ProfileListener;

/**
 * Adapts various events to build a {@link BuildProfile} model, and then
 * notifies a {@link ReportGeneratingProfileListener} when the model is ready.
 */
public class EpsilonProfileEventAdapter implements BuildListener, ProjectEvaluationListener, TaskExecutionListener,
		DependencyResolutionListener, BuildCompletionListener {

	/*private final BuildStartedTime buildStartedTime;
	private final Clock clock;
	private final ProfileListener listener;
	private final ThreadLocal<ContinuousOperation> currentTransform = new ThreadLocal<ContinuousOperation>();
	private BuildProfile buildProfile;*/

	@Override
	public void completed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterResolve(ResolvableDependencies arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeResolve(ResolvableDependencies arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterExecute(Task arg0, TaskState arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeExecute(Task arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterEvaluate(Project arg0, ProjectState arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeEvaluate(Project arg0) {
		// TODO Auto-generated method stub

	}

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
