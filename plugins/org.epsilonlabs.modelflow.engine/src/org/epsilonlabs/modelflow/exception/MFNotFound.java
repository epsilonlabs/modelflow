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
public class MFNotFound extends MFRuntimeException {

	private static final long serialVersionUID = 9026923835362897046L;

	public MFNotFound(String message) {
		super(message);
	}
	
	public MFNotFound(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFNotFound(Throwable cause) {
		super(cause);
	}
}
