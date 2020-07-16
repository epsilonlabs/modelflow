package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
import org.eclipse.gmf.internal.common.migrate.ModelLoadHelper;
import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.gmf.factory.AbstractGMFTaskFactory;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class GenerateDiagramCodeTask extends AbstractTask implements ITask {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateDiagramCodeTask.class);

	/** FACTORY */

	public static class Factory extends AbstractGMFTaskFactory {

		public Factory() {
			super(GenerateDiagramCodeTask.class);
		}

		@Override
		public String getName() {
			return "genDiagram";
		}

	}
	private AtomicBoolean done = new AtomicBoolean();
	protected IStatus result;
	protected GenEditorGenerator myGenModel;
	protected URI modelFileUri ;
	
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
	
	SimplifiedDiagramGenerator job;
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		job = new SimplifiedDiagramGenerator(this);
		job.setGenModel(myGenModel);
		job.setMonitor(new NullProgressMonitor());
		job.schedule();

		// Blocking
		while(!done()) {
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				LOG.error("Interrupted", e);
			}
		}
		LOG.info("Done");
	}

	private boolean done(){
		return done.get();
	}
	
	public void setDone(){
		done.set(true);
	}
		
	public void setResult(IStatus result){
		this.result = result;
	}
	
	@Output(key = "GeneratedFiles")
	public Set<File> getFilies(){
		return job.getFiles();
	}
	
	@Override
	public Object getResult() {
		return this.result;
	}

	//FIXME add traces
	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

	@Override
	public void processModelsAfterExecution() { }

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream().forEach(m -> {
			if (m.getModel() instanceof EmfModel) {
				EmfModel model = (EmfModel) m.getModel(); 
				EObject eObject = m.getResource().eContents().get(0);
				if (eObject instanceof GenEditorGenerator) {
					this.myGenModel = (GenEditorGenerator) eObject;
				}
				modelFileUri = model.getModelFileUri();
			}
		});
		loadGenModel();
		Diagnostic isGenModelValid = ValidationHelper.validate(myGenModel, true);
		if (!ValidationHelper.isOK(isGenModelValid)) {
			throw new MFInvalidModelException(isGenModelValid.getException());
		}
	}

	@Override
	public void afterExecute() {
		LOG.debug("After execution");
	}

	private Diagnostic loadGenModel() {
		ResourceSet srcResSet = new ResourceSetImpl();
		ModelLoadHelper loadHelper = new ModelLoadHelper(srcResSet, modelFileUri);
		Object root = loadHelper.getContentsRoot();
		if (root instanceof GenDiagram) {
			myGenModel = ((GenDiagram) root).getEditorGen();
		} else if (root instanceof GenEditorGenerator) {
			myGenModel = (GenEditorGenerator) root;
		}
		if (myGenModel != null && myGenModel.getDomainGenModel() != null) {
			myGenModel.getDomainGenModel().reconcile();
		}
		return ValidationHelper.createResourceProblemMarkers(loadHelper.getDiagnostics());
	}


}