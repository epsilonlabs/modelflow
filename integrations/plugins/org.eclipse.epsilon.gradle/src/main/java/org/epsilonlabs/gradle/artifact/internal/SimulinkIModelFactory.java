package org.epsilonlabs.gradle.artifact.internal;

import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.RuntimeModel;
import org.epsilonlabs.gradle.artifact.SimulinkModel;
import org.epsilonlabs.gradle.artifact.SimulinkRuntimeModel;
import org.epsilonlabs.gradle.extensions.EpsilonExtension;
import org.epsilonlabs.gradle.extensions.SimulinkEpsilonConfiguration;
import org.gradle.api.Action;
import org.gradle.api.Project;

public class SimulinkIModelFactory implements Factory {

	@Override
	public IModel get(Model _model, RuntimeModel _runtime, Project p) {
		assert (_model.getName().equals(_runtime.getName()));
		assert (_model.getClass().equals(SimulinkModel.class));
		assert (_runtime.getClass().equals(SimulinkRuntimeModel.class));

		SimulinkModel model = (SimulinkModel) _model;
		SimulinkRuntimeModel runtime = (SimulinkRuntimeModel) _runtime;
/*
		org.eclipse.epsilon.emc.simulink.model.SimulinkModel m = new org.eclipse.epsilon.emc.simulink.model.SimulinkModel();
		m.getAliases().add(runtime.getAlias());
		m.setCachingEnabled(runtime.getIsCachingEnabled());
		m.setReadOnLoad(runtime.getReadOnLoad());
		m.setStoredOnDisposal(runtime.getStoreOnDisposal());
		m.setName(model.getName());
		m.setFile(model.getModelFile());
		m.setWorkingDir(model.getWorkingDir());
		m.setFollowLinks(model.getFollowLinks());

		EpsilonExtension epsilon = p.getExtensions().getByType(EpsilonExtension.class);
		epsilon.getConfig().withType(SimulinkEpsilonConfiguration.class, new Action<SimulinkEpsilonConfiguration>() {
			@Override
			public void execute(SimulinkEpsilonConfiguration config) {
				m.setLibraryPath(config.getLibraryPath().getAbsolutePath());
				m.setEngineJarPath(config.getEngineJar().getAbsolutePath());
				m.setShowInMatlabEditor(config.getHiddenEditor());
			}
		});
					
		return m;*/
		return null;
	}

	@Override
	public IModel get(Model model, Project project) {
		// TODO Auto-generated method stub
		return null;
	}

}