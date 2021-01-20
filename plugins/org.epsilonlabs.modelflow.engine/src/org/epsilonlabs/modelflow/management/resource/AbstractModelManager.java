/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

public abstract class AbstractModelManager<M> implements IModelManager<M> {

	protected Map<String, M> cachedModels;
	protected Map<String, IResource> resources;
	
	protected AbstractModelManager() {
		cachedModels = new ConcurrentHashMap<>();
		resources = new ConcurrentHashMap<>();
	}
	
	@Override
	public void register(IModelResourceNode res) {
		IResource r = res.getModelElement();
		if (!resources.containsKey(r.getName())) {
			resources.put(r.getName(), r);
		}
	}
	
	@Override
	public void dispose(IModelResourceNode r){
		if (cachedModels.containsKey(r.getName())) {
			disposeModel(cachedModels.get(r.getName()));
			cachedModels.remove(r.getName());
		}
		if (resources.containsKey(r.getName())) {
			resources.remove(r.getName());			
		}
	}
	
	@Override
	public void reset(){
		cachedModels.values().forEach(this::disposeModel);
		cachedModels.clear();
		resources.clear();
	}
	
	protected abstract M loadModel(M model) throws MFExecutionException;

	protected abstract void disposeModel(M model);
	
	@Override
	public String toString() {
		return resources.values().toString();
	}
	
}