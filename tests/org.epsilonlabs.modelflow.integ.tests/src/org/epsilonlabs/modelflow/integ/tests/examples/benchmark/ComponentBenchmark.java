/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.ComponentScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Betty Sanchez
 *
 */
public class ComponentBenchmark extends AbstractBenchmark{

	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = ComponentScenarios.class, /*names="NO_MODIFICATION", mode = Mode.INCLUDE,*/ times = MAX_ITER)
	public void componentExampleTwoExecutionTests(ComponentScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {

		// Setup Variables
		setupClass();
		String projectName = "org.epsilonlabs.modelflow.component.example";
		String buildFileName = "component.mflow";
		
		testExecution(scenario, tracing, iteration, projectName, buildFileName);
	}

}
