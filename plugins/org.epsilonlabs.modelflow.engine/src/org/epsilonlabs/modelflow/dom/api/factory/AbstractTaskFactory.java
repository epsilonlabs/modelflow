/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

public abstract class AbstractTaskFactory extends AbstactFactory implements ITaskFactory {

	private Class<? extends ITask> taskClass;

	public AbstractTaskFactory(Class<? extends ITask> taskClazz) {
		this.taskClass = taskClazz;
	}

	@Override
	public Class<? extends ITask> getInstanceClass() {
		return this.taskClass;
	}

	@Override
	public ITask create(ITaskNode node, String name, IModelFlowContext ctx) throws MFInstantiationException {		
		return new TaskFactoryImpl(this, node, name, ctx).create();
	}

}
