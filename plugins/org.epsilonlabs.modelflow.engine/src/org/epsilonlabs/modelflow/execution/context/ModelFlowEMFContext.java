/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.context;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.erl.execute.context.ErlContext;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.Publisher;
import org.epsilonlabs.modelflow.execution.control.DefaultModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.control.IModelFlowProfiler;
import org.epsilonlabs.modelflow.execution.control.ModelFlowExecutionProfiler;
import org.epsilonlabs.modelflow.execution.scheduler.IScheduler;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.management.param.ITaskParameterManager;
import org.epsilonlabs.modelflow.management.resource.IResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.repository.TaskRepository;

public class ModelFlowEMFContext extends ErlContext implements IModelFlowContext {

	protected IScheduler executor;
	protected IModelFlowProfiler profiler;
	protected IModelFlowPublisher publisher = new Publisher();

	protected ExecutionTrace executionTrace;
	protected ManagementTrace managementTrace;

	protected TaskRepository taskRepository;
	protected IResourceManager modelManager;
	protected ITaskParameterManager paramManager;

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
		} else {
			throw new IllegalArgumentException("module is not instance of IModelFlowModule");
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
	public TaskRepository getTaskRepository() {
		return this.taskRepository;
	}

	@Override
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public IResourceManager getResourceManager() {
		return modelManager;
	}

	@Override
	public void setResourceManager(IResourceManager modelManager) {
		this.modelManager = modelManager;
	}

	@Override
	public void setScheduler(IScheduler executor) {
		this.executor = executor;
	}

	@Override
	public IScheduler getScheduler() {
		return this.executor;
	}

	@Override
	public ITaskParameterManager getParamManager() {
		return paramManager;
	}

	@Override
	public void setParamManager(ITaskParameterManager paramManager) {
		this.paramManager = paramManager;
	}

	@Override
	public IModelFlowPublisher getPublisher() {
		return publisher;
	}

	@Override
	public void setPublisher(IModelFlowPublisher publisher) {
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
		/*TaskRepository repo = getTaskRepository();
		Set<ITaskNode> unknownTaskTypes = getExecutionGraph().getTasks().stream().filter(t -> !repo.hasFactory(t))
				.collect(Collectors.toSet());
		if (!unknownTaskTypes.isEmpty()) {
			String msg = "These tasks types are unknown: %s";
			Set<String> unknowns = unknownTaskTypes.stream().map(n -> n.getTaskElement().getDefinition())
					.collect(Collectors.toSet());
			throw new MFExecutionException(String.format(msg, unknowns));
		}*/
	}

	@Override
	public void setProfiler(IModelFlowProfiler profiler) {
		this.profiler = profiler;
	}

	@Override
	public IModelFlowProfiler getProfiler() {
		if (profiler == null) {
			if (isProfilingEnabled()) {
				profiler = new ModelFlowExecutionProfiler();
			} else {
				profiler = new DefaultModelFlowProfiler();
			}
		}
		return this.profiler;
	}

}