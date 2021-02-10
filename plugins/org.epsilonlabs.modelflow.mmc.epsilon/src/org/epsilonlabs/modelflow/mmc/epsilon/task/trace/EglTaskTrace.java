/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EglTaskTrace implements ITrace {

	protected final TraceLink traceLink;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;

	public EglTaskTrace(TraceLink template, ITaskInstance task) {
		this(template, task, null);
	}
	
	private EglTaskTrace(TraceLink template, ITaskInstance task, Trace trace) {
		this.traceLink = template;
		this.task = (AbstractEpsilonTask) task;
		this.trace = trace;
	}	

	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (traceLink == null) 
			throw new RuntimeException("T has not been initialised with TraceLink");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		ManagementTraceBuilder builder = new ManagementTraceBuilder()
				.link("Generated");
				
		Object source= traceLink.getSource().getModelElement();
		String container = EpsilonTraceUtil.getContainerModel(task,source);
		String id = EpsilonTraceUtil.getElementId(task,source);
		builder.addSourceModelElementProperty(id, container, traceLink.getSource().getPropertyName(),null);
		
		TextLocation target = traceLink.getDestination();
		String resource = FilenameUtils.normalize(target.getResource());
		Region region = target.getRegion();
		builder.addTargetElement(resource, region.getOffset(), region.getLength());
		
		Trace builtTrace = builder.build();
		
		return new EglTaskTrace(traceLink, task, builtTrace);
	}

}
