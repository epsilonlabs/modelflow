/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;

public class TaskInputPropertyHandler extends TaskPropertyHandler {

	public TaskInputPropertyHandler(ITaskInstance instance) {
		super(instance);
	}
		
	@Override
	protected Set<Method> getMethods(){
		return new FactoryIntrospector(task.getClass()).getInputMethods();

	}
	
	@Override
	protected String getKey(Method input) {
		String key = input.getAnnotation(Input.class).key();
		if (key != null && !key.isEmpty()) {
			return key;
		} else {
			throw new IllegalStateException(input.getName() 
					+ " should specify a key in @Input");
		}
	}

	@Override
	public Map<String, Object> getHashesFromTrace(TaskExecution taskExecution) {
		throw new IllegalStateException("Method not implemented");
	}
	
}
