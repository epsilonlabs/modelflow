package org.epsilonlabs.gradle.internal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.artifacts.ivyservice.projectmodule.ProjectPublicationRegistry;
import org.gradle.api.internal.project.ProjectTaskLister;
import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.internal.service.scopes.AbstractPluginServiceRegistry;

// FIXME is not being called
public class EpsilonServices extends AbstractPluginServiceRegistry {

	@Override
	public void registerBuildServices(ServiceRegistration registration) {
		super.registerBuildServices(registration);
		registration.addProvider(new EpsilonBuildServices());
		registration.addProvider(new EpsilonProjectServices());
		}

	@Override
	public void registerProjectServices(ServiceRegistration registration) {
		super.registerProjectServices(registration);
		registration.addProvider(new EpsilonProjectServices());
	}
	
	@SuppressWarnings("unused")
	private static class EpsilonBuildServices {

		private Logger log = LogManager.getLogger(EpsilonBuildServices.class);

		ProjectModelRegistry createProjectModelRegistry() {
			return new DefaultProjectModelRegistry();
		}

		protected BuildScopeModelRepositoryRegistryAction createIdeBuildScopeToolingModelBuilderRegistryAction(
				final ProjectTaskLister taskLister,
				final ProjectPublicationRegistry projectPublicationRegistry,
				final ServiceRegistry services) {

			return new BuildScopeModelRepositoryRegistryAction() {
				@Override
				public void execute(ProjectModelRegistry registry) {
					// TODO
				}
			};
		}
	}
	
	@SuppressWarnings("unused")
	private static class EpsilonProjectServices {
		
		private Logger log = LogManager.getLogger(EpsilonProjectServices.class);

		public ModelRepository createProjectModelRegistry(GradleInternal gradle) {
			log.info("createProjectModelRegistry");
			return new ModelRepository();
		}
			
		public ExtendedProperties createExtendedProperties() {
			log.info("createExtendedProperties");
			return new ExtendedProperties();
		}	
		
		public Frame getProjectStackFrame() {
			log.info("getProjectStackFrame");
			return new SingleFrame(FrameType.PROTECTED, null);
		}
		
	}

}