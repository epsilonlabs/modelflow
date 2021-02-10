/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.common;

import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

public class WorkflowHelper {

	private static final String USER_DIR = System.getProperty("user.dir");

	public static IWorkflow workflowWithEolTask() {
		IWorkflow workflow = DomFactory.eINSTANCE.createWorkflow();
		
		ITask task = simpleEolCodeTask();
		workflow.getTasks().add(task);
		
		return workflow;
	}
	
	public static IWorkflow workflowWithEmfModelAndEolTask() {
		IWorkflow workflow = DomFactory.eINSTANCE.createWorkflow();
		
		IModelResource model = simpleEmfModel(workflow);
		workflow.getResources().add(model);
		
		ITask task = simpleEolCodeTask();
		workflow.getTasks().add(task);
		
		return workflow;
	}
	
	public static IWorkflow workflowWithEmfModelConsumedByEolTask() {
		IWorkflow workflow = DomFactory.eINSTANCE.createWorkflow();
		
		IModelResource model = simpleEmfModel(workflow);
		workflow.getResources().add(model);
		
		ITask task = simpleEolCodeTask();
		IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
		ref.setResource(model);
		task.getConsumes().add(ref);
		workflow.getTasks().add(task);
		
		return workflow;
	}

	private static IModelResource simpleEmfModel(IWorkflow workflow) {
		IModelResource model = DomFactory.eINSTANCE.createModelResource();
		model.setName("model");
		model.setDefinition("epsilon:emf");
		
		IProperty src = DomFactory.eINSTANCE.createProperty();
		src.setKey("src");
		src.setValue(USER_DIR + "/resources/model/emf/Simple.model");	
		model.getProperties().add(src);
		
		IProperty ecore = DomFactory.eINSTANCE.createProperty();
		ecore.setKey("metamodelFile");
		ecore.setValue(USER_DIR + "/resources/metamodel/ecore/Simple.ecore");	
		model.getProperties().add(ecore);
		
		IProperty cache = DomFactory.eINSTANCE.createProperty();
		cache.setKey("cache");
		cache.setValue(true);
		model.getProperties().add(cache);
		
		return model;
	}
	
	private static ITask simpleEolCodeTask() {
		ITask task = DomFactory.eINSTANCE.createTask();
		task.setName("hello");
		task.setDefinition("epsilon:eol");		
		/*Property taskSrc = DomFactoryImpl.eINSTANCE.createProperty();
		taskSrc.setKey("src");
		taskSrc.setValue(new File("hello.eol"));
		task.getProperties().add(taskSrc);*/
		IProperty taskCode = DomFactory.eINSTANCE.createProperty();
		taskCode.setKey("code");
		taskCode.setValue("\"Hello World\".println();");
		task.getProperties().add(taskCode);
		IProperty taskProfile = DomFactory.eINSTANCE.createProperty();
		taskProfile.setKey("profile");
		taskProfile.setValue(false);
		task.getProperties().add(taskProfile);
		return task;
	}

}
