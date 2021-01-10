package org.epsilonlabs.modelflow.mmc.gmf.factory;

import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
import org.epsilonlabs.modelflow.dom.api.IPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.AbstractTaskFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;

public abstract class AbstractGMFTaskFactory extends AbstractTaskFactory implements ITaskFactory{

	public AbstractGMFTaskFactory(Class<? extends AbstractTaskInstance> class1) {
		super(class1);
	}

	@Override
	public Class<? extends IPlugin> getPlugin() {
		return GMFPlugin.class;
	}
	
	@Override
	public String getType() {
		return "gmf:" + getName();
	}
	
	protected abstract String getName();
	
}