/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.emf.tests.integ;

import static org.junit.Assert.fail;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowProgramBuilder;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.emf.tests.common.workflow.EmfTask;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EmfTaskTests extends WorkflowBuilderTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EMFPlugin(), new EpsilonPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	protected IWorkflow w;
	
	@Test 
	public void test_emfatic2ecoreWithOutput() {
		w = EmfTask.getEmfatic2EcoreWithModelWorkflow();	
	}
	
	@Test 
	public void test_emfatic2ecore() {
		w = EmfTask.getEmfatic2EcoreWorkflow();	
	}
	
	/** 
	 * FIXME Throws a weird error at the end of execution
	 * 
!ENTRY org.eclipse.core.resources 4 2 2020-01-15 15:33:00.616
!MESSAGE Problems occurred when invoking code from plug-in: "org.eclipse.core.resources".
!STACK 0
java.lang.NullPointerException
	at org.eclipse.ui.activities.WorkbenchActivityHelper.isFiltering(WorkbenchActivityHelper.java:237)
	at org.eclipse.ui.internal.ide.IDEWorkbenchActivityHelper.lambda$1(IDEWorkbenchActivityHelper.java:148)
	at org.eclipse.core.internal.events.NotificationManager$1.run(NotificationManager.java:297)
	at org.eclipse.core.runtime.SafeRunner.run(SafeRunner.java:42)
	at org.eclipse.core.internal.events.NotificationManager.notify(NotificationManager.java:287)
	at org.eclipse.core.internal.events.NotificationManager.broadcastChanges(NotificationManager.java:150)
	at org.eclipse.core.internal.resources.Workspace.broadcastPostChange(Workspace.java:376)
	at org.eclipse.core.internal.resources.Workspace.endOperation(Workspace.java:1499)
	at org.eclipse.core.internal.events.AutoBuildJob.doBuild(AutoBuildJob.java:157)
	at org.eclipse.core.internal.events.AutoBuildJob.run(AutoBuildJob.java:232)
	at org.eclipse.core.internal.jobs.Worker.run(Worker.java:60)

!ENTRY org.eclipse.core.resources 4 2 2020-01-15 15:33:00.618
!MESSAGE Problems occurred when invoking code from plug-in: "org.eclipse.core.resources".
!STACK 0
java.lang.NullPointerException
	at org.eclipse.epsilon.emf.dt.EmfRegistryManager.getMetamodels(EmfRegistryManager.java:156)
	at org.eclipse.epsilon.emf.dt.EmfRegistryManager$1.resourceChanged(EmfRegistryManager.java:95)
	at org.eclipse.core.internal.events.NotificationManager$1.run(NotificationManager.java:297)
	at org.eclipse.core.runtime.SafeRunner.run(SafeRunner.java:42)
	at org.eclipse.core.internal.events.NotificationManager.notify(NotificationManager.java:287)
	at org.eclipse.core.internal.events.NotificationManager.broadcastChanges(NotificationManager.java:150)
	at org.eclipse.core.internal.resources.Workspace.broadcastPostChange(Workspace.java:376)
	at org.eclipse.core.internal.resources.Workspace.endOperation(Workspace.java:1499)
	at org.eclipse.core.internal.events.AutoBuildJob.doBuild(AutoBuildJob.java:157)
	at org.eclipse.core.internal.events.AutoBuildJob.run(AutoBuildJob.java:232)
	at org.eclipse.core.internal.jobs.Worker.run(Worker.java:60)

!ENTRY org.eclipse.core.resources 2 10035 2020-01-15 15:33:01.101
!MESSAGE The workspace will exit with unsaved changes in this session.
	 * 
	 */
	
	/** 
	 * TODO check if the management trace is correct 
	 */
	@Ignore
	@Test 
	public void test_genCode() {
		w = EmfTask.genCodeWorkflow();	
	}
	
	@After
	public void exec() throws Exception {
		execute();
	}

	@Override
	protected void setupSource() {
		final String program = new WorkflowProgramBuilder(w).build();
		try {
			//module.setWorkflow(w);
			module.parse(program);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
		
}
