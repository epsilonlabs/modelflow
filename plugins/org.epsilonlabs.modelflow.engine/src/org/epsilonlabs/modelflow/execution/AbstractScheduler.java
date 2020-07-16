/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractScheduler implements IScheduler {

	protected List<IExecutionListener> executionListeners = new ArrayList<>();
	
	@Override
	public void addExecutionListener(IExecutionListener listener) {
		final IExecutionListener lst = listener;
		this.executionListeners.add(lst);
	}

	@Override
	public List<IExecutionListener> getExecutionListeners() {
		return executionListeners;
	}

}
