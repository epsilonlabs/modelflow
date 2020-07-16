/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epsilon.common.dt.AbstractEpsilonUIPlugin;
import org.epsilonlabs.modelflow.Setup;
import org.osgi.framework.BundleContext;

public class ModelFlowPlugin extends AbstractEpsilonUIPlugin {

	public static final String PLUGIN_ID = "org.epsilonlabs.modelflow.dt"; //$NON-NLS-1$

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Setup.getInstance().init();
	}
	public static ModelFlowPlugin getDefault() {
		return (ModelFlowPlugin) plugins.get(ModelFlowPlugin.class);
	}
	
	public static void log(String message){
		getDefault().getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
	}
	
	public static void warn(String message){
		getDefault().getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, message));
	}
	
	public static void error(String message){
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message));
	}
		
}
