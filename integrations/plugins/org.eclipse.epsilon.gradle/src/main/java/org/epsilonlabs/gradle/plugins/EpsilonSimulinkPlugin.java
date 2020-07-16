package org.epsilonlabs.gradle.plugins;

import javax.inject.Inject;

import org.epsilonlabs.gradle.artifact.DefaultSimulinkModel;
import org.epsilonlabs.gradle.artifact.DefaultSimulinkRuntimeModel;
import org.epsilonlabs.gradle.artifact.SimulinkModel;
import org.epsilonlabs.gradle.artifact.SimulinkRuntimeModel;
import org.epsilonlabs.gradle.artifact.internal.ModelFactory;
import org.epsilonlabs.gradle.artifact.internal.SimulinkIModelFactory;
import org.epsilonlabs.gradle.extensions.DefaultSimulinkEpsilonConfiguration;
import org.epsilonlabs.gradle.extensions.EpsilonExtension;
import org.epsilonlabs.gradle.tasks.AbstractEpsilonTask;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;
import org.gradle.api.internal.artifacts.configurations.DependencyMetaDataProvider;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.internal.reflect.Instantiator;

public class EpsilonSimulinkPlugin extends AbstractEpsilonPlugin {

	private final Instantiator instantiator;
	private final ObjectFactory objectFactory;

	@Inject
	public EpsilonSimulinkPlugin(Instantiator instantiator, ObjectFactory objectFactory,
			DependencyMetaDataProvider dependencyMetaDataProvider, FileResolver fileResolver,
			ImmutableAttributesFactory immutableAttributesFactory,
			ProviderFactory providerFactory) {
		this.instantiator = instantiator;
		this.objectFactory = objectFactory;
	}

	@Override
	public void apply(Project project) {
		super.apply(project);
		
		// Ensure Epsilon Plugin is active
		project.getPluginManager().apply(EpsilonCorePlugin.class);
		
		// Remove the need for "import" statements in buidl
		project.getExtensions().getExtraProperties().set(SimulinkModel.ID, SimulinkModel.class);
		//project.getExtensions().getExtraProperties().set(SimulinkRuntimeModel.ID, SimulinkRuntimeModel.class);

		// Add Simulink Configuration in Epsilon Plugin Settings
		project.getExtensions().getByType(EpsilonExtension.class).getConfig()
				.add(new DefaultSimulinkEpsilonConfiguration(objectFactory));

		// Enable Simulink Model settings in Epsilon models
		project.getExtensions().configure(EpsilonExtension.class, new Action<EpsilonExtension>() {
			@Override
			public void execute(EpsilonExtension models) {
				models.getModels().registerFactory(SimulinkModel.class,
						new SimulinkModelFactory(instantiator));
			}
		});

		/* Enable support for Simulink Models in Epsilon Tasks 
		project.getTasks().withType(AbstractEpsilonTask.class, new Action<AbstractEpsilonTask>() {
			@Override
			public void execute(AbstractEpsilonTask task) {
				task.getModels().registerFactory(SimulinkRuntimeModel.class,
						new SimulinkRuntimeModelFactory(instantiator));
			};
		});*/
		
		// Provide Simulink Model factory from task and epsilon global information 
		//ModelFactory.getInstance().register(SimulinkRuntimeModel.class, SimulinkIModelFactory.class);
	}

	private class SimulinkModelFactory implements NamedDomainObjectFactory<SimulinkModel> {
		private final Instantiator instantiator;

		private SimulinkModelFactory(Instantiator instantiator) {
			super();
			this.instantiator = instantiator;
		}

		public SimulinkModel create(final String name) {
			return instantiator.newInstance(DefaultSimulinkModel.class, name, instantiator,
					objectFactory);
		}

	}

	private class SimulinkRuntimeModelFactory
			implements NamedDomainObjectFactory<SimulinkRuntimeModel> {
		private final Instantiator instantiator;

		private SimulinkRuntimeModelFactory(Instantiator instantiator) {
			super();
			this.instantiator = instantiator;
		}

		public SimulinkRuntimeModel create(final String name) {
			return instantiator.newInstance(DefaultSimulinkRuntimeModel.class, name, objectFactory);
		}

	}

}