/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.epsilonlabs.modelflow.dom.api.AbstractPlugin;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.mmc.core.task.FileReaderTask;
import org.epsilonlabs.modelflow.mmc.core.task.PrintTask;
import org.epsilonlabs.modelflow.mmc.core.task.SleepTask;
import org.epsilonlabs.modelflow.mmc.core.task.XsdValidationTask;

public class CorePlugin extends AbstractPlugin {

	@Override
	public String setFullyQualifiedId() {
		return "org.epsilonlabs.core";
	}

	@Override
	public String getReadableName() {
		return "Core";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}

	@Override
	public List<Class<? extends IModelResourceFactory>> getResourceFactories() {
		return Arrays.asList();
	}

	@Override
	public List<Class<? extends ITaskFactory>> getTaskFactories() {
		return Arrays.asList(FileReaderTask.Factory.class, PrintTask.Factory.class, SleepTask.Factory.class, XsdValidationTask.Factory.class);
	}

	@Override
	public List<Class<?>> getResourceInterfaces() {
		return Collections.emptyList();
	}

}
