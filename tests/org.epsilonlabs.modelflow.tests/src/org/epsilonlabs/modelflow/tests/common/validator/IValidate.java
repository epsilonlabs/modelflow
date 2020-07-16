package org.epsilonlabs.modelflow.tests.common.validator;

import org.epsilonlabs.modelflow.IModelFlowModule;

public interface IValidate {
	
	Boolean ok(IModelFlowModule module) throws Exception;
	
	String expected();
	
}
