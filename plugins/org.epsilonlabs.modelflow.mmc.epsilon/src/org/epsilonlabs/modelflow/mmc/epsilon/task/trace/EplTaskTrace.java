/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EplTaskTrace implements ITrace {

	protected final PatternMatch patternMatch;
	protected final AbstractEpsilonTask task;
	protected final Trace trace;


	public EplTaskTrace(PatternMatch match, ITaskInstance task) {
		this(match, task, null);
	}
	
	private EplTaskTrace(PatternMatch match, ITaskInstance task, Trace trace) {
		this.patternMatch = match;
		this.task = (AbstractEpsilonTask) task;
		this.trace = trace;
	}	

	@Override
	public Trace getTrace() {
		return trace;
	}
	
	public ITrace init() {
		
		if (patternMatch == null) 
			throw new RuntimeException("Trace has not been initialised with Match");
		if (task == null) 
			throw new RuntimeException("Trace has not been initialised with ITask");
		
		ManagementTraceBuilder builder = new ManagementTraceBuilder()
				.managementLink("Pattern", patternMatch.getPattern().getName());
		
		patternMatch.getRoleBindings().forEach((k,v) -> {
			IAbstractResource model = EpsilonTraceUtil.getContainerModel(task, v);
			String id = EpsilonTraceUtil.getElementId(task, v);
			builder.addSourceModelElement(id, model, k);
		});
		
		Trace builtTrace = builder.build();
		
		return new EplTaskTrace(patternMatch, task, builtTrace);
	}

}
