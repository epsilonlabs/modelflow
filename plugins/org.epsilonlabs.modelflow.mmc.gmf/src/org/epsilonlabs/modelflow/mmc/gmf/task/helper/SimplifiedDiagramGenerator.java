package org.epsilonlabs.modelflow.mmc.gmf.task.helper;

import java.io.File;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.util.CodegenEmitters;
import org.eclipse.gmf.codegen.util.EmitterSource;
import org.epsilonlabs.modelflow.mmc.gmf.task.GenerateDiagramCodeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplifiedDiagramGenerator extends Job {

	private static final Logger LOG = LoggerFactory.getLogger(SimplifiedDiagramGenerator.class);

	protected GenerateDiagramCodeTask task;
	
	protected GenEditorGenerator myGenModel;
	
	protected EmitterSource<GenEditorGenerator, CodegenEmitters> emmiterSource;
	protected IProgressMonitor monitor;

	protected GenerateDiagramCodeTask getTask() {
		return this.task;
	}
	
	public void setTask(GenerateDiagramCodeTask task) {
		this.task = task;
	}
	
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
	public IProgressMonitor getMonitor() {
		return monitor;
	}
	
	public void setGenModel(GenEditorGenerator model) {
		myGenModel = model;
	}

	protected final GenEditorGenerator getGenModel() {
		return myGenModel;
	}
	
	
	public  EmitterSource<GenEditorGenerator, CodegenEmitters> getEmitters() {
		return emmiterSource;
	}

	public CodegenEmitters getEmitters(GenEditorGenerator genmodel) {
		if (emmiterSource == null) {
			emmiterSource = new EmitterSource<GenEditorGenerator, CodegenEmitters>() {
				@Override
				protected CodegenEmitters newEmitters(GenEditorGenerator genModel) {
					return new CodegenEmitters(!genModel.isDynamicTemplates(), genModel.getTemplateDirectory(),
							genModel.getModelAccess() != null);
				}
			};
		}
		return emmiterSource.getEmitters(genmodel, genmodel.isDynamicTemplates());
	}

	AbstractGeneratorWrapper generator;
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (getMonitor() == null) setMonitor(monitor); 
		try {
			generator = new GeneratorWrapper(getGenModel(), getEmitters(getGenModel()));
			generator.run(getMonitor());
			
			IStatus s = generator.getRunStatus();
			if (!s.isOK()) {
				LOG.error(s.getMessage(), s.getException());
			}
			return s;
		} catch (InterruptedException ex) {
			return Status.CANCEL_STATUS;
		}
	}
	
	public Set<File> getFiles(){
		return generator.getFiles();
	}

	public SimplifiedDiagramGenerator(GenerateDiagramCodeTask task) {
		super("gmf:genDiagram");
		this.task = task;
		this.setUser(false);
		this.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
//				unloadGenModel();
				IStatus runStatus = event.getResult();
				
				task.setResult(runStatus);
				if (runStatus.isOK()) {
				} else if (runStatus.matches(IStatus.ERROR)) {
					LOG.error(runStatus.getMessage(), runStatus.getException());
				}
				task.setDone();
			}
	
		});
	}

}
