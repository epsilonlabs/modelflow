package org.epsilonlabs.gradle.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.eol.models.IModel;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public abstract class AbstractModel {

	private IModel model;

	protected String modelId;
	protected List<String> alias = new ArrayList<>();

	@Input
	public String getModelId() {
		return modelId;
	}
	
	public void setModelId(String id) {
		this.modelId = id;
	}
	
	@Optional
	@Input
	public List<String> getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = Arrays.asList(new String[]{alias});
	}
	
	public void setAlias(List<String> alias) {
		this.alias = alias;
	}
	
	public IModel getModel() {
		return this.model;
	}
	
	protected IModel setup(IModel model) {
		this.model = model;
		model.setName(getModelId());
		return (IModel) model;
	}

}
