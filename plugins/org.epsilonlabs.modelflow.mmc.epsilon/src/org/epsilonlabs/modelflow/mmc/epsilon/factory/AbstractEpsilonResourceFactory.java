/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.factory;

import org.epsilonlabs.modelflow.dom.api.IPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.AbstractModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.AbstractEpsilonModelResource;

public abstract class AbstractEpsilonResourceFactory extends AbstractModelResourceFactory implements IModelResourceFactory{

	public AbstractEpsilonResourceFactory(Class<? extends AbstractEpsilonModelResource> class1) {
		super(class1);
	}

	@Override
	public Class<? extends IPlugin> getPlugin() {
		return EpsilonPlugin.class;
	}
	
	@Override
	public String getType() {
		return "epsilon:" + getName();
	}
	
	protected abstract String getName();
	
	
}
