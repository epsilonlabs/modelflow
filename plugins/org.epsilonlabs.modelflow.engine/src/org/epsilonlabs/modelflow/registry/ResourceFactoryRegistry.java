/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.Set;

import org.epsilonlabs.modelflow.dom.ModelResource;
import org.epsilonlabs.modelflow.dom.Resource;
import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.exception.MFResourceInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

import com.google.inject.Inject;

public class ResourceFactoryRegistry extends AbstractFactoryRegistry<IModelResourceFactory>{
	
	@Inject
	public ResourceFactoryRegistry(Set<IModelResourceFactory> resourceFactory) {
		factoryRegistry = new FactoryMap<IModelResourceFactory>(resourceFactory);
	}
	
	protected IModelResourceFactory getFactory(ModelResource r) throws MFInvalidFactoryException {
		return super.getFactory(r.getDefinition());
	}
	
	protected IModelResourceFactory getFactory(IModelResourceNode r) throws MFInvalidFactoryException {
		return this.getFactory(r.getInternal());	
	}
	
	public IResource<?> create(IModelResourceNode res, IModelFlowContext ctx) throws MFRuntimeException {
			Resource r = res.getInternal();
			IModelResourceFactory factory;
			try {
				factory = getFactory(r.getDefinition());
			} catch (MFInvalidFactoryException e) {
				throw new MFResourceInstantiationException(e);
			}
			return factory.instantiate(res, res.getName(), ctx);
		
	}
	
}