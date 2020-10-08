package org.epsilonlabs.modelflow.mmc.gmf.tests.integ;

import static org.junit.Assert.fail;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.tests.common.workflow.GmfTask;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.After;
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
		
	protected Workflow w;
	
	@Test 
	public void testGenDiagram() {
		w = GmfTask.getGenDiagramWorkflow();	
	}
	
	@Test 
	public void testGmfMap2gmfGen() {
		w = GmfTask.getGmfmap2GmfGenWorkflow();	
	}
	
	@After
	public void exec() {
		try {
			execute();
		} catch (Exception e) {
			cleanup();
			e.printStackTrace();
			fail("Execution error");
		}
	}

	@Override
	protected void setupSource() {
		module.setWorkflow(w);
	}
	
}