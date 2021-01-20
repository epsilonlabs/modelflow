/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

/**
 * The Interface IResource.
 *
 * @param <M> the generic type
 */
public interface IModelResourceInstance<M> {

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
	 * Configure.
	 *
	 */
	void configure();
	
	/**
	 * As input.
	 *
	 * @return the i resource
	 */
	IModelResourceInstance<M> asInput();
	
	/**
	 * As output.
	 *
	 * @return the i resource
	 */
	IModelResourceInstance<M> asOutput();
	
	/**
	 * As in out.
	 *
	 * @return the i resource
	 */
	IModelResourceInstance<M> asInOut();
	
	/**
	 * As transient.
	 *
	 * @return the i resource
	 */
	IModelResourceInstance<M> asTransient();
	
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
	Optional<Object> loadedHash();
	
	/**
	 * Unloaded hash.
	 *
	 * @param trace the trace
	 * @return the object
	 */
	Optional<Object> unloadedHash(Object trace);

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
