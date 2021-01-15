/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import java.util.ArrayList;
import java.util.List;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.FileModifier;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;

/**
 * @author Betty
 *
 */
public enum EugeniaScenarios implements IScenario {

	NO_MODIFICATION,
	MODIFY_FILE_EMF_GMF,
	/*
	MODIFY_MODEL_ECORE_GMF,
	MODIFY_MODEL_GENMODEL,
	MODIFY_MODEL_GMFGEN,
	MODIFY_TASK_POLISH,
	MODIFY_GENERATED_CODE,
	MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED_NO_ACTION,
	*/
	;
	
	@Override
	public String getName() {
		return name();
	}

	@Override
	public boolean isProtect() {
		return false;	
	}
	
	@Override
	public Runnable getModifications(String basedir) {
		String modelDir 	= basedir + "/resources/model/%s";
		String polishDir 	= basedir + "/resources/task/polish/%s";
		switch(this) {
		case MODIFY_FILE_EMF_GMF:
			String file = String.format(modelDir, "simplebpmn.emf");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(margin=\")(2)(\")(.*)", "$1$23$4$5");// Margin from 2 to 3				
		default:
			return () -> {};
		}
	}

	@Override
	public IValidate getValidator() {
		//Emfatic2Ecore, genPackages, copyright, ValidateEcoreForGenModel, ValidateEcoreForGMFToolGraphMap, Ecore2GenModel, Ecore2GMFToolGraphMap, 
		//FixGenModel, PolishGMFToolGraphMap, PolishGenModel, GmfMap2GmfGen, GenerateDomainModelCode, FixGmfGen, PolishGmfGen, GenerateDiagramCode
		switch (this) {
		case NO_MODIFICATION:
			return expect(false, true, true, false, false, false, false, 
					false, false, false, false, false, false, false, false);
		case MODIFY_FILE_EMF_GMF:
			return expect(true, true, true, true, true, true, true, 
					false, true, false, true, false, true, true, true);
		default:
			return AllTaskStateValidator.SKIPPED;
		}
	}
	
	protected static IValidate expect(
			boolean Emfatic2Ecore, 
			boolean genPackages, 
			boolean copyright,
			boolean ValidateEcoreForGenModel, 
			boolean ValidateEcoreForGMFToolGraphMap, 
			boolean Ecore2GenModel,
			boolean Ecore2GMFToolGraphMap, 
			boolean FixGenModel, 
			boolean PolishGMFToolGraphMap,
			boolean PolishGenModel, 
			boolean GmfMap2GmfGen, 
			boolean GenerateDomainModelCode,
			boolean FixGmfGen, 
			boolean PolishGmfGen,
			boolean GenerateDiagramCode
	) {
		List<IValidate> list = new ArrayList<>();
		
		list.add(new TaskStateValidator((genPackages ? TaskState.EXECUTED : TaskState.SKIPPED), "genPackages"));
		list.add(new TaskStateValidator((copyright ? TaskState.EXECUTED : TaskState.SKIPPED), "copyright"));
		
		list.add(new TaskStateValidator((Emfatic2Ecore ? TaskState.EXECUTED : TaskState.SKIPPED), "Emfatic2Ecore"));
		list.add(new TaskStateValidator((ValidateEcoreForGenModel ? TaskState.EXECUTED : TaskState.SKIPPED), "ValidateEcoreForGenModel"));
		list.add(new TaskStateValidator((ValidateEcoreForGMFToolGraphMap ? TaskState.EXECUTED : TaskState.SKIPPED), "ValidateEcoreForGMFToolGraphMap"));
		list.add(new TaskStateValidator((Ecore2GenModel ? TaskState.EXECUTED : TaskState.SKIPPED), "Ecore2GenModel"));
		list.add(new TaskStateValidator((Ecore2GMFToolGraphMap ? TaskState.EXECUTED : TaskState.SKIPPED), "Ecore2GMFToolGraphMap"));
		list.add(new TaskStateValidator((FixGenModel ? TaskState.EXECUTED : TaskState.SKIPPED), "FixGenModel"));
		list.add(new TaskStateValidator((PolishGMFToolGraphMap ? TaskState.EXECUTED : TaskState.SKIPPED), "PolishGMFToolGraphMap"));
		list.add(new TaskStateValidator((PolishGenModel ? TaskState.EXECUTED : TaskState.SKIPPED), "PolishGenModel"));
		list.add(new TaskStateValidator((GmfMap2GmfGen ? TaskState.EXECUTED : TaskState.SKIPPED), "GmfMap2GmfGen"));
		list.add(new TaskStateValidator((GenerateDomainModelCode ? TaskState.EXECUTED : TaskState.SKIPPED), "GenerateDomainModelCode"));
		list.add(new TaskStateValidator((FixGmfGen ? TaskState.EXECUTED : TaskState.SKIPPED), "FixGmfGen"));
		list.add(new TaskStateValidator((PolishGmfGen ? TaskState.EXECUTED : TaskState.SKIPPED), "PolishGmfGen"));
		list.add(new TaskStateValidator((GenerateDiagramCode ? TaskState.EXECUTED : TaskState.SKIPPED), "GenerateDiagramCode"));
		
		return new CompositeValidator(list.toArray(new IValidate[0]));
	}
	
}
