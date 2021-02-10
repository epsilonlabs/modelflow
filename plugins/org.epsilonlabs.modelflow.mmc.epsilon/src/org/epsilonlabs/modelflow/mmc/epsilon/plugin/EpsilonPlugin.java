/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.EpsilonEmfModelResource;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.EpsilonSimulinkModelResource;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.EpsilonXmlModelResource;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEclTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEglTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEgxTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEmlTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEolTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEplTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEtlTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEunitTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonEvlTask;
import org.epsilonlabs.modelflow.mmc.epsilon.task.EpsilonFlockTask;

public class EpsilonPlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.eclipse.epsilon";
	}

	@Override
	public String getReadableName() {
		return "Epsilon";
	}

	@Override
	public String getVersion() {
		return "1.6.0";
	}

	@Override
	public List<Class<? extends IModelResourceInstance<?>>> getResourceFactories() {
		return Arrays.asList(EpsilonEmfModelResource.class, EpsilonXmlModelResource.class,
				EpsilonSimulinkModelResource.class);
	}

	@Override
	public List<Class<? extends ITaskInstance>> getTaskFactories() {
		return Arrays.asList(EpsilonEolTask.class, EpsilonEclTask.class, EpsilonEvlTask.class,
				EpsilonEtlTask.class, EpsilonEglTask.class, EpsilonEgxTask.class,
				EpsilonEplTask.class, EpsilonEmlTask.class, EpsilonEunitTask.class,
				EpsilonFlockTask.class);
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
