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
public class MFInvalidParameterException extends MFRuntimeException {

	private static final long serialVersionUID = 6630439430699354288L;

	public MFInvalidParameterException() {
		super("Invalid Parameter Exception");
	}
	
	public MFInvalidParameterException(String message) {
		super(message);
	}
	
	public MFInvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFInvalidParameterException(Throwable cause) {
		super(cause);
	}
}
