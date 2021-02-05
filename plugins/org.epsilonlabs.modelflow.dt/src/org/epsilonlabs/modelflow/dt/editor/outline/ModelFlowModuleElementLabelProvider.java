/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dt.editor.outline;

import org.eclipse.epsilon.erl.dom.NamedRule;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFResourceRule;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFTaskRule;
import org.epsilonlabs.modelflow.dt.ModelFlowPlugin;

public class ModelFlowModuleElementLabelProvider extends ErlModuleElementLabelProvider{

	@Override
	public Image getImage(Object element) {
		if (element instanceof EMFTaskRule) {
			return ModelFlowPlugin.getDefault().createImage("icons/process-rule.png"); 
		}
		if (element instanceof EMFResourceRule) {
			return ModelFlowPlugin.getDefault().createImage("icons/static.png"); 
		}
		return super.getImage(element);
	}
	

	@Override
	public String getText(Object element) {
		if (element instanceof NamedRule) {
			return ((NamedRule) element).getName(); 
		}
		return super.getText(element);
	}
}
