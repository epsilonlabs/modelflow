/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFUnknownTaskException extends MFRuntimeException {

	private static final long serialVersionUID = 4593167454351881600L;

	public MFUnknownTaskException(String message) {
		super(message);
	}
	
	public MFUnknownTaskException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFUnknownTaskException(Throwable cause) {
		super(cause);
	}
}
