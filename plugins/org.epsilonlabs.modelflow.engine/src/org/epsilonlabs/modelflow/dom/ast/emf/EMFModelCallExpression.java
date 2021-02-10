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
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IDerivedResource;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ast.ModelCallExpression;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

/**
 * The Class ModelCallExpression.
 *
 * @author bea
 */
public class EMFModelCallExpression extends ModelCallExpression implements IEMFDomElement<IResourceReference> {

	protected IResourceReference ref;

	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			// Create resource reference
			ref = DomFactory.eINSTANCE.createResourceReference();
			// Add the aliases to the resource reference 
			aliases.forEach(a -> ref.getAliases().add(a.getName()));
			// Locate the resource declaration that it comes from
			List<IAbstractResource> resources = ctx.getModule().getWorkflow().getResources().parallelStream()
				.filter(m -> m.getName().equalsIgnoreCase(nameExpression.getName())).collect(Collectors.toList());
			
			// Check if resource is pressent
			if (resources.size()==1) {
				// Assign the dom.Resource to the resource reference
				ref.setResource(resources.get(0));
 			} 
			else if (resources.isEmpty() && getName().contains(".")) {
				IDerivedResource derivedResource = DomFactory.eINSTANCE.createDerivedResource();
				derivedResource.setName(getName());
				
				ctx.getModule().getWorkflow().getResources().add(derivedResource);
				
				ref.setResource(derivedResource);
			}
			else {
 				String msg = String.format("Resource with name '%s' could not be found", nameExpression.getName()); 
				ctx.addErrorMarker(this, msg);
 			}
		}
	}

	@Override
	public Collection<IResourceReference> getDomElements() {
		return Arrays.asList(ref);
	}

	public void addAlias(NameExpression name) {
		aliases.add(name);
	}

}
