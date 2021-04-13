/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param.hash;

public class DefaultInputHasher implements IHasher<Object, Object>{

	@Override
	public Object fromExecutionTrace(Object trace) {
		return trace;
	}

	@Override
	public Object fromEvaluatedParameter(Object taskParameterReturnType) {
		return Hasher.hash(taskParameterReturnType);
	}

}
