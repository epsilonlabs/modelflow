package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.TransformOptions;
import org.eclipse.gmf.mappings.Mapping;
import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.gmf.factory.AbstractGMFTaskFactory;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedGmfMap2GmfGen;
import org.epsilonlabs.modelflow.mmc.gmf.task.monitor.GmfMap2GmfGenMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class GmfMap2GmfGenTask extends AbstractTask implements ITask {

	private static final Logger LOG = LoggerFactory.getLogger(GmfMap2GmfGenTask.class);

	/** FACTORY */

	public static class Factory extends AbstractGMFTaskFactory {

		public Factory() {
			super(GmfMap2GmfGenTask.class);
		}

		@Override
		public String getName() {
			return "gmfMap2gmfGen";
		}

	}

	protected SimplifiedGmfMap2GmfGen transformation;
	protected IStatus result;

	protected URI genmodelLoc;
	protected URI mappingLoc;
	protected URI gmfgenLoc;
	
	protected GenModel genmodel;
	protected Mapping mapping;
	protected GenEditorGenerator gmfgen;

	protected boolean generateRCP = false;
	protected boolean useMapMode = true;
	protected boolean useRuntimeFigures = true;
	
	private Resource output;

	@Param(key="rcp")
	public void setGenerateRCP(Boolean rcp) {
		this.generateRCP = Optional.of(rcp).orElse(generateRCP);
	}
	
	public boolean isGenerateRCP() {
		return generateRCP;
	}

	public boolean isMapMode() {
		return useMapMode;
	}

	public boolean isRuntimeFigures() {
		return useRuntimeFigures;
	}

	@Param(key="mapMode")
	public void setMapMode(Boolean mapMode) {
		this.useMapMode = Optional.of(mapMode).orElse(useMapMode);
	}
	
	@Param(key="runtimeFigures")
	public void setUseRuntimeFigures(Boolean runtimeFigs) {
		this.useRuntimeFigures = Optional.of(runtimeFigs).orElse(useRuntimeFigures);
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {
		transformation = new SimplifiedGmfMap2GmfGen();
		transformation.setMonitor(new GmfMap2GmfGenMonitor());
		
		TransformOptions options = transformation.getOptions();
		options.setGenerateRCP(generateRCP); 
		options.setUseMapMode(useMapMode);
		options.setUseRuntimeFigures(useRuntimeFigures);
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		if (genmodelLoc == null || mappingLoc == null || gmfgenLoc == null) {
			throw new MFExecutionException("Invalid models");
		}

		try {
			// Caused by: java.lang.IllegalStateException: Target gmfgen URI should be specified
			transformation.executeTransformation();
			transformation.getQvtTrace();
			transformation.getTrace();
		} catch (Exception e) {
			throw new MFExecutionException(e);
		}
	}

	@Override
	public IStatus getResult() {
		return result;
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

	@Override
	public void processModelsAfterExecution() { }

	
	/** 
	 * Can only execute for a specific model configuration:
	 */
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream().forEach(m -> {
			if (m.getModel() instanceof EmfModel) {
				boolean isOutput = m.getResourceKind().equals(ResourceKind.OUTPUT) || m.getResourceKind().equals(ResourceKind.INOUT);
				EmfModel model = (EmfModel) m.getModel();
				Resource resource = model.getResource();
				EList<EObject> contents = resource.getContents();
				if (!contents.isEmpty()) {
					EObject eObject = contents.get(0);
					LOG.debug("class: {}", eObject.getClass().getSimpleName());
					if (eObject instanceof GenModel) {
						genmodelLoc = model.getModelFileUri();
						genmodel = (GenModel) eObject;
					} else if (eObject instanceof Mapping) {
						mappingLoc = model.getModelFileUri();
						mapping = (Mapping) eObject;
						
					} else if (eObject instanceof GenEditorGenerator && isOutput) {
						gmfgenLoc = model.getModelFileUri();
						output = resource;
					}
				} else {
					URI uri = resource.getURI();
					if (uri.isFile()){
						if (uri.toString().endsWith("gmfgen") && isOutput) {
							gmfgenLoc = model.getModelFileUri();
							output = resource;
						}
					}
				}
			}
		});
		
		transformation.setGenModel(genmodel);
		transformation.setMapping(mapping);
		transformation.setGmfGenURI(gmfgenLoc);
		transformation.setGmfGenResource(output);
	}

	@Override
	public void afterExecute() {
		LOG.debug("Reloading");
		output.getContents().clear();
		output.getContents().add(transformation.getGmfGen());
	}

}