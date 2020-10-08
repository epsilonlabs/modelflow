/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopyToTestDir;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;

public class MultiTask {
	
	public static final Workflow getFlowchartWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("Flowchart", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("model/emf/wakeup.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Flowchart.ecore"))
			
			.addModelResource("FlowchartPath", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("model/emf/path.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/path.ecore"))
			.addProperty("store", false)
			
			.addTask("validate", "epsilon:evl")
			.addProperty("src", locateAndCopyToTestDir("task/evl/validate_flowchart.evl"))
			.addProperty("failOnError", true)
			.addInput("Flowchart")
			
			.addTask("showPath", "epsilon:eol")
			.addProperty("src", locateAndCopyToTestDir("task/eol/choose_path.eol"))
			.addInput("Flowchart")
			.addOutput("FlowchartPath")
			.dependsOn("validate")
			
			.addTask("generatePath", "epsilon:egl")
			.addProperty("src", locateAndCopyToTestDir("task/egx/path_to_html.egl"))
			.addProperty("outputRoot", locateAndCopyToTestDir("task/egx/output"))
			.addProperty("target",  "path.html")
			.addInput("FlowchartPath")
			
			.build();
	}
	
	public static final Workflow getEmlWorkflow() {
		return new WorkflowBuilder()
				
			.addModelResource("Source", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("model/emf/Source.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Entity.ecore"))
			
			.addModelResource("Target", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("model/emf/Target.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Entity.ecore"))
			
			.addModelResource("Vocabulary", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("model/emf/Vocabulary.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/DomainVocabulary.ecore"))
			
			.addTask("compare", "epsilon:ecl")
			.addProperty("src", locateAndCopyToTestDir("task/ecl/Comparison.ecl"))
			.addInput("Source")
			.addInput("Vocabulary")
			
			.addTask("merge", "epsilon:eml")
			.addProperty("src", locateAndCopyToTestDir("task/eml/Merging.eml"))
			.addInput("Source")
			.addInput("Vocabulary")
			.addOutput("Target")
			.addInput("compare.comparison") 
						
			.build();
	}
		
}