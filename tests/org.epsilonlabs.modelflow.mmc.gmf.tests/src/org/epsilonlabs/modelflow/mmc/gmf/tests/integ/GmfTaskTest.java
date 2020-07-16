package org.epsilonlabs.modelflow.mmc.gmf.tests.integ;

import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.tests.common.workflow.GmfTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GmfTaskTest extends WorkflowBuilderTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new GMFPlugin(), new EpsilonPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
		
	@Test 
	public void test_genDiagram() {
		execute(GmfTask.getGenDiagramWorkflow());	
	}
	
	@Test 
	public void test_gmfMap2gmfGen() {
		execute(GmfTask.getGmfmap2GmfGenWorkflow());	
	}
	
}