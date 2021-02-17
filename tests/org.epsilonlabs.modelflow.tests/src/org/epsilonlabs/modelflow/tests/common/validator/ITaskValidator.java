package org.epsilonlabs.modelflow.tests.common.validator;

import org.epsilonlabs.modelflow.execution.graph.node.TaskState;

public interface ITaskValidator extends IValidate{
		
	TaskState getExpectedTask();
	TaskState getActualTask();
	
}
