package org.epsilonlabs.modelflow.dt.action;

import org.eclipse.jface.action.Action;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;

public class ExecuteTaskAction extends Action {
	
	protected ITaskModuleElement task;

	public ExecuteTaskAction(ITaskModuleElement element) {
		super("Execute Task");
		this.task = element;
	}

	@Override
	public void run() {
		// TODO
	}
}