/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;

public abstract class TaskPropertyHandler implements ITaskPropertyHandler {

	protected Set<Method> annotatedMethods;
	protected ITaskInstance task;
	protected ITaskNode node;
	protected Map<String, Object> properties;
	protected Map<String, Object> hashes;

	public TaskPropertyHandler(ITaskInstance task, ITaskNode node) {
		this.task = task;
		this.node = node;
		this.annotatedMethods = getMethods();
	}
	
	@Override
	public ITaskInstance getTask() {
		return task;
	}
	
	@Override
	public Boolean hasAny(){
		return ! annotatedMethods.isEmpty();
	}
	
	@Override
	public Map<String, Object> get(){
		if (properties == null) {
			if (node.getState().hasBeenInitialised()) {
				this.properties = new HashMap<>();
				for (Method m : annotatedMethods) {
					Object assignableValue = getAssignableValue(m);
					String key = getKey(m);
					this.properties.put(key, assignableValue);
				}
			} else {
				throw new IllegalStateException("Task has not been initialised yet");
			}
		}
		return this.properties;
	}

	protected Object getAssignableValue(Method m) {
		Object value = null; 
		try {
			value = m.invoke(task);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException("Unable to retrieve input value");
		}
		if (value instanceof Optional && ((Optional<?>)value).isPresent()) {
			return ((Optional<?>)value).get();
		}
		return value;
	}
	
	@Override
	public Object get(String key){
		return get().get(key);
	}
	
	@Override
	public Map<String, Object> getHashes(){
		if (node.getState().hasBeenInitialised()) {
			if (this.hashes == null) {
				hashes = new HashMap<>();
				get().entrySet().forEach(e-> hashes.put(e.getKey(),Hasher.hash(e.getValue())) );
			}
			return hashes;
		}
		throw new IllegalStateException("Task has not been initialised yet");
	}
	
	protected abstract String getKey(Method method);

	protected abstract Set<Method> getMethods();
	
}