/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common;

import org.epsilonlabs.modelflow.dom.ModelResource;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.ResourceReference;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.dom.impl.DomFactoryImpl;

public class WorkflowHelper {

	private static final String USER_DIR = System.getProperty("user.dir");

	public static Workflow workflowWithEolTask() {
		Workflow workflow = DomFactoryImpl.eINSTANCE.createWorkflow();
		
		Task task = simpleEolCodeTask();
		workflow.getTasks().add(task);
		
		return workflow;
	}
	
	public static Workflow workflowWithEmfModelAndEolTask() {
		Workflow workflow = DomFactoryImpl.eINSTANCE.createWorkflow();
		
		ModelResource model = simpleEmfModel(workflow);
		workflow.getResources().add(model);
		
		Task task = simpleEolCodeTask();
		workflow.getTasks().add(task);
		
		return workflow;
	}
	
	public static Workflow workflowWithEmfModelConsumedByEolTask() {
		Workflow workflow = DomFactoryImpl.eINSTANCE.createWorkflow();
		
		ModelResource model = simpleEmfModel(workflow);
		workflow.getResources().add(model);
		
		Task task = simpleEolCodeTask();
		ResourceReference ref = DomFactoryImpl.eINSTANCE.createResourceReference();
		ref.setResource(model);
		task.getConsumes().add(ref);
		workflow.getTasks().add(task);
		
		return workflow;
	}

	private static ModelResource simpleEmfModel(Workflow workflow) {
		ModelResource model = DomFactoryImpl.eINSTANCE.createModelResource();
		model.setName("model");
		model.setDefinition("epsilon:emf");
		
		Property src = DomFactoryImpl.eINSTANCE.createProperty();
		src.setKey("src");
		src.setValue(USER_DIR + "/resources/model/emf/Simple.model");	
		model.getProperties().add(src);
		
		Property ecore = DomFactoryImpl.eINSTANCE.createProperty();
		ecore.setKey("metamodelFile");
		ecore.setValue(USER_DIR + "/resources/metamodel/ecore/Simple.ecore");	
		model.getProperties().add(ecore);
		
		Property cache = DomFactoryImpl.eINSTANCE.createProperty();
		cache.setKey("cache");
		cache.setValue(true);
		model.getProperties().add(cache);
		
		return model;
	}
	
	private static Task simpleEolCodeTask() {
		Task task = DomFactoryImpl.eINSTANCE.createTask();
		task.setName("hello");
		task.setDefinition("epsilon:eol");		
		/*Property taskSrc = DomFactoryImpl.eINSTANCE.createProperty();
		taskSrc.setKey("src");
		taskSrc.setValue(new File("hello.eol"));
		task.getProperties().add(taskSrc);*/
		Property taskCode = DomFactoryImpl.eINSTANCE.createProperty();
		taskCode.setKey("code");
		taskCode.setValue("\"Hello World\".println();");
		task.getProperties().add(taskCode);
		Property taskProfile = DomFactoryImpl.eINSTANCE.createProperty();
		taskProfile.setKey("profile");
		taskProfile.setValue(false);
		task.getProperties().add(taskProfile);
		return task;
	}

}
