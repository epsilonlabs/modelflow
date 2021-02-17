package org.epsilonlabs.modelflow.tests.common.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.IModelFlowModule;

public class CompositeValidator implements IValidate {

	protected IValidate[] validators;

	public CompositeValidator(IValidate... validators) {
		this.validators = validators;
	}
	@Override
	public String expected() {
		return Arrays.asList(validators).stream()
				.map(IValidate::expected)
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	@Override
	public Boolean ok(IModelFlowModule module) throws Exception {
		ArrayList<String> errors = new ArrayList<>();
		final boolean ok = Arrays.asList(validators).stream().allMatch(v -> {
			try {
				return v.ok(module);
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(e.getMessage());
				return false;
			}
		});
		if (!errors.isEmpty()) {
			throw new Exception(errors.toString());
		} 
		return ok;
	}

	
}
