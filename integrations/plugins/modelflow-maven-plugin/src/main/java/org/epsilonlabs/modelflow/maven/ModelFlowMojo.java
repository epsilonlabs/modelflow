package org.epsilonlabs.modelflow.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.epsilonlabs.modelflow.IModelFlowConfiguration;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * See: https://maven.apache.org/developers/mojo-api-specification.html
 * @author Betty Sanchez
 */
@Mojo(name="mflow", 
		defaultPhase = LifecyclePhase.COMPILE, 
		requiresDirectInvocation = true,
		executionStrategy = "always"
		)
public class ModelFlowMojo extends AbstractMojo {

	/** The source file */
    @Parameter(required = true)
	private File src;
    
    @Parameter(required = false, alias = "end.to.end", defaultValue = "false")
	private Boolean endToEnd;
    
    @Parameter(required = false, alias = "end.to.end.location", defaultValue = "")
	private File endToEndLocation;
    
    @Parameter(required = false, alias = "output.protection", defaultValue = "false")
	private Boolean outputProtect;
    
    @Parameter(required = false, alias = "execution.trace.location", defaultValue = "")
	private File executionTraceLocation;
    
	@Override
	public void execute() throws MojoExecutionException {
		ModelFlowModule module = new ModelFlowModule();
		Injector injector = Guice.createInjector(new CorePlugin());

		module.setTaskFactoryRegistry(injector.getInstance(TaskFactoryRegistry.class));
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
