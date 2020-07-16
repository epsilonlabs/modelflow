package org.epsilonlabs.modelflow.tests.common.validator;

import java.util.ArrayList;
import java.util.Arrays;

import org.epsilonlabs.modelflow.IModelFlowModule;

public class CompositeValidator implements IValidate {

	protected IValidate[] validators;

	public CompositeValidator(IValidate... validators) {
		this.validators = validators;
	}
	@Override
	public String expected() {
		StringBuilder builder = new StringBuilder();
		Arrays.asList(validators).forEach(v-> builder.append(v.expected()+ "\n"));
		return builder.toString();
	}
	
	@Override
	public Boolean ok(IModelFlowModule module) throws Exception {
		ArrayList<String> errors = new ArrayList<String>();
		if (Arrays.asList(validators).stream().allMatch(v -> {
			try {
				return v.ok(module);
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(e.getMessage());
				return false;
			}
		})) {
			return true;
		} else {
			throw new Exception(errors.toString());
		}
	}

	
}
