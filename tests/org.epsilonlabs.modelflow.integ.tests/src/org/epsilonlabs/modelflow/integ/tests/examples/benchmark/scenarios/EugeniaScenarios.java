/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.CompositeValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;

/**
 * @author Betty
 *
 */
public enum EugeniaScenarios implements IScenario {

	MODIFY_FILE_EMF,
	MODIFY_MODEL_ECORE,
	MODIFY_MODEL_GENMODEL,
	MODIFY_MODEL_GMFGEN,
	MODIFY_TASK_POLISH,
	MODIFY_GENERATED_CODE,
	MODIFY_GENERATED_CODE_OUTSIDE_PROTECTED_NO_ACTION,
	NO_MODIFICATION;

	protected static IValidate expect(boolean validateTaskExecuted, boolean transformTaskExecuted, boolean generateTaskExecuted) {
		TaskStateValidator validate = new TaskStateValidator((validateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "validate");
		TaskStateValidator y = new TaskStateValidator((transformTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2m");
		TaskStateValidator z = new TaskStateValidator((generateTaskExecuted ? TaskState.EXECUTED : TaskState.SKIPPED), "m2t");
		return new CompositeValidator(y, validate, z);
	}
	
	@Override
	public String getName() {
		return name();
	}

	@Override
	public Runnable getModifications(String basedir) {
		switch(this) {
		case NO_MODIFICATION:
		default:
			return () -> {};
		}
	}

	@Override
	public IValidate getValidator() {
		switch (this) {
		case NO_MODIFICATION:
			return AllTaskStateValidator.SKIPPED;
		default:
			return AllTaskStateValidator.SKIPPED;
		}
	}

	@Override
	public boolean isProtect() {
		return false;	
	}
	
}
