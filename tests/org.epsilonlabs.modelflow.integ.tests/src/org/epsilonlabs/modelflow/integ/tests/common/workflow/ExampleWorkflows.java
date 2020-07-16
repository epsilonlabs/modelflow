/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locate;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopy;
import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateInTarget;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;
import org.epsilonlabs.modelflow.dom.MapPropertyBuilder;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;

public class ExampleWorkflows {

	private static String locateEugeniaResourceAndCopy(String name) {
		return locateAndCopy("eugenia/model/filesystem." + name);
	}

	/**
	 * TODO: EuGENia
	 * - Think about the emfatic2ecore task and the cycle it would induce
	 * 	So far emfatic is not a resource...
	 * - Choose resource to start with (emf or ecore) depending on availability or change history
	 */
	public static Workflow getEuGENiaWorkflow() {
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
			.addProperty("src", locateAndCopy("eugenia/task/copyright.txt"))
			
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
	
	public static Workflow getCompareMergeTransformComponent() {
		return new WorkflowBuilder()
			
			/** ======== Resources ======== */

			.addModelResource("Source", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Source.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("Vocabulary", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Vocabulary.model"))
			.addProperty("metamodelFile", locate("mm/DomainVocabulary.ecore"))
			
			.addModelResource("Target", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Target.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("Graph", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/graph.model"))
			.addProperty("metamodelFile", locate("mm/Graph.ecore"))
			
			.addModelResource("Tree", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/tree.model"))
			.addProperty("metamodelFile", locate("mm/tree.ecore"))
			
			/** ======== Tasks ======== */
			
			.addTask("compare", "epsilon:ecl")
			.addProperty("src", locateAndCopy("task/Comparison.ecl"))
			.addInput("Source")
			.addInput("Vocabulary")
			
			.addTask("merge", "epsilon:eml")
			.addProperty("src", locateAndCopy("task/Merging.eml"))
			.addInput("Source")
			.addInput("Vocabulary")
			.addInput("compare.comparison") 
			.addOutput("Target")
			
			.addTask("transform", "epsilon:etl")
			.addProperty("src", locateAndCopy("task/entity2node.etl"))
			.addInput("Target")
			.addInput("Tree")
			.addOutput("Graph") 
			
			.build("mergeTransformComponent");
	}

	public static final Workflow getTwoTransformationsComponent() {
		return new WorkflowBuilder()
			
			/** ======== Resources ======== */

			.addModelResource("A", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Source.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("B", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Vocabulary.model"))
			.addProperty("metamodelFile", locate("mm/DomainVocabulary.ecore"))
			
			.addModelResource("AB", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/Target.model"))
			.addProperty("metamodelFile", locate("mm/Entity.ecore"))
			
			.addModelResource("C", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/tree.model"))
			.addProperty("metamodelFile", locate("mm/tree.ecore"))
			
			.addModelResource("ABC", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/graph.model"))
			.addProperty("metamodelFile", locate("mm/graph.ecore"))
			
			/** ======== Tasks ======== */
			
			.addTask("X", "epsilon:etl")
			.addProperty("src", locateAndCopy("task/transformationA.etl"))
			.addInput("A")
			.addInput("B")
			.addOutput("AB")
			
			.addTask("Y", "epsilon:etl")
			.addProperty("src", locateAndCopy("task/transformationB.etl"))
			.addInput("C")
			.addInput("AB")
			.addOutput("ABC") 
			
			.addTask("Z", "epsilon:egl")
			.addProperty("src", locateAndCopy("task/generate.egl"))
			.addProperty("outputRoot", locateAndCopy("task/output"))
			.addProperty("target", "ListOfNodes.md")
			.addInput("ABC") 
			
			.build("twoTransformationComponent");
	}
	
	public static Workflow getEglWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("OO", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/OOInstance.model"))
			.addProperty("metamodelFile", locateAndCopy("mm/OO.ecore"))
			
			.addTask("generateFromTemplate", "epsilon:egl")
			.addProperty("src", locateAndCopy("task/OO2Java.egl"))
			.addProperty("outputRoot", locateAndCopy("task/ooEgl/output"))
			.addProperty("target", "MyJava.java")
			.addInput("OO")
			
			.build("egl");
	}
	

	public static Workflow getMmtTraceWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("t", "epsilon:emf")
			.addProperty("src", locateInTarget("wf/componentWorkflow.mftrace"))
			.addProperty("metamodelFile", locateAndCopy("mm/managementTrace.ecore"))
			
			.addTask("generateDot", "epsilon:egl")
			.addProperty("src", locateAndCopy("task/mmtTrace.egl"))
			.addProperty("outputRoot", locateAndCopy("trace"))
			.addProperty("target", "mgmTrace.dot")
			.addInput("t")
			
			.build("mgmTrace");
	}
	
	public static Workflow getExecTraceWorkflow() {
		return new WorkflowBuilder()
			
			.addModelResource("t", "epsilon:emf")
			.addProperty("src", locateInTarget("wf/componentWorkflow.mfexec"))
			.addProperty("metamodelFile", locateAndCopy("mm/executionTrace.ecore"))
			
			.addTask("generateDot", "epsilon:egl")
			.addProperty("src", locateAndCopy("task/execTrace.egl"))
			.addProperty("outputRoot", locateAndCopy("trace"))
			.addProperty("target", "execTrace.dot")
			.addInput("t")
			
			.build("execTrace");
	}
	
	public static Workflow getEgxWorkflow() {
		Workflow w = new WorkflowBuilder()
			
			.addModelResource("Wakeup", "epsilon:emf")
			.addProperty("src", locateAndCopy("m/wakeup.model"))
			.addProperty("metamodelFile", locateAndCopy("mm/Flowchart.ecore"))
			.addProperty("expand", true)
			.addProperty("cache", true)
			
			.addTask("generateCode", "epsilon:egx")
			.addProperty("src", locateAndCopy("task/egx/protected/main.egx"))
			.addProperty("outputRoot", locateAndCopy("task/egx/output"))
			.addInput("Wakeup")
			
			.build("egl");
		
		String destination = System.getProperty("user.dir") + "/target/task/egx"; 
		Path sourceDirectory = Paths.get(System.getProperty("user.dir") + "/resources/task/egx/");
		Path targetDirectory = Paths.get(destination);
		ResourceLocator.copyDir(sourceDirectory.toFile(), targetDirectory.toFile());
		
		return w;
	}
	
	public static Workflow getMarkersWorkflow() {
		String metamodel = locateInTarget("markingMate/markingmate.ecore");
		return new WorkflowBuilder()
				
				.addModelResource("BOB", "epsilon:emf")
				.addProperty("src", locateInTarget("markingMate/model/sepr-bob.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("ALICE", "epsilon:emf")
				.addProperty("src", locateInTarget("markingMate/model/sepr-alice.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addTask("bob", "epsilon:eol")
				.addInout("BOB")
				.addProperty("src", locateAndCopy("markingMate/task/helper/bob-marks.eol"))
				
				.addTask("alice", "epsilon:eol")
				.addInout("ALICE")
				.addProperty("src", locateAndCopy("markingMate/task/helper/alice-marks.eol"))
				
				.build("Marker");
	}
				
	
	
	public static Workflow getMarkingMateWorkflow() {
		String metamodel = locateAndCopy("markingMate/markingmate.ecore");
		Workflow w = new WorkflowBuilder()
				
				.addModelResource("SEPR", "epsilon:emf")
				.addProperty("src", locateAndCopy("markingMate/model/sepr.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("BOB", "epsilon:emf")
				.addProperty("src", locateAndCopy("markingMate/model/sepr-bob.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addModelResource("ALICE", "epsilon:emf")
				.addProperty("src", locateAndCopy("markingMate/model/sepr-alice.model"))
				.addProperty("metamodelFile", metamodel)

				.addModelResource("MARKED", "epsilon:emf")
				.addProperty("src", locateAndCopy("markingMate/model/sepr-marked.model"))
				.addProperty("metamodelFile", metamodel)
				
				.addTask("produce", "epsilon:eol")
				.addOutput("SEPR")
				.addProperty("src", locateAndCopy("markingMate/task/generateModel.eol"))
				
				.addTask("validateSetup", "epsilon:evl")
				.addInput("SEPR")
				.addProperty("failOnError", true)
				.addProperty("src", locateAndCopy("markingMate/task/validateSetup.evl"))
				
				.addTask("split", "epsilon:etl")
				.addInput("SEPR")
				.addOutput("ALICE")
				.addOutput("BOB")
				.addGuard("validateSetup.result")
				.addProperty("src", locateAndCopy("markingMate/task/split.etl"))
				
				.addTask("validateMarking", "epsilon:evl")
				.addInput("ALICE")
				.addInput("BOB")
				.addProperty("failOnError", true)
				.addProperty("src", locateAndCopy("markingMate/task/validateMarking.evl"))
			
				.addTask("compare", "epsilon:ecl")
				.addInput("ALICE")
				.addInput("BOB")
				.addGuard("validateMarking.result")
				.addProperty("src", locateAndCopy("markingMate/task/markingmate.ecl"))
				
				.addTask("merge", "epsilon:eml")
				.addInput("compare.comparison")
				.addInput("ALICE")
				.addInput("BOB")
				.addOutput("MARKED")		
				.addGuard("validateMarking.result")
				.addProperty("src", locateAndCopy("markingMate/task/markingmate.eml"))
				
				.addTask("csv", "epsilon:egx")
				.addGuard("validateMarking.result")
				.addInput("MARKED")
				.addProperty("src", locateAndCopy("markingMate/task/feedback.egx"))
				
				.build("MarkingMateWorkflow");
		
		locateAndCopy("markingMate/task/csv.egl");
		locateAndCopy("markingMate/task/feedback.egl");
		locateAndCopy("markingMate/task/all-feedback.egl");
		String destination = System.getProperty("user.dir") + "/target/task/egx"; 
		Path sourceDirectory = Paths.get(System.getProperty("user.dir") + "/resources/task/egx/");
		Path targetDirectory = Paths.get(destination);
		ResourceLocator.copyDir(sourceDirectory.toFile(), targetDirectory.toFile());
		
		return w;
	}
	
	private static final String RES = System.getProperty("user.dir") + "/../../examples/org.epsilonlabs.modelflow.component.example/resources/";

	public static Workflow getComponentWorkflow() {
		Workflow w = new WorkflowBuilder()
			
			.addModelResource("config", "epsilon:emf")
			.addProperty("src", locateAndCopy(RES + "m/config.model"))
			.addProperty("metamodelFile", locateAndCopy(RES + "mm/configuration.ecore"))
			
			.addModelResource("component", "epsilon:emf")
			.addProperty("src", locateAndCopy(RES + "m/component.model"))
			.addProperty("metamodelFile", locateAndCopy(RES + "mm/component.ecore"))
			
			.addModelResource("extended", "epsilon:emf")
			.addProperty("src", locateAndCopy(RES + "m/extended.model"))
			.addProperty("metamodelFile", locateAndCopy(RES + "mm/component.ecore"))
			
			.addTask("validate", "epsilon:evl")
			.addProperty("src", locateAndCopy(RES + "mmt/validate.evl"))
			.addInput("config")
			.addInput("component")
			
			.addTask("m2m", "epsilon:etl")
			.addProperty("src", locateAndCopy(RES + "mmt/addTolerances.etl"))
			.addInput("config")
			.addInput("component")
			.addOutput("extended")
			.dependsOn("validate")
			
			.addTask("m2t", "epsilon:egx")
			.addProperty("src", locateAndCopy(RES + "mmt/generateReactive.egx"))
			.addProperty("outputRoot","../../examples/org.epsilonlabs.modelflow.component.example/src-gen/")
			.addInput("extended")
	
			.build("componentWorkflow");
		
		String destination = System.getProperty("user.dir") + "/target/mmt/"; 
		Path sourceDirectory = Paths.get(RES + "mmt/");
		Path targetDirectory = Paths.get(destination);
		ResourceLocator.copyDir(sourceDirectory.toFile(), targetDirectory.toFile());
		
		return w;
	}

	
}