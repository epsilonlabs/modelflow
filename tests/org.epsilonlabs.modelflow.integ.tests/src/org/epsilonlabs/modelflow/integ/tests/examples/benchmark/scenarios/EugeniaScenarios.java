/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.FileModifier;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeTaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.ITaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;


public enum EugeniaScenarios implements IScenario {

	// No modifications nor second execution
	FIRST_TIME_EXECUTION, 
	// No polishing scripts
	NO_MODIFICATION, 
	
	// As in: Kolovos, et al (2017). Eugenia: towards disciplined and automated development of GMF-based graphical model editors.
	// Change EMF margin from 2 to 3 OR arrow style from dashed to dotted
	EMF_GMF_ANNOTATION,
	// Rename Class from Activity to Task
	EMF_RENAME_CLASS,
	//Add genmodel basepackage
	EMF_GENMODEL_ANNOTATION,
	// Change font and color of activities
	POLISH_ECORE2GMF,
	;
	
	/**
	 * 
	 */
	private static final String EMF = "simplebpmn.emf";

	@Override
	public String getName() {
		return name();
	}
	
	@Override
	public boolean isFirstTimeExecution(){
		return this.equals(FIRST_TIME_EXECUTION);
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
		case EMF_GMF_ANNOTATION:
			// Arrow style dash to dot
			return ()-> {
				String file = String.format(modelDir, EMF);
				FileModifier modifier = new FileModifier(file);				
				modifier.replaceFirst("(.*)(border\\.style=\")(dash)(\")(.*)", "$1$2dot$4$5") ;
			};
		case EMF_RENAME_CLASS:
			// Rename Activity class to Task 
			return ()-> {
				String file = String.format(modelDir, EMF);
				FileModifier modifier = new FileModifier(file);				
				modifier.replaceFirst("(class )(Activity)( extends .*)", "$1Task$3");
			};
		case EMF_GENMODEL_ANNOTATION:
			// Adds basepackage to genmodel
			return () -> {
				String file = String.format(modelDir, EMF);
				FileModifier modifier = new FileModifier(file);	
				modifier.replaceFirst("(@namespace.*)", "@emf.gen(basePackage=\"org.eclipse.epsilon.eugenia.simplebpmn\")$1");
			};
		case POLISH_ECORE2GMF:
			// Appends a few lines of EOL statements that modify the font weight and the color of the activities.
			return () -> {
				String file = String.format(polishDir, "Ecore2GMF.eol");
				String append = String.format(polishDir, "customisation.eol");
				FileModifier modifier = new FileModifier(file);
				modifier.append(new File(append));
				
				file = String.format(polishDir, "operations.eol");
				append = String.format(polishDir, "customisation-op.eol");
				modifier = new FileModifier(file);
				modifier.append(new File(append));
			};
		
		case FIRST_TIME_EXECUTION:
		case NO_MODIFICATION:
		default:
			return () -> {};
		}
	}

	// Emfatic2Ecore, Ecore2GenModel, Ecore2GMFToolGraphMap, FixGenModel, PolishGMFToolGraphMap, PolishGenModel, GmfMap2GmfGen, GenerateDomainModelCode, FixGmfGen, PolishGmfGen, GenerateDiagramCode
	@Override
	public IValidate getValidator() {
		switch (this) {
		case FIRST_TIME_EXECUTION:
		case EMF_RENAME_CLASS:
		case EMF_GENMODEL_ANNOTATION:
			return expect(true, true, true, true, true, false, true, true, true, true, true);
		case NO_MODIFICATION:
			return expect(false, false, false, false, false, false, false, false, false, false, false);
		case EMF_GMF_ANNOTATION:
			return expect(true, true, true, true, true, false, true, true, true, true, true);	
		case POLISH_ECORE2GMF: 
			return expect(false, false, false, false, true, false, true, false, true, true, true);
		default:
			throw new IllegalStateException("We shouldnt have to arrive to this line");
		}
	}
	
	/** Same as below but without the tasks that always execute */
	protected static IValidate expect(
			boolean Emfatic2Ecore, 
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
		return expect(Emfatic2Ecore, true, true, true, true, Ecore2GenModel, Ecore2GMFToolGraphMap, FixGenModel, PolishGMFToolGraphMap, PolishGenModel, 
			GmfMap2GmfGen, GenerateDomainModelCode, FixGmfGen, PolishGmfGen,GenerateDiagramCode);
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
		
		return new CompositeTaskValidator(list.toArray(new ITaskValidator[0]));
	}
	
}
