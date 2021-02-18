/**
 * 
 */
package org.epsilonlabs.modelflow.dt.editor.outline;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentOutlinePage;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;
import org.epsilonlabs.modelflow.dt.action.ExecuteFromTaskAction;
import org.epsilonlabs.modelflow.dt.action.ExecuteTaskAction;
import org.epsilonlabs.modelflow.dt.action.ExecuteUpToTaskAction;
import org.epsilonlabs.modelflow.dt.action.ExecuteWorkflowAction;


public class MFModuleContentOutlinePage extends  ModuleContentOutlinePage{

	protected Action runAllAction;

	public MFModuleContentOutlinePage(IDocumentProvider documentProvider, AbstractModuleEditor editor,
			ILabelProvider labelProvider, ModuleContentProvider contentProvider) {
		super(documentProvider, editor, labelProvider, contentProvider);
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		// Add outline menu
		MenuManager manager= new MenuManager("ModelFlow Menu", ModelFlowPlugin.PLUGIN_ID + ".outline.menu");
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(this::fillContextMenu);

		final Menu menu = manager.createContextMenu(getControl());
		getControl().setMenu(menu);		
		getSite().registerContextMenu(ModelFlowPlugin.PLUGIN_ID + ".outline", manager, this);
		
		makeActions();
	}
	
	protected void fillContextMenu(IMenuManager manager) {
		ModuleElement selection = (ModuleElement) ((StructuredSelection) getSelection()).getFirstElement();
		if (selection instanceof ITaskModuleElement) {
			ITaskModuleElement task = (ITaskModuleElement)selection;
			manager.add(new ExecuteTaskAction(task));
			manager.add(new ExecuteUpToTaskAction(task));
			manager.add(new ExecuteFromTaskAction(task));
		}
		
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	protected void makeActions() {
		runAllAction = new ExecuteWorkflowAction();
	}
	
	
}

