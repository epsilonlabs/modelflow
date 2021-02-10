/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.integ;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.EList;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.dom.WorkflowProgramBuilder;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class TaskTest  {

	protected static TaskFactoryRegistry taskFactoryRegistry;
	protected static ResourceFactoryRegistry resFactoryRegistry;
	protected ModelFlowModule module;
	protected IWorkflow w ;
	protected IValidate validator;
	
	@BeforeClass
	public static void configureRegistries() {
		Injector injector = Guice.createInjector(new CorePlugin(), new EpsilonPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	@Before
	public void setupModule() {
		module = new ModelFlowModule();
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resFactoryRegistry);
		module.getContext().setInteractive(false);
		module.getContext().setProtectResources(false);
	}
	
	@Test
	public void testEnabledTask() {
		w = new WorkflowBuilder().addTask("print", "core:print").addProperty("text", "hi").build();
		validator = new AllTaskStateValidator(TaskState.EXECUTED);
	}
	
	@Test
	public void testDisabledTask() {
		w = new WorkflowBuilder().addTask("print", "core:print").addProperty("text", "hi").skip().build();
		validator = new AllTaskStateValidator(TaskState.SKIPPED);
	}
	
	@Test
	public void testDependentTask() {
		w = new WorkflowBuilder()
				.addTask("print", "core:print")
				.addProperty("text", "hi")
				.addTask("print2", "core:print")
				.addProperty("text", "hi2")
				.dependsOn("print")
				.build();
		validator = new CompositeValidator(new IValidate() {
			
			@Override
			public Boolean ok(IModelFlowModule module) throws Exception {
				ExecutionTrace executionTrace = module.getContext().getExecutionTrace();
				WorkflowExecution execution = executionTrace.getExecutions().get(0);
				EList<TaskExecution> tasks = execution.getTasks();
				assertTrue(tasks.size() ==2);
				assertTrue("print".equals(tasks.get(0).getName()));
				assertTrue("print2".equals(tasks.get(1).getName()));
				return true;
			}
			
			@Override
			public String expected() {
				return "task print should be executed before task print2";
			}
		});
	}
	
	@After
	public void execute() throws Exception{
		try {
			//module.setWorkflow(w);
			final String program = new WorkflowProgramBuilder(w).build();
			module.parse(program);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Parsing issues");	
		}
		try {
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed Execution");
		}
		assertTrue(validator.expected(), validator.ok(module));
	}
	
}
