/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.copyDir;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locate;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopyToTestDir;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateInTestDir;

import java.io.File;

import org.eclipse.emf.ecore.EcorePackage;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;

public class EpsilonTask {

	public static final IWorkflow getFlockWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Original", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/Original.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Original.ecore"))
				
				.addModelResource("Migrated", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/Migrated.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Evolved.ecore"))
				
				.addTask("migrate", "epsilon:flock")
				.addProperty("src", locateAndCopyToTestDir("task/flock/Petrinets.mig"))
				.addInput("Original")
				.addOutput("Migrated")
				
				.build();
	}
	
	public static final IWorkflow getEolNative() {
		return new WorkflowBuilder()
				
				.addTask("test", "epsilon:eol")
				.addProperty("code", "new Native('org.epsilonlabs.modelflow.mmc.epsilon.tests.integ.NativeTypeExample').success().println();")
				
				.build();
	}
	
	public static final IWorkflow getSimulinkWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("M", "epsilon:simulink")
				.addProperty("src", locateAndCopyToTestDir("model/simulink/feedbackController.slx"))
								
				.addTask("query", "epsilon:eol")
				.addProperty("src", locateAndCopyToTestDir("task/eol/simulink.eol"))
				.addInput("M")
				
				.build();
	}

	public static final IWorkflow getEplWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Model", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/railway-repair-32.xmi"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/railway.ecore"))
				.addProperty("expand", true)
				.addProperty("cache", true)
				
				.addTask("migrate", "epsilon:epl")
				.addProperty("src", locateAndCopyToTestDir("task/epl/trainBenchmarkPatterns.epl"))
				.addProperty("repeatWhileMatches", false)
				.addInput("Model")
				
				.build();
	}
		
	public static final IWorkflow getEolGenpackagesWorkflow() {
		return new WorkflowBuilder()
			
				.addModelResource("ECore", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("metamodel/ecore/filesystem.ecore"))
				.addProperty("metamodelUri", EcorePackage.eINSTANCE.getNsURI())
				
				.addTask("genPackages", "epsilon:eol")
				.addProperty("src", locateAndCopyToTestDir("task/eol/genPackages.eol"))
				.addInput("ECore", "Ecore")
			
		.build();
	}
	
	public static final IWorkflow getEtlWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Tree", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/tree.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/tree.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addModelResource("Graph", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/graph.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addTask("transformation", "epsilon:etl")
				.addProperty("src", locateAndCopyToTestDir("task/etl/Tree2Graph.etl"))
				.addInput("Tree")
				.addOutput("Graph")
				
				.build();
	}
	
	public static final IWorkflow getEtlWorkflowWithAlias() {
		return new WorkflowBuilder()
				
				.addModelResource("Source", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/tree.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/tree.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addModelResource("Target", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/graph.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addTask("transformation", "epsilon:etl")
				.addProperty("src", locateAndCopyToTestDir("task/etl/Tree2Graph.etl"))
				.addInput("Source", "Tree") // Alias
				.addOutput("Target", "Graph") // Alias
				
				.build();
	}
		
	public static final IWorkflow getEvlWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Tree", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/tree.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/tree.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addTask("validation", "epsilon:evl")
				.addProperty("src", locateAndCopyToTestDir("task/evl/tree-constraints.evl"))
				.addInput("Tree")
				
				.build();
	}
		
	public static final IWorkflow getEolLibrary() {
		return new WorkflowBuilder()
				
				.addModelResource("Library", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/library.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/library.ecore"))
				.addProperty("expand", false)
				.addProperty("cache", true)
				
				.addTask("navigation", "epsilon:eol")
				.addProperty("src", locateAndCopyToTestDir("task/eol/queries.eol"))
				.addInput("Library")
				
				.build();
	}
		
	public static final IWorkflow getEgxWorkflow() {
		IWorkflow w = new WorkflowBuilder()
				
				.addModelResource("Wakeup", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/wakeup.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Flowchart.ecore"))
				.addProperty("expand", true)
				.addProperty("cache", true)
				
				.addTask("generateCode", "epsilon:egx")
				.addProperty("src", locateInTestDir("task/egx/main.egx"))
				.addProperty("outputRoot", locateInTestDir("task/egx/output"))
				.addInput("Wakeup")
				
				.build();
		
			File source = new File(locate("task/egx/protected"));
			File target = new File(locateInTestDir("task/egx/"));
			copyDir(source, target);
		return w;
	}
		
	public static final IWorkflow getEglWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("OO", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/OOInstance.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/OO.ecore"))
				
				.addTask("generateFromTemplate", "epsilon:egl")
				.addProperty("src", locateAndCopyToTestDir("task/egl/OO2Java.egl"))
				.addProperty("outputRoot", locateAndCopyToTestDir("task/egl/output"))
				.addProperty("target", "MyJava.java")
				.addInput("OO")
				
				.build();
	}
		
	public static final IWorkflow getEclWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Source", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/Source.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/Entity.ecore"))
				
				.addModelResource("Vocabulary", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/Vocabulary.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/DomainVocabulary.ecore"))
				
				.addTask("compare", "epsilon:ecl")
				.addProperty("src", locateAndCopyToTestDir("task/ecl/Comparison.ecl"))
				.addInput("Source")
				.addInput("Vocabulary")
				
				.build();
	}
		
	public static final IWorkflow getEUnitWithModelWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("LoopingA", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/graph-with-loops-1.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
				
				.addModelResource("LoopingB", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/graph-with-loops-2.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
				
				.addModelResource("NonLooping", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("model/emf/graph-without-loops.model"))
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
				
				.addTask("eunit", "epsilon:eunit")
				.addProperty("src", locateAndCopyToTestDir("task/eunit/bind-model.eunit"))
				.addProperty("package", "loops")
				.addInput("LoopingA")
				.addInput("LoopingB")
				.addInput("NonLooping")
				
				.build();
	}
		
	public static final IWorkflow getEUnitWithDataWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("Empty", "epsilon:emf")
				.addProperty("metamodelFile", locateAndCopyToTestDir("metamodel/ecore/graph.ecore"))
			
				.addTask("eunit", "epsilon:eunit")
				.addProperty("src", locateAndCopyToTestDir("task/eunit/bind-data.eunit"))
				.addProperty("package", "loops")
				.addInput("Empty")
				
				.build();
	}
		
	public static final IWorkflow getEUnitWorkflow() {
		return new WorkflowBuilder()
				
				.addTask("test", "epsilon:eunit")
				.addProperty("src", locateAndCopyToTestDir("task/eunit/tests.eunit"))
				
				.build();
	}
		
}