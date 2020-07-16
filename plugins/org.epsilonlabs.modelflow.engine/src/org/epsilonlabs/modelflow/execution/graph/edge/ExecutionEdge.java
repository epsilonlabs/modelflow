/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.edge;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ExecutionEdge extends DefaultWeightedEdge {

	private static final long serialVersionUID = 8331886846634503486L;

	private Boolean isBlocked;
	
	public ExecutionEdge() {
		super();
		isBlocked = false;
	}

	public void block(){
		isBlocked = true;
	}
	
	public void unblock(){
		isBlocked = false;
	}
	
	public Boolean isBlocked(){
		return isBlocked;
	}

}
