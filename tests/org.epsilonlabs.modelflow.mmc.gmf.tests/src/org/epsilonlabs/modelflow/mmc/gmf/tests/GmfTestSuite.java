package org.epsilonlabs.modelflow.mmc.gmf.tests;

import org.epsilonlabs.modelflow.mmc.gmf.tests.common.workflow.GmfPluginTest;
import org.epsilonlabs.modelflow.mmc.gmf.tests.integ.GmfTaskTest;
import org.epsilonlabs.modelflow.mmc.gmf.tests.unit.ValidTaskTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	GmfPluginTest.class,
	GmfTaskTest.class,
	ValidTaskTest.class
})
public class GmfTestSuite {
}
