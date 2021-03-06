/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.eml.trace.Merge;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EmlTaskTrace implements ITrace {

	protected final Merge merge;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;

	public EmlTaskTrace(Merge merge, ITaskInstance task) {
		this(merge, task, null);
	}
	
	private EmlTaskTrace(Merge merge, ITaskInstance task, Trace trace) {
		this.merge = merge;
		this.task = (AbstractEpsilonTask) task;
		this.trace = trace;
	}	

	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (merge == null) 
			throw new RuntimeException("T has not been initialised with Merge");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		String leftId = EpsilonTraceUtil.getElementId(task, merge.getMatch().getLeft());
		String leftModel = EpsilonTraceUtil.getContainerModel(task, merge.getMatch().getLeft());
		String rightId = EpsilonTraceUtil.getElementId(task, merge.getMatch().getRight());
		String rightModel = EpsilonTraceUtil.getContainerModel(task, merge.getMatch().getLeft());

		ManagementTraceBuilder builder = new ManagementTraceBuilder()
				.link("Merge", merge.getRule().getName())
				.addSourceModelElement(leftId, leftModel, "left")
				.addSourceModelElement(rightId, rightModel, "right");
		
		merge.getTargets().forEach(target -> {
			String targetId = EpsilonTraceUtil.getElementId(task, target);
			String targetModel = EpsilonTraceUtil.getContainerModel(task, target);
			builder.addTargetModelElement(targetId, targetModel, null);
		});
		
		merge.getMatch().getInfo().forEach((k, v) -> 
			builder.addProperty((String)k, v)
		);
		
		Trace builtTrace = builder.build();
		
		return new EmlTaskTrace(merge, task, builtTrace);
	}

}
