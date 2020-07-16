/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.Setup;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;

public class ModelFlowLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {

	@Override
	public String getLanguage() {
		return "ModelFlow";
	}

	@Override
	protected ModelFlowDebugger createDebugger() {
		return new ModelFlowDebugger();
	}

	@Override
	public IModelFlowModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			IModelFlowModule module = (IModelFlowModule) ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
			module.setTaskFactoryRegistry(Setup.getInstance().getTaskFactoryRegistry());
			module.setResFactoryRegistry(Setup.getInstance().getResourceFactoryRegistry());
			return module;
		} catch (CoreException e) {
			ModelFlowPlugin.error(e.getMessage());
		}
		return null;
	}
}
