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
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.CachedResourceSet.Cache.CacheItem;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.internal.bridge.transform.TransformOptions;
import org.eclipse.gmf.internal.common.ToolingResourceFactory.ToolResource;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.mappings.Mapping;
import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
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
public class GmfMap2GmfGenTask extends AbstractTaskInstance implements ITaskInstance {

	private static final Logger LOG = LoggerFactory.getLogger(GmfMap2GmfGenTask.class);

	protected SimplifiedGmfMap2GmfGen transformation;

	protected URI genmodelLoc;
	protected URI mappingLoc;
	protected URI gmfgenLoc;
	
	protected GenModel genmodel;
	protected Mapping mapping;
	protected GenEditorGenerator gmfgen;
	protected EmfModel ecore;
	
	protected boolean generateRCP = false;
	protected boolean useMapMode = true;
	protected boolean useRuntimeFigures = true;
	protected Map<String, IModelWrapper> resources = new HashMap<>();
	
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
		if (genmodelLoc == null || mappingLoc == null || gmfgenLoc == null) {
			throw new MFExecutionException("Invalid models");
		}
		try {			
			// Caused by: java.lang.IllegalStateException: Target gmfgen URI should be specified
			final ToolResource resource = (ToolResource) transformation.executeTransformation();
			//resource.getDefaultSaveOptions()
			final IModelWrapper wrapper = getResources().get(GMFGenPackage.eNS_URI);
			
			final EmfModel emfModel = (EmfModel)wrapper.getModel();
			final CachedResourceSet resourceSet = (CachedResourceSet) emfModel.getResource().getResourceSet();
			//Remove resource
			final EList<Resource> resourcesList = resourceSet.getResources();
			resourcesList.remove(emfModel.getResource());
			// Remove from cache
			final Collection<CacheItem> items = resourceSet.getCache().getItems();
			items.stream().filter(i->i.getUri().equals(resource.getURI())).findAny().ifPresent(items::remove);
			
			//((EmfModel)wrapper.getModel()).setResource(resource);
			
			final ToolResource createResource = (ToolResource) resourceSet.createResource(resource.getURI(), "org.eclipse.gmf.gen");
			createResource.getContents().add(resource.getContents().get(0));
			HashMap<String, Object> saveOptions = new HashMap<String, Object>();
			saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
			createResource.getDefaultSaveOptions().putAll(saveOptions);
			emfModel.setResource(createResource);
			// We need to inhibit the workflow from saving
			transformation.getTrace();
		} catch (Exception e) {
			throw new MFExecutionException(e);
		}
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.of(new GmfMap2GmfGenTrace(this, transformation).init().getTraces());
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
	}

	public Map<String, IModelWrapper> getResources() {
		return resources;
	}
	
	@Override
	public void afterExecute() {}
	
}