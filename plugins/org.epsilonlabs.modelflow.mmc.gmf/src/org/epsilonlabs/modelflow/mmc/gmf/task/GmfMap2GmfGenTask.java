package org.epsilonlabs.modelflow.mmc.gmf.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.internal.bridge.transform.TransformOptions;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
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


	protected URI genmodelUri;
	protected URI gmfMappingUri;
	protected URI gmfgenUri;
	
	protected GenModel genmodelRoot;
	protected Mapping gmfMappingRoot;
	protected EmfModel ecoreEmfModel;
	protected ResourceSet rs;
	protected Map<String, IModelWrapper> resources = new HashMap<>();
	
	protected boolean generateRCP = false;
	protected boolean useMapMode = true;
	protected boolean useRuntimeFigures = true;

	private Resource gmfgenResource;

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
		if (ecoreEmfModel != null) {
			try {
				if (evaluator == null) {
					evaluator = new EolEvaluator(ecoreEmfModel);
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
	public Optional<Collection<Trace>> getTrace() {
		final GmfMap2GmfGenTrace trace = new GmfMap2GmfGenTrace(this, transformation);
		trace.init();
		return Optional.of(trace.getTraces());
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
					EObject rootEObject = contents.get(0);
					LOG.debug("class: {}", rootEObject.getClass().getSimpleName());
					if (rootEObject instanceof GenModel) {
						// GenModel
						genmodelUri = model.getModelFileUri();
						genmodelRoot = (GenModel) rootEObject;
						resources.put(GenModelPackage.eNS_URI, m);
						
					} else if (rootEObject instanceof Mapping) {
						//GmfGen
						gmfMappingUri = model.getModelFileUri();
						gmfMappingRoot = (Mapping) rootEObject;
						resources.put(GMFMapPackage.eNS_URI, m);
					} else if (rootEObject instanceof EPackage) { 
						// Ecore
						ecoreEmfModel = model;
					}
				}
				URI uri = resource.getURI();
				if (uri.isFile() && uri.toString().endsWith("gmfgen") && isOutput) {
					gmfgenUri = model.getModelFileUri();
					resources.put(GMFGenPackage.eNS_URI, m);
					rs = model.getResource().getResourceSet();
					gmfgenResource = resource;
				}
			}
		});
		if (genmodelUri == null || gmfMappingUri == null || gmfgenUri == null) {
			throw new MFInvalidModelException("Invalid models");
		}	
		transformation.setMapping(gmfMappingRoot);
		transformation.setGenModel(genmodelRoot);
		transformation.setGmfGenURI(gmfgenUri);
		transformation.setGmfGenResource(gmfgenResource);
		
		TransformOptions options = transformation.getOptions();
		options.setGenerateRCP(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "rcp", "false")));
		options.setUseMapMode(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "useMapMode", "true")));
		options.setUseRuntimeFigures(Boolean.valueOf(getAnnotationDetailValue("gmf.diagram", "useRuntimeFigures", "true")));
		
	}
	

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		Diagnostic genmodelValidation;
		try {
			genmodelValidation = ValidationHelper.validate(genmodelRoot, true, new NullProgressMonitor());
		} catch (RuntimeException re) {
			genmodelValidation = BasicDiagnostic.toDiagnostic(re);
		}
		if (genmodelValidation.getCode() != Diagnostic.OK){
			throw new MFExecutionException(genmodelValidation.getMessage());
		}

		Diagnostic mapValidation;
		try {
			mapValidation = ValidationHelper.validate(gmfMappingRoot, true, new NullProgressMonitor());
		} catch (RuntimeException re) {
			mapValidation = BasicDiagnostic.toDiagnostic(re);
		}
		if (mapValidation.getCode() != Diagnostic.OK){
			throw new MFExecutionException(mapValidation.getMessage());
		}
		
		try {			
			rs = new ResourceSetImpl();
			rs.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());
	
			transformation.setMonitor(new GmfMap2GmfGenMonitor());		
			transformation.setResourceSet(rs);
			transformation.executeTransformation();
			
			gmfgenResource.getContents().add(transformation.getGmfGen());	
		} catch (Exception e) {
			throw new MFExecutionException(e);
		}
	}

	public Map<String, IModelWrapper> getResources() {
		return resources;
	}
	
	@Override
	public void afterExecute() {
		resources.clear();
		transformation = null;
		genmodelRoot = null;
		gmfMappingRoot = null;
		ecoreEmfModel= null;
		rs= null;
	}
	
}