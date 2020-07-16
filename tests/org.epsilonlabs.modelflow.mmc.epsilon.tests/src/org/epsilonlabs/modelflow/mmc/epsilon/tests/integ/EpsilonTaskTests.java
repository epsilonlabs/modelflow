/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests.integ;

import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow.EpsilonTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
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

	@Test
	@Ignore //FIXME
	public void testFlock() {
		execute(EpsilonTask.getFlockWorkflow());
	}
	
	@Test
	public void testEpl() {
		execute(EpsilonTask.getEplWorkflow());
	}
	
	@Test
	public void testGenPackagesEOL() {
		execute(EpsilonTask.getEolGenpackagesWorkflow());
	}
	
	@Test
	public void testEtl() {
		execute(EpsilonTask.getEtlWorkflow());
	}
	
	@Test
	public void testEtlWithAlias() {
		execute(EpsilonTask.getEtlWorkflowWithAlias());
	}

	@Test
	public void testEvl() {
		execute(EpsilonTask.getEvlWorkflow());
	}
	
	@Test
	public void testEol() {
		execute(EpsilonTask.getEolLibrary());
	}
	
	@Test
	public void testEgx() {
		execute(EpsilonTask.getEgxWorkflow());
	}	
	
	@Test
	public void testEgl() {
		execute(EpsilonTask.getEglWorkflow());
	}	
	
	@Test
	public void testEcl() {
		execute(EpsilonTask.getEclWorkflow());	
	}
	
	@Ignore
	@Test
	public void testEUnitWithModel() {
		execute(EpsilonTask.getEUnitWithModelWorkflow());
	}
	
	@Ignore
	@Test
	public void testEUnitWithData() {
		execute(EpsilonTask.getEUnitWithDataWorkflow());
	}
	
	@Test
	public void testEUnit() {
		execute(EpsilonTask.getEUnitWorkflow());
	}
	
}
