/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.factory;

import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.IPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.AbstractTaskFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;

public abstract class AbstractCoreTaskFactory extends AbstractTaskFactory implements ITaskFactory{

	public AbstractCoreTaskFactory(Class<? extends AbstractTask> class1) {
		super(class1);
	}

	@Override
	public Class<? extends IPlugin> getPlugin() {
		return CorePlugin.class;
	}
	
	@Override
	public String getType() {
		return "core:" + getName();
	}
	
	protected abstract String getName();
	
}