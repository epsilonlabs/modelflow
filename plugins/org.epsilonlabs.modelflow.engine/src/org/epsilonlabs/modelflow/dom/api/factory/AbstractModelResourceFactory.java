/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

public abstract class AbstractModelResourceFactory extends AbstactFactory implements IModelResourceFactory {

	private Class<? extends IResource<?>> resourceClass;

	public AbstractModelResourceFactory(Class<? extends IResource<?>> clazz) {
		resourceClass = clazz;
	}
	
	@Override
	public Class<? extends IResource<?>> getInstanceClass(){
		return resourceClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IResource<?> create(IModelResourceNode node, String name, IModelFlowContext ctx) throws MFInstantiationException {
		return new ResourceFactoryImpl(this, node, name, ctx).create();
	}
	
}
