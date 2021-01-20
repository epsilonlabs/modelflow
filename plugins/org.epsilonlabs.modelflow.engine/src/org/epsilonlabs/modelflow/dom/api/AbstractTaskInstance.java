/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

public abstract class AbstractTaskInstance implements ITaskInstance {

	protected ITaskNode node;
	protected boolean alwaysExecute = false;
		
	@Override
	public void configure(ITaskNode task) {
		this.node = task;
		task.getTaskElement().getProperties();
	} 

	@Override
	public boolean isAlwaysExecute() {
		return alwaysExecute;
	}
	
}
