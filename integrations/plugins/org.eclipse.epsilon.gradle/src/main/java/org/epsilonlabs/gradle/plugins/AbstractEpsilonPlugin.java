package org.epsilonlabs.gradle.plugins;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.epsilonlabs.gradle.tasks.AbstractEpsilonTask;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

abstract class AbstractEpsilonPlugin implements Plugin<Project> {

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(AbstractEpsilonPlugin.class);

	public static final String PLUGIN_GROP = "Epsilon";

	@Override
	public void apply(Project project) {
		project.setGroup(PLUGIN_GROP);

		// FIXME Auto import dependencies
		/*
		final Configuration epsilonConfig = project.getConfigurations().create("epsilonDependencies")
				.setVisible(false).setDescription("Epsilon dependencies");
		epsilonConfig.defaultDependencies(new Action<DependencySet>() {
			public void execute(DependencySet dependencies) {
				dependencies.add(project.getDependencies().create("org.eclipse.epsilon:epsilon-core:latest.integration"));
			}
		});*/

	}

	protected static void addTasks(Project project, Class<? extends AbstractEpsilonTask> clazz) {
		String taskCommand, taskDescription;
		try {
			taskCommand = (String) clazz.getField(AbstractEpsilonTask.TASK_COMMAND).get(null);
			taskDescription = (String) clazz.getField(AbstractEpsilonTask.TASK_DESCRIPTION)
					.get(null);
			project.getTasks().create(taskCommand, clazz, new Action<AbstractEpsilonTask>() {
				public void execute(AbstractEpsilonTask task) {
					task.setGroup(PLUGIN_GROP);
					task.setDescription(taskDescription);
					/*
					 * The folowing line enables us to call tasks by name withoug requiring import
					 * statements at the begining of the script
					 */
					project.getExtensions().getExtraProperties().set(taskCommand.toUpperCase(),
							clazz);
				};
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
