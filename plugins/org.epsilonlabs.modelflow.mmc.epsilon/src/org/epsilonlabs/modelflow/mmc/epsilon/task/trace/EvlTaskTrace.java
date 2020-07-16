/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.evl.trace.ConstraintTraceItem;
import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EvlTaskTrace implements ITrace {

	protected final ConstraintTraceItem constraint;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;

	public EvlTaskTrace(ConstraintTraceItem constraint, ITask task) {
		this(constraint, task, null);
	}
	
	private EvlTaskTrace(ConstraintTraceItem constraint, ITask task, Trace trace) {
		this.constraint = constraint;
		this.task = (AbstractEpsilonTask) task;
		this.trace = trace;
	}	

	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (constraint == null) 
			throw new RuntimeException("T has not been initialised with Constraint");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		String sourceId = EpsilonTraceUtil.getElementId(task, constraint.getInstance());
		AbstractResource sourceContainer = EpsilonTraceUtil.getContainerModel(task, constraint.getInstance());
		
		Trace builtTrace = new ManagementTraceBuilder()
				.managementLink("Invariant", constraint.getConstraint().getName())
				.addSourceModelElement(sourceId, sourceContainer, null)
				.addTargetModelElement(sourceId, sourceContainer, null)
				.addProperty("result", constraint.getResult())
				.build();
		
		return new EvlTaskTrace(constraint, task, builtTrace);
	}

}
