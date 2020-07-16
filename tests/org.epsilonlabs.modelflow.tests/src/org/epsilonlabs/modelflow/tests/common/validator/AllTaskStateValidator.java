package org.epsilonlabs.modelflow.tests.common.validator;

import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceAnalyser;

public class AllTaskStateValidator implements IValidate {

	protected TaskState state;

	public AllTaskStateValidator(TaskState state) {
		this.state = state;
	}
	@Override
	public Boolean ok(IModelFlowModule module) {
		ExecutionTrace trace = module.getContext().getExecutionTrace();
		return new ExecutionTraceAnalyser(trace).allTasksWithState(state);
	}
	
	@Override
	public String expected() {
		return "All " + state.name();
	}

	public static final AllTaskStateValidator EXECUTED = new AllTaskStateValidator(TaskState.EXECUTED);
	
	public static final AllTaskStateValidator SKIPPED = new AllTaskStateValidator(TaskState.SKIPPED);
}
