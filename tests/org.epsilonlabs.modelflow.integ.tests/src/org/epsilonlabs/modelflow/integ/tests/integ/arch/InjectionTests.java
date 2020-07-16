/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.integ.arch;

import static org.junit.Assert.assertTrue;

import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.junit.After;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectionTests {

	Module plugin;
	
	@Test
	public void retrieveEpsilonExtensionPoints() throws Exception {
		plugin = new EpsilonPlugin();
	}
	
	@Test
	public void retrieveEMFExtensionPoints() throws Exception {
		plugin = new EMFPlugin();
	}
	
	@Test
	public void retrieveCoreExtensionPoints() throws Exception {
		plugin = new CorePlugin();
	}
	
	@Test
	public void retrieveGMFExtensionPoints() throws Exception {
		plugin = new GMFPlugin();
	}
	
	
	@After
	public void evaluate() throws Exception {
		Injector injector = Guice.createInjector(plugin);
		
		TaskFactoryRegistry taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		assertTrue(taskFactoryRegistry.getFactories()!=null);
		
		ResourceFactoryRegistry resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		assertTrue(resFactoryRegistry.getFactories()!=null);
	}
}
