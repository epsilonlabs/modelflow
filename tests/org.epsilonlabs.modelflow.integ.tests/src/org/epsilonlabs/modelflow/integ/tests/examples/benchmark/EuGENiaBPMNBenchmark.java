/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.EugeniaScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Betty Sanchez
 *
 * This test must be run as a plugin test and the workspace should be set to ${project_loc}/target/modelflow/test/
 * Ensure that the RuntimeConfiguration does not include the original eugenia example plugin or it will conflict with the one in the target 
 */
public class EuGENiaBPMNBenchmark extends AbstractBenchmark {

	protected static final int MAX_ITER = 1;
	protected Path diagramProjectOutputPath;
	protected String metamodelName = "simplebpmn";
	protected String base = "org.eclipse.epsilon.eugenia";

	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = EugeniaScenarios.class, times = MAX_ITER)
	public void eugeniaExampleTwoExecutionTests(EugeniaScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {
		// Setup Variables
		setupClass();
		
		TestUtils.clearExecutionFiles(Paths.get(ResourceLocator.getTestDir()));

		String projectName = String.format("%s.%s", base, metamodelName);
		String buildFileName = "eugenia.mflow";
		
		final Path eugeniaSource = Paths.get(System.getProperty("user.dir"), "..","..", "examples", "EuGENia");
		final Path eugeniaOutputProjectPath = TestUtils.copyExampleProjectToTempLocation(eugeniaSource.resolve(projectName), projectName);
		importProject(eugeniaOutputProjectPath);
		final File buildScript = TestUtils.getBuildScript(eugeniaOutputProjectPath, buildFileName);
		
		// Copy dependent diagram project
		final String diagramProjectName = String.format("%s.%s.diagram.custom", base, metamodelName);
		this.diagramProjectOutputPath = TestUtils.copyExampleProjectToTempLocation(eugeniaSource.resolve(diagramProjectName), diagramProjectName);
		importProject(diagramProjectOutputPath);
		
		testExecution(scenario, tracing, iteration, eugeniaOutputProjectPath, buildScript, MAX_ITER);
	}

	protected void importProject(final Path outputPath) throws CoreException {
		final org.eclipse.core.runtime.Path mainEclipseProject = new org.eclipse.core.runtime.Path(outputPath.resolve(".project").toString());
		IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(mainEclipseProject);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
		project.create(description, null);
		project.open(null);
	}
	
	@Override
	protected void cleanup(Path outputPath) {
		super.cleanup(outputPath);
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++ ) {
			try {
				final IProject p = projects[i];
				p.delete(IResource.ALWAYS_DELETE_PROJECT_CONTENT, null);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unable to close projects");
			}
		}
		TestUtils.clearExecutionFiles(diagramProjectOutputPath.getParent());
		
	}

	@Override
	protected ModelFlowModule createModule(Boolean tracing, Boolean protect, Path outputPath) {
		final ModelFlowModule module = super.createModule(tracing, protect, outputPath);
		
		// Execution parameters
		Map<String, Object> params = new HashMap<>();
		params.put("metamodelName", metamodelName);
		params.put("pluginPrefix", base);
		params.put("copyrightStatement", "copyright.txt");
		
		FrameStack fs = module.getContext().getFrameStack();
		params.entrySet().stream().map(Variable::createReadOnlyVariable).forEach(fs::put);
	
		return module;
	}
	
}
