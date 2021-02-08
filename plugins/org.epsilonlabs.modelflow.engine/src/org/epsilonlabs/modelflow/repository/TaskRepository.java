/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

public class TaskRepository {

	protected TaskFactoryRegistry taskFactoryRegistry;
	protected ResourceRepository resourceRepository;
	protected Map<String, ITaskInstance> tasks;


	public TaskRepository(TaskFactoryRegistry registry, ResourceFactoryRegistry resRegistry) {
		this.taskFactoryRegistry = registry;
		this.resourceRepository = new ResourceRepository(resRegistry);
		this.tasks = new ConcurrentHashMap<>();
	}
	
	public ResourceRepository getResourceRepository() {
		return resourceRepository;
	}
	
	public Boolean hasFactory(ITaskNode node){
		return hasFactory(node.getName());
	}
	
	public Boolean hasFactory(String factoryName){
		try{
			this.taskFactoryRegistry.getFactory(factoryName);
			return true;
		} catch (MFInvalidFactoryException e) {
			return false;
		}
	}
	
	public ITaskInstance create(ITaskNode node, IModelFlowContext ctx) throws MFRuntimeException {
		Class<ITaskInstance> taskClazz;
		try {
			taskClazz = taskFactoryRegistry.getFactory(node.getDefinition());
			final ITaskInstance instance = ctx.getScheduler().getTaskInstanceFactory().create(taskClazz, node, ctx);
			this.tasks.put(node.getName(), instance);
			return instance;
		} catch (MFInvalidFactoryException e) {
			throw new MFInstantiationException(e);
		}
	}
	
	public void clear() {
		this.tasks.clear();
		this.resourceRepository.clear();
	}

}
