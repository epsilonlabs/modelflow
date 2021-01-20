/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.ast.ResourceRule;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class ResourceFactory extends AbstractFactory {

	protected final IModelResourceNode node;
	protected final String name;
	protected IModelResource resource;
	protected IModelResourceInstance<?> iResource;
	
	public ResourceFactory(Class<? extends IModelResourceInstance<?>> factory, IModelResourceNode node, String name, IModelFlowContext ctx) {
		super(ctx, factory);
		this.node = node;
		this.resource = node.getInternal();
		this.name = name;
	}
	
	public IModelResourceInstance<?> create() throws MFInstantiationException {
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		iResource = (IModelResourceInstance<?>) injector.getInstance(clazz);
		iResource.setName(name);
		configure();
		iResource.configure(node);
		return iResource;
	}
	
	@Override
	protected Object getIObject() {
		return iResource;
	}
	
	@Override
	protected IConfigurable getConfigurable(){
		return resource;
	}

	@Override
	protected ModuleElement prepareFrameStack() {
		Variable[] variables = new Variable[0];
		ResourceRule me = (ResourceRule) getConfigurable().getModuleElement();
		/* if (me.isGenerator()) {
			variables = me.getVars(getConfigurable().getName());
		} */
		ctx.getFrameStack().enterLocal(FrameType.PROTECTED, me, variables);
		return me;
	}
	
	@Override
	protected Object handleMapProperty(Map<?,?> mapVal) {
		return mapVal;
	}
	
	@Override
	protected Object handleStringProperty(String value) {
		return value;
	}
		
}
