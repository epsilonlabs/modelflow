/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFInvalidFactoryException extends MFRuntimeException {

	private static final long serialVersionUID = 2150298134373009709L;
	
	public MFInvalidFactoryException() {
		super("Invalid Factory Exception");
	}
	
	public MFInvalidFactoryException(String message) {
		super(message);
	}
	
	public MFInvalidFactoryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFInvalidFactoryException(Throwable cause) {
		super(cause);
	}

}
