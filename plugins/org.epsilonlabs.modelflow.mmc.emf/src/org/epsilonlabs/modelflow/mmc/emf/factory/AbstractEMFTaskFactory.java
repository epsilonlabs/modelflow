package org.epsilonlabs.modelflow.mmc.emf.factory;

import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
import org.epsilonlabs.modelflow.dom.api.IPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.AbstractTaskFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;

public abstract class AbstractEMFTaskFactory extends AbstractTaskFactory implements ITaskFactory{

	public AbstractEMFTaskFactory(Class<? extends AbstractTaskInstance> class1) {
		super(class1);
	}

	@Override
	public Class<? extends IPlugin> getPlugin() {
		return EMFPlugin.class;
	}
	
	@Override
	public String getType() {
		return "emf:" + getName();
	}
	
	protected abstract String getName();
	
}