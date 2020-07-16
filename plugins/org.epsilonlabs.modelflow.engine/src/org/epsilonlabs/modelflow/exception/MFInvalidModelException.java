/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFInvalidModelException extends MFRuntimeException {

	private static final long serialVersionUID = -2918420918255729828L;

	public MFInvalidModelException() {
		super("Invalid Model Exception");
	}
	
	public MFInvalidModelException(String message) {
		super(message);
	}
	
	public MFInvalidModelException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFInvalidModelException(Throwable cause) {
		super(cause);
	}
	
}
