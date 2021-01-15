/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.RuntimeTracer;

public class EpsilonEolTask extends AbstractEpsilonTask implements ITaskInstance {

	@SuppressWarnings("unchecked") 
	@Override
	public IEolModule getModule() {
		if (module == null) {
			this.module = new EolModule();
		}
		return this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEolTask.class);
		}

		@Override
		public String getName() {
			return "eol";
		}

	}

	protected RuntimeTracer tracer;
	
	@Output(key="result")
	public Object getResult() {
		return result;
	}
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (tracer !=null) {
			return Optional.of(tracer.getTraces());
		} 
		return Optional.empty();
	}

	@Override
	protected void beforeParse() throws MFExecutionException {
		super.beforeParse();
		FrameStack frameStack = getModule().getContext().getFrameStack();
		tracer = new RuntimeTracer(this);
		frameStack.putGlobal(Variable.createReadOnlyVariable("mfTrace", tracer));
	}
}