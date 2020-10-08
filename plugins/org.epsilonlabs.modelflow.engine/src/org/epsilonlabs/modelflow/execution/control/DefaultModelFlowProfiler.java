/**
 * 
 */
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
	public void start(IMeasurable.Stage stage, IGraphNode ast, IModelFlowContext context) {
		// Do nothing		
	}

	@Override
	public void stop(IMeasurable.Stage stage, IGraphNode ast, IModelFlowContext context) {
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
	public MemoryTracker getMemoryTracker() {
		return null;
	}

}
