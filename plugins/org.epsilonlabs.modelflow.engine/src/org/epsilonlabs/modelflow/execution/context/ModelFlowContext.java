/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.context;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.erl.execute.context.ErlContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.IScheduler;
import org.epsilonlabs.modelflow.execution.IPublisher;
import org.epsilonlabs.modelflow.execution.Publisher;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.management.param.TaskParamManager;
import org.epsilonlabs.modelflow.management.resource.ResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.repository.TaskRepository;

public class ModelFlowContext extends ErlContext implements IModelFlowContext {

	protected IDependencyGraph dependencyGraph;
	protected IExecutionGraph executionGraph;
	protected IScheduler executor;
	protected IPublisher publisher = new Publisher();
	
	protected ExecutionTrace executionTrace;
	protected ManagementTrace managementTrace;
	
	protected TaskRepository taskRepository;
	protected ResourceManager modelManager;	
	protected TaskParamManager paramManager;	
	
	protected Boolean endToEndTracing = true;
	protected Boolean interactive = true;
	protected Boolean protectResources = true;
	
	@Override
	public IModelFlowModule getModule() {
		return (IModelFlowModule) this.module;
	}

	@Override
	public void setModule(IModule module) {
		if (module instanceof IModelFlowModule) {
			super.setModule(module);
		}
	}
	
	@Override
	public ExecutionTrace getExecutionTrace() {
		return this.executionTrace;
	}

	@Override
	public void setExecutionTrace(ExecutionTrace trace) {
		this.executionTrace = trace;
	}
	
	@Override
	public ManagementTrace getManagementTrace() {
		return this.managementTrace;
	}

	@Override
	public void setManagementTrace(ManagementTrace trace) {
		this.managementTrace = trace;
	}

	@Override
	public IDependencyGraph getDependencyGraph() {
		return dependencyGraph;
	}

	@Override
	public void setDependencyGraph(IDependencyGraph dependencyGraph) {
		this.dependencyGraph = dependencyGraph;
	}

	@Override
	public IExecutionGraph getExecutionGraph() {
		return executionGraph;
	}

	@Override
	public void setExecutionGraph(IExecutionGraph executionGraph) {
		this.executionGraph = executionGraph;
	}

	@Override
	public TaskRepository getTaskRepository() {
		return this.taskRepository;
	}

	@Override
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@Override
	public ResourceManager getResourceManager() {
		return modelManager;
	}
	
	@Override
	public void setResourceManager(ResourceManager modelManager) {
		this.modelManager = modelManager;
	}
	
	@Override
	public void setExecutor(IScheduler executor) {
		this.executor = executor;
	}
	
	@Override
	public IScheduler getExecutor() {
		return this.executor;
	}
	
	@Override
	public TaskParamManager getParamManager() {
		return paramManager;
	}
	
	@Override
	public void setParamManager(TaskParamManager paramManager) {
		this.paramManager = paramManager;
	}
	
	@Override
	public IPublisher getPublisher() {
		return publisher;
	}
	
	@Override
	public void setPublisher(IPublisher publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public boolean isEndToEndTracing() {
		return endToEndTracing;
	}

	@Override
	public void setEndToEndTracing(Boolean enabled) {
		endToEndTracing = enabled;
	}

	@Override
	public boolean isInteractive() {
		return interactive;
	}

	@Override
	public void setInteractive(Boolean enabled) {
		interactive = enabled;
	}

	@Override
	public boolean isProtectResources() {
		return protectResources;
	}

	@Override
	public void setProtectResources(Boolean protect) {
		protectResources = protect;
	}

	@Override
	public void validate() throws MFExecutionException {
		TaskRepository repo = getTaskRepository();
		Set<ITaskNode> unknownTaskTypes = getExecutionGraph().getTasks().stream().filter(t->!repo.hasFactory(t)).collect(Collectors.toSet());
		if (!unknownTaskTypes.isEmpty()) {
			String msg ="These tasks types are unknown: %s";
			Set<String> unknowns = unknownTaskTypes.stream().map(n->n.getTaskDefinition().getDefinition()).collect(Collectors.toSet());
			throw new MFExecutionException(String.format(msg, unknowns));
		}
	}
	
	/** Managing inherited methods from parent contexts 
	
	@Override
	public ModelRepository getModelRepository() {}
	
	@Override
	public void setModelRepository(ModelRepository modelRepository) {}
	*/
	
}