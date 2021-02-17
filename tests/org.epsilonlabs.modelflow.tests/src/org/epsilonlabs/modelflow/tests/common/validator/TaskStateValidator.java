package org.epsilonlabs.modelflow.tests.common.validator;

import java.util.Objects;

import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.tests.common.ExecutionTraceAnalyser;

public class TaskStateValidator implements ITaskValidator {

	protected final TaskState expected;
	protected final String taskName;
	protected TaskState actual;

	public TaskStateValidator(TaskState state, String taskName) {
		this.expected = state;
		this.taskName = taskName;
	}
	
	@Override
	public String expected() {
		return String.format("Task %s expected %s but was %s", taskName, expected.name(), actual.name());
	}
	
	public TaskState getActualTask() {
		return actual;
	}
	
	public TaskState getExpectedTask() {
		return expected;
	}

	@Override
	public Boolean ok(IModelFlowModule module) throws Exception {
		ExecutionTrace trace = module.getContext().getExecutionTrace();
		final String result = new ExecutionTraceAnalyser(trace).taskState(taskName);
		actual = TaskState.valueOf(result);
		return Objects.equals(actual, expected);
	}

}
