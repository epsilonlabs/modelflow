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

import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;

public class TaskRepository {

	protected TaskFactoryRegistry taskFactoryRegistry;
	protected ResourceRepository resourceRepository;
	protected Map<String, ITask> tasks;


	public TaskRepository(TaskFactoryRegistry registry, ResourceFactoryRegistry resRegistry) {
		this.taskFactoryRegistry = registry;
		this.resourceRepository = new ResourceRepository(resRegistry);
		this.tasks = new ConcurrentHashMap<>();
	}
	
	public ResourceRepository getResourceRepository() {
		return resourceRepository;
	}
	
	public Boolean hasFactory(ITaskNode node){
		try{
			this.taskFactoryRegistry.getFactory(node.getTaskDefinition());
			return true;
		} catch (MFInvalidFactoryException e) {
			return false;
		}
	}
	
	public ITask create(ITaskNode node, IModelFlowContext ctx) throws MFRuntimeException {
		String id = uniqueId(node);
		ITask task = this.taskFactoryRegistry.create(node, id, ctx);
		this.tasks.put(id, task);
		return task;
	}

	protected String uniqueId(ITaskNode node){
		return node.getName();
	}
	
	public void clear() {
		this.tasks.clear();
		this.resourceRepository.clear();
	}

}
