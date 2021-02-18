package org.epsilonlabs.modelflow.dt.action;

import org.eclipse.jface.action.Action;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;

public class ExecuteUpToTaskAction extends Action {
	protected ITaskModuleElement task;
	
	public ExecuteUpToTaskAction(ITaskModuleElement element){
		super("Execute up to this task");
		this.task = element;
	}
	
	@Override
	public void run() {
		// TODO 
	}
}