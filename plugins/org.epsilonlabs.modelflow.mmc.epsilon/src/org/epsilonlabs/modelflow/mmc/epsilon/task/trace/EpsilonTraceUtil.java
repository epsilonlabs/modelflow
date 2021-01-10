/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.mmc.epsilon.task.AbstractEpsilonTask;

public class EpsilonTraceUtil {

	public static String getElementId(AbstractEpsilonTask task, Object element) {
		ModelRepository modelRepository = task.getModule().getContext().getModelRepository();
		IModel model = modelRepository.getOwningModel(element);
		try {
			String elementId = model.getElementId(element);
			return elementId;
		} catch (Exception e) {
			return "";
		}
	}

	public static IAbstractResource getContainerModel(AbstractEpsilonTask task, Object element) {
		ModelRepository modelRepository = task.getModule().getContext().getModelRepository();
		IModel model = modelRepository.getOwningModel(element);
		for (IModelWrapper r : task.getResources()) {
			if (model.equals(r.getModel())) {
				return r.getResource();
			}
		}
		return null;
	}

}
