/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locate;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopyToTestDir;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateInTestDir;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;
import org.epsilonlabs.modelflow.dom.MapPropertyBuilder;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;

public class ExampleWorkflows {

	private static String locateEugeniaResourceAndCopy(String name) {
		return locateAndCopyToTestDir("eugenia/model/filesystem." + name);
	}

	/**
	 * TODO: EuGENia
	 * - Think about the emfatic2ecore task and the cycle it would induce
	 * 	So far emfatic is not a resource...
	 * - Choose resource to start with (emf or ecore) depending on availability or change history
	 */
	public static IWorkflow getEuGENiaWorkflow() {
		return new WorkflowBuilder()
				
			/** ======== Resources ======== */
				
			.addModelResource("ECore", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("ecore"))
			.addProperty("metamodelUri", EcorePackage.eINSTANCE.getNsURI())
			.addProperty("alias", "Ecore")
			
			.addModelResource("GenModel", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("genmodel"))
			.addProperty("metamodelUri", GenModelPackage.eINSTANCE.getNsURI())
			
			.addModelResource("GmfGen", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("gmfgen"))
			.addProperty("metamodelUri", GMFGenPackage.eINSTANCE.getNsURI())
			
			.addModelResource("GmfMap", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("gmfmap"))
			.addProperty("metamodelUri", GMFMapPackage.eINSTANCE.getNsURI())

			.addModelResource("GmfTool", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("gmftool"))
			.addProperty("metamodelUri", GMFToolPackage.eINSTANCE.getNsURI())
			
			.addModelResource("GmfGraph", "epsilon:emf")
			.addProperty("src", locateEugeniaResourceAndCopy("gmfgraph"))
			.addProperty("metamodelUri", GMFGraphPackage.eINSTANCE.getNsURI())
			
			/** ======== Tasks ======== */
			
			/** GenPackages */
			
			.addTask("genPackages", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/genPackages.eol"))
			.addInput("ECore")
			
			/** copyright */
			
			.addTask("copyright", "core:fileReader")
			.addProperty("src", locateAndCopyToTestDir("eugenia/task/copyright.txt"))
			
			/** Emfatic2Ecore */
			
			.addTask("Emfatic2Ecore", "emf:emfatic2ecore")
			.addProperty("src", locateEugeniaResourceAndCopy("emf"))
			.addOutput("ECore")
			
			/** ValidateEcoreForGenModel */
			
			.addTask("ValidateEcoreForGenModel", "epsilon:evl")
			.addProperty("src", locate("eugenia/task/Ecore2GenModel.evl"))
			.addInput("ECore")
			
			/** Ecore2GenModel */
			
			.addTask("Ecore2GenModel", "epsilon:etl")
			.addProperty("src", locate("eugenia/task/Ecore2GenModel.etl"))
			.addProperty("params", 
					new MapPropertyBuilder()
					.put("pluginName", "org.epsilonlabs.modelflow.test.filesystem")
					.put("foreignModel", "Ecore2GenModel")
					.put("usedGenPackages", "${genPackages.result}")
					.put("copyright", "${copyright.result}")
					.build())			
			.addInput("ECore")
			.addOutput("GenModel")
			.dependsOn("ValidateEcoreForGenModel")
			
			/** FixGenModel */
			
			.addTask("FixGenModel", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/FixGenModel.eol"))
			.addProperty("params", 
					new MapPropertyBuilder()
					.put("copyright", "${copyright.result}")
					.build())
			.addInput("ECore")
			.addInout("GenModel")
			
			/** PolishGenModel */		
			.addTask("PolishGenModel", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/polish/FixGenModel.eol"))
			.addInput("ECore")
			.addInout("GenModel")
			.dependsOn("FixGenModel")
			.addGuard("self.src.exists()")
			
			/** ValidateEcoreForGMFToolGraphMap */
			
			.addTask("ValidateEcoreForGMFToolGraphMap", "epsilon:evl")
			.addProperty("src", locate("eugenia/task/ECore2GMF.evl"))
			.addInput("ECore")
			
			/** Ecore2GMFToolGraphMap */
			
			.addTask("Ecore2GMFToolGraphMap", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/ECore2GMF.eol"))
			.addInput("ECore")
			.addOutput("GmfMap")
			.addOutput("GmfGraph")
			.addOutput("GmfTool")
			.dependsOn("ValidateEcoreForGMFToolGraphMap")
			
			/** PolishGMFToolGraphMap */

			.addTask("PolishGMFToolGraphMap", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/polish/ECore2GMF.eol"))
			.addInput("ECore")
			.addInout("GmfMap")
			.addInout("GmfGraph")
			.addInout("GmfTool")
			.addGuard("self.src.exists()")
			
			/** FixGmfGen */
			
			.addTask("FixGmfGen", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/FixGMFGen.eol"))
			.addProperty("params", 
					new MapPropertyBuilder()
					.put("copyright", "${copyright.result}")
					.build())
			.addInput("ECore")
			.addInput("GenModel")
			.addInput("GmfGraph")
			.addInput("GmfTool")
			.addInput("GmfMap")
			.addInout("GmfGen")
			
			/** PolishGmfGen */
			
			.addTask("PolishGmfGen", "epsilon:eol")
			.addProperty("src", locate("eugenia/task/polish/FixGMFGen.eol"))
			.addInput("ECore")
			.addInput("GenModel")
			.addInput("GmfGraph")
			.addInput("GmfTool")
			.addInput("GmfMap")
			.addInout("GmfGen")
			.dependsOn("FixGmfGen")
			.addGuard("self.src.exists()")
			
			/** GenerateDomainModelCode */
			
			.addTask("GenerateDomainModelCode", "emf:genCode")
			.addInput("GenModel")
			
			/** GmfMap2GmfGen */
			
			.addTask("GmfMap2GmfGen", "gmf:gmfMap2gmfGen")
			.addInput("ECore")
			.addInput("GmfMap")
			.addInput("GenModel")
			.addOutput("GmfGen")
			
			/** GenerateDiagramCode */
			
			.addTask("GenerateDiagramCode", "gmf:genDiagram")
			.addInput("GmfGen")
			
			.build("EuGENia");
	}
	
	public static IWorkflow getCompareMergeTransformComponent() {
		return new WorkflowBuilder()
			
			/** ======== Resources ======== */

			.addModelResource("Source", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Source.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("Vocabulary", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Vocabulary.model"))
			.addProperty("metamodelFile", locate("mm/DomainVocabulary.ecore"))
			
			.addModelResource("Target", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Target.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("Graph", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/graph.model"))
			.addProperty("metamodelFile", locate("mm/Graph.ecore"))
			
			.addModelResource("Tree", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/tree.model"))
			.addProperty("metamodelFile", locate("mm/tree.ecore"))
			
			/** ======== Tasks ======== */
			
			.addTask("compare", "epsilon:ecl")
			.addProperty("src", locateAndCopyToTestDir("task/Comparison.ecl"))
			.addInput("Source")
			.addInput("Vocabulary")
			
			.addTask("merge", "epsilon:eml")
			.addProperty("src", locateAndCopyToTestDir("task/Merging.eml"))
			.addInput("Source")
			.addInput("Vocabulary")
			.addInput("compare.comparison") 
			.addOutput("Target")
			
			.addTask("transform", "epsilon:etl")
			.addProperty("src", locateAndCopyToTestDir("task/entity2node.etl"))
			.addInput("Target")
			.addInput("Tree")
			.addOutput("Graph") 
			
			.build("mergeTransformComponent");
	}

	public static final IWorkflow getTwoTransformationsComponent() {
		return new WorkflowBuilder()
			
			/** ======== Resources ======== */

			.addModelResource("A", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Source.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("B", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Vocabulary.model"))
			.addProperty("metamodelFile", locate("mm/DomainVocabulary.ecore"))
			
			.addModelResource("AB", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/Target.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("C", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/tree.model"))
			.addProperty("metamodelFile", locate("mm/tree.ecore"))
			
			.addModelResource("ABC", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/graph.model"))
			.addProperty("metamodelFile", locate("mm/graph.ecore"))
			
			/** ======== Tasks ======== */
			
			.addTask("X", "epsilon:etl")
			.addProperty("src", locateAndCopyToTestDir("task/transformationA.etl"))
			.addInput("A")
			.addInput("B")
			.addOutput("AB")
			
			.addTask("Y", "epsilon:etl")
			.addProperty("src", locateAndCopyToTestDir("task/transformationB.etl"))
			.addInput("C")
			.addInput("AB")
			.addOutput("ABC") 
			
			.addTask("Z", "epsilon:egl")
			.addProperty("src", locateAndCopyToTestDir("task/generate.egl"))
			.addProperty("outputRoot", locateAndCopyToTestDir("task/output"))
			.addProperty("target", "ListOfNodes.md")
			.addInput("ABC") 
			
			.build("twoTransformationComponent");
	}
	
	public static IWorkflow getEglWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("OO", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/OOInstance.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("mm/OO.ecore"))
			
			.addTask("generateFromTemplate", "epsilon:egl")
			.addProperty("src", locateAndCopyToTestDir("task/OO2Java.egl"))
			.addProperty("outputRoot", locateAndCopyToTestDir("task/ooEgl/output"))
			.addProperty("target", "MyJava.java")
			.addInput("OO")
			
			.build("egl");
	}
	

	public static IWorkflow getMmtTraceWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("t", "epsilon:emf")
			.addProperty("src", locateInTestDir("wf/componentWorkflow.mftrace"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("mm/managementTrace.ecore"))
			
			.addTask("generateDot", "epsilon:egl")
			.addProperty("src", locateAndCopyToTestDir("task/mmtTrace.egl"))
			.addProperty("outputRoot", locateAndCopyToTestDir("trace"))
			.addProperty("target", "mgmTrace.dot")
			.addInput("t")
			
			.build("mgmTrace");
	}
	
	public static IWorkflow getExecTraceWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("t", "epsilon:emf")
			.addProperty("src", locateInTestDir("wf/componentWorkflow.mfexec"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("mm/executionTrace.ecore"))
			
			.addTask("generateDot", "epsilon:egl")
			.addProperty("src", locateAndCopyToTestDir("task/execTrace.egl"))
			.addProperty("outputRoot", locateAndCopyToTestDir("trace"))
			.addProperty("target", "execTrace.dot")
			.addInput("t")
			
			.build("execTrace");
	}
	
	public static IWorkflow getEgxWorkflow() {
		IWorkflow w = new WorkflowBuilder()
			
			.addModelResource("Wakeup", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir("m/wakeup.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir("mm/Flowchart.ecore"))
			.addProperty("expand", true)
			.addProperty("cache", true)
			
			.addTask("generateCode", "epsilon:egx")
			.addProperty("src", locateAndCopyToTestDir("task/egx/protected/main.egx"))
			.addProperty("outputRoot", locateAndCopyToTestDir("task/egx/output"))
			.addInput("Wakeup")
			
			.build("egl");
		
		Path sourceDirectory = Paths.get(ResourceLocator.locate("task/egx/"));
		Path outputDirectory = Paths.get(ResourceLocator.locateInTestDir("task/egx"));
		ResourceLocator.copyDir(sourceDirectory.toFile(), outputDirectory.toFile());
		
		return w;
	}
	
	public static IWorkflow getMarkersWorkflow() {
		String metamodel = locateInTestDir("markingMate/markingmate.ecore");
		return new WorkflowBuilder()
				
				.addModelResource("BOB", "epsilon:emf")
				.addProperty("src", locateInTestDir("markingMate/model/sepr-bob.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("ALICE", "epsilon:emf")
				.addProperty("src", locateInTestDir("markingMate/model/sepr-alice.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addTask("bob", "epsilon:eol")
				.addInout("BOB")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/helper/bob-marks.eol"))
				
				.addTask("alice", "epsilon:eol")
				.addInout("ALICE")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/helper/alice-marks.eol"))
				
				.build("Marker");
	}
				
	
	
	public static IWorkflow getMarkingMateWorkflow() {
		String metamodel = locateAndCopyToTestDir("markingMate/markingmate.ecore");
		IWorkflow w = new WorkflowBuilder()
				
				.addModelResource("SEPR", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("markingMate/model/sepr.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("BOB", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("markingMate/model/sepr-bob.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("ALICE", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("markingMate/model/sepr-alice.model"))
				.addProperty("metamodelFile", metamodel)

				.addModelResource("MARKED", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("markingMate/model/sepr-marked.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addTask("produce", "epsilon:eol")
				.addOutput("SEPR")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/generateModel.eol"))
				
				.addTask("validateSetup", "epsilon:evl")
				.addInput("SEPR")
				.addProperty("failOnError", true)
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/validateSetup.evl"))
				
				.addTask("split", "epsilon:etl")
				.addInput("SEPR")
				.addOutput("ALICE")
				.addOutput("BOB")
				.addGuard("validateSetup.result")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/split.etl"))
				
				.addTask("validateMarking", "epsilon:evl")
				.addInput("ALICE")
				.addInput("BOB")
				.addProperty("failOnError", true)
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/validateMarking.evl"))
			
				.addTask("compare", "epsilon:ecl")
				.addInput("ALICE")
				.addInput("BOB")
				.addGuard("validateMarking.result")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/markingmate.ecl"))
				
				.addTask("merge", "epsilon:eml")
				.addInput("compare.comparison")
				.addInput("ALICE")
				.addInput("BOB")
				.addOutput("MARKED")		
				.addGuard("validateMarking.result")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/markingmate.eml"))
				
				.addTask("csv", "epsilon:egx")
				.addGuard("validateMarking.result")
				.addInput("MARKED")
				.addProperty("src", locateAndCopyToTestDir("markingMate/task/feedback.egx"))
				
				.build("MarkingMateWorkflow");
		
		locateAndCopyToTestDir("markingMate/task/csv.egl");
		locateAndCopyToTestDir("markingMate/task/feedback.egl");
		locateAndCopyToTestDir("markingMate/task/all-feedback.egl");
		String destination = ResourceLocator.locateInTestDir("task/egx"); 
		Path sourceDirectory = Paths.get(ResourceLocator.locateInTestDir("/resources/task/egx/"));
		Path outputDirectory = Paths.get(destination);
		ResourceLocator.copyDir(sourceDirectory.toFile(), outputDirectory.toFile());
		
		return w;
	}
	
	private static final String RES = System.getProperty("user.dir") + "/../../examples/org.epsilonlabs.modelflow.component.example/resources/";

	public static IWorkflow getComponentWorkflow() {
		IWorkflow w = new WorkflowBuilder()
			
			.addModelResource("config", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir(RES + "m/config.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir(RES + "mm/configuration.ecore"))
			
			.addModelResource("component", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir(RES + "m/component.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir(RES + "mm/component.ecore"))
			
			.addModelResource("extended", "epsilon:emf")
			.addProperty("src", locateAndCopyToTestDir(RES + "m/extended.model"))
			.addProperty("metamodelFile", locateAndCopyToTestDir(RES + "mm/component.ecore"))
			
			.addTask("validate", "epsilon:evl")
			.addProperty("src", locateAndCopyToTestDir(RES + "mmt/validate.evl"))
			.addInput("config")
			.addInput("component")
			
			.addTask("m2m", "epsilon:etl")
			.addProperty("src", locateAndCopyToTestDir(RES + "mmt/addTolerances.etl"))
			.addInput("config")
			.addInput("component")
			.addOutput("extended")
			.dependsOn("validate")
			
			.addTask("m2t", "epsilon:egx")
			.addProperty("src", locateAndCopyToTestDir(RES + "mmt/generateReactive.egx"))
			.addProperty("outputRoot","../../examples/org.epsilonlabs.modelflow.component.example/src-gen/")
			.addInput("extended")
	
			.build("componentWorkflow");
		
		String destination = ResourceLocator.locateInTestDir("/mmt/"); 
		Path sourceDirectory = Paths.get(RES + "mmt/");
		Path outputDirectory = Paths.get(destination);
		ResourceLocator.copyDir(sourceDirectory.toFile(), outputDirectory.toFile());
		
		return w;
	}

	
}