/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.control.IMeasurable.Stage;
import org.epsilonlabs.modelflow.execution.control.IModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.execution.control.MeasurableObject;
import org.epsilonlabs.modelflow.execution.control.MeasureableSnapshot;
import org.epsilonlabs.modelflow.execution.control.MemoryUnit;
import org.epsilonlabs.modelflow.execution.control.ProfiledStage;
import org.epsilonlabs.modelflow.execution.control.StageProfilerMap;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.IScenario;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.BenchmarkUtils;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;

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
	
	protected static File detailsFile;
	protected static File overheadFile;
	protected static File invocationsFile;
	protected static TaskFactoryRegistry taskFactoryRegistry;
	protected static ResourceFactoryRegistry resourceFactoryRegistry;

	public void setupClass() {
		if (overheadFile ==null) {			
			final Date time = new Date();
			overheadFile = BenchmarkUtils.getResultsFile("overhead", time);
			invocationsFile = BenchmarkUtils.getResultsFile("invocations", time);
			detailsFile = BenchmarkUtils.getResultsFile("details", time);
			String[] detailsHeaders = new String[] { "scenario", "tracing", "iteration", "task", "parentStage", "stage", "state", "startTime", "endTime", "startFreeMemory", "endFreeMemory"};
			List<String> headersList = new ArrayList<>();
			headersList.addAll(Arrays.asList("scenario", "tracing", "iteration"));
			headersList.addAll(IMeasurable.Stage.names());
			String[] overheadHeaders = headersList.toArray(new String[0]);
			try {
				BenchmarkUtils.prepareResultFile(overheadFile, overheadHeaders);
				BenchmarkUtils.prepareResultFile(invocationsFile, overheadHeaders);
				BenchmarkUtils.prepareResultFile(detailsFile, detailsHeaders);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
			taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
			resourceFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		}
	}

	protected void testExecution(IScenario scenario, Boolean tracing, Integer iteration, Path outputPath,
			File buildFile, int maxIter) throws Exception {
		boolean protect = scenario.isProtect();
		ModelFlowModule module = createModule(tracing, protect, outputPath);
		
		System.out.printf(">>>>[ EXECUTING ] SCENARIO: %s, TRACING: %b, ITERATION: %d%n", scenario.getName(), tracing, iteration);
		// Parse
		System.out.println("Parsing" );
		try {
			module.parse(buildFile);
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(outputPath);
			fail(e);
		}
		// Execute
		System.out.println("First execution");
		try {
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			cleanup(outputPath);
			fail("Exception during first execution");
		}
		
		if (scenario.isFirstTimeExecution()) {
			System.out.println("Skipping modifications and second execution");
		} else {			
			// Run modifications
			System.out.println("Performing modifications");
			try {
				final Runnable modifications = scenario.getModifications(outputPath);
				modifications.run();
			} catch (Exception e) {
				e.printStackTrace();
				cleanup(outputPath);
				fail("Unable to perform modifications");
			}
			
			module = createModule(tracing, protect, outputPath);
			
			System.out.println("Parsing second execution" );
			try {
				module.parse(buildFile);
			} catch (Exception e) {
				e.printStackTrace();
				cleanup(outputPath);
				fail(e);
			}
			
			// Execute
			System.out.println("Second execution");
			try {
				module.execute();
			} catch (Exception e) {
				e.printStackTrace();
				cleanup(outputPath);
				fail("Exception during second execution");
			}
		}
		
		// Assert the execution performed as expected
		final IValidate validator = scenario.getValidator();
		if (!validator.ok(module)) {
			cleanup(outputPath);
			fail(validator.expected());
		}
		
		storeResults(scenario, tracing, iteration, module, maxIter);
 
		TimeUnit.SECONDS.sleep(1);
		cleanup(outputPath);
		module.clearCache();
		System.gc();
		
	}

	protected void cleanup(Path outputPath) {
		TestUtils.clearExecutionFiles(outputPath);
	}
	
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
		IModelFlowExecutionProfiler profiler = (IModelFlowExecutionProfiler) module.getContext().getProfiler();
		
		StageProfilerMap profiledStages = profiler.getProfiledStages();
	
		saveOverhead(scenario, tracing, iteration, profiledStages);
		saveDetails(scenario, tracing, iteration, profiledStages);
		//saveTrackedDetails(scenario, tracing, iteration, maxIter, profiler);
	}

	protected void saveOverhead(IScenario scenario, Boolean tracing, Integer iteration,
			StageProfilerMap profiledStages) {
		List<Object> values = new ArrayList<>();
		List<Object> invocations = new ArrayList<>();
		invocations.addAll(Arrays.asList(scenario, tracing, iteration));
		values.addAll(Arrays.asList(scenario, tracing, iteration));
		for (Stage stage : Stage.values()) {
			 final Collection<ProfiledStage> profiles = profiledStages.getByStage(stage).values();
			// Sums all ocurrences during the execution
			 final Long duration = profiles.stream().map(v-> v.delta().getTime(TU)).reduce(0l, Long::sum);
			 values.add(duration);
			 invocations.add(profiles.size());
		}
		
		try {
			BenchmarkUtils.writeResults(overheadFile, values.toArray());
			BenchmarkUtils.writeResults(invocationsFile, invocations.toArray());
		} catch (IOException e) {
			fail(e);
		}
	}


	protected void saveDetails(IScenario scenario, Boolean tracing, Integer iteration,
			StageProfilerMap profiledStages) {
		profiledStages.entrySet().forEach(s -> {
			ProfiledStage stage = s.getValue();
			MeasurableObject key = s.getKey();
			final MeasureableSnapshot start = stage.getStart();
			final MeasureableSnapshot end = stage.getEnd();
			String state = "";
			IGraphNode n = key.getNode();
			if (n instanceof ITaskNode) {
				state = ((ITaskNode) n).getState().name();
			}
			Object[] results = new Object[] { 
					scenario, 
					tracing, 
					iteration, 
					key.getName(),
					key.getStage().getParent(), 
					key.getStage().name(), 
					state,
					(start != null) ? start.getTime(TU) : "",
					(end != null) ? end.getTime(TU) : "",
					(start != null) ? start.getFreeMemory(MU) : "",
					(end != null) ? end.getFreeMemory(MU) : ""
			};
			try {
				BenchmarkUtils.writeResults(detailsFile, results);
			} catch (IOException e) {
				fail(e);
			}
		});
	}

	protected void saveTrackedDetails(IScenario scenario, Boolean tracing, Integer iteration, int maxIter,
			IModelFlowExecutionProfiler profiler) {
		if (maxIter == iteration) {		
			String[] split = detailsFile.getName().split("\\.");
			final String originalName = split[0];
			final String extension = split[1];
			String newName = String.format("%s_tracked_%s_%s.%s", originalName, scenario.getName(), tracing, extension); 
			try {
				File destinationFile = detailsFile.toPath().resolveSibling(newName).toFile();
				destinationFile.createNewFile();
				Files.copy(profiler.getMemoryTracker().getLogFile(), destinationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
