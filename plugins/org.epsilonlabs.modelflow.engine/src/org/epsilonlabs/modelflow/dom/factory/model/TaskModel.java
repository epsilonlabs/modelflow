/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.factory.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.java.JavaObjectModel;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;

public class TaskModel extends JavaObjectModel {
	
	protected ITaskInstance task;
	
	public TaskModel(ITaskInstance task) {
		super();
		this.task = task;
	}
	
	@Override
	public Object createInstance(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		throw new IllegalAccessError("Not allowed to create instances");
	}
	
	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		throw new IllegalAccessError("Not allowed to delete instances");
	}
	
	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		throw new IllegalAccessError("Not allowed to create instances");
	}
	
	@Override
	public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		return new FactoryIntrospector(task.getClass()).getParameterNames();
	}
	
	@Override
	public boolean store() {
		return false;
	}
	
	@Override
	public boolean store(String location) {
		return false;
	}
	
	@Override
	public void setStoredOnDisposal(boolean storedOnDisposal) {}
	
	@Override
	public void setReadOnLoad(boolean readOnLoad) {}
	
	@Override
	public IPropertySetter getPropertySetter() {
		throw new IllegalAccessError("Not allowed to modify tasks");
	}
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return new TaskPropertyGetter();
	}
	
	@Override
	public String getName() {
		return "Task"; // FIXME
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList("self");
	}
	
	@Override
	public Collection<Object> allContents() {
		return Arrays.asList(task);
	}
	
	@Override
	public Collection<?> allInstances() {
		return allContents();
	}
	
	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		try {
			return instance.equals(task) && getPropertiesOf("").contains(property);
		} catch (EolModelElementTypeNotFoundException e) {
			return false;
		}
	}

	

}
