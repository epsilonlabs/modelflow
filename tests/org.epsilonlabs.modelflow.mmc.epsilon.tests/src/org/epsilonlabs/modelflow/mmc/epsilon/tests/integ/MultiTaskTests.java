/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests.integ;

import static org.junit.Assert.fail;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowProgramBuilder;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow.MultiTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MultiTaskTests extends WorkflowBuilderTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}

	protected IWorkflow w;
	@Test
	public void testFlowchart() {
		w = MultiTask.getFlowchartWorkflow();
	}

	@Test
	public void testEml() {
		w = MultiTask.getEmlWorkflow();
	}

	@After
	public void exec() {
		try {
			execute();
		} catch (Exception e) {
			cleanup();
			e.printStackTrace();
			fail("Execution error");
		}
	}

	@Override
	protected void setupSource() {
		final String program = new WorkflowProgramBuilder(w).build();
		try {
			//module.setWorkflow(w);
			module.parse(program);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
