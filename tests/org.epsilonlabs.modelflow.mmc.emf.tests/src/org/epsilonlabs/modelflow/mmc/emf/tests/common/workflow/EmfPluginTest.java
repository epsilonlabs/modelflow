/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.emf.tests.common.workflow;

import static org.junit.Assert.assertTrue;

import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.tests.common.ExtensionPointTests;

import com.google.inject.Module;

public class EmfPluginTest extends ExtensionPointTests{ 
	
	@Override
	protected Module getPlugin(){
		return new EMFPlugin();
	}

	@Override
	protected void expectedTasks() {
		assertTrue(taskFactories.containsKey("emf:emfatic2ecore"));
		assertTrue(taskFactories.containsKey("emf:genCode"));
	}

	@Override
	protected void expectedResources() {
		
	}
		
}
