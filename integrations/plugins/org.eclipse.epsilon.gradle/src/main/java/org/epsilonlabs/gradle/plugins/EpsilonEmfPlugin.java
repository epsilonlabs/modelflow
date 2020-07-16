package org.epsilonlabs.gradle.plugins;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.epsilonlabs.gradle.artifact.DefaultEmfModel;
import org.epsilonlabs.gradle.artifact.DefaultEmfRuntimeModel;
import org.epsilonlabs.gradle.artifact.EmfModel;
import org.epsilonlabs.gradle.artifact.EmfRuntimeModel;
import org.epsilonlabs.gradle.artifact.internal.EmfIModelFactory;
import org.epsilonlabs.gradle.artifact.internal.ModelFactory;
import org.epsilonlabs.gradle.extensions.EpsilonExtension;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;
import org.gradle.api.internal.artifacts.configurations.DependencyMetaDataProvider;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.internal.reflect.Instantiator;

public class EpsilonEmfPlugin extends AbstractEpsilonPlugin {

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EpsilonEmfPlugin.class);

	private final Instantiator instantiator;
	private final ObjectFactory objectFactory;
	
	@Inject
	public EpsilonEmfPlugin(Instantiator instantiator, ObjectFactory objectFactory,
			DependencyMetaDataProvider dependencyMetaDataProvider, FileResolver fileResolver,
			ImmutableAttributesFactory immutableAttributesFactory, ProviderFactory providerFactory) {
		this.instantiator = instantiator;
		this.objectFactory = objectFactory;
	}

	@Override
	public void apply(Project project) {
		super.apply(project);
		
		project.getPluginManager().apply(EpsilonCorePlugin.class);
		
		project.getExtensions().getExtraProperties().set(EmfModel.ID, EmfModel.class);
		//project.getExtensions().getExtraProperties().set(EmfRuntimeModel.ID, EmfRuntimeModel.class);
		
		project.getExtensions().configure(EpsilonExtension.class, new Action<EpsilonExtension>() {
			@Override
			public void execute(EpsilonExtension models) {
				models.getModels().registerFactory(EmfModel.class, new EmfModelFactory(instantiator));		
			}
		});
		
		/*
		project.getTasks().withType(AbstractEpsilonTask.class, new Action<AbstractEpsilonTask>() {
			@Override
			public void execute(AbstractEpsilonTask task) {
				task.getModels().registerFactory(
						EmfRuntimeModel.class, new EmfRuntimeModelFactory(instantiator));
			};
		});*/
		
		//ModelFactory.getInstance().register(EmfRuntimeModel.class, EmfIModelFactory.class);
		ModelFactory.getInstance().register(EmfModel.class, EmfIModelFactory.class);
	}

	private class EmfModelFactory implements NamedDomainObjectFactory<EmfModel> {
		private final Instantiator instantiator;

		private EmfModelFactory(Instantiator instantiator) {
			super();
			this.instantiator = instantiator;
		}

		public EmfModel create(final String name) {
			return instantiator.newInstance(DefaultEmfModel.class, name, objectFactory);
		}
	}

	private class EmfRuntimeModelFactory implements NamedDomainObjectFactory<EmfRuntimeModel> {
		private final Instantiator instantiator;

		private EmfRuntimeModelFactory(Instantiator instantiator) {
			super();
			this.instantiator = instantiator;
		}

		public EmfRuntimeModel create(final String name) {
			return instantiator.newInstance(DefaultEmfRuntimeModel.class, name, objectFactory);
		}

	}
}