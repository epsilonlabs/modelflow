/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;

public class ModelWrapper implements IModelWrapper {

	protected Object model;
	protected IAbstractResourceNode node;
	protected ResourceKind kind;
	protected Collection<String> aliases = new ArrayList<>();

	public ModelWrapper(ResourceKind kind, IAbstractResourceNode node, Object model, String... aliases) {
		this.model = model;
		this.kind = kind;
		this.aliases = Arrays.asList(aliases);
		this.node = node;
	}
	
	@Override
	public Object getModel() {
		return this.model;
	}
	
	@Override
	public IAbstractResourceNode getResourceNode() {
		return this.node;
	}
	
	@Override
	public ResourceKind getResourceKind() {
		return this.kind;
	}
	
	@Override
	public Collection<String> getAliases() {
		return aliases;
	}
	
}
