/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.ecl.trace.Match;
import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EclTaskTrace implements ITrace {

	protected final Match match;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;

	public EclTaskTrace(Match match, ITask t) {
		this(match, t, null);
	}

	private EclTaskTrace(Match match, ITask t, Trace trace) {
		this.match = match;
		this.task = (AbstractEpsilonTask) t;
		this.trace = trace;
	}
	
	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (match == null) 
			throw new RuntimeException("Trace has not been initialised with Match");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		/** Source */
		String sourceElement = EpsilonTraceUtil.getElementId(task, match.getLeft());
		AbstractResource sourceModel = EpsilonTraceUtil.getContainerModel(task, match.getLeft());
		
		/** Target */
		String targetElement = EpsilonTraceUtil.getElementId(task, match.getRight());
		AbstractResource targetModel = EpsilonTraceUtil.getContainerModel(task, match.getRight());
				
		Trace trace = new ManagementTraceBuilder()
				.managementLink("Comparison", match.getRule().getName())
				.addProperty("isMatching", match.isMatching())
				.addProperty("userSpecified", match.isUserSpecified())
				.addSourceModelElement(sourceElement, sourceModel, null)
				.addTargetModelElement(targetElement, targetModel, null)
				.build();
		
		return new EclTaskTrace(match, task, trace);
	}

}
