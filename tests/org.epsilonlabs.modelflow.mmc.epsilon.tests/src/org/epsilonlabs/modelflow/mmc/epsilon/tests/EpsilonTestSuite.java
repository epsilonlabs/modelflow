/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests;

import org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow.EpsilonPluginTest;
import org.epsilonlabs.modelflow.mmc.epsilon.tests.integ.EpsilonTaskTests;
import org.epsilonlabs.modelflow.mmc.epsilon.tests.integ.MultiTaskTests;
import org.epsilonlabs.modelflow.mmc.epsilon.tests.unit.ValidTaskTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	EpsilonTaskTests.class,
	MultiTaskTests.class,
	ValidTaskTest.class,
	EpsilonPluginTest.class
})
public class EpsilonTestSuite {
}
