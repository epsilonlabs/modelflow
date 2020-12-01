/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowConfiguration implements IModelFlowConfiguration {

	protected boolean saveEndToEndTraces = false;
	protected File executionTraceLocation;
	protected File endToEndTraceLocation;
	
	protected IModelFlowModule module;
	
	public ModelFlowConfiguration(IModelFlowModule module) {
		this.module = module;
	}
	
	@Override
	public void setExecutionTraceLocation(File file) {
		this.executionTraceLocation = file;
	}

	@Override
	public File getExecutionTraceFile() {
		File file = module.getFile();
		if (executionTraceLocation == null && file != null && file.exists()) {
			String execTraceName = file.getName().replace(ModelFlowModule.EXTENSION, ModelFlowModule.EXECUITON_TRACE_EXTENSION);
			executionTraceLocation = Paths.get(file.getParent()).resolve(execTraceName).toFile();
		}
		return executionTraceLocation;
	}
	
	@Override
	public void setEndToEndTraceLocation(File file) {
		this.endToEndTraceLocation = file;
	}

	@Override
	public File getEndToEndTraceFile() {
		return endToEndTraceLocation;
	}
	
	@Override
	public void setEndToEndTraceLocation(String location) {
		this.endToEndTraceLocation = new File(location);
	}
	
	@Override
	public void setSaveEndToEndTraces(boolean save) {
		this.saveEndToEndTraces = save;
	}
	
	@Override
	public boolean isSaveEndToEndTraces() {
		return this.saveEndToEndTraces;
	}

}
