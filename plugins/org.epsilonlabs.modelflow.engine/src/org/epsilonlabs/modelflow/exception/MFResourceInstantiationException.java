/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFResourceInstantiationException extends MFRuntimeException {

	private static final long serialVersionUID = -5262511867883417083L;

	public MFResourceInstantiationException() {
		super("Resource Instantiation Exception");
	}
	
	public MFResourceInstantiationException(String message) {
		super(message);
	}
	
	public MFResourceInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFResourceInstantiationException(Throwable cause) {
		super(cause);
	}
}
