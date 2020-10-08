package org.epsilonlabs.modelflow.mmc.gmf.tests.common.workflow;

import static org.epsilonlabs.modelflow.tests.common.ResourceLocator.locateAndCopyToTestDir;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;

public class GmfTask {

	private static String location(String name) {
		return locateAndCopyToTestDir("filesystem." + name);
	}
	
	public static Workflow getGenDiagramWorkflow() {
		return new WorkflowBuilder()
			
				.addModelResource("GmfGen", "epsilon:emf")
				.addProperty("src", locateAndCopyToTestDir("filesystemGmfGen.gmfgen"))
				.addProperty("metamodelUri", GMFGenPackage.eINSTANCE.getNsURI())
				
				.addTask("GenerateDiagramCode", "gmf:genDiagram")
				.addInput("GmfGen")
			
			.build();
	}
	
	public static Workflow getGmfmap2GmfGenWorkflow() {
		return new WorkflowBuilder()
				.addModelResource("ECore", "epsilon:emf")
				.addProperty("src", location("ecore"))
				.addProperty("metamodelUri", EcorePackage.eINSTANCE.getNsURI())
				.addProperty("alias", "Ecore")
				
				.addModelResource("GenModel", "epsilon:emf")
				.addProperty("src", location("genmodel"))
				.addProperty("metamodelUri", GenModelPackage.eINSTANCE.getNsURI())
				
				.addModelResource("GmfMap", "epsilon:emf")
				.addProperty("src", location("gmfmap"))
				.addProperty("metamodelUri", GMFMapPackage.eINSTANCE.getNsURI())
			
				.addModelResource("GmfGen", "epsilon:emf")
				.addProperty("src", location("gmfgen"))
				.addProperty("metamodelUri", GMFGenPackage.eINSTANCE.getNsURI())
				
				.addTask("GmfMap2GmfGen", "gmf:gmfMap2gmfGen")
				.addInput("ECore")
				.addInput("GmfMap")
				.addInput("GenModel")
				.addOutput("GmfGen")
			
			.build();
	}
	
}
