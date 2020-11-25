package org.epsilonlabs.modelflow.maven;

import java.io.File;

import org.apache.maven.artifact.resolver.ResolutionErrorHandler;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.repository.RepositorySystem;
import org.codehaus.plexus.component.annotations.Requirement;
import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * See: https://maven.apache.org/developers/mojo-api-specification.html
 * 
 * @author Betty Sanchez
 */
@Mojo(name = "mflow", defaultPhase = LifecyclePhase.COMPILE, requiresDirectInvocation = true, executionStrategy = "always")
public class ModelFlowMojo extends AbstractMojo {

	@Requirement
	RepositorySystem repositorySystem;

	@Requirement
	ResolutionErrorHandler resolutionErrorHandler;

	@Parameter(property = "session", readonly = true)
	MavenSession session;

	// @Component
	// private EquinoxServiceFactory equinox;

	// @Parameter(required = false)
	// private String[] dependencies;

	/** The source file */
	@Parameter(required = true)
	File src;

	@Parameter(required = false, alias = "end.to.end", defaultValue = "false")
	Boolean endToEnd;

	@Parameter(required = false, alias = "end.to.end.location")
	File endToEndLocation;

	@Parameter(required = false, alias = "output.protection", defaultValue = "false")
	Boolean outputProtect;

	@Parameter(required = false, alias = "execution.trace.location")
	File executionTraceLocation;

	@Override
	public void execute() throws MojoExecutionException {
		/*
		 * try { processDependences(); } catch (MavenExecutionException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		ModelFlowModule module = new ModelFlowModule();
		Injector injector = Guice.createInjector(new CorePlugin());

		module.setTaskFactoryRegistry(injector.getInstance(TaskFactoryRegistry.class));
		module.setResFactoryRegistry(injector.getInstance(ResourceFactoryRegistry.class));
		IModelFlowConfiguration configuration = module.getConfiguration();
		configuration.setSaveEndToEndTraces(endToEnd);
		module.getContext().setEndToEndTracing(endToEnd);
		module.getContext().setInteractive(false);
		try {
			module.parse(src);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
		try {
			module.execute();
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	// @Requirement FileLockService fileLockService;

	/*
	 * @Requirement private LegacySupport buildContext;
	 * 
	 * 
	 * @Requirement private Map<String, TychoOsgiRuntimeArtifacts> runtimeArtifacts;
	 * 
	 * @Requirement private DevWorkspaceResolver workspaceState;
	 * 
	 * private void processDependences() throws MavenExecutionException { Dependency
	 * dependencyPom = new Dependency(); dependencyPom.setType("pom");
	 * dependencyPom.setGroupId("org.epsilonlabs.modelflow");
	 * dependencyPom.setArtifactId("org.epsilonlabs.modelflow.epsilon");
	 * dependencyPom.setVersion("1.0.0");
	 * 
	 * Artifact pom = resolveDependency(buildContext.getSession(), dependencyPom); }
	 * 
	 * private Artifact resolveDependency(MavenSession session, Dependency
	 * dependency) throws MavenExecutionException { Artifact artifact =
	 * repositorySystem.createArtifact(dependency.getGroupId(),
	 * dependency.getArtifactId(), dependency.getVersion(), dependency.getType());
	 * 
	 * ArtifactResolutionRequest request = new ArtifactResolutionRequest();
	 * request.setArtifact(artifact);
	 * request.setResolveRoot(true).setResolveTransitively(false);
	 * request.setLocalRepository(session.getLocalRepository());
	 * request.setRemoteRepositories(getPluginRepositories(session));
	 * request.setCache(session.getRepositoryCache());
	 * request.setOffline(session.isOffline());
	 * request.setProxies(session.getSettings().getProxies());
	 * request.setForceUpdate(session.getRequest().isUpdateSnapshots());
	 * ArtifactResolutionResult result = repositorySystem.resolve(request);
	 * 
	 * try { resolutionErrorHandler.throwErrors(request, result); } catch
	 * (ArtifactResolutionException e) { throw new
	 * MavenExecutionException("Could not resolve artifact for Tycho's OSGi runtime"
	 * , e); }
	 * 
	 * return artifact; }
	 * 
	 * protected List<ArtifactRepository> getPluginRepositories(MavenSession
	 * session) { List<ArtifactRepository> repositories = new ArrayList<>(); for
	 * (MavenProject project : session.getProjects()) {
	 * repositories.addAll(project.getPluginArtifactRepositories()); } return
	 * repositorySystem.getEffectiveRepositories(repositories); }
	 */
}
