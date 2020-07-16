package org.epsilonlabs.modelflow.tests.common.validator;

import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceAnalyser;

public class TaskStateValidator implements IValidate {

	protected TaskState state;
	protected String taskName;

	public TaskStateValidator(TaskState state, String taskName) {
		this.state = state;
		this.taskName = taskName;
	}
	
	@Override
	public String expected() {
		return taskName + " " + state.name();
	}

	@Override
	public Boolean ok(IModelFlowModule module) throws Exception {
		ExecutionTrace trace = module.getContext().getExecutionTrace();
		return new ExecutionTraceAnalyser(trace).taskWithState(taskName, state);	
	}

}
