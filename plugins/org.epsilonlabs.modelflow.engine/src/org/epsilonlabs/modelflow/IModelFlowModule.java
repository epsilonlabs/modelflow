/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import org.eclipse.epsilon.erl.IErlModule;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

/**
 * The Interface IModelFlowModule.
 */
public interface IModelFlowModule extends IErlModule {

	/**
	 * Gets the workflow.
	 *
	 * @return the workflow
	 */
	IWorkflow getWorkflow();
	
	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	@Override
	default IModelFlowContext getContext() {
		return this.getContext();
	}
	
	/**
	 * Gets the compilation context.
	 *
	 * @return the compilation context
	 */
	@Override
	IModelFlowCompilationContext getCompilationContext();

	/**
	 * Clear cache.
	 */
	void clearCache();

	/**
	 * Sets the res factory registry.
	 *
	 * @param resFactoryRegistry the new res factory registry
	 */
	void setResFactoryRegistry(ResourceFactoryRegistry resFactoryRegistry);

	/**
	 * Gets the res factory registry.
	 *
	 * @return the res factory registry
	 */
	ResourceFactoryRegistry getResFactoryRegistry();

	/**
	 * Sets the task factory registry.
	 *
	 * @param taskFactoryRegistry the new task factory registry
	 */
	void setTaskFactoryRegistry(TaskFactoryRegistry taskFactoryRegistry);

	/**
	 * Gets the task factory registry.
	 *
	 * @return the task factory registry
	 */
	TaskFactoryRegistry getTaskFactoryRegistry();
	
	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	String getIdentifier();
	
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	IModelFlowConfiguration getConfiguration();

}
