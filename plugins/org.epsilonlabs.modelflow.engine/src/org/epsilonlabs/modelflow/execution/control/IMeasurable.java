/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public interface IMeasurable {

	String getName();
	
	ExecutionStage getStage();
		
	Class<? extends IGraphNode> getType();
	
	IMeasurable getParent();

	/**
	 * @return
	 */
	IGraphNode getNode();
	
}
