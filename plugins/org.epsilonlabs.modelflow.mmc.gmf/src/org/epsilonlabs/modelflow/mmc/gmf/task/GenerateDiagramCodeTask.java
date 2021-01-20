package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.param.hash.FileHasher;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedDiagramGenerator;
import org.epsilonlabs.modelflow.mmc.gmf.task.trace.GmfDiagramTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
@Definition(name = "gmf:genDiagram")
public class GenerateDiagramCodeTask extends AbstractTaskInstance implements ITaskInstance {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateDiagramCodeTask.class);

	private AtomicBoolean done = new AtomicBoolean();
	protected GenEditorGenerator myGenModel;
	protected URI modelFileUri ;
	protected IAbstractResource resource;
	
	protected File outputDir;
	
	@Param(key="outputDir")
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	
	@Param(key="outputDir")
	public void setOutputDir(String outputDir) {
		this.outputDir = new File(outputDir);
	}
	
	public File getOutputDir() {
		return outputDir;
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {}
	
	protected SimplifiedDiagramGenerator job;
	protected Set<File> files;
	protected List<GmfDiagramTrace> traces;

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		job = new SimplifiedDiagramGenerator(this);
		job.setGenModel(myGenModel);
		job.setMonitor(new NullProgressMonitor());
		job.schedule();
		job.addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {}
			
			@Override
			public void scheduled(IJobChangeEvent event) {}
			
			@Override
			public void running(IJobChangeEvent event) {}
			
			@Override
			public void done(IJobChangeEvent event) {
				files = job.getFiles();
				traces = job.getTraces();
			}
			
			@Override
			public void awake(IJobChangeEvent event) {}
			
			@Override
			public void aboutToRun(IJobChangeEvent event) {}
		});
		
		// Blocking
		while(!done()) {
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				LOG.error("Interrupted", e);
			}
		}		
	}

	private boolean done(){
		return done.get();
	}
	
	public void setDone(){
		done.set(true);
	}

	@Output(key = "GeneratedFiles", hasher = FileHasher.class)
	public Set<File> getFilies(){
		return files.stream().collect(Collectors.toSet());
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		Collection<Trace> list = traces.stream()
				.map(t->t.setTask(this).setResourceName(resource).getTrace())
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		return Optional.of(list);
	}

	@Override
	public void processModelsAfterExecution() { }

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream().forEach(m -> {
			if (m.getModel() instanceof EmfModel) {
				resource = m.getResource();
				EmfModel model = (EmfModel) m.getModel(); 
				EObject eObject = model.getResource().getContents().get(0);
				if (eObject instanceof GenDiagram) {
					myGenModel = ((GenDiagram) eObject).getEditorGen();
				} else if (eObject instanceof GenEditorGenerator) {
					myGenModel = (GenEditorGenerator) eObject;
				}
				if (myGenModel != null && myGenModel.getDomainGenModel() != null) {
					myGenModel.getDomainGenModel().reconcile();
				}
				modelFileUri = model.getModelFileUri();
			}
		});
		Diagnostic isGenModelValid = ValidationHelper.validate(myGenModel, true);
		if (!ValidationHelper.isOK(isGenModelValid)) {
			throw new MFInvalidModelException(isGenModelValid.getException());
		}
	}

	@Override
	public void afterExecute() {
		LOG.debug("After execution");
	}

}