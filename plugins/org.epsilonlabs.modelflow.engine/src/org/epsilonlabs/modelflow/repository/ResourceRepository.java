/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceRepository {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceRepository.class);

	protected ResourceFactoryRegistry factoryRegistry;
	protected Map<String, Object> /* <ResourceIdentity, Resource> */ derivedResources;
	protected Map<String, IResource<?>> /* <ResourceIdentity, Resource> */ resources;
	protected Map<String, String> /* <ResourceIdentity, Resource> */ hashes;
	
	public ResourceRepository(ResourceFactoryRegistry registry) {
		factoryRegistry = registry;
		resources = new HashMap<>();
		derivedResources = new HashMap<>();
	}

	public void addDerived(DerivedResourceNode node, Object resource) {
		derivedResources.put(node.getName(), resource);
	}
	
	public Object getDerived(DerivedResourceNode node) {
		return derivedResources.get(node.getName());
	}
	
	public Object getDerived(String node) {
		return derivedResources.get(node);
	}
	
	public Optional<IResource<?>> get(IModelResourceNode node) throws MFRuntimeException {
		String id = node.getName();
		if (this.resources.containsKey(id)) {
			return Optional.of(this.resources.get(id));
		} else {
			return Optional.empty();
		}
	}
	
	public IResource<?> getOrCreate(IModelResourceNode node, IModelFlowContext ctx) throws MFRuntimeException {
		Optional<IResource<?>> optional = get(node);
		IResource<?> iResource;
		if (optional.isPresent()) {
			iResource = optional.get();
		} else {			
			String id = node.getName();
			iResource = this.factoryRegistry.create(node, ctx);
			this.resources.put(id, iResource);
		}
		iResource.clearAliases();
		return iResource;
	}

	public void clear() {
		LOG.info("Clearing Resource Repository");
		this.resources.clear();
		this.derivedResources.clear();
	}

	public void clearDerived() {
		this.derivedResources.clear();
	}
	
	public void clearNonInputModels() {
		Set<String> keys = this.resources.entrySet().stream().filter(e->e.getValue().getKind().isInput()).map(e->e.getKey()).collect(Collectors.toSet());
		for (String k : keys) {
			this.resources.remove(k);
		}
	}

	/**
	 * 
	 */
	public void flush() {
		clearDerived();
		clearNonInputModels();		
	}	

}
