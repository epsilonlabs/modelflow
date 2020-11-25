/**
 * 
 */
package org.epsilonlabs.modelflow.maven;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.maven.Maven;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ResolutionErrorHandler;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.repository.RepositorySystem;
import org.codehaus.plexus.PlexusContainer;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowMojoTest extends AbstractMojoTestCase {

	private ModelFlowMojo mojo;
	protected Maven maven;
	private MavenExecutionRequestPopulator requestPopulator;

	@Override
	protected void setUp() throws Exception {
		this.maven = lookup(Maven.class);
		this.requestPopulator = lookup(MavenExecutionRequestPopulator.class);
		this.mojo = new ModelFlowMojo();
		this.mojo.repositorySystem = lookup(RepositorySystem.class);
		this.mojo.resolutionErrorHandler = lookup(ResolutionErrorHandler.class);
		
		final PlexusContainer container = getContainer();
		
	}

	public void testUnitExecution() throws Exception {
		this.mojo.endToEnd = false;
		this.mojo.src = Paths.get(System.getProperty("user.dir"),"src/test/resources/org/epsilonlabs/modelflow/maven/test.mflow").toFile().getAbsoluteFile();
		this.mojo.execute();
	}
	
	public void testIntegrationExecution() throws Exception {
		final Path path = Paths.get(System.getProperty("user.dir"), "../../tests/modelflow-maven-plugin-test/pom.xml").normalize();
		final File pom = path.toFile();
		try {
			final MavenExecutionResult execute = execute(pom, "compile");
			if (execute.hasExceptions()) {
				execute.getExceptions().forEach(Throwable::printStackTrace);
				fail("There are execution errors");			
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public MavenExecutionResult execute(File pom, String... goals) throws Exception {
		if (!pom.exists()) {
			fail("Pom does not exist");
		}
		
		Properties systemProps = new Properties();
		systemProps.putAll(System.getProperties());
		Properties userProps = new Properties();
		userProps.put("tycho-version", "0.0.0");

		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		request.setBaseDirectory(pom.getParentFile());
		request.setPom(pom);
		request.setSystemProperties(systemProps);
		request.setUserProperties(userProps);
		request.setLocalRepository(getLocalRepository());
		request.setStartTime(new Date());

		requestPopulator.populateDefaults(request);
		request.setGoals(Arrays.asList(goals));
		request.setLocalRepositoryPath(getLocalRepository().getBasedir());

		// Execution
		request.getProjectBuildingRequest().setProcessPlugins(true);
		request.setLocalRepository(getLocalRepository());
		
		/*if (platform != null) {
			request.getUserProperties().put("tycho.targetPlatform", platform.getAbsolutePath());
		}*/
		
		return maven.execute(request);
	}

	protected ArtifactRepository getLocalRepository() throws Exception {
		final Path localRepo = Paths.get(System.getProperty("user.home")).resolve(".m2/repository/").normalize();
		final Path portableRepo = Paths.get("target/local-repo");
		final Path repo = localRepo;
		
		RepositorySystem repoSystem = lookup(RepositorySystem.class);
		File repoPath = repo.toFile().getAbsoluteFile();
		ArtifactRepository r = repoSystem.createLocalRepository(repoPath);
		return r;
	}

}
