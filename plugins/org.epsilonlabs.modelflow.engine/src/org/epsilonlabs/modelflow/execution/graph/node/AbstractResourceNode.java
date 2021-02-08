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
	
	protected String name;
	protected PublishSubject<?> statusUpdater = PublishSubject.create();
	protected String definition;
	protected T resource;
	
	public AbstractResourceNode(T resource) {
		this.name = resource.getName();
		this.definition = resource.getDefinition();
		this.resource = resource;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getDefinition() {
		return this.definition;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractResourceNode && ((AbstractResourceNode<?>) obj).getName().equals(getName());
	}
	
	@Override
	public void subscribe(IModelFlowPublisher pub) {
		statusUpdater.subscribe(state -> pub.resourceState(getName(), state));
	}

}
