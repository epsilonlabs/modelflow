/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Optional;

import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;

@Definition(name = "epsilon:xml")
public class EpsilonXmlModelResource extends EpsilonEmfModelResource {

	protected Optional<File> xsdFile = Optional.empty();
	
	public EpsilonXmlModelResource() {
		super();
	}
	
	@Param(key="xsd")
	public void setXsdFile(File file){
		xsdFile = Optional.of(file);
	}
		
	@Override
	public XmlModel getModel() {
		if (model == null) {
			this.model = new XmlModel();
		}
		return (XmlModel) this.model;
	}
	
	@Override
	public void configure(){
		if (xsdFile.isPresent()) {
			//getModel().setXsdFile(xsdFile.get()); //FIXME
			try {			
				Field field = XmlModel.class.getField("xsdFile");
				field.setAccessible(true);
				field.set(getModel(), xsdFile.get());
			} catch (Exception e) {	}
		}
		super.configure();
	}
	
}
