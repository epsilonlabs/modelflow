package gradle.modelflow;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GradlePerformanceExperiment extends IterativeExperiment{

	@Override
	protected String getApproachName() {
		return "Gradle";
	}
	
	protected GradleInvoker invoker;
		
	@Override
	protected void beforeTestExecution() {
		boolean production = (MAX_ITERATIONS == 20);
	    Properties properties = new Properties();
	    try {
			properties.load(new FileInputStream("experiment.properties"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    String installation = (String) properties.getOrDefault("gradle", "/usr/local/opt/gradle/libexec");
		invoker = new GradleInvoker(installation, "gradle.workflow", production, "workflow");
		try {
			invoker.prepareForExecution(this);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Override
	@Test
	public void runIteration() {
		if (scenarioId == 1) {
			invoker.setScenario(scenarioId);			
		}

		/** First Execution */
		execute();
		
		if (scenarioId == 1) {
			stopTimer();			
		}
		/** Invoke Scenario Modifications */
		Scenario.get(scenarioId).getModifications(getTestProjectRoot()).run();
		
		/** Second Execution */
		if (scenarioId != 1) {
			invoker.setScenario(scenarioId);			
		}
		execute();
		if (scenarioId != 1) {
			stopTimer();			
		}
	}
	
	@Override
	protected void afterTestExecution() {
		try {
			invoker.postExecution();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Override
	protected void execute() {
		invoker.executeTask();
	}

	@Override
	protected Path getTestProjectRoot() {
		return invoker.getOutPath();
	}

}