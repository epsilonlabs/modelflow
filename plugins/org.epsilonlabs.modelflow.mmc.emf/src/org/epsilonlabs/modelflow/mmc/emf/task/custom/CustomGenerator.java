package org.epsilonlabs.modelflow.mmc.emf.task.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapter;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceBuilder;
import org.epsilonlabs.modelflow.management.trace.Trace;

public class CustomGenerator extends Generator {

	class GeneratorData {
		public Object object;
		public GeneratorAdapter adapter;

		public GeneratorData(Object object, GeneratorAdapter adapter) {
			this.object = object;
			this.adapter = adapter;
		}
	}

	protected String name;
	protected List<Trace> traces = new ArrayList<>();
	protected Set<URI> outputs = new LinkedHashSet<>();

	protected Object currentObject;
	protected Object currentProjectType;

	protected Object object;
	protected Monitor monitor;
	protected Map<String, String> javaOptions;
	protected String jdk;
	protected Boolean dynamic;
	protected Boolean formatting;
	protected String templateDir;

	public CustomGenerator(Object object, Monitor monitor) {
		this.monitor = monitor;
		this.object = object;
		GenModel gm = (GenModel) object;
		this.javaOptions = GenModelUtil.getJavaOptions(gm);
		this.jdk = complianceLevel(gm);
		this.formatting= gm.isCodeFormatting();
		this.dynamic = gm.isDynamicTemplates();
		this.templateDir = gm.getTemplateDirectory();
		setInput(object);
	}

	protected String complianceLevel(GenModel gm){
		switch (gm.getComplianceLevel())
        {
          case JDK14_LITERAL:
          {
            return JavaCore.VERSION_1_4;
          }
          case JDK50_LITERAL:
          {
            return JavaCore.VERSION_1_5;
          }
          case JDK60_LITERAL: 
          {
            return JavaCore.VERSION_1_6;
          }
          case JDK70_LITERAL: 
          {
            return JavaCore.VERSION_1_7;
          }
          case JDK80_LITERAL:
          {
            return "1.8";
          }
          case JDK90_LITERAL:
          {
            return "9";
          }
          case JDK100_LITERAL:
          {
            return "10";
          }
          default:
        	  return "";
        }
	}

	public void setResourceName(String name) {
		this.name = name;
	}

	public Map<String, String> getJavaOptions() {
		return javaOptions;
	}

	public String getJdk() {
		return jdk;
	}

	public Boolean isFormatting() {
		return formatting;
	}
	public Boolean isDynamic() {
		return dynamic;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	@Override
	public Diagnostic generate(Object object, Object projectType, String projectTypeName, Monitor monitor) {
		return generate(projectType, projectTypeName);
	}

	public Diagnostic generate(Object projectType) {
		return generate(projectType, null);
	}

	public Diagnostic generate(Object projectType, String projectTypeName) {
		currentProjectType = projectType;
		try {
			String message = projectTypeName != null
					? CodeGenEcorePlugin.INSTANCE.getString("_UI_Generating_message", new Object[] { projectTypeName })
					: CodeGenEcorePlugin.INSTANCE.getString("_UI_GeneratingCode_message");
			BasicDiagnostic result = new BasicDiagnostic(CodeGenEcorePlugin.ID, 0, message, null);

			GeneratorData[] data = getGeneratorData(object, projectType, true);
			monitor.beginTask("", data.length + 2);
			monitor.subTask(message);

			if (initializeNeeded) {
				initializeNeeded = false;
				initialize();
			}

			int preIndex = 0;
			for (; preIndex < data.length && canContinue(result); preIndex++) {
				currentObject = data[preIndex].object;
				result.add(data[preIndex].adapter.preGenerate(currentObject, projectType));
			}
			monitor.worked(1);

			for (int i = 0; i < data.length && canContinue(result); i++) {
				currentObject = data[i].object;
				result.add(data[i].adapter.generate(currentObject, projectType, CodeGenUtil.createMonitor(monitor, 1)));
				if (monitor.isCanceled()) {
					result.add(Diagnostic.CANCEL_INSTANCE);
				}
			}

			for (int i = 0; i < preIndex; i++) {
				currentObject = data[i].object;
				result.add(data[i].adapter.postGenerate(currentObject, projectType));
			}

			if (getOptions().cleanup && CommonPlugin.IS_RESOURCES_BUNDLE_AVAILABLE && !generatedOutputs.isEmpty()
					&& jControlModel != null && jControlModel.getFacadeHelper() != null) {
				EclipseHelper.sourceCleanup(generatedOutputs);
			}

			return result;
		} finally {
			monitor.done();
		}
	}

	@Override
	public void generatedOutput(URI workspacePath) {
		EObject eObject = (EObject) currentObject;
		String id = eObject.eResource().getURIFragment(eObject);
		Trace trace = new ManagementTraceBuilder().addSourceModelElement(id, this.name, null)
				.addTargetElement(workspacePath.toString(), -1, -1)
				.link("generate", currentProjectType.toString()).build();
		traces.add(trace);
		generatedOutputs.add(workspacePath);
	}

	public void addOutput(URI workspacePath) {
		outputs.add(workspacePath);
	}

	public Set<URI> getOutputs() {
		return outputs;
	}

	public List<Trace> getTraces() {
		return traces;
	}

	protected GeneratorData[] getGeneratorData(Object object, Object projectType, boolean forGenerate) {
		Set<Object> objects = new HashSet<>();
		List<GeneratorData> childrenData = getGeneratorData(object, projectType, forGenerate, true, false, objects);
		List<GeneratorData> parentsData = getGeneratorData(object, projectType, forGenerate, false, true, objects);
		List<GeneratorData> result = new ArrayList<>(parentsData.size() + childrenData.size());
		Collections.reverse(parentsData);
		result.addAll(parentsData);
		result.addAll(childrenData);
		return result.toArray(new GeneratorData[result.size()]);
	}

	protected List<GeneratorData> getGeneratorData(Object object, Object projectType, boolean forGenerate,
			boolean forChildren, boolean skipFirst, Set<Object> objects) {
		List<Object> result = new ArrayList<>();
		result.add(object);

		for (int i = 0; i < result.size(); skipFirst = false) {
			Object o = result.get(i);

			Collection<GeneratorAdapter> adapters = getAdapters(o);
			result.remove(i);
			if (!adapters.isEmpty()) {
				for (GeneratorAdapter adapter : adapters) {
					if (forChildren) {
						Collection<?> children = forGenerate ? adapter.getGenerateChildren(o, projectType)
								: adapter.getCanGenerateChildren(o, projectType);
						for (Object child : children) {
							if (objects.add(child)) {
								result.add(child);
							}
						}
					} else {
						Object parent = forGenerate ? adapter.getGenerateParent(o, projectType)
								: adapter.getCanGenerateParent(o, projectType);
						if (parent != null && objects.add(parent)) {
							result.add(parent);
						}
					}

					if (!skipFirst) {
						result.add(i++, new GeneratorData(o, adapter));
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		List<GeneratorData> list = (List<GeneratorData>) (List<?>) result;
		return list;
	}

	/*
	 * @Override protected Collection<GeneratorAdapter> getAdapters(Object object) {
	 * // return super.getAdapters(object); Collection<GeneratorAdapterFactory>
	 * adapterFactories = getAdapterFactories(object); List<GeneratorAdapter> result
	 * = new ArrayList<GeneratorAdapter>(adapterFactories.size());
	 * 
	 * for (AdapterFactory adapterFactory : adapterFactories) { if
	 * (adapterFactory.isFactoryForType(GeneratorAdapter.class)) { Object adapter =
	 * adapterFactory.adapt(object, GeneratorAdapter.class); if (adapter != null) {
	 * result.add((GeneratorAdapter) adapter); } } } return result; }
	 */

	@Override
	protected Collection<GeneratorAdapterFactory> getAdapterFactories(Object object) {
		// return super.getAdapterFactories(object);
		if (packageIDToAdapterFactories == null) {
			packageIDToAdapterFactories = new HashMap<String, Collection<GeneratorAdapterFactory>>();
		}

		String packageID = getPackageID(object);
		Collection<GeneratorAdapterFactory> result = packageIDToAdapterFactories.get(packageID);
		if (result == null) {
			Collection<GeneratorAdapterFactory.Descriptor> descriptors = getAdapterFactoryDescriptorRegistry()
					.getDescriptors(packageID);
			result = new ArrayList<>(descriptors.size());
			for (GeneratorAdapterFactory.Descriptor descriptor : descriptors) {
				GeneratorAdapterFactory adapterFactory = descriptor.createAdapterFactory();
				if (adapterFactory instanceof GenModelGeneratorAdapterFactory) {
					adapterFactory = new CustomGenModelGeneratorAdapterFactory();
				}
				adapterFactory.setGenerator(this);
				result.add(adapterFactory);
			}
			packageIDToAdapterFactories.put(packageID, result);
		}
		return result;
	}

	protected static class EclipseHelper {
		private static final CleanupScheduler SCHEDULER;
		static {
			CleanupScheduler cleanupScheduler = null;
			try {
				Class<?> generatorUIUtilClass = CommonPlugin.loadClass("org.eclipse.emf.codegen.ecore.ui",
						"org.eclipse.emf.codegen.ecore.genmodel.presentation.GeneratorUIUtil");
				cleanupScheduler = (CleanupScheduler) generatorUIUtilClass.getField("CLEANUP_SCHEDULER").get(null);
			} catch (Exception exception) {
				// Ignore
			}

			SCHEDULER = cleanupScheduler;
		}

		public static void sourceCleanup(final Set<URI> generatedOutputs) {
			if (SCHEDULER != null) {
				IWorkspaceRoot workspaceRoot = EcorePlugin.getWorkspaceRoot();
				if (workspaceRoot != null) {
					Set<ICompilationUnit> compilationUnits = new LinkedHashSet<>();
					for (URI generatedOutput : generatedOutputs) {
						if ("java".equals(generatedOutput.fileExtension())) {
							IFile file = workspaceRoot.getFile(new Path(generatedOutput.toString()));
							ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
							if (compilationUnit.getJavaProject().isOnClasspath(compilationUnit)) {
								compilationUnits.add(compilationUnit);
							}
						}
					}

					SCHEDULER.schedule(compilationUnits);
				}
			}
		}
	}

}
