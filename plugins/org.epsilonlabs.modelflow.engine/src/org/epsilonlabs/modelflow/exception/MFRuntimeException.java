/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class MFRuntimeException extends EolRuntimeException {

	private static final long serialVersionUID = -1116046074324463941L;

	public MFRuntimeException(String message) {
		super(message);
	}
	
	public MFRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
