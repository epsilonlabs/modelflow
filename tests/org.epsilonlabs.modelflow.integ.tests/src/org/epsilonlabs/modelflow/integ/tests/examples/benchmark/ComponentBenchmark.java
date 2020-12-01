/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.execution.control.IModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.execution.control.MeasurableObject;
import org.epsilonlabs.modelflow.execution.control.MemoryUnit;
import org.epsilonlabs.modelflow.execution.control.ProfiledStage;
import org.epsilonlabs.modelflow.execution.control.StageProfilerMap;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.ComponentScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.BenchmarkUtils;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.junit.jupiter.params.ParameterizedTest;

import com.google.common.io.Files;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class ComponentBenchmark {

	protected static final TimeUnit TU = TimeUnit.NANOSECONDS;
	protected static final MemoryUnit MU = MemoryUnit.BYTES;
	protected static final int MAX_ITER = 1;

	protected static File resultsFile;
	protected static TaskFactoryRegistry taskFactoryRegistry;
	protected static ResourceFactoryRegistry resourceFactoryRegistry;

	protected void setupClass() {
		if (resultsFile ==null) {			
			resultsFile = BenchmarkUtils.getResultsFile();
			String[] headers = new String[] { "scenario", "tracing", "iteration", "task", "group", "stage", "startTime", "endTime", "startFreeMemory", "endFreeMemory"};
			try {
				BenchmarkUtils.prepareResultFile(resultsFile, headers);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
			taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
			resourceFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		}
	}
	
	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = ComponentScenarios.class, /*names="NO_MODIFICATION", mode = Mode.INCLUDE,*/ times = MAX_ITER)
	public void componentExampleTwoExecutionTests(ComponentScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {

		// Setup Variables
		setupClass();
		String projectName = "org.epsilonlabs.modelflow.component.example";
		String buildFileName = "component.mflow";
		final Path outputPath = TestUtils.copyExampleProjectToTempLocation(projectName);

		boolean protect = scenario.isProtect();
		ModelFlowModule module = createModule(tracing, protect, outputPath);
		
		// Parse
		System.out.println("Parsing" );
		try {
			module.parse(TestUtils.getBuildScript(projectName, buildFileName));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e);
		}
		// Execute
		System.out.println("First execution");
		try {
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception during first execution");
		}
		// Run modifications
		System.out.println("Performing modifications");
		try {
			final Runnable modifications = scenario.getModifications(outputPath);
			modifications.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unable to perform modifications");
		}
		
		module = createModule(tracing, protect, outputPath);
		
		System.out.println("Parsing second execution" );
		try {
			module.parse(TestUtils.getBuildScript(projectName, buildFileName));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e);
		}
		
		// Execute
		System.out.println("Second execution");
		try {
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception during second execution");
		}
		
		// Assert the execution performed as expected
		final IValidate validator = scenario.getValidator();
		assertTrue(validator.expected(), validator.ok(module));
		
		storeResults(scenario, tracing, iteration, module);
 
		TestUtils.clearExecutionFiles(projectName);
	}

	/**
	 * @param tracing
	 * @param outputPath
	 * @return
	 */
	protected ModelFlowModule createModule(Boolean tracing, Boolean protect, final Path outputPath) {
		File endToEndTraceFile = outputPath.resolve("endToEndTrace.mftrace").toFile();
		File executionTraceFile = outputPath.resolve("executionTrace.mfexec").toFile();

		// Configure Module
		ModelFlowModule module = new ModelFlowModule();
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resourceFactoryRegistry);
		module.getContext().setProfilingEnabled(true);
		module.getContext().setInteractive(false);
		module.getContext().setProtectResources(protect);
		IModelFlowConfiguration configuration = module.getConfiguration();
		configuration.setExecutionTraceLocation(executionTraceFile);
		module.getContext().setEndToEndTracing(tracing);
		if (tracing) {
			configuration.setEndToEndTraceLocation(endToEndTraceFile);	
		}
		return module;
	}

	protected void storeResults(ComponentScenarios scenario, Boolean tracing, Integer iteration, ModelFlowModule module) {
		// Save tracked memory profile of last iteration
		if (MAX_ITER == iteration) {			
			IModelFlowExecutionProfiler profiler = (IModelFlowExecutionProfiler) module.getContext().getProfiler();
			StageProfilerMap profiledStages = profiler.getProfiledStages();
			profiledStages.entrySet().forEach(s -> {
				ProfiledStage stage = s.getValue();
				MeasurableObject key = s.getKey();
				Object[] results = new Object[] { 
						scenario, 
						tracing, 
						iteration, 
						key.getNode(),
						key.getStage().getGroup(), 
						key.getStage().name(), 
						stage.getStart().getTime(TU),
						stage.getEnd().getTime(TU),
						stage.getStart().getFreeMemory(MU),
						stage.getEnd().getFreeMemory(MU)
				};
				try {
					BenchmarkUtils.writeResults(resultsFile, results);
				} catch (IOException e) {
					fail(e);
				}
			});
			String[] extension = resultsFile.getName().split("\\.");
			String newName = extension[0] + "_tracked_"+scenario.name()+"_"+tracing+"." + extension[1]; 
			try {
				File destinationFile = resultsFile.toPath().resolveSibling(newName).toFile();
				destinationFile.createNewFile();
				Files.copy(profiler.getMemoryTracker().getLogFile(), destinationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
