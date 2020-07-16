/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.tests;

import org.epsilonlabs.modelflow.mmc.core.tests.integ.CoreTaskTest;
import org.epsilonlabs.modelflow.mmc.core.tests.unit.ValidTaskTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	CoreTaskTest.class,
	ValidTaskTest.class
})
public class CoreTestSuite {
}
