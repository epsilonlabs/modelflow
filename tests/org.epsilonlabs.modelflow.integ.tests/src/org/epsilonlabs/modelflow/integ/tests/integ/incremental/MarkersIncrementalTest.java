/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.integ.incremental;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.integ.tests.common.workflow.ExampleWorkflows;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.IncrementalTest;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author bea
 *
 */
public class MarkersIncrementalTest extends IncrementalTest {
	 
	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	@Override
	protected void setupSource() {
		Workflow w = ExampleWorkflows.getMarkersWorkflow();
		module.setWorkflow(w);
	}
	
	@Ignore
	@Test
	public void testNoChanges() {
		second = AllTaskStateValidator.SKIPPED;
		modifications = new Runnable() {
			@Override public void run() {}
		};
	}
}
