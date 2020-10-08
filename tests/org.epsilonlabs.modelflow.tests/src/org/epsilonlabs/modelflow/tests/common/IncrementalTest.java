package org.epsilonlabs.modelflow.tests.common;

import static org.junit.Assert.fail;

import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.IValidate;
import org.epsilonlabs.modelflow.tests.common.validator.Validator;
import org.junit.After;
import org.junit.Before;

public abstract class IncrementalTest extends WorkflowBuilderTest {
	
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
		try {
			execute();
		} catch (Exception e) {
			cleanup();
			e.printStackTrace();
			fail("First execution error");
		}
		try {
			first.ok(module);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		System.out.println(">>>MODIFICATIONS");
		modifications.run();
		System.out.println(">>>SECOND EXECUTION");
		try {
			reExecute();
		} catch (Exception e) {
			cleanup();
			e.printStackTrace();
			fail("Second execution error");
		}
		try {
			second.ok(module);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		String methodName = testName.getMethodName();
		ExecutionHelper helper = new ExecutionHelper(module);
		helper.saveGraphs(methodName);
		helper.saveModels(methodName);
		
		cleanup();
	}

}
