package gradle.modelflow;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.execution.control.IMeasurable.Stage;
import org.epsilonlabs.modelflow.execution.control.IModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(Parameterized.class)
public class ModelflowPerformanceExperiment extends IterativeExperiment {
	
	protected ModelFlowModule module;
	
	private static final Path ROOT = Paths.get(System.getProperty("user.dir"),"target","gradle.workflow");
	
	@Override
	protected Path getTestProjectRoot() {
		return ROOT;
	}

	@Override
	protected String getApproachName() {
		return "ModelFlow";
	}

	@Override
	protected void beforeTestExecution() throws Exception {
		if (getTestProjectRoot().toFile().exists()) {
			FileUtils.deleteDirectory(getTestProjectRoot().toFile());
		}
		
		Injector injector = Guice.createInjector(new EpsilonPlugin());//, new EMFPlugin(), new CorePlugin(), new GMFPlugin());
		TaskFactoryRegistry taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		ResourceFactoryRegistry resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		
		/** Module Workflow */
		module = new ModelFlowModule();
		Workflow workflow = EcoreUtil.copy(getComponentWorkflow());
		module.getContext().setProfilingEnabled(true);
		module.setWorkflow(workflow);
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resFactoryRegistry);
		module.getContext().setProtectResources(false);
		module.getContext().setInteractive(false);
	}

	@Override
	protected void afterTestExecution() {
		module.getContext().dispose();
	}
	
	@Override
	protected void execute() {
		try{
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed Execution");
		}
	}
	
	@Override
	@Test
	public void runIteration() {
		/** First Execution */
		execute();
		
		IModelFlowExecutionProfiler profiler = (IModelFlowExecutionProfiler) module.getContext().getProfiler();
		if (scenarioId == 1) {
			evl = deltaNanoTime("evl", profiler);
			etl = deltaNanoTime("etl", profiler);
			egx = deltaNanoTime("egx", profiler);
			duration = evl + etl + egx;
			
		}
		/** Invoke Scenario Modifications */
		Scenario.get(scenarioId).getModifications(getTestProjectRoot()).run();
		
		/** Second Execution */
		execute();
		if (scenarioId != 1) {
			evl = deltaNanoTime("evl", profiler);
			etl = deltaNanoTime("etl", profiler);
			egx = deltaNanoTime("egx", profiler);
			duration = evl + etl + egx;

		}
	}
	
	protected Long deltaNanoTime(String name, IModelFlowExecutionProfiler profiler) {
		final AtomicLong result = new AtomicLong();
		profiler.getByStage(Stage.EXECUTION_PROCESS).getByNode(name).values().stream().findFirst().ifPresent(p-> result.set(p.delta().getTime(TimeUnit.NANOSECONDS)));
		return result.get();
	}
			
	public static Workflow getComponentWorkflow() {
		Workflow w = new WorkflowBuilder()
			
			.addModelResource("config", "epsilon:emf")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","m","config.model")))
			.addProperty("metamodelFile", 	ROOT.resolve(Paths.get("resources","mm","configuration.ecore")))
			
			.addModelResource("component", "epsilon:emf")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","m","component.model")))
			.addProperty("metamodelFile", 	ROOT.resolve(Paths.get("resources","mm","component.ecore")))
			
			.addModelResource("variant", "epsilon:emf")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","m","variant.model")))
			.addProperty("metamodelFile",  	ROOT.resolve(Paths.get("resources","mm","component.ecore")))
			
			.addTask("validate", "epsilon:evl")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","mmt","validation.evl")))
			.addInput("config")
			.addInput("component")
			
			.addTask("m2m", "epsilon:etl")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","mmt","variant.etl")))
			.addInput("config")
			.addInput("component")
			.addOutput("variant")
			.dependsOn("validate")
			
			.addTask("m2t", "epsilon:egx")
			.addProperty("src", 			ROOT.resolve(Paths.get("resources","mmt","generate.egx")))
			.addProperty("outputRoot", 		ROOT.resolve(Paths.get("src-gen")))
			.addInput("variant")
	
			.build("componentWorkflow");
		
		if (ROOT.toFile().exists()) {
			try {
				FileUtils.deleteDirectory(ROOT.toFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Path sourceDirectory = Paths.get(System.getProperty("user.dir"),"..","gradle.workflow").normalize();
		ResourceLocator.copyDir(sourceDirectory.toFile(), ROOT.toFile());
		
		return w;
	}
}
