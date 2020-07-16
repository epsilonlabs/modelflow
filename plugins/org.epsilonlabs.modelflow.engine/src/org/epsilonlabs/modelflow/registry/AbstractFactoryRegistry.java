/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.Map;

import org.epsilonlabs.modelflow.dom.api.factory.IFactory;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;

public abstract class AbstractFactoryRegistry<F extends IFactory>{
	
	protected FactoryMap<F> factoryRegistry;

	public Map<String, F> getFactories() {
		return factoryRegistry.getFactories();
	}

	public F getFactory(String type) throws MFInvalidFactoryException {
		return factoryRegistry.getFactory(type);
	}

	public boolean addFactory(F factory) {
		try {
			factoryRegistry.factories.put(factory.getType(), factory);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeFactory(F factory) {
		try {
			factoryRegistry.factories.remove(factory.getType());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%s : %n\tAvailable Factories : %s", getClass().getSimpleName(),
				factoryRegistry.getFactories().keySet());
	}

}
