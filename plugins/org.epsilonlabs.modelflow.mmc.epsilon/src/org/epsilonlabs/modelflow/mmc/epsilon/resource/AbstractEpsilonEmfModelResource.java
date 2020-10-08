/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;

public abstract class AbstractEpsilonEmfModelResource extends AbstractEpsilonCachedModelResource {

	protected Boolean expand = false;
	protected Boolean parallelAllOf = false;

	public AbstractEpsilonEmfModelResource() {
		super();
	}
	
	public Boolean getExpand() {
		return expand;
	}

	@Param(key="expand")
	public void setExpand(Boolean expand) {
		this.expand = expand;
	}
	
	public Boolean getParallelAllOf() {
		return parallelAllOf;
	}
	
	@Param(key="parallelAllOf")
	public void setParallelAllOf(Boolean parallelAllOf) {
		this.parallelAllOf = parallelAllOf;
	}
	
	@Override
	protected AbstractEmfModel getModel() {
		return getModel();
	}
	
	@Override
	public void loadImpl() throws MFRuntimeException {
		super.loadImpl();
		/* FIXME: This seems to be a bug in the CachedResourceSet of Epsilon EmfModel which is ignoring the flag
		isReadOnLoad and is loading the model anyway.*/
		if (!this.read.get()) {
			getModel().getResource().getContents().clear();
		}
	}
	
	@Override
	public void configure() {
		super.configure();
		getModel().setParallelAllOf(parallelAllOf);
		getModel().setExpand(expand);
	}
	
}
