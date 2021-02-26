package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.TransformOptions;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.mappings.Mapping;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.gmf.task.helper.SimplifiedGmfMap2GmfGen;
import org.epsilonlabs.modelflow.mmc.gmf.task.monitor.GmfMap2GmfGenMonitor;
import org.epsilonlabs.modelflow.mmc.gmf.task.trace.GmfMap2GmfGenTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
@Definition(name = "gmf:gmfMap2gmfGen")
public class GmfMap2GmfGenTask implements ITaskInstance {

	private static final Logger LOG = LoggerFactory.getLogger(GmfMap2GmfGenTask.class);

	protected SimplifiedGmfMap2GmfGen transformation = new SimplifiedGmfMap2GmfGen();


	protected URI genmodelLoc;
	protected URI mappingLoc;
	protected URI gmfgenLoc;
	
	protected GenModel genmodel;
	protected Mapping mapping;
	protected GenEditorGenerator gmfgen;
	protected EmfModel ecore;
	protected ResourceSet rs;
	protected Map<String, IModelWrapper> resources = new HashMap<>();
	
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
			
	}
	
	protected EolEvaluator evaluator;

	protected String getAnnotationDetailValue(String annotation, String detail, String default_) {
		if (ecore != null) {
			try {
				if (evaluator == null) {
					evaluator = new EolEvaluator(ecore);
				}
				Object o = evaluator.evaluate("EAnnotation.all.select(a|a.source='" + annotation + "').collect(a|a.details.select(d|d.key='" + detail + "')).flatten().collect(d|d.value).first()");
				if (o == null) return default_;
				else return "" + o  ;
			}
			catch (Exception ex) {
				return default_;
			}
		}
		return default_;
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		try {			
			Resource resource = CachedResourceSet.getCache().checkoutResource(gmfgenLoc);
			rs.getResources().remove(resource);
			
			transformation.setMonitor(new GmfMap2GmfGenMonitor());		
			transformation.setResourceSet(rs);
			transformation.executeTransformation();
			
			resource = rs.createResource(gmfgenLoc);
			resource.getContents().add(transformation.getGmfGen());
			rs.getResources().add(resource);			
		} catch (Exception e) {
			throw new MFExecutionException(e);
		} finally {
			genmodel = null;
			mapping = null;
			genmodelLoc = null;
			mappingLoc= null;
			gmfgenLoc= null;
			gmfgen= null;
			ecore= null;
			rs= null;
		}
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		final GmfMap2GmfGenTrace trace = new GmfMap2GmfGenTrace(this, transformation);
		trace.init();
		return Optional.of(trace.getTraces());
	}	

	@Override
	public void processModelsAfterExecution() {
		resources.clear();
		transformation = null;
	}

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
						resources.put(GenModelPackage.eNS_URI, m);
					} else if (eObject instanceof Mapping) {
						mappingLoc = model.getModelFileUri();
						mapping = (Mapping) eObject;
						resources.put(GMFMapPackage.eNS_URI, m);
					} else if (eObject instanceof EPackage) { // Ecore
						ecore = model;
					}
				}
				URI uri = resource.getURI();
				if (uri.isFile()){
					if (uri.toString().endsWith("gmfgen") && isOutput) {
						gmfgenLoc = model.getModelFileUri();
						rs = model.getResource().getResourceSet();
						output = resource;
						resources.put(GMFGenPackage.eNS_URI, m);
					}
				}
			}
		});
		
		transformation.setMapping(mapping);
		transformation.setGenModel(genmodel);
		transformation.setGmfGenURI(gmfgenLoc);
		transformation.setGmfGenResource(output);
		
		TransformOptions options = transformation.getOptions();
		options.setGenerateRCP(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "rcp", "false")));
		options.setUseMapMode(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "useMapMode", "true")));
		options.setUseRuntimeFigures(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "useRuntimeFigures", "true")));
		if (genmodelLoc == null || mappingLoc == null || gmfgenLoc == null) {
			throw new MFInvalidModelException("Invalid models");
		}	
	}

	public Map<String, IModelWrapper> getResources() {
		return resources;
	}
	
	@Override
	public void afterExecute() {
	}
	
}