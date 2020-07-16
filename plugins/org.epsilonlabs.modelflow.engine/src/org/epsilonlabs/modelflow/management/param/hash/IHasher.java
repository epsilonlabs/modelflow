/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param.hash;

/**
 * The Interface IHasher.
 *
 * @param <O> the output type
 * @param <T> the trace type
 */
public interface IHasher<O,T> {

	/**
	 * Calculates the new hash from stable properties stored in the previous execution trace hash e.g. file locations 
	 * @param trace the hash returned by the past execution trace
	 * @return a new hash calculated from atemporal* properties from the previous trace
	 */
	T fromExecutionTrace(T trace);
	
	/**
	 * Calculates hash from a task parameter that is populated by invoking its getter from the task. 
	 * In case of inputs, this is called after task initiation but before execution, in case of outputs, this is called after execution. 
	 * @param taskParameterReturnType accepts the same object type as that returned by the method annotated with this class as hasher.
	 * @return the recently calculated hash which will be stored in the execution trace.
	 */
	T fromTaskPopulatedParameter(O taskParameterReturnType);

}
