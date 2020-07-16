package org.epsilonlabs.modelflow.tests.common.validator;

import org.epsilonlabs.modelflow.IModelFlowModule;

public class Validator implements IValidate {

	private Boolean ok;
	
	private Validator(Boolean ok) {
		this.ok = ok;
	}
	
	@Override
	public Boolean ok(IModelFlowModule module) {
		return this.ok;
	}
	
	@Override
	public String expected() {
		return ok.toString();
	}
	
	public static final Validator OK = new Validator(true);
	public static final Validator NOT_OK = new Validator(false);

}
