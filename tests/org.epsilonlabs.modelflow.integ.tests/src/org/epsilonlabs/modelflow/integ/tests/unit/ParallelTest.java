/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.unit;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.execution.IScheduler;
import org.epsilonlabs.modelflow.execution.TopologicalSequentialScheduler;
import org.epsilonlabs.modelflow.execution.concurrent.TopologicalConcurrentScheduler;
import org.epsilonlabs.modelflow.execution.control.IMeasurable.Stage;
import org.epsilonlabs.modelflow.execution.control.IModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
@RunWith(Parameterized.class)
public class ParallelTest extends WorkflowBuilderTest {

	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new TopologicalConcurrentScheduler(), 4000, 5000 }, 
                { new TopologicalSequentialScheduler(), 7000, 8000 }
        });
    }

	private final IScheduler scheduler;
	private final int min;
	private final int max;
    
    public ParallelTest(IScheduler scheduler, int min, int max) {
    	this.scheduler = scheduler;
		this.min = min;
		this.max = max;
    }
	    
	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new CorePlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	protected IWorkflow w ;
	
	@Test
	public void testScheduler() {
		String sleepTask = "core:sleep";
		String timeout = "timeout";
		String timeunit = "seconds";
		String timeunitParam = "unit";
		
		int value = 1;
		w = new WorkflowBuilder()
				
				.addTask("A", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				
				.addTask("B", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("A")
				
				.addTask("C", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("A")
				
				.addTask("D", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("C")
				.dependsOn("B")
				
				.addTask("E", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("C")
				
				.addTask("F", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("D")
				
				.addTask("G", sleepTask)
				.addProperty(timeout, value)
				.addProperty(timeunitParam, timeunit)
				.dependsOn("A")
				
				.build();
	}
	
	@After
	public void exec() throws Exception {
		execute();
		IModelFlowExecutionProfiler profiler = (IModelFlowExecutionProfiler) module.getContext().getProfiler();
		
		long time = profiler.getByStage(Stage.EXECUTION_PROCESS).values().stream().findFirst().get().delta().getTime(TimeUnit.MILLISECONDS);
		assertTrue("Went over max ("+max+"): "+ time,time<max);
		assertTrue("Was less than min ("+min+"): "+ time,time>min);
	}

	@Override
	protected void setupSource() {
		module.setWorkflow(w);
		module.getContext().setProfilingEnabled(true);
		module.getContext().setExecutor(scheduler);
	}

}
