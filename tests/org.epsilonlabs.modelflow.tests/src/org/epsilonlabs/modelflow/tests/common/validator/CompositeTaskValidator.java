package org.epsilonlabs.modelflow.tests.common.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.IModelFlowModule;

public class CompositeTaskValidator implements IValidate {

	protected ITaskValidator[] validators;

	public CompositeTaskValidator(ITaskValidator... validators) {
		this.validators = validators;
	}
	@Override
	public String expected() {
		return Arrays.asList(validators).stream()
				.filter(v->!v.getExpectedTask().equals(v.getActualTask()))
				.map(IValidate::expected)
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	@Override
	public Boolean ok(IModelFlowModule module) throws Exception {
		ArrayList<String> errors = new ArrayList<>();
		final Set<Boolean> result = Arrays.asList(validators).stream().map(v -> {
			try {
				return v.ok(module);
			} catch (Exception e) {
				e.printStackTrace();
				errors.add(e.getMessage());
				return false;
			}
		}).collect(Collectors.toSet());
		if (!errors.isEmpty()) {
			throw new Exception(errors.toString());
		} 
		return result.stream().allMatch(Boolean::booleanValue);
	}

	
}
