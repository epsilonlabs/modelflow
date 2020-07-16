/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.validator;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleValidator;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.epsilonlabs.modelflow.ModelFlowModule;

public class ModelFlowModuleValidator extends AbstractModuleValidator {

	@Override
	public List<ModuleMarker> validate(IModule module) {
		if (module instanceof ModelFlowModule) {
			return ((ModelFlowModule) module).getCompilationContext().getMarkers();
		}
		return Collections.emptyList();
	}

}
