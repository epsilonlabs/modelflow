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

import org.epsilonlabs.modelflow.dom.api.ITaskFactory;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.TaskDefinitionValidator;
import org.epsilonlabs.modelflow.dom.api.factory.EMFTaskFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ModuleTaskFactory;
import org.epsilonlabs.modelflow.dom.ast.TaskDeclaration;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFInvalidFactoryException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

import com.google.inject.Inject;

@SuppressWarnings("unchecked")
public class TaskFactoryRegistry extends AbstractFactoryRegistry<ITaskInstance>{

	@Inject
	public TaskFactoryRegistry(Set<ITaskFactory> tasks) {
		super(tasks.stream()
			.map(f->(Class<ITaskInstance>)f.getFactoryClass())
			.filter(t-> new TaskDefinitionValidator(t).isValid())
			.collect(Collectors.toSet()));
	}

	public ITaskInstance create(ITaskNode node, IModelFlowContext ctx) throws MFInvalidFactoryException, MFInstantiationException {
		Class<ITaskInstance> taskClazz;
		try {
			taskClazz = getFactory(node.getDefinition());
			final ITaskInstance instance = new EMFTaskFactory(taskClazz, node, node.getName(), ctx).create();
			return instance;
		} catch (MFInvalidFactoryException e) {
			throw new MFInstantiationException(e);
		}
	}
	
	public ITaskInstance create(TaskDeclaration declaration, IModelFlowContext ctx) throws MFRuntimeException {
		Class<ITaskInstance> taskClazz;
		try {
			taskClazz = getFactory(declaration.getType().getName());
			return new ModuleTaskFactory(taskClazz, declaration).create(ctx);
		} catch (MFInvalidFactoryException e) {
			throw new MFInstantiationException(e);
		}
	}

}
