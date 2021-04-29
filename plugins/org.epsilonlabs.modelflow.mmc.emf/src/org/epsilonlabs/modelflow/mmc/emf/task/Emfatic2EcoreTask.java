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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfatic.core.generator.ecore.Builder;
import org.eclipse.emf.emfatic.core.generator.ecore.Connector;
import org.eclipse.emf.emfatic.core.lang.gen.parser.EmfaticParserDriver;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.gymnast.runtime.core.parser.ParseContext;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;

@Definition(name = "emf:emfatic2ecore")
public class Emfatic2EcoreTask implements ITaskInstance {
	
	protected File emfatic = null; 
	protected Resource resource = null;
	protected IModelWrapper output;
	
	@Param(key="src")
	public void setEmfatic(File emfatic){
		this.emfatic = emfatic;
	}
	
	@Input(key = "src")
	public File getEmfatic() {
		return emfatic;
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {
		if (emfatic !=null && !emfatic.exists()) {
			throw new MFExecutionException("Invalid emfatic file"); 
		}
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
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(emfatic)));
		} catch (FileNotFoundException e) {
			throw new MFExecutionException(e);
		}
		EmfaticParserDriver parser = new EmfaticParserDriver(URI.createFileURI(emfatic.getAbsolutePath()));
		ParseContext parseContext = parser.parse(reader);
		Builder builder = new Builder();
		Resource tmp = new ResourceSetImpl().createResource(resource.getURI());
		try {
			builder.build(parseContext, tmp, monitor);
		} catch (IOException e) {
			throw new MFExecutionException(e);
		}

		if (!parseContext.hasErrors()) {
			Connector connector = new Connector(builder);
			connector.connect(parseContext, tmp, monitor);
			resource.getContents().add(tmp.getContents().get(0));
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
					&& m.getModel() instanceof EmfModel) 
			.findFirst().ifPresent(m -> {
				output = m;
				resource = ((EmfModel) m.getModel()).getResource();
			});
		if (resource == null) {
			throw new MFInvalidModelException("Expected EMF model as output");
		}
	}	

	@Override
	public void afterExecute() {
		resource = null;
	}	

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

}