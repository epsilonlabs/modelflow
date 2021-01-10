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

import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.api.factory.IModelResourceFactory;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

public class ResourceRule extends ConfigurableRule<IModelResource> {

	protected IModelResource modelResource;

	@Override
	public Collection<IModelResource> getDomElements() {
		return Arrays.asList(modelResource);
	}

	@Override
	public void compile(IEolCompilationContext context) {
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			
			IModelResource resource = DomFactory.eINSTANCE.createModelResource();
			resource.setName(getName());
			resource.setDefinition(getType().getName());
			
			IModelResourceFactory factory = null;
			try {
				// Factory exists?
				factory = ctx.getModule().getResFactoryRegistry().getFactory(getType().getName());
			} catch (Exception e) {
				String msg = String.format("Unkown resource factory '%s'", getType().getName());
				ctx.addWarningMarker(getType(), msg);
			}
			
			setupConfigurableParameters(ctx, resource, factory);
			
			/*for (Entry<NameExpression, ModuleElement> p : parameters.entrySet()) {
				Property property = DomFactoryImpl.eINSTANCE.createProperty();
				property.setKey(p.getKey().getName());
				property.setValue(p.getValue());
				modelResource.getProperties().add(property);
				if (factory != null && !factory.getParameters().contains(property.getKey())) {
					String msg = String.format("Parameter '%s' does not exist for resource type '%s'", p.getKey().getName(), getType().getName());
					ctx.addWarningMarker(p.getKey(), msg);
				}
				
			}*/
			
			modelResource = resource;
		}
		
	}

}
