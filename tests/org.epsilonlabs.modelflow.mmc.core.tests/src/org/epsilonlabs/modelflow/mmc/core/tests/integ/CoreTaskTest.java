/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.tests.integ;

import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.core.tests.common.workflow.CoreTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CoreTaskTest extends WorkflowBuilderTest {
	
	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new CorePlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	@Test
	public void testFileReader() throws Exception {
		execute(CoreTask.getFileReader());
	}
	
	@Test
	public void testPrinter() throws Exception {
		execute(CoreTask.getPrintTask());
	}


}
