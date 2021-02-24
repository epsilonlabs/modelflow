/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.api.IModelResourceInstance;
import org.epsilonlabs.modelflow.dom.ast.ModelDeclaration;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

public class EMFResourceRule extends ModelDeclaration implements IEMFDomElement<IModelResource> {

	protected IModelResource modelResource;

	@Override
	public Collection<IModelResource> getDomElements() {
		return Arrays.asList(modelResource);
	}

	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			
			IModelResource resource = DomFactory.eINSTANCE.createModelResource();
			resource.setName(getName());
			resource.setDefinition(getType().getName());
			
			Class<IModelResourceInstance<?>> factory = null;
			try {
				// Factory exists?
				factory = ctx.getModule().getResFactoryRegistry().getFactory(getType().getName());
			} catch (Exception e) {
				String msg = String.format("Unkown resource factory '%s'", getType().getName());
				ctx.addWarningMarker(getType(), msg);
			}
			
			RuleUtil.setupConfigurableParameters(ctx, resource, factory, this);
			
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(modelResource);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof EMFResourceRule)) {
			return false;
		}
		EMFResourceRule other = (EMFResourceRule) obj;
		return Objects.equals(modelResource, other.modelResource);
	}

}
