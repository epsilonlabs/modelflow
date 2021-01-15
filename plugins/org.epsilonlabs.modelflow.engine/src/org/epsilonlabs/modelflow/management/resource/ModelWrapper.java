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

import org.epsilonlabs.modelflow.dom.IAbstractResource;

public class ModelWrapper implements IModelWrapper {

	protected Object model;
	protected IAbstractResource resource;
	protected ResourceKind kind;
	protected Collection<String> aliases = new ArrayList<>();

	public ModelWrapper(ResourceKind kind, IAbstractResource resource, Object model, String... aliases) {
		this.model = model;
		this.kind = kind;
		this.aliases = Arrays.asList(aliases);
		this.resource = resource;
	}
	
	@Override
	public Object getModel() {
		return this.model;
	}
	
	@Override
	public org.epsilonlabs.modelflow.dom.IAbstractResource getResource() {
		return this.resource;
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
