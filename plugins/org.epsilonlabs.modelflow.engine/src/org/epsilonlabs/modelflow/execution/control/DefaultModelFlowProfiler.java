/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public class DefaultModelFlowProfiler implements IModelFlowProfiler {

	@Override
	public void dispose() {
		// Do nothing
	}

	@Override
	public void report(IModelFlowContext context) {
		// Do nothing		
	}

	@Override
	public void start(ExecutionStage stage, IGraphNode ast, IModelFlowContext context) {
		// Do nothing		
	}

	@Override
	public void stop(ExecutionStage stage, IGraphNode ast, IModelFlowContext context) {
		// Do nothing		
	}

	@Override
	public void track() {
		// Do nothing				
	}

	@Override
	public void setTimeout(long timeout) {
		// Do nothing		
	}

	@Override
	public long getTimeout() {
		return 0;
	}

	@Override
	public void setTimeUnit(TimeUnit unit) {
		// Do nothing			
	}

	@Override
	public TimeUnit getTimeUnit() {
		return null;
	}

	@Override
	public IMemoryTracker getMemoryTracker() {
		return null;
	}

}
