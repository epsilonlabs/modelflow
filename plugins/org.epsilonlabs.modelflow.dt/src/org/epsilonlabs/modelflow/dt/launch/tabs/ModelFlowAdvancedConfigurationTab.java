/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.launch.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowAdvancedConfigurationTab extends AbstractAdvancedConfigurationTab {

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// Intentionally left empty
	}

	@Override
	public EpsilonPlugin getPlugin() {
		return ModelFlowPlugin.getDefault();
	}

	@Override
	public String getLanguage(ILaunchConfiguration configuration) {
		return  "ModelFlow";
	}

}
