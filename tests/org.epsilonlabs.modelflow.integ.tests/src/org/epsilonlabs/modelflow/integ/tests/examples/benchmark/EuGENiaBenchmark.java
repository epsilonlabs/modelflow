/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.ComponentScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource.Mode;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Betty Sanchez
 *
 */
public class EuGENiaBenchmark extends AbstractBenchmark {

	protected static final int MAX_ITER = 1;

	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = ComponentScenarios.class, names="NO_MODIFICATION", mode = Mode.INCLUDE, times = MAX_ITER)
	public void componentExampleTwoExecutionTests(ComponentScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {

		// Setup Variables
		setupClass();
		String projectName = "org.epsilonlabs.modelflow.eugenia.bpmn.example";
		String buildFileName = "eugenia.mflow";
		
		testExecution(scenario, tracing, iteration, projectName, buildFileName, MAX_ITER);
	}

	@Override
	protected ModelFlowModule createModule(Boolean tracing, Boolean protect, Path outputPath) {
		final ModelFlowModule module = super.createModule(tracing, protect, outputPath);
		
		// Execution parameters
		Map<String, Object> params = new HashMap<>();
		params.put("metamodelName", "simplebpmn");
		params.put("pluginPrefix", "org.epsilonlabs.modelflow.eugenia.bpmn");
		params.put("copyrightStatement", "copyright.txt");
		
		FrameStack fs = module.getContext().getFrameStack();
		params.entrySet().stream().map(Variable::createReadOnlyVariable).forEach(fs::put);
	
		return module;
	}
	
}
