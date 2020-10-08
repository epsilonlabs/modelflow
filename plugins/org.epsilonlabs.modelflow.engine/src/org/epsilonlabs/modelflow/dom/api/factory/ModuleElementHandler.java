/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.io.Serializable;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.IModelFlowModule;

/**
 * @author Betty Sanchez
 *
 */
public class ModuleElementHandler implements Serializable {

	private static final long serialVersionUID = 6230895397012955951L;

	private Integer identifier;
	
	protected transient ModuleElement element; 
	
	public ModuleElementHandler(ModuleElement element){
		this.element = element;
		computeIdentifier();
	}
	
	public ModuleElementHandler(Integer identifier){
		this.identifier = identifier;
	}
	
	protected void computeIdentifier() {
		if (element.getParent() == null) {
			this.identifier = 1;
		} else {
			int indexOf = element.getParent().getChildren().indexOf(element) + 1;
			ModuleElementHandler h = new ModuleElementHandler(element.getParent());
			String newIdentifier = String.format("%d%d", h.getIdentifier(), indexOf);
			this.identifier = Integer.valueOf(newIdentifier);
		}
	}
	
	public void resolveModuleElement(IModelFlowModule module){
		// TODO fix
		this.element = null;
	}
	
	public Integer getIdentifier() {
		return identifier;
	}
	
	public ModuleElement getModuleElement(){
		return element;
	}

}
