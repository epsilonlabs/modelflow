/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples;

import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.FileModifier;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.ExampleProjectTest;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeTaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class EuGENiaExampleTest extends ExampleProjectTest {

	protected String basedir 		= getOutputPath().toString() ;
	protected String modelDir 		= basedir + "/resources/model/%s";
	protected String scriptDir 		= basedir + "/resources/task/%s";
	protected String polishDir 		= basedir + "/resources/task/polish/%s";
	protected String srcgenDir 		= basedir + "/src-gen/%s";
	
	@BeforeClass
	public static void configureModule() {
		if (taskFactoryRegistry == null || resFactoryRegistry == null) {
			Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
			taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
			resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		}
	}
	
	@Override
	protected void setupModule() {
		super.setupModule();
		FrameStack frameStack = module.getContext().getFrameStack();
		frameStack.put("base", "resources/model/filesystem");
		frameStack.put("copyrightStatement", "resources/task/copyright.txt");
	}
	
	@Override
	protected String getProjectName() {
		return "org.epsilonlabs.modelflow.eugenia.example";
	}

	@Override
	protected String getBuildFileName() {
		return "eugenia.mflow";
	}
	
	protected IValidate expect(boolean validateTaskExecuted, boolean transformTaskExecuted, boolean generateTaskExecuted) {
		TaskStateValidator x = new TaskStateValidator((validateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "validate");
		TaskStateValidator y = new TaskStateValidator((transformTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2m");
		TaskStateValidator z = new TaskStateValidator((generateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2t");
		return new CompositeTaskValidator(y, x, z);
	}

	// NO MODIFICATIONS
	
	@Test
	public void noModificationsTest() {
		modifications = () -> {};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	// MODEL MODIFICATIONS
	
	@Test
	public void ecoreGMFModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.ecore");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*style=\")(dash)(\".*)", "$1dot$3");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void ecoreGenmodelModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.ecore");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst(
					"(@emf.gen\\(basePackage=\"org.eclipse.epsilon.eugenia.examples\")(.*)",
					"$1, fileExtensions=\"fsys\"$2");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	/*
	@Test
	public void genmodelModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.genmodel");
			FileModifier modifier = new FileModifier(file);
			//modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void gmfmapModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.gmfmap");
			FileModifier modifier = new FileModifier(file);
			//modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	
	@Test
	public void gmftoolModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.gmftool");
			FileModifier modifier = new FileModifier(file);
			//modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void gmfgraphModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.gmfgraph");
			FileModifier modifier = new FileModifier(file);
			//modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = AllTaskStateValidator.SKIPPED;
	}

	@Test
	public void gmfgenhModificationTest() {
		modifications = () -> {
			String file = String.format(modelDir, "filesystem.gmfgen");
			FileModifier modifier = new FileModifier(file);
			//modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = AllTaskStateValidator.SKIPPED;
	}*/
	
	// Script Modifications
	@Test
	public void genPackageCommentModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "genPackages.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified genPackages eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void copyrightModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "copyright.txt");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("University of York");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void genmodelValidationModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "ECore2GMF.evl");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified ECore2GMF evl");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	// FIXME ECore2GenModel validation has nothing
	
	@Test
	public void ecore2genmodelTranformationModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "Ecore2GenModel.etl");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified Ecore2GenModel etl");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void fixgenmodelEolModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "FixGenModel.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified FixGenModel eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void polishgenmodelEolModificationTest() {
		modifications = () -> {
			String file = String.format(polishDir, "PolishGenModel.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified PolishGenModel eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void ecore2gmfEolModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "ECore2GMF.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified ECore2GMF eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void polishgmfEolModificationTest() {
		modifications = () -> {
			String file = String.format(polishDir, "ECore2GMF.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified polish ECore2GMF eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void fixGmfgenModificationTest() {
		modifications = () -> {
			String file = String.format(scriptDir, "FixGMFGen.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified FixGMFGen eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	@Test
	public void fixpolishGmfgenModificationTest() {
		modifications = () -> {
			String file = String.format(polishDir, "FixGMFGen.eol");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("// modified polish FixGMFGen eol");
		};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	// MODIFY CODE
	
	
}
