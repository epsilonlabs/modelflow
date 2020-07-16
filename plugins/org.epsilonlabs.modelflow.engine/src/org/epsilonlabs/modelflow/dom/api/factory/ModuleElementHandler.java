/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import org.eclipse.epsilon.common.module.ModuleElement;

/**
 * @author Betty Sanchez
 *
 */
public class ModuleElementHandler {

	private final ModuleElement e;
	
	public ModuleElementHandler(ModuleElement e ) {
		this.e = e;
	}

	public int getIdentifier() {
		if (e.getParent() == null) {
			return 1;
		} else {
			int indexOf = e.getParent().getChildren().indexOf(e) + 1;
			ModuleElementHandler h = new ModuleElementHandler(e.getParent());
			String newIdentifier = String.format("%d%d", h.getIdentifier(), indexOf);
			return Integer.valueOf(newIdentifier);
		}
	}

}
