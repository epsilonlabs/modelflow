package org.epsilonlabs.modelflow.tests.common;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.graph.GraphState;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import io.reactivex.Observable;

public abstract class WorkflowBuilderTest {
	
	protected static final String DIR = System.getProperty("user.dir") + "/testOutput/";
	protected ModelFlowModule module;
	protected Boolean protect = true;
	
    @Rule public TestName name = new TestName();

	protected static TaskFactoryRegistry taskFactoryRegistry;
	protected static ResourceFactoryRegistry resFactoryRegistry;
	
	@Before
	public void log() {
		System.out.println(name.getMethodName());
	}
	
	protected void execute() throws Exception {
		module = new ModelFlowModule();
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resFactoryRegistry);

		setupModule();
		setupSource();
		
		module.getContext().setPublisher(getPublisher());
		module.getContext().setProfilingEnabled(true);
		module.execute();
	}

	protected IModelFlowPublisher getPublisher() {
		return new IModelFlowPublisher() {
		
			@Override
			public void taskState(String taskName, TaskState state) {
				String msg = "Task State: %s - %s";
				System.out.println(String.format(msg, taskName, state.name()));
			}
			
			@Override
			public void resourceState(String name, Object state) {
				String msg = "Resource State: %s - %s";
				System.out.println(String.format(msg, name, state));					
			}

			@Override
			public void dependencyGraph(GraphState state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void executionGraph(GraphState state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void scheduler(GraphState state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Observable<TaskUpdate> forTasks() {
				// TODO Auto-generated method stub
				return null;
			}
	
		};
	}
	
	/**
	 * use to invoke:
	 * module.setWorkflow(workflow) or module.setSrc(..)
	 */
	protected abstract void setupSource();
	
	protected void setupModule() {
		module.getContext().setInteractive(false);
		module.getContext().setProtectResources(protect);
		module.getContext().setEndToEndTracing(true);
		module.getConfiguration().setSaveEndToEndTraces(false);
	}	
	
	public void reExecute() throws Exception {
		module.execute();
	}
	
	protected void cleanup() {
		try {
			FileUtils.deleteDirectory(new File(DIR));
		} catch (IOException e) {
			fail("Unable to delete directory");
		}
	}
	
	@Rule
	public TestName testName = new TestName();
	
}
