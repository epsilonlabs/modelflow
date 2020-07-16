/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests;

import java.util.Scanner;

import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.execution.IPublisher;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.workflow.ExampleWorkflows;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.WorkflowBuilderTest;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Demo {

	private static final String MSG = "\n\nPress %s to execute, %s to reset or %s to quit\n\n";
	private static final String EXECUTE = "(execute|e)";
	private static final String RESET = "(reset|r)";
	private static final String QUIT = "(quit|q|exit)";
	
	@Test
	public void run() {
		ModelFlowModule module = getModule();
		System.out.printf(MSG, EXECUTE,RESET, QUIT);
		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNext()) {
				String command = scanner.next();
				if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("q")) {
					break;
				} else if (command.equalsIgnoreCase("reset") || command.equalsIgnoreCase("r")) {
					System.out.println("Resetting");
					module = getModule();
				} else if (command.equalsIgnoreCase("execute") || command.equalsIgnoreCase("e")) {
					System.out.println("Executing");
					try{
						module.execute();
						WorkflowBuilderTest.saveModels(module, module.getWorkflow().getName());
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Exception caught, please: reset.");
					}
				}
				System.out.printf(MSG, EXECUTE,RESET, QUIT);
			}
		}
	}
	
	private ModelFlowModule getModule() {
		ModelFlowModule module = new ModelFlowModule();
		Workflow workflow = getWorkflow();
		module.setWorkflow(workflow);
		
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin());
		TaskFactoryRegistry taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		ResourceFactoryRegistry resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resFactoryRegistry);
		
		IPublisher publisher = module.getContext().getPublisher();
		publisher.forTasks().filter(t->t.getState().equals(TaskState.EXECUTED) || t.getState().equals(TaskState.SKIPPED)).subscribe(t-> {
			System.out.println(t.getName() + ": " + t.getState().name());
		});
		return module;
	}
	
	protected Workflow getWorkflow() {
		return ExampleWorkflows.getComponentWorkflow();
		//return ExampleWorkflows.getMarkingMateWorkflow();
		//return ExampleWorkflows.getEuGENiaWorkflow();
	}
}