package org.epsilonlabs.modelflow.tests.common;

import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.epsilonlabs.modelflow.Setup;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class ExtensionPointTests {

	protected Map<String, ITaskFactory> taskFactories;
	protected Map<String, IModelResourceFactory> resourceFactories;
	
	@Before
	public void retrieveExtensionPoints() {
		Injector injector;
		if (Platform.isRunning()) {			
			Setup setup = Setup.getInstance();
			setup.init();
			injector = Guice.createInjector(setup.getPluginExtensions());
		} else {
			injector = Guice.createInjector(getPlugin());
		}
		taskFactories = injector.getInstance(TaskFactoryRegistry.class).getFactories();
		resourceFactories = injector.getInstance(ResourceFactoryRegistry.class).getFactories();
	}
	
	@Test
	public void testAvailableTasks() {
		expectedTasks();
	}
	
	@Test
	public void testAvailableResourceTypes() {
		expectedResources();
	}
	
	
	protected abstract void expectedTasks();
	protected abstract void expectedResources();
	protected abstract Module getPlugin();	
	
}
