package org.epsilonlabs.modelflow.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.Setup;

/**
 * See: https://maven.apache.org/developers/mojo-api-specification.html
 * 
 * @author Betty Sanchez
 */
@Mojo(name = "mflow", defaultPhase = LifecyclePhase.COMPILE, requiresDirectInvocation = true, executionStrategy = "always")
public class ModelFlowMojo extends AbstractMojo {
	
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

		final Setup setup = Setup.getInstance();
		setup.init();
		
		ModelFlowModule module = new ModelFlowModule();
		module.setTaskFactoryRegistry(setup.getTaskFactoryRegistry());
		module.setResFactoryRegistry(setup.getResourceFactoryRegistry());
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

}
