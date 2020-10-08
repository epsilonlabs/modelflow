/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param;

import java.util.Map;

import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskParamManager {

	private static final Logger LOG = LoggerFactory.getLogger(TaskParamManager.class);

	protected IModelFlowContext ctx;
	protected ExecutionTrace executionTrace;
	
	public void processInputs(ITaskNode node, IModelFlowContext ctx){
		Map<String, Object> hashes = node.getInputParams().getHashes();
		LOG.debug("InputHashes: {}", hashes);
		ExecutionTrace trace = ctx.getExecutionTrace();
		synchronized (trace) {		
			new ExecutionTraceUpdater(trace).addTaskInputProperties(node, hashes);
		}
	}
	
	public void processOutputs(ITaskNode node, IModelFlowContext ctx){
		Map<String, Object> hashes = node.getOutputParams().getHashes();
		LOG.debug("OutputHashes: {}", hashes);
		ExecutionTrace trace = ctx.getExecutionTrace();
		synchronized (trace) {			
			new ExecutionTraceUpdater(trace).addTaskOutputProperties(node, hashes);
		}
	}
	
}
