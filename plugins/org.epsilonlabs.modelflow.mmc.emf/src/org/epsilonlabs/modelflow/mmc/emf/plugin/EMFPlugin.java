package org.epsilonlabs.modelflow.mmc.emf.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.mmc.emf.task.Emfatic2EcoreTask;
import org.epsilonlabs.modelflow.mmc.emf.task.GenerateModelCodeTask;

public class EMFPlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.eclipse.gmf";
	}

	@Override
	public String getReadableName() {
		return "EMF";
	}

	@Override
	public String getVersion() {
		return "1.6.0";
	}

	@Override
	public List<Class<? extends IModelResourceInstance<?>>> getResourceFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<? extends ITaskInstance>> getTaskFactories() {
		return Arrays.asList(Emfatic2EcoreTask.class, GenerateModelCodeTask.class);
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
