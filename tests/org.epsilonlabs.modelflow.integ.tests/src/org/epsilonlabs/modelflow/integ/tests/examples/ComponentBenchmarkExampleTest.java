/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples;

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
public class ComponentBenchmarkExampleTest extends ExampleProjectTest {

	protected String basedir 		= getOutputPath().toString() ;
	protected String modelDir 		= basedir + "/resources/m/%s";
	protected String scriptDir 		= basedir + "/resources/mmt/%s";
	protected String templateDir 	= basedir + "/resources/mmt/template/reactive/%s";
	protected String srcgenDir 		= basedir + "/src-gen/reactive/%s";

	@BeforeClass
	public static void configureModule() {
		if (taskFactoryRegistry == null || resFactoryRegistry == null) {
			Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
			taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
			resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		}
	}
	
	@Override
	protected String getProjectName() {
		return "org.epsilonlabs.modelflow.component.example";
	}

	@Override
	protected String getBuildFileName() {
		return "component.mflow";
	}
	
	protected IValidate expect(boolean validateTaskExecuted, boolean transformTaskExecuted, boolean generateTaskExecuted) {
		TaskStateValidator x = new TaskStateValidator((validateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "validate");
		TaskStateValidator y = new TaskStateValidator((transformTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2m");
		TaskStateValidator z = new TaskStateValidator((generateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2t");
		return new CompositeTaskValidator(y, x, z);
	}

	// NO MODIFICATIONS
	
	@Test
	public void noModificationTest() {
		modifications = () -> {};
		second = AllTaskStateValidator.SKIPPED;
	}
	
	// ONE MODIFICATON
	
	@Test
	public void modifyConfigModel() {
		modifications = () -> {
			String file = String.format(modelDir, "config.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
		};
		second = expect(true, true, true);
	}
	
	@Test
	public void modifyComponentModel() {
		modifications = () -> {
			String file = String.format(modelDir, "component.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(true, true, true);
	}
	
	@Test
	public void modifyExtendedModel() {
		protect = false;
		modifications = () -> {
			String file = String.format(modelDir, "extended.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(false, true, false);
	}
	
	@Test
	public void modifyExtendedModelSkip() {
		modifications = () -> {
			String file = String.format(modelDir, "extended.model");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
		};
		second = expect(false, false, true);
	}
	
	@Test
	public void modifyValidationScript() {
		modifications = () -> {
			String file = String.format(scriptDir, "validate.evl");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("//modified validation");
		};
		second = expect(true, false, false);
	}
	
	@Test
	public void modifyTransformationScript() {
		modifications = () -> {
			String file = String.format(scriptDir, "addTolerances.etl");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("//modified transformation");
		};
		second = expect(false, true, false);
	}
	
	@Test
	public void modifyGenerationScript() {
		modifications = () -> {
			String file = String.format(scriptDir, "generateReactive.egx");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("//modified generation");
		};
		second = expect(false, false, true);
	}

	@Test
	public void modifyTemplateFile() {
		modifications = () -> {
			String file = String.format(templateDir, "component.egl");
			FileModifier modifier = new FileModifier(file);
			modifier.newLineAtEnd("//modified template");
		};
		second = expect(false, false, true);
	}
	
	@Test
	public void modifyGeneratedCodeInProtected() {
		modifications = () -> {
			String file = String.format(srcgenDir, "BoilerControllerReactive.java");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(// TODO: Autogenerated method stub)", "System.out.println(\"inside\");");
		};
		second = expect(false, false, false);
	}
	
	@Test
	public void modifyGeneratedCodeOutsideProtected() {
		protect = false;
		modifications = () -> {
			String file = String.format(srcgenDir, "BoilerControllerReactive.java");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(private String )(name)(;)(.*)", "$1$2newName$4$5");
		};
		second = expect(false, false, true);
	}
	
	@Test  
	public void modifyGeneratedCodeOutsideProtectedNoAction() {
		modifications = () -> {
			String file = String.format(srcgenDir, "BoilerControllerReactive.java");
			FileModifier modifier = new FileModifier(file);
			modifier.replaceFirst("(.*)(private String )(name)(;)(.*)", "$1$2newName$4$5");
		};
		second = expect(false, false, false);
	}
	
	// MULTIPLE MODIFICATIONS

}
