/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.etl.trace.Transformation;
import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EtlTaskTrace implements ITrace {

	protected final Transformation transformation;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;

	public EtlTaskTrace(Transformation transformation, ITask task) {
		this(transformation, task, null);
	}
	
	private EtlTaskTrace(Transformation transformation, ITask task, Trace trace) {
		this.transformation = transformation;
		this.task = (AbstractEpsilonTask) task;
		this.trace = trace;
	}	

	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (transformation == null) 
			throw new RuntimeException("T has not been initialised with Transformation");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		String sourceId = EpsilonTraceUtil.getElementId(task,transformation.getSource());
		AbstractResource sourceModel = EpsilonTraceUtil.getContainerModel(task,transformation.getSource());

		ManagementTraceBuilder builder = new ManagementTraceBuilder()
				.managementLink("Transformation", transformation.getRule().getName())
				.addSourceModelElement(sourceId, sourceModel, null);
		
		transformation.getTargets().forEach(target -> {
			String targetId = EpsilonTraceUtil.getElementId(task,target);
			AbstractResource targetModel = EpsilonTraceUtil.getContainerModel(task,target);
			builder.addTargetModelElement(targetId, targetModel, null);
		});
		
		Trace builtTrace = builder.build();
		
		return new EtlTaskTrace(transformation, task, builtTrace);
	}

}
