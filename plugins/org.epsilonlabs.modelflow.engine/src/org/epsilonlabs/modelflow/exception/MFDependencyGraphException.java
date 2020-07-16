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
public class MFDependencyGraphException extends MFRuntimeException {

	private static final long serialVersionUID = -1237175475275526453L;

	public MFDependencyGraphException() {
		super("Dependency Graph Exception");
	}
	
	public MFDependencyGraphException(String message) {
		super(message);
	}
	
	public MFDependencyGraphException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFDependencyGraphException(Throwable cause) {
		super(cause);
	}
	
}
