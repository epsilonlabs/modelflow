/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockModule;
import org.eclipse.epsilon.flock.execute.exceptions.FlockUnsupportedModelException;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;

@Definition(name = "epsilon:flock")
public class EpsilonFlockTask extends AbstractEpsilonTask implements ITaskInstance {

	protected IModel migrated;
	protected IModel original;
	protected String migratedName; 
	protected String originalName;

	@SuppressWarnings("unchecked")
	@Override
	public IFlockModule getModule() {
		if (module == null) {
			this.module = new FlockModule();
		}
		return (IFlockModule) this.module;
	}

	@Param(key = "migratedName")
	public void setMigratedModelName(String model) {
		this.migratedName = model;
	}

	@Param(key = "originalName")
	public void setOriginalModelName(String model) {
		this.originalName = model;
	}
	public String getMigratedName() {
		return migratedName;
	}
	
	public String getOriginalName() {
		return originalName;
	}
	

	/*
	 * Ideally the following two methods should be used to process 
	 * the models as parameters, but in future iterations
	 * 
	@Param(key = "original")
	public void setOriginalModel(IModel model) {
		this.original = model;
	}
	
	@Param(key = "migrated")
	public void setMigratedModel(IModel model) {
		this.migrated = model;
	}
	*/
	
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		super.acceptModels(models);
		try {
			for (IModelWrapper model : models) {
				if (model.getModel() instanceof IModel) {
					IModel m = (IModel) model.getModel();
					if (/* model.getExtraLabel().equals(originalName) || */ m.getName().equals(originalName)) {
						getModule().getContext().setOriginalModel(m);
					}
					if (/* model.getExtraLabel().equals(migratedName) || */ m.getName().equals(migratedName)) {
						getModule().getContext().setMigratedModel(m);
					}
				}
			}
		} catch (FlockUnsupportedModelException e) {
			throw new MFInvalidModelException(e);
		}
	}

}