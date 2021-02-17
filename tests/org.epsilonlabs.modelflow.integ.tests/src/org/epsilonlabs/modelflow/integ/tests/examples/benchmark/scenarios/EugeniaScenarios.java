/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.FileModifier;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeTaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.ITaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;

/**
 * @author Betty
 *
 */
public enum EugeniaScenarios implements IScenario {

	// No polishing scripts
	NO_MODIFICATION,
	
	// Change EMF margin
	MODIFY_EMF_GMF_ANNOTATION,
	//Change
	MODIFY_EMF_GENMODEL_ANNOTATION,	
	
	// As in: Kolovos, et al (2017). Eugenia: towards disciplined and automated development of GMF-based graphical model editors.
	// Change EMF to use bold
	//CHANGE_FONT_TO_BOLD,
	// Change EMF to use background color
	//CHANGE_BACKGROUND_COLOR,
	// Change EMF class name
	//RENAME_METAMODEL_CLASS,
	// Add polishing scripts
	//ADD_POLISHING_SCRIPT,
	
	MODIFY_TASK_POLISH_GMFGEN,
	
	
	/*
	MODIFY_MODEL_GENMODEL,
	MODIFY_MODEL_GMFGEN,
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
		switch (this) {
		
		default:
			return false;
		}	
	}
	
	@Override
	public Runnable getModifications(String basedir) {
		String modelDir 	= basedir + "/resources/model/%s";
		String polishDir 	= basedir + "/resources/task/polish/%s";
		switch(this) {
		case MODIFY_EMF_GMF_ANNOTATION:
			// EMF changes margin of XOR, then Ecore is updated, GenModel does not change, GmfGraph changes for XORFigure which is now 3
			// FixGenModel executes again because Ecore changed
			return ()-> {
				String file = String.format(modelDir, "simplebpmn.emf");
				FileModifier modifier = new FileModifier(file);				
				modifier.replaceFirst("(.*)(margin=\")(2)(\")(.*)", "$1$23$4$5") ;// Margin from 2 to 3 
			};
		case MODIFY_EMF_GENMODEL_ANNOTATION:
			// EMF changes margin of XOR, then Ecore is updated, GenModel does not change, GmfGraph changes for XORFigure which is now 3
			// FixGenModel executes again because Ecore changed
			return ()-> {
				String file = String.format(modelDir, "simplebpmn.emf");
				FileModifier modifier = new FileModifier(file);	
				//modifier.replaceFirst("(@gmf.node\\(tool.name=\\\"XOR Gateway\\\",)", "@emf.gen(documentation=\"An XOR Gateway\")$1") ;// adding documentation
				//modifier.replaceFirst("(.*)(attr String name;)", "$1@emf.gen(propertyMultiline=\"true\") $2") ;// Adding multiline property
				modifier.replaceFirst("(@namespace.*)", "@emf.gen(basePackage=\"org.eclipse.epsilon.eugenia.simplebpmn\")$1") ;// Adding multiline property
			};
		case MODIFY_TASK_POLISH_GMFGEN:
			return () -> {
				String file = String.format(polishDir, "FixGMFGen.eol");
				try {
					new File(file).createNewFile();
					FileModifier modifier = new FileModifier(file);
					String code = "var genPlugin = GmfGen!GenPlugin.all.first();\n" +
							"genPlugin.requiredPlugins.add(\"org.eclipse.epsilon.eugenia.simplebpmn.diagram.custom\");";
					modifier.newLineAtEnd(code);
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		case NO_MODIFICATION:
		default:
			return () -> {};
		}
		
	}

	// Emfatic2Ecore, Ecore2GenModel, Ecore2GMFToolGraphMap, FixGenModel, PolishGMFToolGraphMap, PolishGenModel, GmfMap2GmfGen, GenerateDomainModelCode, FixGmfGen, PolishGmfGen, GenerateDiagramCode
	@Override
	public IValidate getValidator() {
		switch (this) {
		case NO_MODIFICATION:
			return expect(false, false, false, false, false, false, false, false, false, false, false);
		case MODIFY_EMF_GMF_ANNOTATION:
			return expect(true, true, true, true, true, false, true, false, true, false, true);
		case MODIFY_TASK_POLISH_GMFGEN: 
			return expect(false, false, false, false, false, false, false, false, false, true, true);
		case MODIFY_EMF_GENMODEL_ANNOTATION:
			return expect(true, true, true, true, true, false, true, true, true, false, true);
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
