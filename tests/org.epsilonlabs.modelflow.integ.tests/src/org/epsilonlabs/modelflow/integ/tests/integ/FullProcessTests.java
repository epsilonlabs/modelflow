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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.integ.tests.common.WorkflowHelper;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("unused")
public class FullProcessTests {
	
	protected ModelFlowModule moduleEolTask, moduleEolTaskOneModel, consumed;
	
	/** 
	 * Mocking different types of workflow 
	 */
	@Before
	public void prepareModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
		TaskFactoryRegistry taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		ResourceFactoryRegistry resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		
		moduleEolTask = spy(new ModelFlowModule());
		moduleEolTask.setTaskFactoryRegistry(taskFactoryRegistry);
		moduleEolTask.setResFactoryRegistry(resFactoryRegistry);
		when(moduleEolTask.getWorkflow()).thenReturn(WorkflowHelper.workflowWithEolTask());
		
		moduleEolTaskOneModel = spy(new ModelFlowModule());
		moduleEolTaskOneModel.setTaskFactoryRegistry(taskFactoryRegistry);
		moduleEolTaskOneModel.setResFactoryRegistry(resFactoryRegistry);
		when(moduleEolTaskOneModel.getWorkflow()).thenReturn(WorkflowHelper.workflowWithEmfModelAndEolTask());
		
		consumed = spy(new ModelFlowModule());
		consumed.setTaskFactoryRegistry(taskFactoryRegistry);
		consumed.setResFactoryRegistry(resFactoryRegistry);
		when(consumed.getWorkflow()).thenReturn(WorkflowHelper.workflowWithEmfModelConsumedByEolTask());
	}
	
	/** 
	 * A single independent execution of a one task one resource workflow
	 */
	@Test
	@Ignore	
	public void oneTaskNoResourceOneExecution() {
		try {
			final Workflow workflow = moduleEolTask.getWorkflow();
			moduleEolTask.execute();
			
			ExecutionTrace trace = moduleEolTask.getContext().getExecutionTrace();
			assertTrue(trace.getExecutions().size() == 1);
			assertTrue(trace.getExecutions().get(0).getTasks().size() == 1);
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/** 
	 * A single independent execution of a one task and one independent resource workflow
	 */
	@Test
	@Ignore
	public void oneTaskOneResourceOneExecution() {
		try {
			moduleEolTaskOneModel.getWorkflow();
			moduleEolTaskOneModel.execute();
			
			ExecutionTrace trace = moduleEolTaskOneModel.getContext().getExecutionTrace();
			assertTrue(trace.getExecutions().size() == 1);
			assertTrue(trace.getExecutions().get(0).getTasks().size() == 1);
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/** 
	 * A single independent execution of a one task and one consumed resource workflow
	 */
	@Test
	public void oneTaskOneConsumedResourceOneExecution() {
		try {
			consumed.getWorkflow();
			consumed.execute();
			
			ExecutionTrace trace = consumed.getContext().getExecutionTrace();
			assertTrue(trace.getExecutions().size() == 1);
			assertTrue(trace.getExecutions().get(0).getTasks().size() == 1);
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	/** 
	 * A consecutive non-monitored execution of a single task
	 * where a workflow property has been modified from the previous execution 
	 */
	@Test
	@Ignore
	public void oneTaskNoResourceTwoExecutions() {
		try {
			moduleEolTask.getWorkflow();
			moduleEolTask.execute();
			
			ExecutionTrace trace = moduleEolTask.getContext().getExecutionTrace();
			assertTrue(trace.getExecutions().size() == 1);
			assertTrue(trace.getExecutions().get(0).getTasks().size() == 1);
			
			/* FIXME
			 * //change file
			 * engine.execute();
			 * engine.getContext().getTrace()
			 */
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * A consecutive single-task execution triggered by a modified resource  
	 */
	@Test
	@Ignore
	public void oneTaskNoResourceTwoExecutionsContinuous() { }
	
}
