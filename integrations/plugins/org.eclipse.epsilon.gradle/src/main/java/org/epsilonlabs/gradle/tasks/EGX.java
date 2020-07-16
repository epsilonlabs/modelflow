package org.epsilonlabs.gradle.tasks;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.egl.traceability.OutputFile;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.epsilonlabs.gradle.tasks.trace.EgxEndToEndTraceContextAdaptor;
import org.epsilonlabs.gradle.tasks.trace.EgxListenableTemplateFactory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;

public class EGX extends AbstractEglTask {

	public static final String TASK_COMMAND = "egx";
	public static final String TASK_DESCRIPTION = "Executes orchestrated model to text transformation";

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EGX.class);

	/** PROPERTIES WITH DEFAULTS */

	protected Set<File> templates, generated;
	
	public EGX() {
		super();
		templates = new HashSet<File>();
		generated = new HashSet<File>();
	}

	@Override
	public void setSrc(File src) {
		super.setSrc(src);
		try {
			EgxModule egxModule = new EgxModule();
			egxModule.parse(src);
			for (GenerationRule s : egxModule.getGenerationRules()) {
				try {
					Field field = s.getClass().getDeclaredField("templateBlock");
					field.setAccessible(true);
					ExecutableBlock<String> executableBlock = (ExecutableBlock<String>) field.get(s);
					String templateFile = executableBlock.execute(egxModule.getContext());
					URI file = egxModule.getTemplateFactory().resolveTemplate(templateFile);
					templates.add(new File(file.getPath()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {}
	}
	
	@Override
	public void setCode(String code) {
		super.setCode(code);
		try{
			EgxModule egxModule = new EgxModule();
			egxModule.parse(code);
			for (GenerationRule s : egxModule.getGenerationRules()) {
				try {
					Field field = s.getClass().getDeclaredField("templateBlock");
					field.setAccessible(true);
					ExecutableBlock<String> executableBlock = (ExecutableBlock<String>) field.get(s);
					String templateFile = executableBlock.execute(egxModule.getContext());
					URI file = egxModule.getTemplateFactory().resolveTemplate(templateFile);
					templates.add(new File(file.getPath()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {}
				
	}
	
	@InputFiles
	public Set<File> getTemplates(){
		//templates.forEach(t-> System.out.println("templ :"+t));
		return templates;
	}
	
	@OutputFiles
	public Iterable<File> getGenerated(){
		//System.out.println("IN EGX GENERATED");
		//System.out.println(generated.size());
		//generated.add(new File("/Users/bea/SW/PhD/experiments/2020-MODELS-ModelFlowGradle/out/gradle.workflow/src-gen/reactive/BoilerControllerReactive.java"));
		return generated;
	}
	
	/** ACTIONS */
	protected org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace eglTrace;

	
	@Override
	protected IEolModule createModule() throws Exception {

		this.module = new EgxModule(new EgxListenableTemplateFactory());

		try {
			if (((IEgxModule) module).getTemplateFactory() instanceof EglFileGeneratingTemplateFactory && outputRoot!= null) {
				try {
					EglFileGeneratingTemplateFactory tf = (EglFileGeneratingTemplateFactory) ((IEgxModule) module).getTemplateFactory();
					tf.setOutputRoot(outputRoot.getAbsolutePath());
					tf.getDefaultIncrementalitySettings().setOverwriteUnchangedFiles(true);
				} catch (EglRuntimeException e) {
					throw e;
				}
			}
		} catch (Exception e) {}
			
		eglTrace = new EgxEndToEndTraceContextAdaptor().adapt((IEgxContext) module.getContext());


		return module;
	}
	protected Set<OutputFile> outputFiles = new HashSet<OutputFile>();

	@Override
	protected void examine() throws Exception {
		super.examine();	
		
		((IEgxContext) module.getContext()).getInvokedTemplates()
			.forEach(t->{
				this.outputFiles.addAll(t.getOutputFiles());
			});		
		
		final Set<String> files = new HashSet<String>();
		if (outputFiles.isEmpty()) {
			eglTrace.getTraceLinks().forEach(t->{
				String destination = t.getDestination().getResource();
				files.add(destination);
/*				if (!destination.startsWith("file:/")){	
				} else {
				}*/
			});
		} else {			
			outputFiles.stream().forEach(f->{
				files.add(f.getURI().getPath());
			});
		}
		files.forEach(f -> {
			try {
				//System.out.println(f);
				generated.add(new File(f));
			}catch (Exception e) {
				e.printStackTrace();
				System.err.println(e);
			}
		});
		
		
		((IEgxContext) module.getContext()).getInvokedTemplates().stream()
			.flatMap(t->t.getOutputFiles().stream())
			.forEach(of->generatedFiles.add(of.getURI().getPath()));
				
	}
	

}
