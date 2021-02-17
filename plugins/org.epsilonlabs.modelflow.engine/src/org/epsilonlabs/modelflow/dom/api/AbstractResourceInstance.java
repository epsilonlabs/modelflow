/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

public abstract class AbstractResourceInstance<M> implements IModelResourceInstance<M> {

	protected String name;
	protected boolean isLoaded = false;
	protected Set<String> aliases = new HashSet<>();
	protected ResourceKind kind = null;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
		
	@Override
	public boolean isLoaded() {
		return this.isLoaded;
	}
	
	@Override
	public void load() throws MFRuntimeException {
		loadImpl();
		this.isLoaded = true;
	}
	
	@Override
	public void dispose() {
		disposeImpl();
		this.isLoaded = false;
	}
	
	@Override
	public Set<String> getAliases() {
		return aliases;
	}
	
	@Override
	public void setAlias(String alias) {
		this.aliases.add(alias);
		setModelAlias(alias);
	}
	
	@Override
	public void clearAliases() {
		this.aliases.clear();
		clearModelAliases();
	}
	
	protected abstract void setModelAlias(String alias);
	protected abstract void clearModelAliases();
	
	protected abstract void loadImpl() throws MFRuntimeException;
	
	protected abstract void disposeImpl();
			
	@Override
	public ResourceKind getKind() {
		return kind;
	}
	
	protected abstract void modelAsInOut();
	protected abstract void modelAsInput();
	protected abstract void modelAsOutput();
	protected abstract void modelAsTransient();

	@Override
	public IModelResourceInstance<M> asInOut() {
		kind = ResourceKind.INOUT;
		modelAsInOut();
		return this;
	}
	
	@Override
	public IModelResourceInstance<M> asInput() {
		ResourceKind pastKind = getKind(); // TODO something with past kind 
		kind = ResourceKind.INPUT;
		modelAsInput();
		return this;
	}
	
	@Override
	public IModelResourceInstance<M> asOutput() {
		kind = ResourceKind.OUTPUT;
		modelAsOutput();
		return this;
	}
	
	@Override
	public IModelResourceInstance<M> asTransient() {
		kind = ResourceKind.TRANSIENT;
		modelAsTransient();
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getCanonicalName() + " [ ");
		
		Arrays.asList(getClass().getFields()).forEach(f -> {
			builder.append(f.getName() + " : ");
			f.setAccessible(true);
			Object value = "{inaccessible}";
			try {				
				value = f.get(this);
			} catch (Exception e) {
				// Value null
			}
			builder.append(value.toString() + " ; ");
		});
		builder.append(" ]");
		return builder.toString();
	}
	
}
