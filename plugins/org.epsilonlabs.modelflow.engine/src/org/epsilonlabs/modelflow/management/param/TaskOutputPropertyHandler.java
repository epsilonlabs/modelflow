/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;
import org.epsilonlabs.modelflow.execution.trace.PropertySnapshot;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.management.param.hash.IHasher;

public class TaskOutputPropertyHandler extends TaskPropertyHandler {
	
	public TaskOutputPropertyHandler(ITaskInstance task) {
		super(task);
	}
		
	@Override
	protected Set<Method> getMethods(){
		return new FactoryIntrospector(task.getClass()).getOutputMethods();
	}
	
	@Override
	protected String getKey(Method output) {
		String key = output.getAnnotation(Output.class).key();
		if (key != null && !key.isEmpty()) {
			return key;
		} else {
			throw new IllegalStateException(output.getName() 
					+ " should specify a key in @Output");
		}
	}
	
	public Map<String, File> getFiles() {
		return this.properties.entrySet().stream()
			.filter(e->{
				Object val = e.getValue();
				boolean isFile = val instanceof File;
				boolean isFileArray = val instanceof File[];
				boolean isFileCollection = val instanceof Collection<?> && ((Collection<?>)val).stream().allMatch(f-> f instanceof File);  
				return isFile || isFileArray || isFileCollection ;
			}).collect(Collectors.toMap(Entry::getKey, e -> (File) e.getValue()));
	}
	
	@Override
	public Map<String, Object> getHashes() {
		if (this.task.getTaskNode().getState().hasBeenExecuted()) {
			this.properties = new HashMap<String, Object>();
			this.hashes = new HashMap<String, Object>();
			for (Method m : annotatedMethods) {
				Object value = null; 
				try {
					value = m.invoke(task);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalStateException("Unable to retrieve input value");
				}
				if (value != null) {
					try{
						String key = getKey(m);
						if (value instanceof Optional && ((Optional<?>)value).isPresent()) {
							Object val = ((Optional<?>)value).get();
							this.properties.put(key, val);
						} else {
							this.properties.put(key, value);
						}						
						@SuppressWarnings("unchecked")
						IHasher<Object, Object> hasher = (IHasher<Object, Object>) m.getAnnotation(Output.class).hasher().newInstance();
						this.hashes.put(key, hasher.fromTaskPopulatedParameter(this.properties.get(key)));
					} catch (Exception e) {
						throw new IllegalStateException("Unable to retrieve hasher for output property");
					}
				}
			}
		} 
		return hashes;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHashesFromTrace(TaskExecution taskExecution){
		taskExecution = EcoreUtil.copy(taskExecution);
		if (!this.task.getTaskNode().getState().hasBeenExecuted()) {
			this.hashes = new HashMap<String, Object>();
			for (Method m : annotatedMethods) {
				String key = getKey(m);
				Optional<Object> value = taskExecution.getOutputProperties().stream().filter(p->p.getName().equals(key)).map(PropertySnapshot::getStamp).findFirst();
				if (value.isPresent()){
					Object trace = value.get();
					try {
						IHasher<Object, Object> hasher = (IHasher<Object, Object>) m.getAnnotation(Output.class).hasher().newInstance();
						this.hashes.put(key, hasher.fromExecutionTrace(trace));
					} catch (Exception e) {
						throw new IllegalStateException("Unable to retrieve hasher for output property from trace", e);
					}
				}
			}
		} 
		return hashes;
	}


}
