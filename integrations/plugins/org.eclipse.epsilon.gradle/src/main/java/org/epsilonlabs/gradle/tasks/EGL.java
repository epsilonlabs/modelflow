package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.egl.engine.traceability.fine.EglFineGrainedTraceContextAdaptor;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.eol.IEolModule;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public class EGL extends AbstractEglTask {

	public static final String TASK_COMMAND = "egl";
	public static final String TASK_DESCRIPTION = "Executes model to text transformation";

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EGL.class);

	/** PROPERTIES WITH DEFAULTS */

	protected final Property<String> target;

	public EGL() {
		super();
		target = objectFactory.property(String.class);
		target.set("");
		
	}

	/** CONFIGURABLE */

	@Optional
	@Input
	public String getTarget() {
		return target.get();
	}

	public void setTarget(String output) {
		this.target.set(output);
	}

	/** ACTIONS */

	@Override
	protected IEolModule createModule() throws Exception {
		final IEolModule module;
		
		final EglTemplateFactory templateFactory = templateFactoryType.newInstance();

		try {
			if (templateFactory instanceof EglFileGeneratingTemplateFactory && outputRoot != null) {
				try {
					((EglFileGeneratingTemplateFactory) templateFactory)
							.setOutputRoot(outputRoot.getAbsolutePath());
				} catch (EglRuntimeException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) {}

		templateFactory.setDefaultFormatters(instantiateDefaultFormatters());

		module = (IEglModule) new EglTemplateFactoryModuleAdapter(templateFactory);
	
		if (shouldExportAsModel()) {
			trace.set(new EglFineGrainedTraceContextAdaptor()
					.adapt((IEglContext) module.getContext()));
		}

		return module;
	}

	
	@Override
	protected void examine() throws Exception {
		super.examine();

		if (!target.get().isEmpty()) {
			/*File output = getProject().getBuildDir();

			if (outputRoot != null) {
				output = outputRoot;
			}*/

			EglFileGeneratingTemplate eglFileGeneratingTemplateFactory = (EglFileGeneratingTemplate) ((IEglModule) module).getCurrentTemplate();
			try {
				eglFileGeneratingTemplateFactory.generate(target.get());
			} catch (EglRuntimeException e) {
				e.printStackTrace();
			}
			/*
			File file = new File(output, target.get());
			generatedFiles.add(file);
			FileWriter fw = new FileWriter(file);
			fw.write(String.valueOf(result));
			fw.flush();
			fw.close();*/
			eglFileGeneratingTemplateFactory.getTemplate().getOutputFiles().forEach(f->generatedFiles.add(f.getURI().getPath()));
			
		} 
	}

	

}
