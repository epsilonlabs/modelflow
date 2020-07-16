/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.wizard;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewModelFlowFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New ModelFlow Workflow";
	}

	@Override
	public String getExtension() {
		return "mflow";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new ModelFlow workflow file with *.mflow extension";
	}

}
