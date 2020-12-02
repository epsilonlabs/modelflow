/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.execution.control.IModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.execution.control.MeasurableObject;
import org.epsilonlabs.modelflow.execution.control.MeasureableSnapshot;
import org.epsilonlabs.modelflow.execution.control.MemoryUnit;
import org.epsilonlabs.modelflow.execution.control.ProfiledStage;
import org.epsilonlabs.modelflow.execution.control.StageProfilerMap;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.IScenario;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.BenchmarkUtils;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

import com.google.common.io.Files;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractBenchmark {
	
	protected static final TimeUnit TU = TimeUnit.NANOSECONDS;
	protected static final MemoryUnit MU = MemoryUnit.BYTES;
	
	protected static File resultsFile;
	protected static TaskFactoryRegistry taskFactoryRegistry;
	protected static ResourceFactoryRegistry resourceFactoryRegistry;

	public void setupClass() {
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

	/**
	 * @param scenario
	 * @param tracing
	 * @param iteration
	 * @param projectName
	 * @param buildFileName
	 * @param maxIter 
	 * @throws Exception
	 */
	protected void testExecution(IScenario scenario, Boolean tracing, Integer iteration, Path outputPath,
			File buildFile, int maxIter) throws Exception {
		boolean protect = scenario.isProtect();
		ModelFlowModule module = createModule(tracing, protect, outputPath);
		
		// Parse
		System.out.println("Parsing" );
		try {
			module.parse(buildFile);
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
		/*
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
			module.parse(buildFile);
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
		*/
		
		storeResults(scenario, tracing, iteration, module, maxIter);
 
		TestUtils.clearExecutionFiles(outputPath);
		cleanup();
	}

	protected void cleanup() {}
	
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

	protected void storeResults(IScenario scenario, Boolean tracing, Integer iteration, ModelFlowModule module, int maxIter) {
		// Save tracked memory profile of last iteration
		if (maxIter == iteration) {			
			IModelFlowExecutionProfiler profiler = (IModelFlowExecutionProfiler) module.getContext().getProfiler();
			StageProfilerMap profiledStages = profiler.getProfiledStages();
			profiledStages.entrySet().forEach(s -> {
				ProfiledStage stage = s.getValue();
				MeasurableObject key = s.getKey();
				final MeasureableSnapshot start = stage.getStart();
				final MeasureableSnapshot end = stage.getEnd();
				Object[] results = new Object[] { 
						scenario, 
						tracing, 
						iteration, 
						key.getNode(),
						key.getStage().getGroup(), 
						key.getStage().name(), 
						(start != null) ? start.getTime(TU) : "",
						(end != null) ? end.getTime(TU) : "",
						(start != null) ? start.getFreeMemory(MU) : "",
						(end != null) ? end.getFreeMemory(MU) : ""
				};
				try {
					BenchmarkUtils.writeResults(resultsFile, results);
				} catch (IOException e) {
					fail(e);
				}
			});
			String[] extension = resultsFile.getName().split("\\.");
			String newName = extension[0] + "_tracked_"+scenario.getName()+"_"+tracing+"." + extension[1]; 
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
