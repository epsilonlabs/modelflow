package org.epsilonlabs.modelflow.mmc.emf.task;

import static org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE;
import static org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE;
import static org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE;
import static org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.param.hash.FileHasher;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.emf.factory.AbstractEMFTaskFactory;
import org.epsilonlabs.modelflow.mmc.emf.task.custom.CustomGenerator;

public class GenerateModelCodeTask extends AbstractTask implements ITask {

	/** FACTORY */

	public static class Factory extends AbstractEMFTaskFactory {

		public Factory() {
			super(GenerateModelCodeTask.class);
		}

		@Override
		public String getName() {
			return "genCode";
		}

	}

	protected Boolean generateModel 	= true;
	protected Boolean generateEdit 		= false;
	protected Boolean generateEditor 	= false;
	protected Boolean generateTests 	= false;
	protected Set<String> outputs = new HashSet<String>();
	protected Set<File> generatedOutputs = new HashSet<File>();
	
	protected IModelWrapper modelRes;
	protected GenModel genModel;
	protected CustomGenerator generator;
	//protected File outputDir;

	@Param(key = "generateModel")
	public void setGenerateModelProject(Boolean generate) {
		this.generateModel = generate;
	}

	public Boolean getGenerateModel() {
		return generateModel;
	}
	
	@Param(key = "generateEdit")
	public void setGenerateEditProject(Boolean generate) {
		this.generateEdit = generate;
	}

	public Boolean getGenerateEdit() {
		return generateEdit;
	}
	
	@Param(key = "generateEditor")
	public void setGenerateEditorProject(Boolean generate) {
		this.generateEditor = generate;
	}

	public Boolean getGenerateEditor() {
		return generateEditor;
	}
	
	@Param(key = "generateTests")
	public void setGenerateTestsProject(Boolean generate) {
		this.generateTests = generate;
	}

	public Boolean getGenerateTests() {
		return generateTests;
	}
	
	@Output(key="generatedFiles")
	public Set<File> getGeneratedOutputs() {
		return generatedOutputs;
	}
	
	/*@Output(key="outputs", hasher = GeneratedNotHasher.class)
	public CodegenOutputUtil getOutputs() {
		return new CodegenOutputUtil(generator.getJavaOptions(),generator.getJdk(), 
				outputs, generator.getTemplateDir(), generator.isDynamic(), generator.isFormatting());
	}*/
	@Output(key="outputs", hasher = FileHasher.class)
	public Set<File> getOutputs() {
		return outputs.parallelStream().map(f->new File(f)).collect(Collectors.toSet());
	}
	
	/*	
	@Param(key = "outputDir")
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public File getOutputDir() {
		return outputDir;
	}
	*/
	
	@Override
	public void validateParameters() throws MFExecutionException {}

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		int oldSize;
		do {
			oldSize = genModel.getGenPackages().size();
			genModel.reconcile();
		} while (genModel.getGenPackages().size() != oldSize);
		
		genModel.setCanGenerate(true);

		generator = new CustomGenerator(genModel, new BasicMonitor());
		generator.setTask(this);
		generator.setResource(modelRes.getResource());
		
		if (generateModel) {
			generator.generate(MODEL_PROJECT_TYPE);
		}
		if (generateEdit) {
			generator.generate(EDIT_PROJECT_TYPE);
		}
		if (generateEditor) {
			generator.generate(EDITOR_PROJECT_TYPE);
		}
		if (generateTests) {
			generator.generate(TESTS_PROJECT_TYPE);
		}
	}
	
	@Override
	public void afterExecute() {
		IPath workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		generator.getGeneratedOutputs().forEach(f->{
			File file = workspace.append(new Path(f.toString())).toFile();
			generatedOutputs.add(file);
		});
		generator.getOutputs().forEach(f->{
			File file = workspace.append(new Path(f.toString())).toFile();
			outputs.add(file.getAbsolutePath());
		});
	}
	
	@Override
	public Object getResult() {
		return null;//monitor.getResult();
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.of(generator.getTraces());
	}
	
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream().filter(m ->{
			if (m.getModel() instanceof EmfModel) {
				EmfModel emf = (EmfModel) m.getModel();
				Resource resource = emf.getResource();
				Object first = resource.getContents().get(0);
				if (first instanceof GenModel) {					
					genModel = (GenModel) resource.getContents().get(0);				
					modelRes = m;
					return true;
				}
			}			
			return false;
		}).findFirst();
	}

	@Override
	public void processModelsAfterExecution() {}

}