/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.atl.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;

public class AtlPlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.eclipse.atl";
	}

	@Override
	public String getReadableName() {
		return "ATL";
	}

	@Override
	public String getVersion() {
		return "1.6.0"; //FIXME
	}

	@Override
	public List<Class<? extends IModelResourceInstance<?>>> getResourceFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<? extends ITaskInstance>> getTaskFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
