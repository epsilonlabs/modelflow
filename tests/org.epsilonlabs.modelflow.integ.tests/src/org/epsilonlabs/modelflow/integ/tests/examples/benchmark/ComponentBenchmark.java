/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import java.io.File;
import java.nio.file.Path;

import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.ComponentScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Betty Sanchez
 * Run as Junit Test
 * FIXME: PluginTest has some failed assertions
 */
public class ComponentBenchmark extends AbstractBenchmark{

	protected static final int MAX_ITER = 2;

	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = ComponentScenarios.class, times = MAX_ITER)
	public void componentExampleTwoExecutionTests(ComponentScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {

		// Setup Variables
		setupClass();
		String projectName = "org.epsilonlabs.modelflow.component.example";
		String buildFileName = "component.mflow";
		final Path outputPath = TestUtils.copyExampleProjectToTempLocation(projectName);
		final File buildScript = TestUtils.getBuildScript(outputPath, buildFileName);

		testExecution(scenario, tracing, iteration, outputPath, buildScript, MAX_ITER);
	}

}
