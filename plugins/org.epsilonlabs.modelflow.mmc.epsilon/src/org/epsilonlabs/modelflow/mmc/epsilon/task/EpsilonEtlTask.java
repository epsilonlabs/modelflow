/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.trace.Transformation;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EtlTaskTrace;

@Definition(name = "epsilon:etl")
public class EpsilonEtlTask extends AbstractEpsilonTask {

	@SuppressWarnings("unchecked")
	@Override
	public IEtlModule getModule() {
		if (module == null) {
			this.module = new EtlModule();
		}
		return (IEtlModule) this.module;
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		Collection<Transformation> trace = getModule().getContext().getTransformationTrace().getTransformations();
		List<Trace> traces = trace.stream()
				.map(transformation -> new EtlTaskTrace(transformation, this).init().getTrace())
				.collect(Collectors.toList());
		return Optional.of(traces);
	}
}