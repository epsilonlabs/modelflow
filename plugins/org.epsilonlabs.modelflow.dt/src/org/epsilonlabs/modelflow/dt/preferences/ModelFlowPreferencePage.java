/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.preferences;

import java.util.ArrayList;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.epsilonlabs.modelflow.ModelFlowModule;

public class ModelFlowPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	protected final ArrayList<FieldEditor> fieldEditors = new ArrayList<>();
	protected IPreferenceStore preferences = EpsilonCommonsPlugin.getDefault().getPreferenceStore();
	
	@Override
	protected Control createContents(Composite parent) {
		
		final Composite composite = new Composite(parent, SWT.FILL);
		
		final DirectoryFieldEditor executionTraceDirectoryFieldEditor = new DirectoryFieldEditor(ModelFlowModule.EXECUTION_TRACE_LOCATION_ATTRIBUTE, "Execution trace base directory", composite);
		
		fieldEditors.add(executionTraceDirectoryFieldEditor);
		
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.setPreferenceStore(preferences);
			fieldEditor.load();
		}
		return composite;		
		
	}
	
	@Override
	public void init(IWorkbench workbench) {
		// Do nothing
	}
	
	@Override
	public boolean performOk() {
		super.performOk();
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.store();
		}
		return true;
	}

}
