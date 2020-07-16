/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

import org.eclipse.epsilon.eol.exceptions.EolInternalException;

/**
 * @author Betty Sanchez
 *
 */
public class MFInternalException extends EolInternalException {

	
	private static final long serialVersionUID = 6343868826306842512L;

	public MFInternalException(Throwable internal) {
		super(internal);
	}

}
