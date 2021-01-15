/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.util.Collection;

import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.graph.node.IResourceNode;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

/**
 * The Interface IResource.
 *
 * @param <M> the generic type
 */
public interface IResourceInstance<M> {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	IResourceNode getNode();

	/**
	 * Configure.
	 *
	 * @param node the node
	 */
	void configure(IResourceNode node);
	
	/**
	 * As input.
	 *
	 * @return the i resource
	 */
	IResourceInstance<M> asInput();
	
	/**
	 * As output.
	 *
	 * @return the i resource
	 */
	IResourceInstance<M> asOutput();
	
	/**
	 * As in out.
	 *
	 * @return the i resource
	 */
	IResourceInstance<M> asInOut();
	
	/**
	 * As transient.
	 *
	 * @return the i resource
	 */
	IResourceInstance<M> asTransient();
	
	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	void setAlias(String alias);
	
	/**
	 * Gets the aliases.
	 *
	 * @return the aliases
	 */
	Collection<String> getAliases();
	
	/**
	 * Gets the.
	 *
	 * @return the m
	 */
	M get();
		
	/**
	 * Load.
	 *
	 * @throws MFRuntimeException the MF runtime exception
	 */
	void load() throws MFRuntimeException;
	
	/**
	 * Dispose.
	 */
	void dispose();
	
	/**
	 * Save.
	 */
	void save();
		
	//void register(IModelIndexer indexer) throws Exception; 

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if is loaded
	 */
	boolean isLoaded();
	
	/**
	 * Loaded hash.
	 *
	 * @return the object
	 */
	Object loadedHash();
	
	/**
	 * Unloaded hash.
	 *
	 * @param trace the trace
	 * @return the object
	 */
	Object unloadedHash(Object trace);

	/**
	 * Get resource kind
	 * 
	 * @return
	 */
	ResourceKind getKind();

	/**
	 * 
	 */
	void clearAliases();
	
	void beforeTask();
	
	void afterTask();
	
}