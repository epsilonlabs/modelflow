/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.trace;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionTracePrinter {

	private static final String MAIN_SEPARATOR 	= "===================== \n";
	private static final String SEPARATOR 		= "--------------- \n";
	
	private final ExecutionTrace trace;
	
	public ExecutionTracePrinter(ExecutionTrace trace) {
		this.trace = trace;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(MAIN_SEPARATOR);
		builder.append("== Execution Trace == \n");
		builder.append(MAIN_SEPARATOR);
		for (WorkflowExecution execution : trace.getExecutions()) {
			builder.append("==  Workflow == \n");
			builder.append(SEPARATOR);
			appendFormatted(builder, "status: %s\n", execution.getEndState());
			builder.append(SEPARATOR);
			builder.append("==== Tasks ==== \n");
			builder.append(SEPARATOR);
			for (TaskExecution t : execution.getTasks()) {
				appendTask(builder, t);
				builder.append(SEPARATOR);
			}
		}
	    builder.append("==== Resources ==== \n");
		for (ResourceSnapshot r : trace.getLatest()) {
			appendResourceSnapshot(builder, r);
			builder.append(SEPARATOR);
		}
		return builder.toString();
	}

	

	protected void appendTask(StringBuilder builder, TaskExecution t) {
		appendFormatted(builder, ">> %s \n", t.getName());
		appendFormatted(builder, " status: %s\n", t.getEndState());
		if (!t.getInputModels().isEmpty()) {					
			builder.append(" inputs:\n");
			for (ResourceSnapshot i : t.getInputModels()) {
				appendResourceSnapshot(builder, i);
			}
		}
		if (!t.getOutputModels().isEmpty()) {					
			builder.append(" outputs:\n");
			for (ResourceSnapshot o : t.getOutputModels()) {
				appendResourceSnapshot(builder, o);
			}	
		}
		if (!t.getInputProperties().isEmpty()) {					
			builder.append(" input properties:\n");
			for (PropertySnapshot o : t.getInputProperties()) {
				appendPropertySnapshot(builder, o);
			}	
		}
		if (!t.getOutputProperties().isEmpty()) {					
			builder.append(" output properties:\n");
			for (PropertySnapshot o : t.getOutputProperties()) {
				appendPropertySnapshot(builder, o);
			}	
		}
	}

	protected void appendResourceSnapshot(StringBuilder builder, ResourceSnapshot o) {
		appendFormatted(builder, "  -id: %s\n", o.getName());
		appendFormatted(builder, "   timestamp: %s\n", millisToDate(o.getTimestamp()));
		appendFormatted(builder, "   stamp: %s\n", o.getStamp());
	}
	
	protected void appendPropertySnapshot(StringBuilder builder, PropertySnapshot o) {
		appendFormatted(builder, "  -id: %s\n", o.getName());
		appendFormatted(builder, "   timestamp: %s\n", millisToDate(o.getTimestamp()));
		appendFormatted(builder, "   stamp: %s\n", o.getStamp());
	}

	protected void appendFormatted(StringBuilder builder, String msg, Object... args) {
		String formattedMessage = String.format(msg, args);
		builder.append(formattedMessage);
	}
	
	protected String millisToDate(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");    
		Date resultdate = new Date(millis);
		return sdf.format(resultdate);
	}

}
