package org.epsilonlabs.modelflow.dt.action;

import org.eclipse.jface.action.Action;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;

public class ExecuteFromTaskAction extends Action {
	protected ITaskModuleElement task;
	
	public ExecuteFromTaskAction(ITaskModuleElement element){
		super("Execute from this task");
		this.task = element;
	}
	
	@Override
	public void run() {
		// TODO 
	}
}