/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eml.trace.MergeTrace;
import org.eclipse.epsilon.etl.trace.TransformationTrace;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EmlTaskTrace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EtlTaskTrace;

/** 
 * May use a pre-existing match trace, 
 * Produces a merge trace model and a transformation trace model
 * 
 */
public class EpsilonEmlTask extends AbstractEpsilonTask implements ITask {

	@SuppressWarnings("unchecked")
	@Override
	public IEmlModule getModule() {
		if (module == null) {
			this.module = new EmlModule();
		}
		return (IEmlModule) this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEmlTask.class);
		}

		@Override
		public String getName() {
			return "eml";
		}

	}
	
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		super.acceptModels(models);
		Arrays.asList(models).stream()
			.map(m -> m.getModel())
			.filter(m -> m instanceof MatchTrace)
			.map(m -> (MatchTrace) m)
			.findFirst()
			.ifPresent(m-> getModule().getContext().setMatchTrace( m.getReduced() ) );
	}

	@Output(key="transformation")
	public TransformationTrace getTransformation() {
		return getModule().getContext().getTransformationTrace();
	}
	
	@Output(key="merge")
	public MergeTrace getMatchTrace() {
		return getModule().getContext().getMergeTrace();
	}
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (traces == null) {
			List<Trace> mergeTraces = getModule().getContext().getMergeTrace().stream()
					.map(merge -> new EmlTaskTrace(merge, this).init().getTrace())
					.collect(Collectors.toList());
			
			List<Trace> transformationTraces = getModule().getContext().getTransformationTrace().getTransformations().stream()
				.map(transformation -> new EtlTaskTrace(transformation, this).init().getTrace())
				.collect(Collectors.toList());
			
			traces = new ArrayList<Trace>();
			traces.addAll(mergeTraces);
			traces.addAll(transformationTraces);
		}
		return Optional.of(traces);
	}
	
}