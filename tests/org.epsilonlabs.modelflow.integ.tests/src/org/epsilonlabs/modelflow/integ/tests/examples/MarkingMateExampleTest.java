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
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.ExampleProjectTest;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeValidator;
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
public class MarkingMateExampleTest extends ExampleProjectTest {

	protected String basedir 		= getOutputPath().toString() ;
	protected String modelDir 		= basedir + "/resources/m/%s";
	protected String scriptDir 		= basedir + "/resources/mmt/%s";
	protected String srcgenDir 		= basedir + "/src-gen/reactive/%s";
	
	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	@Override
	protected void setupModule() {
		super.setupModule();
		FrameStack frameStack = module.getContext().getFrameStack();
		frameStack.put("mBasedir", "resources/m/");
		frameStack.put("tBasedir", "resources/mmt/");
		frameStack.put("mm", "resources/mm/markingmate.ecore");
	}
	
	@Override
	protected String getProjectName() {
		return "org.epsilonlabs.modelflow.markingmate.example";
	}

	@Override
	protected String getBuildFileName() {
		return "markingmate.mflow";
	}
	
	protected IValidate expect(
			boolean validateSetup, 
			boolean splitBob, 
			boolean splitAlice,
			boolean validateMarking,
			boolean compare,
			boolean merge,
			boolean csv) 
	{
		return new CompositeValidator(
				new TaskStateValidator((validateSetup ? TaskState.EXECUTED : TaskState.SKIPPED), "validateSetup"), 
				new TaskStateValidator((splitBob ? TaskState.EXECUTED : TaskState.SKIPPED), "splitBob"),
				new TaskStateValidator((splitAlice ? TaskState.EXECUTED : TaskState.SKIPPED), "splitAlice"),
				new TaskStateValidator((validateMarking ? TaskState.EXECUTED : TaskState.SKIPPED), "validateMarking"),
				new TaskStateValidator((compare ? TaskState.EXECUTED : TaskState.SKIPPED), "compare"),
				new TaskStateValidator((merge ? TaskState.EXECUTED : TaskState.SKIPPED), "merge"),
				new TaskStateValidator((csv ? TaskState.EXECUTED : TaskState.SKIPPED), "csv") );
	}
	
	// NO MODIFICATIONS

	@Test
	public void noModificationTest() {
		modifications = () -> {};
		second = AllTaskStateValidator.SKIPPED;
	}
	

	// ONE MODIFICATON
	
	@Test
	public void modifySeprModelTest() {
		modifications = () -> {
			String file = String.format(modelDir, "sepr.model");
			FileModifier modifier = new FileModifier(file);
			//modifier.copyFrom("");
		};
		second = expect(true, true, true, true, false, false, false);
	}
	
	@Test
	public void bobMarksTest() {
		modifications = () -> {
			String file = String.format(modelDir, "sepr-bob.model");
			FileModifier modifier = new FileModifier(file);
			//modifier.copyFrom("");
		};
		second = expect(false, true, false, true, false, false, false);
	}
	
	@Test
	public void bobAndAliceMarksTest() {
		modifications = () -> {
			String file = String.format(modelDir, "sepr-bob.model");
			FileModifier modifier = new FileModifier(file);
			//modifier.copyFrom("");
			
			String file1 = String.format(modelDir, "sepr-alice.model");
			FileModifier modifier1 = new FileModifier(file1);
			//modifier1.copyFrom("");
		};
		second = expect(false, true, true, true, true, true, true);
	}
	
	@Test
	public void modifyMarkedProtectTest() {
		protect = true;
		modifications = () -> {
			String file = String.format(modelDir, "sepr-marked.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = expect(false, false, false, false, false, false, true);
	}
	
	@Test
	public void modifyMarkedNonProtectTest() {
		protect = false;
		modifications = () -> {
			String file = String.format(modelDir, "sepr-marked.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = expect(false, false, false, false, true, true, false);
	}
	
	/*
	@Test
	public void modifyValidateSetupTest() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
	
	@Test
	public void modifySplitTest() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
		
	@Test
	public void modifyCompareTest() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
	
	@Test
	public void modifyMergeTest() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
	
	@Test
	public void modifyCsvTest() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
	
	*/

}
