/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

/**
 * @author Betty Sanchez
 *
 */
public class MFExecutionGraphExeption extends MFRuntimeException {

	private static final long serialVersionUID = -3856245963820521665L;

	public MFExecutionGraphExeption() {
		super("Execution Graph Exception");
	}
	
	public MFExecutionGraphExeption(String message) {
		super(message);
	}
	
	public MFExecutionGraphExeption(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFExecutionGraphExeption(Throwable cause) {
		super(cause);
	}
	
}
