/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

public class MFUnknownPropertyException extends MFInstantiationException {

	private static final long serialVersionUID = 2525344683326312589L;

	public MFUnknownPropertyException(String key, String task) {
		super(String.format("Unknown property '%s' for task '%s'", key, task));
	}
}
