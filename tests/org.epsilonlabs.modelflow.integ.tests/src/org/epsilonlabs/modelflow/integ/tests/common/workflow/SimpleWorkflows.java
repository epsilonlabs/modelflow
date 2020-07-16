/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locate;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;

public class SimpleWorkflows {

	public static final Workflow ONE_TASK_NO_RESOURCE = new WorkflowBuilder()
			
			.addTask("copyright", "core:fileReader")
			.addProperty("src", ResourceLocator.locate("copyright.txt"))
			
			.build("oneTask");
	
	public static final Workflow ONE_TASK_TWO_RESOURCES = new WorkflowBuilder()
			
			.addModelResource("Tree", "epsilon:emf")
			.addProperty("src", locate("etl/tree.model"))
			.addProperty("metamodelFile", locate("etl/tree.ecore"))
			.addProperty("expand", "false")
			.addProperty("cache", "true")
			
			.addModelResource("Graph", "epsilon:emf")
			.addProperty("src", locate("etl/graph.model"))
			.addProperty("metamodelFile", locate("etl/graph.ecore"))
			.addProperty("expand", "false")
			.addProperty("cache", "true")
			
			.addTask("transformation", "epsilon:etl")
			.addProperty("src", locate("etl/Tree2Graph.etl"))
			.addInput("Tree")
			.addOutput("Graph")
			
			.build("oneTaskTwoResources");
	
	public static final Workflow TWO_TASKS_THREE_RESOURCES = new WorkflowBuilder()
			
			.addModelResource("Source", "epsilon:emf")
			.addProperty("src", locate("eml/Source.model"))
			.addProperty("metamodelFile", locate("eml/Entity.ecore"))
			
			.addModelResource("Target", "epsilon:emf")
			.addProperty("src", locate("eml/Target.model"))
			.addProperty("metamodelFile", locate("eml/Entity.ecore"))
			
			.addModelResource("Vocabulary", "epsilon:emf")
			.addProperty("src", locate("eml/Vocabulary.model"))
			.addProperty("metamodelFile", locate("eml/DomainVocabulary.ecore"))
			
			.addTask("compare", "epsilon:ecl")
			.addProperty("src", locate("eml/Comparison.ecl"))
			.addInput("Source")
			.addInput("Vocabulary")
			
			.addTask("merge", "epsilon:eml")
			.addProperty("src", locate("eml/Merging.eml"))
			.addInput("Source")
			.addInput("Vocabulary")
			.addOutput("Target")
			.addInput("compare.comparison") 
			
			.build("twoTasksThreeResources");
}
