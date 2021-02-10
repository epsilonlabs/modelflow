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
import org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow.EpsilonTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EpsilonTaskTests extends WorkflowBuilderTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}

	protected IWorkflow w;

	@Test
	@Ignore //FIXME it takes very long
	public void testFlock() {
		w = EpsilonTask.getFlockWorkflow();
	}

	@Test
	public void testEpl() {
		w = EpsilonTask.getEplWorkflow();
	}

	@Test
	public void testGenPackagesEOL() {
		w = EpsilonTask.getEolGenpackagesWorkflow();
	}

	@Test
	public void testEtl() {
		w = EpsilonTask.getEtlWorkflow();
	}

	@Test
	public void testEtlWithAlias() {
		w = EpsilonTask.getEtlWorkflowWithAlias();
	}
	
	@Test
	public void testEolNative() {
		w = EpsilonTask.getEolNative();
	}

	@Test
	public void testEvl() {
		w = EpsilonTask.getEvlWorkflow();
	}

	@Test
	public void testEol() {
		w = EpsilonTask.getEolLibrary();
	}

	@Test
	public void testEgx() {
		w = EpsilonTask.getEgxWorkflow();
	}

	@Test
	public void testEgl() {
		w = EpsilonTask.getEglWorkflow();
	}

	@Test
	public void testEcl() {
		w = EpsilonTask.getEclWorkflow();
	}
	
	@Test
	@Ignore
	public void testSimulink() {
		w = EpsilonTask.getSimulinkWorkflow();
	}

	@Ignore
	@Test
	public void testEUnitWithModel() {
		w = EpsilonTask.getEUnitWithModelWorkflow();
	}

	@Ignore
	@Test
	public void testEUnitWithData() {
		w = EpsilonTask.getEUnitWithDataWorkflow();
	}

	@Test
	public void testEUnit() {
		w = EpsilonTask.getEUnitWorkflow();
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
