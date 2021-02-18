/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.FileModifier;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeTaskValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;

/**
 * @author Betty Sanchez
 *
 */
public enum ComponentScenarios implements IScenario {

	MODIFY_COMPONENT_MODEL,
	MODIFY_CONFIG_MODEL,
	MODIFY_EXTENDED_MODEL,
	MODIFY_EXTENDED_MODEL_SKIP,
	MODIFY_GENERATED_CODE_IN_PROTECTED,
	MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED,
	MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED_NO_ACTION,
	MODIFY_GENERATION_SCRIPT,
	MODIFY_TEMPLATE_FILE,
	MODIFY_TRANSFORMATION_SCRIPT,
	MODIFY_VALIDATIONS_CRIPT,
	NO_MODIFICATION,
	FIRST_TIME_EXECUTION;
	
	@Override
	public String getName() {
		return name();
	}
	
	protected static IValidate expect(boolean validateTaskExecuted, boolean transformTaskExecuted, boolean generateTaskExecuted) {
		TaskStateValidator x = new TaskStateValidator((validateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "validate");
		TaskStateValidator y = new TaskStateValidator((transformTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2m");
		TaskStateValidator z = new TaskStateValidator((generateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2t");
		return new CompositeTaskValidator(y, x, z);
	}

	@Override
	public boolean isProtect() {
		switch (this) {
		case MODIFY_EXTENDED_MODEL:
		case MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED:
			return false;
		default:
			return true;
		}
	}
	
	@Override
	public boolean isFirstTimeExecution() {
		return this.equals(FIRST_TIME_EXECUTION);
	}

	@Override
	public IValidate getValidator(){
		switch (this) {
		case NO_MODIFICATION:
			return AllTaskStateValidator.SKIPPED;
		case MODIFY_CONFIG_MODEL:
			return expect(true, true, true);
		case MODIFY_COMPONENT_MODEL:
			return expect(true, true, true);
		case MODIFY_EXTENDED_MODEL:
			return expect(false, true, false);
		case MODIFY_EXTENDED_MODEL_SKIP:
			return expect(false, false, true);
		case MODIFY_VALIDATIONS_CRIPT:
			return expect(true, false, false);
		case MODIFY_TRANSFORMATION_SCRIPT:
			return expect(false, true, false);
		case MODIFY_GENERATION_SCRIPT:
			return expect(false, false, true);
		case MODIFY_TEMPLATE_FILE:
			return expect(false, false, true);
		case MODIFY_GENERATED_CODE_IN_PROTECTED:
			return expect(false, false, false);
		case MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED:
			return expect(false, false, true);
		case MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED_NO_ACTION:
			return expect(false, false, false);
		default:
			return AllTaskStateValidator.EXECUTED;
		}
	}
	
	@Override
	public Runnable getModifications(String basedir){
		String modelDir 	= basedir + "/resources/m/%s";
		String scriptDir 	= basedir + "/resources/mmt/%s";
		String templateDir 	= basedir + "/resources/mmt/template/reactive/%s";
		String srcgenDir 	= basedir + "/src-gen/reactive/%s";
		
		switch (this) {
		case FIRST_TIME_EXECUTION:
		case NO_MODIFICATION:
			return () -> {};
		case MODIFY_CONFIG_MODEL:
			return () -> {
				String file = String.format(modelDir, "config.model");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(value=\")(0.5)(\")(.*)", "$1$21.0$4$5");
			};
		case MODIFY_COMPONENT_MODEL:
			return () -> {
				String file = String.format(modelDir, "component.model");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
			};
		case MODIFY_EXTENDED_MODEL:
			return () -> {
				String file = String.format(modelDir, "extended.model");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
			};
		case MODIFY_EXTENDED_MODEL_SKIP:
			return () -> {
				String file = String.format(modelDir, "extended.model");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(difference)(.*)", "$1diff$3");
			};
		case MODIFY_VALIDATIONS_CRIPT:
			return () -> {
				String file = String.format(scriptDir, "validate.evl");
				FileModifier modifier = new FileModifier(file);
				modifier.newLineAtEnd("//modified validation");
			};
		case MODIFY_TRANSFORMATION_SCRIPT:
			return () -> {
				String file = String.format(scriptDir, "addTolerances.etl");
				FileModifier modifier = new FileModifier(file);
				modifier.newLineAtEnd("//modified transformation");
			};
		case MODIFY_GENERATION_SCRIPT:
			return () -> {
				String file = String.format(scriptDir, "generateReactive.egx");
				FileModifier modifier = new FileModifier(file);
				modifier.newLineAtEnd("//modified generation");
			};
		case MODIFY_TEMPLATE_FILE:
			return () -> {
				String file = String.format(templateDir, "component.egl");
				FileModifier modifier = new FileModifier(file);
				modifier.newLineAtEnd("//modified template");
			};
		case MODIFY_GENERATED_CODE_IN_PROTECTED:
			return () -> {
				String file = String.format(srcgenDir, "BoilerControllerReactive.java");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(// TODO: Autogenerated method stub)", "System.out.println(\"inside\");");
			};
		case MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED:
			return () -> {
				String file = String.format(srcgenDir, "BoilerControllerReactive.java");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(private String )(name)(;)(.*)", "$1$2newName$4$5");
			};
		case MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED_NO_ACTION:
			return () -> {
				String file = String.format(srcgenDir, "BoilerControllerReactive.java");
				FileModifier modifier = new FileModifier(file);
				modifier.replaceFirst("(.*)(private String )(name)(;)(.*)", "$1$2newName$4$5");
			};
		default:
			return null;
		}
	}
}
