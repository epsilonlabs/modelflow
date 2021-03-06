/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.emf.tests;

import org.epsilonlabs.modelflow.mmc.emf.tests.common.workflow.EmfPluginTest;
import org.epsilonlabs.modelflow.mmc.emf.tests.integ.EmfTaskTests;
import org.epsilonlabs.modelflow.mmc.emf.tests.unit.ValidTaskTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	EmfPluginTest.class,
	EmfTaskTests.class,
	ValidTaskTest.class	
})
public class EmfTestSuite {
}
