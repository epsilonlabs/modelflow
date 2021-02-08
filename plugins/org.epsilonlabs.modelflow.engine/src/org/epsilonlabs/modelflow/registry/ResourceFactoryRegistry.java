/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.Set;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.dom.api.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;

import com.google.inject.Inject;

@SuppressWarnings("unchecked")
public class ResourceFactoryRegistry extends AbstractFactoryRegistry<IModelResourceInstance<?>>{
	
	@Inject
	public ResourceFactoryRegistry(Set<IModelResourceFactory> resourceFactory) {
		super(resourceFactory.stream()
				.map(f->(Class<IModelResourceInstance<?>>)f.getFactoryClass())
				.collect(Collectors.toSet()));
	}

}