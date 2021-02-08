/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.scheduler.TopologicalConcurrentScheduler;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowModuleParallel extends ModelFlowModule {

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		IModelFlowContext ctx = getContext();
		ctx.setScheduler(new TopologicalConcurrentScheduler());
	}
}
