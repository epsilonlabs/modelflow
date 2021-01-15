/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

public abstract class AbstractTaskFactory extends AbstactFactory implements ITaskFactory {

	private Class<? extends ITaskInstance> taskClass;

	public AbstractTaskFactory(Class<? extends ITaskInstance> taskClazz) {
		this.taskClass = taskClazz;
	}

	@Override
	public Class<? extends ITaskInstance> getInstanceClass() {
		return this.taskClass;
	}

	@Override
	public ITaskInstance create(ITaskNode node, String name, IModelFlowContext ctx) throws MFInstantiationException {		
		return new TaskFactoryImpl(this, node, name, ctx).create();
	}

}
