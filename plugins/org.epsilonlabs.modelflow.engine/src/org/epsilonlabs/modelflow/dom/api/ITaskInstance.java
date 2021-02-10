/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;

/**
 * The Interface ITask.
 */
public interface ITaskInstance {
	
	/**
	 * After invoking the parameter setters of the tasks, 
	 * this method should validate that their values are in fact correct,
	 * and that the task is ready to proceed with the execution, 
	 * populating any missing parameters required for execution.
	 *
	 * @throws MFRuntimeException the MF runtime exception
	 */
	void validateParameters() throws MFRuntimeException;
	
	/**
	 * Methods that receives models and decides how to use them in the execution.
	 * Must throw an exception if models aren't used, if they are of 
	 * wrong type or are in the wrong format.
	 *
	 * @param models the models
	 * @throws MFInvalidModelException the MF invalid model exception
	 */
	void acceptModels(IModelWrapper[] models) throws MFInvalidModelException;

	/**
	 *   
	 * Main Execution.
	 *
	 * @param ctx Context
	 * @throws MFRuntimeException the MF runtime exception
	 */
	void execute(IModelFlowContext ctx) throws MFRuntimeException;
	
	/**
	 *  
	 * Clean up method.
	 *
	 * @throws MFRuntimeException the MF runtime exception
	 */
	void afterExecute() throws MFRuntimeException;

	/** 
	 * In case the tasks used infromation from the models but not the actual model, 
	 * this is the method where they must be synchronised. 
	 */
	void processModelsAfterExecution();
	
	/**
	 *  
	 * Model management trace in ModelFlow's format.
	 *
	 * @return the trace
	 */
	Optional<Collection<Trace>> getTrace();
	
	default boolean isAlwaysExecute() {
		return false;
	}

	/**
	 * 
	 */
	default void beforeExecute() {}
	
}
