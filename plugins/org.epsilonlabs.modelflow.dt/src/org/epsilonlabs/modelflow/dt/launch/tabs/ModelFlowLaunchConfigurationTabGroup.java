/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.launch.tabs;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;

public class ModelFlowLaunchConfigurationTabGroup extends EpsilonLaunchConfigurationTabGroup {

	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new ModelFlowSourceConfigurationTab();
	}

	@Override
	public ILaunchConfigurationTab getAdvancedConfigurationTab() {
		return new ModelFlowAdvancedConfigurationTab();
	}

}
