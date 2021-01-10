/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.EglHasher;
import org.epsilonlabs.modelflow.mmc.epsilon.resource.hash.ProtectedFiles;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.egl.EgxEndToEndTraceContextAdaptor;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.egl.EgxListenableTemplateFactory;

@SuppressWarnings("unchecked")
public class EpsilonEgxTask extends AbstractEglTask implements ITaskInstance {

	@Override
	public IEgxModule getModule() {
		if (module == null) {
			/** Helper factory which adds missing listeners to executor */
			this.module = new EgxModule(new EgxListenableTemplateFactory());
		}
		return (IEgxModule) this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEgxTask.class);
		}

		@Override
		public String getName() {
			return "egx";
		}

	}
		
	@Override
	protected void beforeParse() throws MFExecutionException {
		super.beforeParse();
		if (templateFactoryClazz.isPresent()) {			
			try {
				EglTemplateFactory templateFactory = templateFactoryClazz.get().getDeclaredConstructor().newInstance();
				getModule().setTemplateFactory(templateFactory);
			} catch (Exception e) {
				throw new MFExecutionException(e);
			}
		}
		if (getModule().getTemplateFactory() instanceof EglFileGeneratingTemplateFactory && outputRoot.isPresent()) {
			try {
				EglFileGeneratingTemplateFactory templateFactory = (EglFileGeneratingTemplateFactory) getModule().getTemplateFactory();
				templateFactory.setOutputRoot(outputRoot.get().getAbsolutePath());
				templateFactory.getDefaultIncrementalitySettings().setOverwriteUnchangedFiles(true);
			} catch (EglRuntimeException e) {
				throw new MFExecutionException(e);
			}
		}
		eglTrace = new EgxEndToEndTraceContextAdaptor().adapt(getModule().getContext());
	}
	
	@Override
	public void afterExecute() throws MFExecutionException {
		super.afterExecute();
		
		/*getModule().getTemplateFactory().getTemplateExecutionListeners()
			.forEach(l -> getModule().getContext().getTemplateCache().entrySet()
				.forEach(t -> {
					if (t.getValue() instanceof EglPersistentTemplate) {						
						EglPersistentTemplate template = (EglPersistentTemplate)t.getValue();
						l.finishedGenerating(template, t.getKey().toString());
						template.getTemplate().getOutputFiles();
					}
				}));
		*/
		getModule().getContext().getInvokedTemplates()
			.forEach(t->{
				this.outputFiles.addAll(t.getOutputFiles());
			});		
		
	}
	
	
	@Input(key="templateFiles")
	public Set<File> getTemplatesFiles(){
		Set<File> files = new HashSet<>();
		for (GenerationRule s : getModule().getGenerationRules()) {
			try {
				Field field = s.getClass().getDeclaredField("templateBlock");
				field.setAccessible(true);
				ExecutableBlock<String> executableBlock = (ExecutableBlock<String>) field.get(s);
				String templateFile = executableBlock.execute(getModule().getContext());
				URI file = getModule().getTemplateFactory().resolveTemplate(templateFile);
				files.add(new File(file.getPath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return files;
	}
	
	@Output(key = "outputFiles", hasher=EglHasher.class)
	public ProtectedFiles getOutputFiles(){
		final Set<String> files = new HashSet<>();
		if (outputFiles.isEmpty()) {
			eglTrace.getTraceLinks().forEach(t->{
				String destination = t.getDestination().getResource();
				if (!destination.startsWith("file:/")){	
					files.add(destination);
				} else {
					//System.out.println(destination);
				}
			});
		} else {			
			outputFiles.stream().forEach(f->files.add(f.getURI().getPath()));
		}
		CompositePartitioner partitioner = getModule().getTemplateFactory().getContext().getPartitioner();
		return new ProtectedFiles(files, partitioner);
		
	}
	
	
}