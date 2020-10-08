/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.core.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.epsilonlabs.modelflow.dom.api.AbstractTask;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.core.factory.AbstractCoreTaskFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Betty Sanchez
 *
 */
public class XsdValidationTask extends AbstractTask {

	/** FACTORY */

	public static class Factory extends AbstractCoreTaskFactory {

		public Factory() {
			super(XsdValidationTask.class);
		}

		@Override
		public String getName() {
			return "xsdValidate";
		}

	}
	
	protected File xsd;
	protected File xml;
	
	@Param(key = "xsd")
	public void setXsd(File xsd){
		this.xsd = xsd;
	}
	
	/**
	 * @return the xsd
	 */
	public File getXsd() {
		return xsd;
	}
	
	
	@Param(key = "xml")
	public void setXml(File xml){
		this.xml = xml;
	}
	
	/**
	 * @return the xml
	 */
	public File getXml() {
		return xml;
	}
	
	@Override
	public void validateParameters() throws MFRuntimeException {
		if (!getXml().exists()) {
			throw new MFInvalidModelException("XML file does not exist");
		} 
		if (!getXsd().exists()) {
			throw new MFInvalidModelException("XSD file does not exist");
		}
	}

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		// Do nothing
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;
		try {
			schema = schemaFactory.newSchema(xsd);
		} catch (SAXException e1) {
			throw new MFRuntimeException("Schema factory exception", e1);
		}
		Validator validator = schema.newValidator();
		ValidatorErrorHandler errorHandler = new ValidatorErrorHandler();
		validator.setErrorHandler(errorHandler);
		StreamSource xmlSource = new StreamSource(xml);
		try {
			validator.validate(xmlSource);
		} catch (SAXException e) {
			throw new MFRuntimeException("Validation exception", e);
		} catch (IOException e) {
			throw new MFRuntimeException("Validation exception", e);

		}
	}
	
	class ValidatorErrorHandler implements ErrorHandler{
		final List<SAXException> exceptions = new ArrayList<>();

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			exceptions.add(exception);
		}
		
		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			exceptions.add(exception);
		}
		
		@Override
		public void error(SAXParseException exception) throws SAXException {
			exceptions.add(exception);
		}
		
		/**
		 * @return the exceptions
		 */
		public List<SAXException> getExceptions() {
			return exceptions;
		}
	}

	@Override
	public void afterExecute() throws MFRuntimeException {
		// Do nothing
	}

	@Override
	public void processModelsAfterExecution() {
		// Do nothing
	}


	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

}