/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFInstantiationException extends MFRuntimeException {

	private static final long serialVersionUID = -7240374728970305532L;

	public MFInstantiationException() {
		super("Instantiation Exception");
	}
	
	public MFInstantiationException(String message) {
		super(message);
	}
	
	public MFInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFInstantiationException(Throwable cause) {
		super(cause);
	}
}
