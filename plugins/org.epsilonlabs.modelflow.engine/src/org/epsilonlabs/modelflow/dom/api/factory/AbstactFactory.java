/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.util.Collection;
import java.util.Set;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstactFactory implements IFactory {
	
	protected Set<String> parameters;

	@Override
	public Collection<String> getParameters() {
		if (this.parameters == null) {
			this.parameters = new FactoryIntrospector(getInstanceClass()).getParameterNames();
		}
		return this.parameters;
	}	

}
