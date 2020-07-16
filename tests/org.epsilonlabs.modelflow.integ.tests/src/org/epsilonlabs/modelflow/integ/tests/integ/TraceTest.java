/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.integ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.integ.tests.common.workflow.SimpleWorkflows;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(Parameterized.class)
public class TraceTest extends WorkflowBuilderTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}

	
	@Parameters(name = "{index}: tracesOf({2}({0}))")
	public static Collection<Object[]> data() {
		List<Workflow> workflows = Arrays.asList(
				SimpleWorkflows.ONE_TASK_NO_RESOURCE,
				SimpleWorkflows.ONE_TASK_TWO_RESOURCES,
				SimpleWorkflows.TWO_TASKS_THREE_RESOURCES
				//,ExampleWorkflows.EuGENia
				);
		int max = 3;

		List<Object[]> result = new ArrayList<Object[]>();
		for (Workflow workflow : workflows) {
			for (int times = 1; times <= max; times++) {
				Object[] test = new Object[3];
				test[0] = times;
				test[1] = workflow;
				test[2] = workflow.getName();
				result.add(test);
			}
		}
		return result;
	}

	private Integer times;
	private Workflow workflow;
	private String name;
	
	public TraceTest(Integer times, Workflow workflow, String name) {
		this.times = times;
		this.workflow = workflow;
		this.name= name;
	}

	@Test
	public void test() {
		System.out.println(">>>>>>>>>>>>>EXECUTION 1");
		execute(this.workflow);
		int count = times - 1;
		while (count>0) {
			System.out.println(">>>>>>>>>>>>>EXECUTION " + (this.times-count+1));
			reExecute();
			count -= 1;
		}
		
		saveModels(module, this.name + this.times);
	}

}
