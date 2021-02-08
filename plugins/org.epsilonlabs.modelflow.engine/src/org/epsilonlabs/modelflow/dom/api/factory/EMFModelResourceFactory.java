/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.util.Map;
import java.util.Optional;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFResourceRule;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class EMFModelResourceFactory extends AbstractFactory<IModelResourceInstance<?>, IModelResourceNode> {

	protected IModelResource resource;	
	
	@Override
	public IModelResourceInstance<?> create(Class<? extends IModelResourceInstance<?>> factory, IModelResourceNode node, IModelFlowContext ctx) throws MFInstantiationException {
		super.create(factory, node, ctx);
		final Optional<IModelResource> optionalResource = ctx.getModule().getWorkflow().getResources().stream().filter(IModelResource.class::isInstance).filter(m->m.getName().equals(this.name)).map(IModelResource.class::cast).findFirst();
		if (optionalResource.isPresent()) {
			this.resource = optionalResource.get();
		} else {			
			throw new NullPointerException("Setup the resource from the dependency graph or scheduelr");
		}
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		instance = (IModelResourceInstance<?>) injector.getInstance(clazz);
		instance.setName(name);
		configure();
		instance.configure();
		return instance;
	}
	
	@Override
	protected IConfigurable getConfigurable(){
		return resource;
	}

	@Override
	protected ModuleElement prepareFrameStack() {
		Variable[] variables = new Variable[0];
		EMFResourceRule me = (EMFResourceRule) getConfigurable().getModuleElement();
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
