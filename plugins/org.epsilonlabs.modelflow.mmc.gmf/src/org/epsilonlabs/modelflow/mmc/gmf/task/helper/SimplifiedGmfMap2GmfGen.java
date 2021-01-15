package org.epsilonlabs.modelflow.mmc.gmf.task.helper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.ExternalCrossReferencer;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.graphdef.codegen.MapModeCodeGenStrategy;
import org.eclipse.gmf.internal.bridge.History;
import org.eclipse.gmf.internal.bridge.genmodel.BasicDiagramRunTimeModelHelper;
import org.eclipse.gmf.internal.bridge.genmodel.DiagramGenModelTransformer;
import org.eclipse.gmf.internal.bridge.genmodel.DiagramRunTimeModelHelper;
import org.eclipse.gmf.internal.bridge.genmodel.GenModelProducer;
import org.eclipse.gmf.internal.bridge.genmodel.InnerClassViewmapProducer;
import org.eclipse.gmf.internal.bridge.genmodel.ModeledViewmapProducer;
import org.eclipse.gmf.internal.bridge.genmodel.QVTDiagramGenModelTransformer;
import org.eclipse.gmf.internal.bridge.genmodel.ViewmapProducer;
import org.eclipse.gmf.internal.bridge.naming.gen.GenNamingMediatorImpl;
import org.eclipse.gmf.internal.bridge.transform.Messages;
import org.eclipse.gmf.internal.bridge.transform.TransformOptions;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
import org.eclipse.gmf.internal.bridge.transform.VisualIdentifierDispenserProvider;
import org.eclipse.gmf.internal.bridge.ui.Plugin;
import org.eclipse.gmf.internal.codegen.util.GMFGenConfig;
import org.eclipse.gmf.internal.common.ToolingResourceFactory.ToolResource;
import org.eclipse.gmf.internal.common.reconcile.Reconciler;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.m2m.internal.qvt.oml.trace.Trace;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "restriction", "deprecation" })
public class SimplifiedGmfMap2GmfGen {

	private static final Logger LOG = LoggerFactory.getLogger(SimplifiedGmfMap2GmfGen.class);

	protected TransformOptions myOptions;
	protected final ResourceSet myResourceSet;
	protected IProgressMonitor monitor;
	protected Diagnostic myGMFGenValidationResult;

	protected URI myGMFGenModelURI;
	protected Mapping myMapping;
	protected GenModel myGenModel;
	protected GenEditorGenerator genEditor;
	protected Resource myGMFGenModelResource;

	public void setMonitor(IProgressMonitor monitor){
		this.monitor = monitor;
	}
	
	public SimplifiedGmfMap2GmfGen() {
		myResourceSet = new ResourceSetImpl();
		this.myOptions = new TransformOptions();
	}

	public TransformOptions getOptions() {
		return myOptions;
	}
	
	public URI getGmfGenURI() {
		return this.myGMFGenModelURI;
	}

	public void setGmfGenURI(URI gmfGen) {
		this.myGMFGenModelURI = gmfGen;
	}
	
	public Resource getGmfGenResource() {
		return this.myGMFGenModelResource;
	}

	public void setGmfGenResource(Resource gmfGen) {
		this.myGMFGenModelResource = gmfGen;
	}

	/** GENMODEL */
	public GenModel getGenModel() {
		return this.myGenModel;
	}

	public void setGenModel(GenModel model){
		this.myGenModel = model;
	}

	/** GMFGEN */
	public GenEditorGenerator getGmfGen() {
		return this.genEditor;
	}

	public void setGmfGen(GenEditorGenerator model){
		this.genEditor = model;
	}

	/** GMFMAP */
	public Mapping getMapping() {
		return this.myMapping;
	}

	public void setMapping(Mapping model){
		this.myMapping = model;
	}
	
	public final ResourceSet getResourceSet() {
		return myResourceSet;
	}

	protected void setGMFGenValidationResult(Diagnostic validationResult) {
		this.myGMFGenValidationResult = validationResult;
	}
	
	public Resource executeTransformation() throws Exception{
		Diagnostic validation = Diagnostic.CANCEL_INSTANCE;
		try {
			if (getGmfGenURI() == null) {
				throw new IllegalStateException(Messages.TransformToGenModelOperation_e_null_gmfgen_uri);
			}
			monitor.beginTask("", 100); //$NON-NLS-1$
			if (monitor.isCanceled()) {
				throw new Exception("Cancelled");
			}
			final VisualIdentifierDispenserProvider idDispenser = new VisualIdentifierDispenserProvider(getGmfGenURI());
			idDispenser.acquire();

			GenModelProducer t = createGenModelProducer(idDispenser);

			monitor.subTask(Messages.TransformToGenModelOperation_task_generate);
			genEditor = t.process(getMapping(), new SubProgressMonitor(monitor, 20));
			monitor.subTask(Messages.TransformToGenModelOperation_task_reconcile);
			if (Plugin.needsReconcile()) {
				handlePreReconcileHooks(genEditor);
				reconcile(genEditor);
				handlePostReconcileHooks(genEditor);
			}
			if (hasExtensionTransformation(getMapping().eResource().getURI())) {
				executeExtensionTransformation(getMapping().eResource().getURI(), genEditor);
			}
			GenNamingMediatorImpl namer = new GenNamingMediatorImpl();
			namer.setMode(GenNamingMediatorImpl.Mode.COLLECT_NAMES);
			namer.traverse(genEditor); // collect reconciled names
			namer.setMode(GenNamingMediatorImpl.Mode.DISPENSE_NAMES);
			namer.traverse(genEditor); // dispense names to new elements

			monitor.subTask(Messages.TransformToGenModelOperation_task_save);

			Resource save = save(genEditor);
			monitor.subTask(Messages.TransformToGenModelOperation_task_validate);
			try {
				validation = ValidationHelper.validate(genEditor, true, monitor);
			} catch (RuntimeException re) {
				validation = BasicDiagnostic.toDiagnostic(re);
			}
			if (Diagnostic.CANCEL != validation.getSeverity()) {
				idDispenser.release();
			}
			return save;
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = Messages.TransformToGenModelOperation_e_generator_creation;
			}
			throw new Exception(message, ex);
		} finally {
			setGMFGenValidationResult(validation);
			if (monitor != null) {
				monitor.done();
			}
		}
	}

	protected boolean hasExtensionTransformation(URI uri) {
		final URI transfURI = uri.trimFileExtension().appendFileExtension("qvto");
		final TransformationExecutor executor = new TransformationExecutor(transfURI);
		Diagnostic diag = executor.loadTransformation();
		return diag.getCode() == Diagnostic.OK;
	}

	protected void executeExtensionTransformation(URI uri, GenEditorGenerator result) {
		final URI transfURI = uri.trimFileExtension().appendFileExtension("qvto");
		final TransformationExecutor executor = new TransformationExecutor(transfURI);
		final ExecutionContextImpl context = new ExecutionContextImpl();
		ExecutionDiagnostic execute = executor.execute(context, new BasicModelExtent(Arrays.asList(new GenEditorGenerator[] { result })));
	}

	protected void handlePreReconcileHooks(GenEditorGenerator result) {
		if (getOptions().getPreReconcileTransform() != null) {
			URI transfURI = URI.createURI(getOptions().getPreReconcileTransform().toExternalForm());
			final TransformationExecutor executor = new TransformationExecutor(transfURI);
			final ExecutionContextImpl context = new ExecutionContextImpl();
			executor.execute(context, new BasicModelExtent(Arrays.asList(new GenEditorGenerator[] { result })));
		}
	}

	protected void handlePostReconcileHooks(GenEditorGenerator result) {
		if (getOptions().getPostReconcileTransform() != null) {
			URI transfURI = URI.createURI(getOptions().getPostReconcileTransform().toExternalForm());
			final TransformationExecutor executor = new TransformationExecutor(transfURI);
			final ExecutionContextImpl context = new ExecutionContextImpl();
			executor.execute(context, new BasicModelExtent(Arrays.asList(new GenEditorGenerator[] { result })));
		}
	}


	protected ViewmapProducer detectTransformationOptions() {
		boolean useModeledViewmaps = !getOptions().getUseInTransformationCodeGen();
		if (useModeledViewmaps) {
			return new ModeledViewmapProducer();
		}

		String runtimeToken = getOptions().getUseRuntimeFigures() ? "full" : "lite";
		MapModeCodeGenStrategy mmStrategy = getOptions().getUseMapMode() ? MapModeCodeGenStrategy.DYNAMIC : MapModeCodeGenStrategy.STATIC;
		URL dynamicFigureTemplates = getOptions().getFigureTemplatesPath();
		return new InnerClassViewmapProducer(runtimeToken, mmStrategy, dynamicFigureTemplates == null ? null : new URL[] { dynamicFigureTemplates });
	}

	protected GenModelProducer createGenModelProducer(VisualIdentifierDispenserProvider idDespenser) {
		if (getOptions().getMainTransformation() != null) {
			final ExecutionContextImpl context = new ExecutionContextImpl();
			context.setConfigProperty("rcp", getOptions().getGenerateRCP());
			context.setConfigProperty("useMapMode", getOptions().getUseMapMode());
			context.setConfigProperty("useFullRunTime", getOptions().getUseRuntimeFigures());
			context.setConfigProperty("useInTransformationCodeGen", getOptions().getUseInTransformationCodeGen());

			final QVTDiagramGenModelTransformer transformer = new QVTDiagramGenModelTransformer(getResourceSet(), idDespenser.get());
			transformer.setTransformationL(getOptions().getMainTransformation());

			return new GenModelProducer() {

				@Override
				public GenEditorGenerator process(Mapping mapping, IProgressMonitor progress) {
					progress.beginTask(null, 1);
					try {
						final BasicModelExtent output = new BasicModelExtent();
						final ExecutionDiagnostic result = transformer.transform(mapping, getGenModel(), output, context);
						qvtTrace = transformer.getTrace();
						if (Plugin.printTransformationConsole()) {
							System.err.println(result.getMessage());
						}
						if (result.getSeverity() == Diagnostic.OK) {
							List<EObject> outObjects = output.getContents();
							return outObjects.get(0) instanceof GenEditorGenerator ? (GenEditorGenerator) outObjects.get(0) : null;
						}
						String errorMessage = result.getMessage();
						if (errorMessage == null || errorMessage.isEmpty()) {
							errorMessage = "Transformation has no out parameter of GenEditorGenerator type";
						}
						
					} finally {
						progress.done();
					}
					return null;
				}
			};
		} else {
			final DiagramRunTimeModelHelper drtModelHelper = new BasicDiagramRunTimeModelHelper();
			final ViewmapProducer viewmapProducer = detectTransformationOptions();
			DiagramGenModelTransformer.Parameters opts = new DiagramGenModelTransformer.Parameters(drtModelHelper, viewmapProducer, idDespenser.get(), getOptions().getGenerateRCP());
			t = new DiagramGenModelTransformer(opts);
			if (getGenModel() != null) {
				t.setEMFGenModel(getGenModel());
			}
			
			return new GenModelProducer() {

				@Override
				public GenEditorGenerator process(Mapping mapping, IProgressMonitor progress) {
					progress.beginTask(null, 1);
					try {
						t.transform(mapping);
						trace = t.getTrace();
						return t.getResult();
					} finally {
						progress.done();
					}
				}
			};
		}
	}
	
	protected DiagramGenModelTransformer t;
	protected Trace qvtTrace;
	protected History trace;
	
	public DiagramGenModelTransformer getDiagramTransformer() {
		return t;
	}
	
	public History getTrace() {
		return trace;
	}
	
	public Trace getQvtTrace() {
		return qvtTrace;
	}
	
	protected void reconcile(GenEditorGenerator genBurdern) throws Exception {
		GenEditorGenerator old = null;
		Resource resource = null;
		try {
			resource = getResourceSet().getResource(getGmfGenURI(), false);
			if (resource == null) {
				resource = getResourceSet().createResource(getGmfGenURI(), ContentHandler.UNSPECIFIED_CONTENT_TYPE);
				resource.load(getResourceSet().getLoadOptions());
			}
			List<EObject> contents = resource.getContents();
			if (!contents.isEmpty() && contents.get(0) instanceof GenEditorGenerator) {
				old = (GenEditorGenerator) contents.get(0);
			}
			if (old != null) {
				new Reconciler(new GMFGenConfig()).reconcileTree(genBurdern, old);
			}
		} catch (IOException e) {
			// can't load resource, means no old file, IGNORE the exception
		} catch (RuntimeException e) {
			old = null;
			throw new Exception(e);
		} finally {
			if (resource != null) {
				if (resource.isLoaded()) {
					// not sure I need to unload given I'll remove the resource from resource set anyway, but it doesn't hurt? 
					resource.unload();
				}
				// Need to remove created resource from resource set, not to affect further load attempts
				// (e.g. the one in #save() method, with another content type)
				// Another option would be use of correct content type here, but what 
				// if loaded/reconciled model has old content type? 
				getResourceSet().getResources().remove(resource);
			}
		}
	}

	// FIXME do not save twice
	protected Resource save(GenEditorGenerator genBurdern) throws IOException {
		HashMap<String, Object> saveOptions = new HashMap<String, Object>();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
		Resource gmfgenRes = getResourceSet().getResource(getGmfGenURI(), false);
		//gmfgenRes.getDefaultSaveOptions().putAll(saveOptions);
		try {
			if (gmfgenRes == null) {
				gmfgenRes = getResourceSet().createResource(getGmfGenURI(), "org.eclipse.gmf.gen");
				gmfgenRes.load(getResourceSet().getLoadOptions());
				if (gmfgenRes instanceof ToolResource) {
					((ToolResource) gmfgenRes).getDefaultSaveOptions().putAll(saveOptions);
				}
				//gmfgenRes.getDefaultSaveOptions().putAll(saveOptions);
			}
			updateExistingResource(gmfgenRes, genBurdern);
			// one might want to ignore dangling href on save when there are more than one
			// content object - there are chances we don't match them during reconcile and 
			// failed update all the references.
			if (gmfgenRes.getContents().size() > 1 && Plugin.ignoreDanglingHrefOnSave()) {
				//saveOptions.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_RECORD);
			}
		//	gmfgenRes.save(saveOptions);
		} catch (IOException ex) {
			// load failed, no file exists
			gmfgenRes.getContents().add(genBurdern);
			if (gmfgenRes instanceof ToolResource) {
				((ToolResource) gmfgenRes).getDefaultSaveOptions().putAll(saveOptions);
			}
		//	gmfgenRes.save(saveOptions);
		} catch (RuntimeException ex) {
			LOG.error(ex.getMessage(), ex);
			// save anyway, for later examination
			gmfgenRes.getContents().add(genBurdern);
			if (gmfgenRes instanceof ToolResource) {
				((ToolResource) gmfgenRes).getDefaultSaveOptions().putAll(saveOptions);
			}
		//	gmfgenRes.save(saveOptions);
		}
		return gmfgenRes;
	}

	protected static void updateExistingResource(Resource gmfgenRes, GenEditorGenerator genBurden) {
		boolean editorGenFound = false;
		for (int i = 0; !editorGenFound && i < gmfgenRes.getContents().size(); i++) {
			if (gmfgenRes.getContents().get(i) instanceof GenEditorGenerator) {
				if (gmfgenRes.getContents().size() > 1) {
					// chances there are other content eobjects that reference 
					// some parts of old GenEditorGenerator, hence need update
					LinkedList<EObject> rest = new LinkedList<EObject>(gmfgenRes.getContents());
					GenEditorGenerator oldEditorGenerator = (GenEditorGenerator) rest.remove(i);
					updateExternalReferences(genBurden, oldEditorGenerator, rest);
				}
				gmfgenRes.getContents().set(i, genBurden); // replace with new one
				editorGenFound = true;
			}
		}
		if (!editorGenFound) {
			gmfgenRes.getContents().add(genBurden);
		}
	}

	protected static void updateExternalReferences(GenEditorGenerator newEditorGenerator, final GenEditorGenerator oldEditorGenerator, List<EObject> allContentButOldGenerator) {
		// find references from rest of the content to old generator
		final Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = new ExternalCrossReferencer(allContentButOldGenerator) {

			private static final long serialVersionUID = 4383601037841211175L;

			@Override
			protected boolean crossReference(EObject object, EReference reference, EObject crossReferencedEObject) {
				return super.crossReference(object, reference, crossReferencedEObject) && EcoreUtil.isAncestor(oldEditorGenerator, crossReferencedEObject);
			}

			Map<EObject, Collection<EStructuralFeature.Setting>> find() {
				return findExternalCrossReferences();
			}
		}.find();
		// match new and old objects using reconciler without decisions
		new Reconciler(new GMFGenConfig()) {

			@Override
			protected void handleNotMatchedCurrent(EObject current) {/* no-op */
			};

			@Override
			protected EObject handleNotMatchedOld(EObject currentParent, EObject notMatchedOld) {
				return null; /* no-op */
			};

			@Override
			protected void reconcileVertex(EObject current, EObject old) {
				if (!crossReferences.containsKey(old)) {
					return;
				}
				// and replace old values with new
				for (EStructuralFeature.Setting s : crossReferences.get(old)) {
					EcoreUtil.replace(s, old, current);
				}
			}
		}.reconcileTree(newEditorGenerator, oldEditorGenerator);
	}

}
