/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;

import io.reactivex.subjects.PublishSubject;

public abstract class AbstractResourceNode<T extends IAbstractResource> implements IAbstractResourceNode {
	
	protected T internalResource;
	protected PublishSubject<?> statusUpdater = PublishSubject.create();
	
	public AbstractResourceNode(T resource) {
		this.internalResource = resource;
	}

	@Override
	public String getName() {
		return this.internalResource.getName();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractResourceNode && ((AbstractResourceNode<?>) obj).getName().equals(getName());
	}
	
	public T getModelElement() {
		return internalResource;
	}
	
	@Override
	public void subscribe(IModelFlowPublisher pub) {
		statusUpdater.subscribe(state -> pub.resourceState(this.getModelElement().getName(), state));
	}

}
