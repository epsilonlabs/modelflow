/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.context;

import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.IScheduler;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.management.param.TaskParamManager;
import org.epsilonlabs.modelflow.management.resource.ResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.repository.TaskRepository;

/**
 * The Interface IModelFlowContext.
 */
public interface IModelFlowContext extends IErlContext{

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	@Override
	IModelFlowModule getModule();

	/*
	 * TRACES
	 */

	/**
	 * Gets the execution trace.
	 *
	 * @return the execution trace
	 */
	ExecutionTrace getExecutionTrace();

	/**
	 * Sets the execution trace.
	 *
	 * @param trace the new execution trace
	 */
	void setExecutionTrace(ExecutionTrace trace);

	/**
	 * Gets the management trace.
	 *
	 * @return the management trace
	 */
	ManagementTrace getManagementTrace();

	/**
	 * Sets the management trace.
	 *
	 * @param trace the new management trace
	 */
	void setManagementTrace(ManagementTrace trace);

	/*
	 * GRAPHS
	 */

	/**
	 * Gets the dependency graph.
	 *
	 * @return the dependency graph
	 */
	IDependencyGraph getDependencyGraph();

	/**
	 * Sets the dependency graph.
	 *
	 * @param dependencyGraph the new dependency graph
	 */
	void setDependencyGraph(IDependencyGraph dependencyGraph);

	/**
	 * Sets the execution graph.
	 *
	 * @param executionGraph the new execution graph
	 */
	void setExecutionGraph(IExecutionGraph executionGraph);

	/**
	 * Gets the execution graph.
	 *
	 * @return the execution graph
	 */
	IExecutionGraph getExecutionGraph();
	
	/**
	 * Validate.
	 *
	 * @throws MFExecutionException the MF execution exception
	 */
	void validate() throws MFExecutionException;	
	
	/* EXECUTOR */
	
	/**
	 * Gets the executor.
	 *
	 * @return the executor
	 */
	IScheduler getExecutor();
	
	/**
	 * Sets the executor.
	 *
	 * @param executor the new executor
	 */
	void setExecutor(IScheduler executor);

	/**
	 * Gets the publisher.
	 *
	 * @return the publisher
	 */
	IModelFlowPublisher getPublisher();

	/**
	 * Sets the publisher.
	 *
	 * @param publisher the new publisher
	 */
	void setPublisher(IModelFlowPublisher publisher); 	

	/*
	 * REPOSITORIES
	 */

	/**
	 * Gets the task repository.
	 *
	 * @return the task repository
	 */
	TaskRepository getTaskRepository();

	/**
	 * Sets the task repository.
	 *
	 * @param taskRegistry the new task repository
	 */
	void setTaskRepository(TaskRepository taskRegistry);

	/* MODEL MANAGER */
	
	/**
	 * Gets the resource manager.
	 *
	 * @return the resource manager
	 */
	ResourceManager getResourceManager();
	
	/**
	 * Sets the resource manager.
	 *
	 * @param modelManager the new resource manager
	 */
	void setResourceManager(ResourceManager modelManager);

	/* PARAM MANAGER */
	
	/**
	 * Gets the param manager.
	 *
	 * @return the param manager
	 */
	TaskParamManager getParamManager();

	/**
	 * Sets the param manager.
	 *
	 * @param paramManager the new param manager
	 */
	void setParamManager(TaskParamManager paramManager);
	
	/* EXECUTION */
	
	/**
	 * Checks if is end to end tracing.
	 *
	 * @return true, if is end to end tracing
	 */
	boolean isEndToEndTracing();
	
	/**
	 * Sets the end to end tracing.
	 *
	 * @param enable the new end to end tracing
	 */
	void setEndToEndTracing(Boolean enable);
	
	/**
	 * Checks if is interactive.
	 *
	 * @return true, if is interactive
	 */
	boolean isInteractive();
	
	/**
	 * Sets the interactive.
	 *
	 * @param enable the new interactive
	 */
	void setInteractive(Boolean enable);
	
	/**
	 * Checks if is protect resources.
	 *
	 * @return true, if is protect resources
	 */
	boolean isProtectResources();
	
	/**
	 * Sets the protect resources.
	 *
	 * @param enable the new protect resources
	 */
	void setProtectResources(Boolean enable);
	
	/**
	 * Set the profiler
	 * @param profiler
	 */
	void setProfiler(IModelFlowProfiler profiler);
	
	/**
	 * Get the profiler
	 * @param profiler
	 */
	IModelFlowProfiler getProfiler();

}
