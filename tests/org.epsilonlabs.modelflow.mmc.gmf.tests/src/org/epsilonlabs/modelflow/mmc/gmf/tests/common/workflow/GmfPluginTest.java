package org.epsilonlabs.modelflow.mmc.gmf.tests.common.workflow;

import static org.junit.Assert.assertTrue;

import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.tests.common.ExtensionPointTests;

import com.google.inject.Module;

public class GmfPluginTest extends ExtensionPointTests{ 
	
	@Override
	protected Module getPlugin(){
		return new GMFPlugin();
	}

	@Override
	protected void expectedTasks() {
		assertTrue(taskFactories.containsKey("gmf:genDiagram"));
		assertTrue(taskFactories.containsKey("gmf:gmfMap2gmfGen"));
	}

	@Override
	protected void expectedResources() {
		
	}
		
}
