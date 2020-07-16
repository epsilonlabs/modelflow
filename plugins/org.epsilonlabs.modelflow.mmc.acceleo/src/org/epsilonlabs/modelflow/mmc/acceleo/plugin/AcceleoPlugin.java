/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.acceleo.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;

public class AcceleoPlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.eclipse.acceleo";
	}

	@Override
	public String getReadableName() {
		return "Acceleo";
	}

	@Override
	public String getVersion() {
		return "1.6.0"; // FIXME
	}

	@Override
	public List<Class<? extends IModelResourceFactory>> getResourceFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<? extends ITaskFactory>> getTaskFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
