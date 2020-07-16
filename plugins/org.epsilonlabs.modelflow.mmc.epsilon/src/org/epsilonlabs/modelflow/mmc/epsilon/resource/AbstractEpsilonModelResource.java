/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import java.util.Optional;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.modelflow.dom.api.AbstractResource;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;

public abstract class AbstractEpsilonModelResource extends AbstractResource<IModel> {

	protected IModel model = null; 

	protected Optional<Boolean> read = Optional.empty();
	protected Optional<Boolean> store = Optional.empty();
	
	public AbstractEpsilonModelResource() {
		getModel();
	}
	
	protected abstract <M extends IModel> M getModel();
	
	@Param(key="read")
	public void setRead(Boolean read) {
		this.read = Optional.of(read);
	}
	
	@Param(key="store")
	public void setStore(Boolean store) {
		this.store = Optional.of(store);
	}

	@Override
	public void configure(){
		getModel().setName(getName());
		this.read.ifPresent(val -> getModel().setReadOnLoad(val));
		this.store.ifPresent(val -> getModel().setStoredOnDisposal(val));
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
		if (!this.read.isPresent()) getModel().setReadOnLoad(true);
		if (!this.store.isPresent()) getModel().setStoredOnDisposal(true);
	}
	
	@Override
	public void modelAsInput() {
		if (!this.read.isPresent()) getModel().setReadOnLoad(true);
		if (!this.store.isPresent()) getModel().setStoredOnDisposal(false);
	}
	
	@Override
	public void modelAsOutput() {
		if (!this.read.isPresent()) getModel().setReadOnLoad(false);
		if (!this.store.isPresent()) getModel().setStoredOnDisposal(true);
	}
	
	@Override
	public void modelAsTransient() {
		if (!this.read.isPresent()) getModel().setReadOnLoad(false);
		if (!this.store.isPresent()) getModel().setStoredOnDisposal(false);
	}
	
	@Override
	public IModel get() {
		return getModel();
	}
	
}
