/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow;

import static org.junit.Assert.assertTrue;

import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.tests.common.ExtensionPointTests;

import com.google.inject.Module;

public class EpsilonPluginTest extends ExtensionPointTests{ 
	
	@Override
	protected Module getPlugin(){
		return new EpsilonPlugin();
	}

	@Override
	protected void expectedTasks() {
		assertTrue(taskFactories.containsKey("epsilon:eol"));
		assertTrue(taskFactories.containsKey("epsilon:ecl"));
		assertTrue(taskFactories.containsKey("epsilon:egl"));
		assertTrue(taskFactories.containsKey("epsilon:egx"));
		assertTrue(taskFactories.containsKey("epsilon:eml"));
		assertTrue(taskFactories.containsKey("epsilon:etl"));
		assertTrue(taskFactories.containsKey("epsilon:evl"));
		assertTrue(taskFactories.containsKey("epsilon:flock"));
	}

	@Override
	protected void expectedResources() {
		assertTrue(resourceFactories.containsKey("epsilon:emf"));		
	}
		
}
