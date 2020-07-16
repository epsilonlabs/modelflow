/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.resource;

public enum ResourceKind {
	
	INPUT, OUTPUT, INOUT, TRANSIENT; 
	
	public boolean isOutput(){
		return this.equals(OUTPUT);
	}
	
	public boolean isInput(){
		return this.equals(INPUT);
	}
	
	public boolean isInout(){
		return this.equals(INOUT);
	}


}