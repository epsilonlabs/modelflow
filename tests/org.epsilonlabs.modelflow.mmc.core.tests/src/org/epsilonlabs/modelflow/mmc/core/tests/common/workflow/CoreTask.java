/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.tests.common.workflow;


import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopy;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;

public class CoreTask {

	public static Workflow getFileReader() {
		return new WorkflowBuilder()
			
			.addTask("copyright", "core:fileReader")
			.addProperty("src", locateAndCopy("copyright.txt"))
			
			.build();
	}
	
	public static Workflow getPrintTask() {
		return new WorkflowBuilder()
			
			.addTask("hi", "core:print")
			.addProperty("text", "Hello")
			
			.build();
	}	

}
