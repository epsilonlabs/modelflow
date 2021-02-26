/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.egl.traceability.OutputFile;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EglTaskTrace;

public abstract class AbstractEglTask extends AbstractEpsilonTask {

	protected org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace eglTrace;

	protected Optional<Class<? extends EglTemplateFactory>> templateFactoryClazz = Optional.empty();
	protected Optional<File> outputRoot = Optional.empty();
	protected Collection<OutputFile> outputFiles = new ArrayList<>();
	
	@Param(key="outputRoot")
	public void setOutputRoot(String outputRoot) {
		this.outputRoot = Optional.of(new File(outputRoot));
	}
	
	@Param(key="outputRoot")
	public void setOutputRoot(File outputRoot) {
		this.outputRoot = Optional.of(outputRoot);
	}
	
	public File getOutputRoot() {
		return outputRoot.get();
	}
	
	@Param(key="templateFactoryClass")
	public void setTemplateFactory(Class<? extends EglTemplateFactory> templateFactory) {
		this.templateFactoryClazz = Optional.of(templateFactory);
	}
	
	public String getTemplateFactoryClass() {
		if (templateFactoryClazz.isPresent()){
			return templateFactoryClazz.get().getName();
		} 
		return null;	
	}
		
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (traces == null) {			
			Collection<TraceLink> trace = eglTrace.getTraceLinks();
			traces = trace.stream()
					.map(link -> new EglTaskTrace(link, this).init().getTrace())
					.collect(Collectors.toList());
			trace.clear();
			eglTrace = null;
		}
		return Optional.ofNullable(traces);
	}
	
}
