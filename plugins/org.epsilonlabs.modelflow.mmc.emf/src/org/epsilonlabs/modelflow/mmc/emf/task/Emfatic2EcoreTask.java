package org.epsilonlabs.modelflow.mmc.emf.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.generator.ecore.Builder;
import org.eclipse.emf.emfatic.core.generator.ecore.Connector;
import org.eclipse.emf.emfatic.core.lang.gen.parser.EmfaticParserDriver;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gymnast.runtime.core.parser.ParseContext;
import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.emf.factory.AbstractEMFTaskFactory;

public class Emfatic2EcoreTask extends AbstractTask implements ITask {

	/** FACTORY */
	
	public static class Factory extends AbstractEMFTaskFactory {

		public Factory() {
			super(Emfatic2EcoreTask.class);
		}

		@Override
		public String getName() {
			return "emfatic2ecore";
		}

	}
	
	protected Optional<File> emfatic = Optional.empty(); 
	protected Resource resource = null;
	protected boolean mustSave = true;
	
	@Param(key="src")
	public void setEmfatic(File emfatic){
		this.emfatic = Optional.of(emfatic);
	}
	
	public Optional<File> getEmfatic() {
		return emfatic;
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {
		if (!getEmfatic().isPresent() && !getEmfatic().get().exists()) {
			throw new MFExecutionException("Invalid emfatic file"); 
		}
		if (resource == null) {
			String filePath = getEmfatic().get().getAbsolutePath().replaceAll("\\.emf$", ".ecore");
			resource = createResource(filePath);
		} else {
			mustSave = false;
		}
	}
	
	protected Resource createResource(String filePath)  {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = null;
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		uri = URI.createFileURI(filePath);
		Resource resource = resourceSet.createResource(uri);
		return resource;
	}

	/** 
	 * Mostly from EcoreGenerator.class but with provisions for saving the ecore 
	 * in a different location 
	 */
	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		NullProgressMonitor monitor = new NullProgressMonitor();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(emfatic.get())));
		} catch (FileNotFoundException e) {
			throw new MFExecutionException(e);
		}
		EmfaticParserDriver parser = new EmfaticParserDriver(URI.createFileURI(emfatic.get().getAbsolutePath()));
		ParseContext parseContext = parser.parse(reader);
		Builder builder = new Builder();
		try {
			builder.build(parseContext, resource, monitor);
		} catch (IOException e) {
			throw new MFExecutionException(e);
		}

		if (!parseContext.hasErrors()) {
			Connector connector = new Connector(builder);
			connector.connect(parseContext, resource, monitor);
			if (mustSave) {
				try {
					resource.save(null);
				} catch (IOException e) {
					throw new MFExecutionException(e);
				}
			}
		}
		else {
			String message = parseContext.getMessages()[0].getMessage();
			message = message.replaceAll("\\r|\\n", " ");
			message = message.trim();
			throw new MFExecutionException("Syntax error: " + message);
		}
	}

	
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		Arrays.asList(models).stream()
			.filter(m-> m.getResourceKind().isOutput() 
					&& m.getModel() instanceof EmfModel) //FIXME
			.findFirst().ifPresent(m-> {
				resource = ((EmfModel) m.getModel()).getResource();		
			});
	}	

	@Override
	public void afterExecute() {}
	
	@Override
	public void processModelsAfterExecution() { }

	@Override
	public Object getResult() {
		return null;
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

}