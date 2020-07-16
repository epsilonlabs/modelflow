/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;

import org.eclipse.epsilon.eol.models.CachedModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;

public abstract class AbstractEpsilonCachedModelResource extends AbstractEpsilonModelResource {

	protected Boolean cachingEnabled = false;
	protected Boolean concurrent = false;
	
	public AbstractEpsilonCachedModelResource() {
		super();
	}
	
	public Boolean getCachingEnabled() {
		return cachingEnabled;
	}

	@Param(key="cache")
	public void setCachingEnabled(Boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}

	public Boolean getConcurrent() {
		return concurrent;
	}

	@Param(key="concurrent")
	public void setConcurrent(Boolean concurrent) {
		this.concurrent = concurrent;
	}

	@Override
	public void configure() {
		super.configure();
		getModel().setCachingEnabled(cachingEnabled);
		getModel().setConcurrent(concurrent);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected CachedModel<?> getModel() {
		return getModel();
	}
	
	
}
