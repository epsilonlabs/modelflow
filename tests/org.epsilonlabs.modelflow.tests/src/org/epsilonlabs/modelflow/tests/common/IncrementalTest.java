package org.epsilonlabs.modelflow.tests.common;

import static org.junit.Assert.fail;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.Validator;
import org.junit.After;
import org.junit.Before;

public abstract class IncrementalTest extends WorkflowBuilderTest {

	protected Workflow w;
	protected Runnable modifications;
	protected IValidate first;
	protected IValidate second;

	/** TESTING PROCESS */

	@Before
	public void preprocess() {
		System.out.println(">>>Executing " + testName.getMethodName());
		first = AllTaskStateValidator.EXECUTED;
		second = Validator.NOT_OK;
	}

	@After
	public void postprocess() {
		System.out.println(">>>FIRST EXECUTION");
		execute(w);
		try {
			first.ok(module);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		System.out.println(">>>MODIFICATIONS");
		modifications.run();
		System.out.println(">>>SECOND EXECUTION");
		reExecute();
		try {
			second.ok(module);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		saveModels(module, testName.getMethodName());
		saveGraphs(module, testName.getMethodName());

	}

}
