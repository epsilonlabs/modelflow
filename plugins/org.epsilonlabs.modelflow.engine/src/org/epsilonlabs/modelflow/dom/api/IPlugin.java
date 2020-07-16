/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.util.List;

import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPlugin.
 */
public interface IPlugin {

	/**
	 * Plugin Fully Qualified Name.
	 *
	 * @return the string
	 */
	String setFullyQualifiedId();
	
	/**
	 * Plugin Readable Name.
	 *
	 * @return the readable name
	 */
	String getReadableName();
	
	/**
	 * Plugin Version.
	 *
	 * @return the version
	 */
	String getVersion();
	
	/**
	 * Resource Factories.
	 *
	 * @return the resource factories
	 */
	List<Class<? extends IModelResourceFactory>> getResourceFactories();
	
	/**
	 * Task Factories.
	 *
	 * @return the task factories
	 */
	List<Class<? extends ITaskFactory>> getTaskFactories();
	
	/**
	 * Gets the resource interfaces.
	 *
	 * @return the resource interfaces
	 */
	List<Class<?>> getResourceInterfaces();

}
