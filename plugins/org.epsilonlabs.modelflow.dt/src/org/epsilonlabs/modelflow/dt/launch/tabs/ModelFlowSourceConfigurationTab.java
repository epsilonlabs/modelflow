/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.launch.tabs;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowSourceConfigurationTab extends AbstractSourceConfigurationTab implements SelectionListener {
	
	protected Button enableTracingCheckbox;
	protected Button protectOutputsCheckbox;
	protected Button interactiveModeCheckbox;
	protected Button saveTraceCheckbox;
	protected Text tracePathText;
	
	@Override
	public EpsilonPlugin getPlugin() {
		return ModelFlowPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/mflow.gif";
	}

	@Override
	public String getSelectionTitle() {
		return "Select ModelFlow program source";
	}

	@Override
	public String getSelectionSubtitle() {
		return "ModelFlow program in workspace";
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		
		configuration.setAttribute(ModelFlowModule.PROTECT_OUTPUT_ATTRIBUTE, true);
		configuration.setAttribute(ModelFlowModule.INTERACTIVE_ATTRIBUTE, true);
		configuration.setAttribute(ModelFlowModule.END_TO_END_TRACING_ATTRIBUTE, true);
		configuration.setAttribute(ModelFlowModule.SAVE_END_TO_END_ATTRIBUTE, false);
		configuration.setAttribute(ModelFlowModule.END_TO_END_TRACE_LOCATION_ATTRIBUTE, "");
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);		
		try {
			interactiveModeCheckbox.setSelection(configuration.getAttribute(ModelFlowModule.INTERACTIVE_ATTRIBUTE, true));
			if (!interactiveModeCheckbox.getSelection()) {				
				protectOutputsCheckbox.setSelection(configuration.getAttribute(ModelFlowModule.PROTECT_OUTPUT_ATTRIBUTE, true));			
			} else {
				protectOutputsCheckbox.setEnabled(false);
			}
			enableTracingCheckbox.setSelection(configuration.getAttribute(ModelFlowModule.END_TO_END_TRACING_ATTRIBUTE, true));
			saveTraceCheckbox.setSelection(configuration.getAttribute(ModelFlowModule.SAVE_END_TO_END_ATTRIBUTE, false));
			tracePathText.setText(configuration.getAttribute(ModelFlowModule.END_TO_END_TRACE_LOCATION_ATTRIBUTE, ""));
			tracePathText.setEnabled(saveTraceCheckbox.getSelection());
		} catch (Exception e) {
			// Ignore
		}
		update();
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		boolean protect = protectOutputsCheckbox.isEnabled() ? protectOutputsCheckbox.getSelection() : Boolean.TRUE;
		configuration.setAttribute(ModelFlowModule.PROTECT_OUTPUT_ATTRIBUTE, protect);
		configuration.setAttribute(ModelFlowModule.INTERACTIVE_ATTRIBUTE, interactiveModeCheckbox.getSelection());
		configuration.setAttribute(ModelFlowModule.END_TO_END_TRACING_ATTRIBUTE, enableTracingCheckbox.getSelection());
		configuration.setAttribute(ModelFlowModule.SAVE_END_TO_END_ATTRIBUTE, saveTraceCheckbox.getSelection());
		configuration.setAttribute(ModelFlowModule.END_TO_END_TRACE_LOCATION_ATTRIBUTE, tracePathText.getText());
		IFolder containers = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(filePath.getText()));
		Arrays.asList(containers).stream().map(IFolder::getProject).distinct().findFirst().ifPresent(e->{
			configuration.setAttribute(ModelFlowModule.BASEDIR,e.getRawLocation().toString());
		});
		configuration.setAttribute(ModelFlowModule.EXECUTION_TRACE_LOCATION_ATTRIBUTE,getExecutionTracePathForActiveEditor());
	}

	/** 
	 * Overriding to include the modelflow part in the default control
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		extras.setLayout(new GridLayout(1, true));
		createModelFlowExecutionConfigurationGroup(extras);
		createModelFlowTraceConfigurationGroup(extras);
	}
	
	/** 
	 * Creates the ModelFlow control
	 * @param control
	 */
	protected void createModelFlowExecutionConfigurationGroup(Composite control) {
		Group group = createGroup(control, "Execution Configuration", 2);
		
		// Interactive Mode
		Label interactiveModeLabel = new Label(group, SWT.NONE);
		interactiveModeLabel.setText("Interactive mode: ");
		interactiveModeLabel.setToolTipText("TODO");
		
		interactiveModeCheckbox = new Button(group, SWT.CHECK);
		interactiveModeCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		interactiveModeCheckbox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				if (btn.getSelection()) {					
					protectOutputsCheckbox.setSelection(false);
					protectOutputsCheckbox.setEnabled(false);
				} else {
					protectOutputsCheckbox.setEnabled(true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				protectOutputsCheckbox.setSelection(false);
				protectOutputsCheckbox.setEnabled(false);
			}
		});
		interactiveModeCheckbox.addSelectionListener(this);
				
		// Protect Resources
		Label protectOutputsLabel = new Label(group, SWT.NONE);
		protectOutputsLabel.setText("Protect outputs: ");
		protectOutputsLabel.setToolTipText("TODO");
		
		protectOutputsCheckbox = new Button(group, SWT.CHECK);
		protectOutputsCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		protectOutputsCheckbox.addSelectionListener(this);
		
		// Clean Traces
		Button clearTracesButton = new Button(group, SWT.PUSH);
		clearTracesButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		clearTracesButton.setText("Clear execution traces");
		clearTracesButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {		
				new Path(getExecutionTracePathForActiveEditor()).toFile().delete();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing				
			}
		});
	}
	
	/** 
	 * Creates the ModelFlow control
	 * @param control
	 */
	protected void createModelFlowTraceConfigurationGroup(Composite control) {
		Group group = createGroup(control, "End-to-End Trace Configuration", 3);
		
		// End-to-End Tracing
		Label enableTracingLabel = new Label(group, SWT.NONE);
		enableTracingLabel.setText("Enable end-to-end tracing: ");
		enableTracingLabel.setToolTipText("TODO");
		
		enableTracingCheckbox = new Button(group, SWT.CHECK);
		enableTracingCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		enableTracingCheckbox.addSelectionListener(this);
		
		new Label(group, SWT.NONE);
		
		Label saveTraceLabel = new Label(group, SWT.NONE);
		saveTraceLabel.setText("Save trace: ");
		saveTraceLabel.setToolTipText("TODO");
		
		saveTraceCheckbox = new Button(group, SWT.CHECK);
		saveTraceCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		saveTraceCheckbox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				tracePathText.setEnabled(btn.getSelection());
				if (btn.getSelection() && tracePathText.getText().isEmpty()) {
					tracePathText.setText(getTracePathForActiveEditor());
				}
				update();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			
		});
		
		new Label(group, SWT.NONE);
		
		// Custom Location
		
		Label traceLocationLabel = new Label(group, SWT.NONE);
		traceLocationLabel.setText("Trace location: ");
		traceLocationLabel.setToolTipText("TODO");
		
		GridData filePathData = new GridData(GridData.FILL_HORIZONTAL);
		tracePathText = new Text(group, SWT.BORDER);
		tracePathText.setLayoutData(filePathData);
		tracePathText.addModifyListener(this);
		
		createBrowseWorkspaceForFileButton(group, tracePathText);
	}

	/** 
	 * Selection Listener Method. Similar to what {@link #modifyText(org.eclipse.swt.events.ModifyEvent)} does.
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		update();
	}
	
	/** 
	 * Selection Listener Method. Similar to what {@link #modifyText(org.eclipse.swt.events.ModifyEvent)} does.
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		update();
	}
	
	/** 
	 * Shortcut for the update proceedure. Should be related to {@link #modifyText(org.eclipse.swt.events.ModifyEvent)}.
	 */
	protected void update() {
		canSave();
		updateLaunchConfigurationDialog();
	}
	
	/**
	 * Locates the active editor and proposes a location to store the trace
	 * @return
	 */
	public String getTracePathForActiveEditor(){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorPart part = page.getActiveEditor();
		
		if (part.getEditorInput() instanceof FileEditorInput){
			FileEditorInput fileEditorInput = (FileEditorInput) part.getEditorInput();
			IPath fullPath = fileEditorInput.getFile().getFullPath();
			return fullPath.removeFileExtension().addFileExtension(ModelFlowModule.MANAGEMENT_TRACE_EXTENSION).toString();
		}
		return "";
	}
	
	public String getExecutionTracePathForActiveEditor(){	
		java.nio.file.Path mflowHome = Paths.get(System.getProperty("user.home"),"." + ModelFlowModule.EXTENSION);
		if (!mflowHome.toFile().exists()) {
			mflowHome.toFile().mkdirs();
		}
		java.nio.file.Path resolve = mflowHome.resolve(filePath.getText().substring(1) + "." + ModelFlowModule.EXECUITON_TRACE_EXTENSION);
		File file = resolve.toFile();
		file.getParentFile().mkdirs();
		return resolve.toString();		
	}
	
}
