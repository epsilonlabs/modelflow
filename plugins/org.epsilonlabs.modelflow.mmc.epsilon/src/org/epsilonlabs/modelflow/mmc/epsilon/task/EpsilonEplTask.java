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
import java.util.stream.Collectors;

import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.context.IEplContext;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EplTaskTrace;

/** 
 * Produces a PatternMatchModel
 *
 */
public class EpsilonEplTask extends AbstractEpsilonTask implements ITaskInstance {

	protected Integer maxLoops = -1;
	protected Boolean repeatWhileMatches = false;

	@SuppressWarnings("unchecked")
	@Override
	public IEplModule getModule() {
		if (module == null) {
			this.module = new EplModule();
		}
		return (IEplModule) this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEplTask.class);
		}

		@Override
		public String getName() {
			return "epl";
		}
	}

	@Param(key = "maxLoops")
	public void setMaxLoops(Integer loops) {
		if (maxLoops >= -1) {
			this.maxLoops = loops;
		}
	}

	public Integer getMaxLoops() {
		return maxLoops;
	}
	
	@Param(key = "repeatWhileMatches")
	public void setRepeatWhileMatches(Boolean repeat) {
		this.repeatWhileMatches = repeat;
	}
	
	public Boolean getRepeatWhileMatches() {
		return repeatWhileMatches;
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {
		super.validateParameters();
		getModule().setMaxLoops(maxLoops);
		getModule().setRepeatWhileMatches(repeatWhileMatches);
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (traces == null) {			
			Collection<PatternMatch> trace = ((IEplContext) getModule().getContext()).getPatternMatchTrace().getMatches();
			traces = trace.stream()
					.map(patternMatch -> new EplTaskTrace(patternMatch, this).init().getTrace())
					.collect(Collectors.toList());
		}
		return Optional.of(traces);
	}

}