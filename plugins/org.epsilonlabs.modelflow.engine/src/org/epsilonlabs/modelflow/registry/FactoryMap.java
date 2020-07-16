/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.epsilonlabs.modelflow.dom.api.factory.IFactory;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;

public class FactoryMap<I extends IFactory> {

	protected Map<String, I> factories = new HashMap<>();
	
	public FactoryMap(Set<I> resourceMonitor) {
		resourceMonitor.parallelStream().forEach(r-> this.factories.put(r.getType(), r) );
	}
	
	@Override
	public String toString() {
		return factories.keySet().toString();
	}	
	
	public Map<String, I> getFactories() {
		return factories;
	}
	
	public I getFactory(String type) throws MFInvalidFactoryException {
		if (type != null && !type.isEmpty()) {
			I f = factories.get(type);
			if (f == null){
				throw new MFInvalidFactoryException(String.format("type %s is not available", type));
			} else {
				return f;
			}
		} else {
			throw new MFInvalidFactoryException(String.format("type %s is null or empty", type));
		}
	}
	
	
}
