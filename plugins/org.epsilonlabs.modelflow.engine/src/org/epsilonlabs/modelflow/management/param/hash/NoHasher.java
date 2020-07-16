/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param.hash;

public class NoHasher implements IHasher<Object,String> {

	@Override
	public String fromExecutionTrace(String trace) {
		return "";
	}

	@Override
	public String fromTaskPopulatedParameter(Object outputType) {
		return "";
	}

}
