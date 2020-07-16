/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.exception;

import org.eclipse.epsilon.common.module.ModuleElement;

public class MFExecutionException extends MFRuntimeException {

	private static final long serialVersionUID = 1858270595034950799L;

	public MFExecutionException() {
		super("Execution Exception");
	}
	
	public MFExecutionException(String message) {
		super(message);
	}
	
	public MFExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MFExecutionException(Throwable cause) {
		super(cause);
	}
	
	public MFExecutionException(ModuleElement ast, Throwable cause) {
		super(cause);
		setAst(ast);
	}

}
