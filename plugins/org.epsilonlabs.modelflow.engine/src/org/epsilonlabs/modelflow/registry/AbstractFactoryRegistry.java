/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;

public abstract class AbstractFactoryRegistry<F>{
	
	protected Map<String, Class<F>> factories = new ConcurrentHashMap<>();
	
	public AbstractFactoryRegistry(Set<Class<F>> resourceMonitor) {
		resourceMonitor.parallelStream().forEach(r-> {
			Definition definition = r.getAnnotation(Definition.class);
			if (definition != null) {				
				this.factories.put(definition.name(), r); 
			}
		});
	}	
	
	public Class<F> getFactory(String type) throws MFInvalidFactoryException {
		if (type != null && !type.isEmpty()) {
			Class<F> f = factories.get(type);
			if (f == null){
				throw new MFInvalidFactoryException(String.format("type %s is not available", type));
			} else {
				return f;
			}
		} else {
			throw new MFInvalidFactoryException(String.format("type %s is null or empty", type));
		}
	}
	
	public boolean addFactory(Class<F> factory) {
		try {
			Definition definition = factory.getAnnotation(Definition.class);
			if (definition != null) {				
				factories.put(definition.name(), factory);			
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	public boolean removeFactory(Class<F> factory) {
		try {
			Definition definition = factory.getAnnotation(Definition.class);
			if (definition != null) {				
				factories.remove(definition.name());
				return true;
			}
		} catch (Exception e) {}
		return false;
	}
	
	/**
	 * @return the factories
	 */
	public Map<String, Class<F>> getFactories() {
		return factories;
	}

	@Override
	public String toString() {
		return String.format("%s : %n\tAvailable Factories : %s", getClass().getSimpleName(),
				factories.keySet());
	}

}
