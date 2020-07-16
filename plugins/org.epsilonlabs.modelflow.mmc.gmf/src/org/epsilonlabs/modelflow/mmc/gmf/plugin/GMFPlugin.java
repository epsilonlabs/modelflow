package org.epsilonlabs.modelflow.mmc.gmf.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.mmc.gmf.task.GenerateDiagramCodeTask;
import org.epsilonlabs.modelflow.mmc.gmf.task.GmfMap2GmfGenTask;

public class GMFPlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.eclipse.gmf";
	}

	@Override
	public String getReadableName() {
		return "GMF";
	}

	@Override
	public String getVersion() {
		return "1.6.0";
	}

	@Override
	public List<Class<? extends IModelResourceFactory>> getResourceFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<? extends ITaskFactory>> getTaskFactories() {
		return Arrays.asList(GenerateDiagramCodeTask.Factory.class, GmfMap2GmfGenTask.Factory.class);
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
