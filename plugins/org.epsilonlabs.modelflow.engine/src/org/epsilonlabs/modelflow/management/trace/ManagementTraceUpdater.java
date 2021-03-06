/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.trace;

import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceFactoryImpl;

public class ManagementTraceUpdater {

	public final ManagementTrace trace;
	public final ITask task;
	
	public ManagementTraceUpdater(ManagementTrace trace, ITask task) {
		this.trace = trace;
		this.task = task;
	}
	
	public void update(Collection<Trace> traces){
		TaskTrace taskTrace = null;
		Optional<TaskTrace> previousRecord = trace.getTasks().stream().filter(t->{
			return t.getTask().equals(task.getName());
		}).findFirst();
		if (!previousRecord.isPresent()) {
			taskTrace = ManagementTraceFactoryImpl.eINSTANCE.createTaskTrace();
			taskTrace.setTask(task.getName());
			taskTrace.getTraces().addAll(traces);
			trace.getTasks().add(taskTrace);
		} else {
			taskTrace = previousRecord.get();
			taskTrace.getTraces().clear();
			taskTrace.getTraces().addAll(traces);
		}
	}
	
}
