/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.exception.MFResourceInstantiationException;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResourceLoader.class);

	private final ResourceKind as;
	private final IModelResourceInstance<?> res;

	private IModelResourceNode node;
	
	public ResourceLoader(ResourceKind as, IModelResourceInstance<?> res, IModelResourceNode node){
		this.as = as;
		this.res = res;
		this.node = node;
	}
	
	public IModelWrapper load(ITaskNode taskNode) throws MFResourceInstantiationException {
		
		IModelResourceInstance<?> resource = null;
		switch (as) {
		case INPUT:
			resource = res.asInput();
			break;
		case OUTPUT:
			resource = res.asOutput();
			break;
		case INOUT:
			resource = res.asInOut();
			break;
		case TRANSIENT:
			resource = res.asTransient();
			break;
		default:
			throw new MFResourceInstantiationException("Unkown Resource Kind");	
		}
		LOG.debug("Using {} as {}", res.getName(), as);
				
		if (!resource.isLoaded()) {
			try {
				LOG.debug("Loading {}", res.getName());
				resource.load();
			} catch (Exception e) {
				throw new MFResourceInstantiationException(e);
			}
		}
		return new ModelWrapper(as, node.getModelElement(), resource.get());
	}

}
