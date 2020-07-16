/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.egl.engine.traceability.fine.EglFineGrainedTraceContextAdaptor;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.EglHasher;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.ProtectedFiles;

public class EpsilonEglTask extends AbstractEglTask implements ITask {

	protected Optional<String> target = Optional.empty();
	
	@SuppressWarnings("unchecked")
	@Override
	public IEglModule getModule() {
		if (module == null) {
			this.module = new EglTemplateFactoryModuleAdapter(new EglFileGeneratingTemplateFactory());
		}
		return (IEglModule) this.module;
	}
	
	@Param(key="target")
	public void setTarget(String target) {
		this.target = Optional.of(target);
	}
	
	public String getTarget() {
		return target.get();
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEglTask.class);
		}

		@Override
		public String getName() {
			return "egl";
		}

	}
	
	@Override
	protected void beforeParse() throws MFExecutionException {
		super.beforeParse();
		if (templateFactoryClazz.isPresent()) {			
			try {
				getModule().setTemplateFactory(templateFactoryClazz.get().getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				throw new MFExecutionException(e);
			}
		}
		if (getModule().getTemplateFactory() instanceof EglFileGeneratingTemplateFactory && outputRoot.isPresent()) {
			try {
				((EglFileGeneratingTemplateFactory) getModule().getTemplateFactory()).setOutputRoot(outputRoot.get().getAbsolutePath());
			} catch (EglRuntimeException e) {
				throw new MFExecutionException(e);
			}
		}
		eglTrace = new EglFineGrainedTraceContextAdaptor().adapt(getModule().getContext());
	}
		
	@Override
	public void afterExecute() throws MFExecutionException {
		super.afterExecute();
		
		if (target.isPresent()) {			
			if (!outputRoot.isPresent()) {
				// use basedir
			}

			String location = target.get();

			EglFileGeneratingTemplate eglFileGeneratingTemplateFactory = (EglFileGeneratingTemplate) getModule().getCurrentTemplate();
			try {
				eglFileGeneratingTemplateFactory.generate(location);
			} catch (EglRuntimeException e) {
				e.printStackTrace();
			}
			outputFiles = eglFileGeneratingTemplateFactory.getTemplate().getOutputFiles();
		}
	}
	
	@Output(key="outputFiles", hasher=EglHasher.class)
	public ProtectedFiles getOutputFiles() {
		Collection<String> files;
		if (outputFiles.isEmpty() && outputRoot.isPresent() && target.isPresent()) {
			files = Arrays.asList(outputRoot.get() +File.separator+ target.get());
		} else {			
			files = outputFiles.stream().map(f->f.getName()).collect(Collectors.toList());
		}
		CompositePartitioner partitioner = getModule().getContext().getPartitioner();
		return new ProtectedFiles(files, partitioner);
	}

}