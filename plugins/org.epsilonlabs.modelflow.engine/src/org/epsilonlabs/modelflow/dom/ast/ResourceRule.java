/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.ModelResource;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.impl.DomFactoryImpl;

public class ResourceRule extends ParametrisedRule<ModelResource> {

	protected ModelResource modelResource;

	@Override
	public EList<Property> getProperties() {
		return modelResource.getProperties();
	}

	@Override
	public Collection<ModelResource> getDomElements() {
		return Arrays.asList(modelResource);
	}

	@Override
	public void compile(EolCompilationContext context) {
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			
			modelResource = DomFactoryImpl.eINSTANCE.createModelResource();
			modelResource.setName(getName());
			modelResource.setDefinition(getType().getName());
			
			IModelResourceFactory factory = null;
			try {
				// Factory exists?
				factory = ctx.getModule().getResFactoryRegistry().getFactory(getType().getName());
			} catch (Exception e) {
				String msg = String.format("Unkown resource factory '%s'", getType().getName());
				ctx.addWarningMarker(getType(), msg);
			}
	
			for (Entry<NameExpression, ModuleElement> p : parameters.entrySet()) {
				Property property = DomFactoryImpl.eINSTANCE.createProperty();
				property.setKey(p.getKey().getName());
				property.setValue(p.getValue());
				modelResource.getProperties().add(property);
				if (factory != null && !factory.getParameters().contains(property.getKey())) {
					String msg = String.format("Parameter '%s' does not exist for resource type '%s'", p.getKey().getName(), getType().getName());
					ctx.addWarningMarker(p.getKey(), msg);
				}
				
			}
			
		}
		
	}

}
