/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EclTaskTrace;

@Definition(name = "epsilon:ecl")
public class EpsilonEclTask extends AbstractEpsilonTask{

	protected String matchTraceName;

	@SuppressWarnings("unchecked")
	@Override
	public IEclModule getModule() {
		if (module == null) {
			this.module = new EclModule();
		}
		return (IEclModule) this.module;
	}

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		super.acceptModels(models);
		Arrays.asList(models).stream()
			.filter(m -> m.getModel() instanceof MatchTrace)
			.findFirst()
			.ifPresent(m-> getModule().getContext().setMatchTrace( ((MatchTrace) m) ) );
	}
	
	@Output(key="comparison")
	public MatchTrace getMatchTrace() {
		return getModule().getContext().getMatchTrace();
	}
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (traces == null) {			
			MatchTrace trace = getMatchTrace();
			traces = trace.stream()
					.map(match -> new EclTaskTrace(match, this).init().getTrace())
					.collect(Collectors.toList());
		}
		return Optional.of(traces);
	}

}