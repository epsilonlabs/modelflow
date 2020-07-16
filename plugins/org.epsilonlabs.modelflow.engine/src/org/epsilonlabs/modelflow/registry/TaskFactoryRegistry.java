/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.registry;

import java.util.Set;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.TaskDefinitionValidator;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

import com.google.inject.Inject;

public class TaskFactoryRegistry extends AbstractFactoryRegistry<ITaskFactory>{

	@Inject
	public TaskFactoryRegistry(Set<ITaskFactory> tasks) {
		/** Ensure task types are valid */
		tasks = tasks.stream()
			.filter(t->
			new TaskDefinitionValidator(t.getInstanceClass()).isValid())
			.collect(Collectors.toSet());
		factoryRegistry = new FactoryMap<ITaskFactory>(tasks);
	}

	public ITaskFactory getFactory(Task t) throws MFInvalidFactoryException {
		return super.getFactory(t.getDefinition());
	}
	
	public ITask create(ITaskNode node, String name, IModelFlowContext ctx) throws MFInvalidFactoryException, MFInstantiationException {
		ITaskFactory factory;
		try {
			factory = getFactory(node.getTaskDefinition().getDefinition());
		} catch (MFInvalidFactoryException e) {
			throw new MFInstantiationException(e);
		}
		ITask task = factory.create(node, name, ctx);
		node.setInstance(task);
		return task;
	}

}
