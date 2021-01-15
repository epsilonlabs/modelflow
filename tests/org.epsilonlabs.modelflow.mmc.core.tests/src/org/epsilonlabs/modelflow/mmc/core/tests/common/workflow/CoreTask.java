/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.tests.common.workflow;


import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopyToTestDir;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;

public class CoreTask {

	public static IWorkflow getFileReader() {
		return new WorkflowBuilder()
			
			.addTask("copyright", "core:fileReader")
			.addProperty("src", locateAndCopyToTestDir("copyright.txt"))
			
			.build();
	}
	
	public static IWorkflow getPrintTask() {
		return new WorkflowBuilder()
			
			.addTask("hi", "core:print")
			.addProperty("text", "Hello")
			
			.build();
	}	
	
	public static IWorkflow getSleepTask() {
		return new WorkflowBuilder()
			
			.addTask("sleep", "core:sleep")
			.addProperty("timeout", 3)
			.addProperty("unit", "seconds")
			
			.build();
	}	
	
	public static IWorkflow getXsdTask() {
		return new WorkflowBuilder()
			
			.addTask("valdiate", "core:xsdValidate")
			.addProperty("xsd", locateAndCopyToTestDir("students.xsd"))
			.addProperty("xml", locateAndCopyToTestDir("students.xml"))
			
			.build();
	}	

}
