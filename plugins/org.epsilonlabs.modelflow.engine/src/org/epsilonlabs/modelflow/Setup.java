/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Setup {

	private static final Logger LOG = LoggerFactory.getLogger(Setup.class);

	public static final String TASK_EP_ID = "org.epsilonlabs.modelflow.engine.taskExtension";
	public static final String TASK_EP_ATTRIBUTE = "TaskFactoryModule";

	public static final String PLUGIN_EP_ID = "org.epsilonlabs.modelflow.engine.pluginExtension";
	public static final String PLUGIN_EP_ATTRIBUTE = "PluginModule";

	public static final String RESOURCE_EP_ID = "org.epsilonlabs.modelflow.engine.resourceExtension";
	public static final String RESOURCE_EP_ATTRIBUTE = "ResourceFactoryModule";

	protected TaskFactoryRegistry taskFactoryRegistry;
	protected ResourceFactoryRegistry resourceFactoryRegistry;
	protected List<Module> resourceExtensions;
	protected List<Module> taskExtensions;
	protected List<Module> pluginExtensions;

	private static Setup instance = null;

	public static Setup getInstance() {
		if (instance == null) {
			instance = new Setup();
		}
		return instance;
	}

	private Setup() {}
	
	public void init(){
		pluginExtensions = Setup.getExtensions(PLUGIN_EP_ID, PLUGIN_EP_ATTRIBUTE);
		Injector injector = Guice.createInjector(pluginExtensions);
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resourceFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
		taskExtensions = Setup.getExtensions(TASK_EP_ID, TASK_EP_ATTRIBUTE);
		resourceExtensions = Setup.getExtensions(RESOURCE_EP_ID,RESOURCE_EP_ATTRIBUTE); 
	}

	public List<Module> getTaskExtensions() {
		return taskExtensions;
	}
	public List<Module> getResourceExtensions() {
		return resourceExtensions;
	}

	public List<Module> getPluginExtensions() {
		return pluginExtensions;
	}

	public ResourceFactoryRegistry getResourceFactoryRegistry() {
		return resourceFactoryRegistry;
	}

	public TaskFactoryRegistry getTaskFactoryRegistry() {
		return taskFactoryRegistry;
	}

	public static List<Module> getExtensions(String id, String attribute) {
		final List<Module> exts = new ArrayList<>();
		if (Platform.isRunning()) {
			IConfigurationElement[] configelements = Platform.getExtensionRegistry().getConfigurationElementsFor(id);
			for (IConfigurationElement i : configelements) {
				try {
					exts.add((Module) i.createExecutableExtension(attribute));
				} catch (CoreException e) {
					LOG.error("Unable to find Extension", e);
				}
			}
		} else {
//			try {
//				ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//				ClassPath from = ClassPath.from(classLoader);
//				from.getAllClasses().forEach(c -> {
//					try {
//						Class<?> clazz = c.load();
//						if (clazz.isAssignableFrom(Module.class)) {
//							if (id == PLUGIN_EP_ID && clazz.isAssignableFrom(IPlugin.class)) {
//								try {
//									Module newInstance = (Module) clazz.newInstance();
//									exts.add(newInstance);
//								} catch (Exception e) {
//								}
//							} else if (id == TASK_EP_ID && clazz.isAssignableFrom(ITask.class)) {
//								try {
//									Module newInstance = (Module) clazz.newInstance();
//									exts.add(newInstance);
//								} catch (Exception e) {
//								}
//							} else if (id == RESOURCE_EP_ID && clazz.isAssignableFrom(IResource.class)) {
//								try {
//									Module newInstance = (Module) clazz.newInstance();
//									exts.add(newInstance);
//								} catch (Exception e) {
//								}
//							}
//						}
//					} catch (IllegalStateException e) {
//					} catch (NoClassDefFoundError e) {
//					} catch (RuntimeException e) {
//					} catch (VerifyError e) {
//					} catch (Error e) {
//					}
//
//				});
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}

		}
		return exts;
	}
}