/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.modelflow.dom.api.AbstractResourceInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;

public abstract class AbstractEpsilonModelResource extends AbstractResourceInstance<IModel> {

	protected IModel model = null; 

	protected boolean read = false;
	protected boolean store = false;
	protected boolean reload = false;

	
	public AbstractEpsilonModelResource() {
		getModel();
	}
	
	protected abstract <M extends IModel> M getModel();
	
	@Param(key="read")
	public void setRead(Boolean read) {
		this.read = read;
	}
	
	@Param(key="store")
	public void setStore(Boolean store) {
		this.store =store;
	}
	
	@Param(key="reload")
	public void setReload(Boolean reload) {
		this.reload =reload;
	}

	@Override
	public void configure(){
		getModel().setName(getName());
		getModel().setReadOnLoad(read);
		getModel().setStoredOnDisposal(store);
	}
	
	@Override
	public void loadImpl() throws MFRuntimeException {
		try {
			getModel().load();
		} catch (EolModelLoadingException e) {
			throw new MFRuntimeException(e);
		}
	}
	
	@Override
	public void disposeImpl() {
		getModel().dispose();
	}
	
	@Override
	public void save() {
		getModel().store();
	}
	
	@Override
	public void setModelAlias(String alias) {
		getModel().getAliases().add(alias);
	}
	
	@Override
	public void clearModelAliases() {
		getModel().getAliases().clear();
	}
	
	@Override
	public void modelAsInOut() {
		if (!this.read) {
			this.read = true;
			getModel().setReadOnLoad(true);
		}
		if (!this.store) {			
			this.store = true;
			getModel().setStoredOnDisposal(true);
		}
		if (reload) {
			this.isLoaded = false;
		}
	}
	
	@Override
	public void modelAsInput() {
		if (!this.read) {
			this.read = true;
			getModel().setReadOnLoad(true);
		}
		if (this.store) {
			this.store = false;
			getModel().setStoredOnDisposal(false);
		}
		if (reload) {
			this.isLoaded = false;
		}
	}
	
	@Override
	public void modelAsOutput() {
		if (this.read) {
			this.read = false;
			getModel().setReadOnLoad(false);
		}
		if (!this.store) {
			this.store = true;
			getModel().setStoredOnDisposal(true);
		}
	}
	
	@Override
	public void modelAsTransient() {
		if (this.read) {
			this.read = false;
			getModel().setReadOnLoad(false);
		}
		if (this.store) {
			this.store = false;
			getModel().setStoredOnDisposal(false);
		}
	}
	
	@Override
	public IModel get() {
		return getModel();
	}
	
}
