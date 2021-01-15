/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.task;

import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.core.factory.AbstractCoreTaskFactory;

/**
 * @author Betty Sanchez
 *
 */
public class PrintTask extends AbstractTaskInstance {

	/** FACTORY */

	public static class Factory extends AbstractCoreTaskFactory {

		public Factory() {
			super(PrintTask.class);
		}

		@Override
		public String getName() {
			return "print";
		}

	}
	
	protected String string = "";
	
	@Param(key = "text")
	public void setText(String string){
		this.string = string;
	}
	
	public String getText() {
		return string;
	}
	
	@Override
	public boolean isAlwaysExecute() {
		return true;
	}
	
	@Override
	public void validateParameters() throws MFRuntimeException {
		// Nothing to validate
	}

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		// Do nothing
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		ctx.getOutputStream().println(string);
	}

	@Override
	public void afterExecute() throws MFRuntimeException {
		// Do nothing
	}

	@Override
	public void processModelsAfterExecution() {
		// Do nothing
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

}
