package org.epsilonlabs.modelflow.dt.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;

public class ExecuteWorkflowAction extends Action {
	
	public ExecuteWorkflowAction(){
		super("Execute workflow");
	}
	
	@Override
	public void run() {
		System.out.println(getText());
	}
	
	@Override
	public String getToolTipText() {
		return "Run the whole workflow";
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ModelFlowPlugin.getDefault().getImageDescriptor("icons/run.gif");
	}
	
}