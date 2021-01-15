/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.emf.tests.common.workflow;


import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowBuilder;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;

public class EmfTask {

	private static String location(String name) {
		return ResourceLocator.locateAndCopyToTestDir("filesystem." + name);
	}
	
	public static IWorkflow getEmfatic2EcoreWithModelWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("ECore", "epsilon:emf")
				.addProperty("src", location("ecore"))
				.addProperty("metamodelUri", EcorePackage.eINSTANCE.getNsURI())
				
				.addTask("Emfatic2Ecore", "emf:emfatic2ecore")
				.addProperty("src", location("emf"))
				.addOutput("ECore", "Ecore")
				
			.build();
	}
		
		
	public static IWorkflow getEmfatic2EcoreWorkflow() {
		return new WorkflowBuilder()
				
				.addModelResource("ECore", "epsilon:emf")
				.addProperty("src", location("ecore"))
				.addProperty("metamodelUri", EcorePackage.eINSTANCE.getNsURI())
				
				.addTask("Emfatic2Ecore", "emf:emfatic2ecore")
				.addOutput("ECore")
				.addProperty("src", location("emf"))
				
				
			.build();
	}
	
	public static IWorkflow genCodeWorkflow() {
		return new WorkflowBuilder()
			
				.addModelResource("GenModel", "epsilon:emf")
				.addProperty("src", location("genmodel"))
				.addProperty("metamodelUri", GenModelPackage.eINSTANCE.getNsURI())
					
				.addTask("GenerateDomainModelCode", "emf:genCode")
				//.addProperty("outputDir", System.getProperty("user.home") + "/Downloads/genmodel/")
				.addInput("GenModel")
				
			.build();
	}
}
