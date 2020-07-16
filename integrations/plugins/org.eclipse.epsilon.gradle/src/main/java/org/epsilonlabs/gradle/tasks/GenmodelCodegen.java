package org.epsilonlabs.gradle.tasks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.osgi.framework.BundleContext;

public class GenmodelCodegen extends DefaultTask {

	protected final Property<Boolean> generateModel;
	protected final Property<Boolean> generateEdit;
	protected final Property<Boolean> generateEditor;
	protected final Property<Boolean> generateTests;
	protected final Property<File> genmodelFile;// = null; // FIXME

	public GenmodelCodegen() {
		super();
		generateModel = getProject().getObjects().property(Boolean.class);
		generateModel.set(false);
		generateEdit = getProject().getObjects().property(Boolean.class);
		generateEdit.set(false);
		generateEditor = getProject().getObjects().property(Boolean.class);
		generateEditor.set(false);
		generateTests = getProject().getObjects().property(Boolean.class);
		generateTests.set(false);
		genmodelFile = getProject().getObjects().property(File.class);
	}
	
	@Input
	@Optional
	public Boolean getGenerateModel() {
		return generateModel.get();
	}
	
	public void setGenerateModel(Boolean value) {
		generateModel.set(value);
	}
	
	@Input
	@Optional
	public Boolean getGenerateEdit() {
		return generateEdit.get();
	}
	
	public void setGenerateEdit(Boolean value) {
		generateEdit.set(value);
	}
	
	@Input
	@Optional
	public Boolean getGenerateEditor() {
		return generateEditor.get();
	}

	public void setGenerateEditor(Boolean value) {
		generateEditor.set(value);
	}
	
	@Input
	@Optional
	public Boolean getGenerateTests() {
		return generateTests.get();
	}
	
	public void setGenerateTests(Boolean value) {
		generateTests.set(value);
	}
	
	
	@InputFile
	public File getGenmodelFile() {
		return genmodelFile.get();
	}
	
	public void setGenmodelFile(File file) {
		this.genmodelFile.set(file);
	}
	
	@TaskAction
	public void doSomething(){
		if (genmodelFile.isPresent()) {
			BundleContext context = null;
			try {
	
				Map<String, String> ip = new HashMap<>();
				ip.put("eclipse.ignoreApp", "true");
				ip.put("eclipse.consoleLog", "true");
				ip.put("eclipse.log.level", "ALL");
 				ip.put("osgi.noShutdown", "false");
				EclipseStarter.setInitialProperties(ip);
				context = EclipseStarter.startup(new String[0], null);
				Arrays.asList(context.getBundles()).forEach(b->System.out.println(b.getClass().getName()));
								
				System.out.println(EclipseStarter.isRunning());
				System.out.println(EMFPlugin.IS_ECLIPSE_RUNNING);
				System.out.println(Platform.isRunning());
				ResourceSet rs = new ResourceSetImpl();
				System.out.println("resources size" + rs.getResources().size());
				URI createFileURI = URI.createFileURI(genmodelFile.get().getAbsolutePath());

				Registry packageRegistry = rs.getPackageRegistry();
				packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
				packageRegistry.put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
				
				EcoreResourceFactoryImpl resourceFactory = new EcoreResourceFactoryImpl();
			    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", resourceFactory);
			    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("genmodel", resourceFactory);
				
				System.out.println(createFileURI);
				Resource resource = rs.createResource(createFileURI);
				System.out.println("Res: "+resource);
				try {
					resource.load(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				EcoreUtil.resolveAll(rs);
		
				GenModel genModel = (GenModel) resource.getContents().get(0);
				int oldSize;
				do {
					oldSize = genModel.getGenPackages().size();
					genModel.reconcile();
				} while (genModel.getGenPackages().size() != oldSize);
				genModel.setCanGenerate(true);
				
				Generator generator = new Generator();
				if (generateModel.get()) {
					generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor());
					generator.getGeneratedOutputs().forEach(o->System.out.println(o.toFileString()));
				}
				if (generateEdit.get()) {
					generator.generate(genModel, GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE, new BasicMonitor());
					generator.getGeneratedOutputs().forEach(o->System.out.println(o.toFileString()));

				}
				if (generateEditor.get()) {
					generator.generate(genModel, GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,new BasicMonitor());
					generator.getGeneratedOutputs().forEach(o->System.out.println(o.toFileString()));

				}
				if (generateTests.get()) {
					generator.generate(genModel, GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE, new BasicMonitor());
					generator.getGeneratedOutputs().forEach(o->System.out.println(o.toFileString()));

				}
				
			} catch (Exception e1) {
				
			}
			if (context != null) {
				try {
					EclipseStarter.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
}
