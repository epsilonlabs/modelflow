package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
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
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.IGenerator;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedDiagramGenerator;
import org.epsilonlabs.modelflow.mmc.gmf.task.monitor.GmfMap2GmfGenMonitor;
import org.epsilonlabs.modelflow.mmc.gmf.task.trace.GmfDiagramTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
@Definition(name = "gmf:genDiagram")
public class GenerateDiagramCodeTask implements ITaskInstance {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateDiagramCodeTask.class);

	protected GenEditorGenerator myGenModel;
	protected URI modelFileUri;
	protected File outputDir;
	protected String resource;
	protected Set<File> files = new HashSet<>();
	protected List<GmfDiagramTrace> traces = new ArrayList<>();
	
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
	
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		SimplifiedDiagramGenerator job = new SimplifiedDiagramGenerator(this);
		job.setGenModel(myGenModel);
		job.setMonitor(new GmfMap2GmfGenMonitor());
		job.schedule();
		job.addJobChangeListener(new JCL(job));
		try {
			job.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	class JCL implements IJobChangeListener{
		SimplifiedDiagramGenerator j;
		JCL(SimplifiedDiagramGenerator j){
			this.j = j;
		}
		@Override
		public void sleeping(IJobChangeEvent event) {}
		
		@Override
		public void scheduled(IJobChangeEvent event) {}
		
		@Override
		public void running(IJobChangeEvent event) {}
		
		@Override
		public void done(IJobChangeEvent event) {
			final IGenerator generator = j.getGenerator();
			files = generator.getFiles();
			traces = generator.getTraces();
			j.clean();
			j.removeJobChangeListener(this);
		}
		
		@Override
		public void awake(IJobChangeEvent event) {}
		
		@Override
		public void aboutToRun(IJobChangeEvent event) {}
	}

	@Output(key = "GeneratedFiles", hasher = FileHasher.class)
	public Set<File> getFilies(){
		return files.stream().collect(Collectors.toSet());
	}

	protected Collection<Trace> resolvedTraces;
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (resolvedTraces == null) {			
			resolvedTraces = traces.stream()
					.map(t->t.setResourceName(resource).getTrace())
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			traces.clear();
			traces = null;
		}
		return Optional.of(resolvedTraces);
	}

	@Override
	public void processModelsAfterExecution() { }

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream().forEach(m -> {
			if (m.getModel() instanceof EmfModel) {
				resource = m.getResourceNode().getName();
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
			throw new MFInvalidModelException(isGenModelValid.getMessage(),  isGenModelValid.getException());
		}
	}

	@Override
	public void afterExecute() {
			LOG.debug("After execution");
		myGenModel = null;
		
	}

}