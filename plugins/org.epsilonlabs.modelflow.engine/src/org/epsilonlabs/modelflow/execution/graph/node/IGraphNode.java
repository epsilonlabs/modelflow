/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;

/**
 *  
 * Accepts Tasks or Resources.
 */
public interface IGraphNode {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
		
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	String getDefinition();

	/**
	 * Subscribe.
	 *
	 * @param pub the pub
	 */
	void subscribe(IModelFlowPublisher pub);
	
}
