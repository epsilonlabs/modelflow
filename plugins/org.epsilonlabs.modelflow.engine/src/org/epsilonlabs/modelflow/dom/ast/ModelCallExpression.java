/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.FeatureCallExpression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.DerivedResource;
import org.epsilonlabs.modelflow.dom.ResourceReference;
import org.epsilonlabs.modelflow.dom.impl.DomFactoryImpl;

/**
 * The Class ModelCallExpression.
 *
 * @author bea
 */
public class ModelCallExpression extends FeatureCallExpression implements IDomElement<ResourceReference> {

	protected ResourceReference ref;

	protected TaskRule taskRule;
	protected List<NameExpression> aliases = new ArrayList<>();

	/**
	 * 
	 */
	public ModelCallExpression(TaskRule taskRule) {
		this.taskRule = taskRule;
	}

	/**
	 * Builds the AST element
	 *
	 * @param cst    the cst
	 * @param module the module
	 */
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		nameExpression = new NameExpression(cst.getText());
		nameExpression.setRegion(cst.getRegion());
		nameExpression.setUri(cst.getUri());
		nameExpression.setModule(cst.getModule());

	}

	/**
	 * Compile.
	 *
	 * @param context the context
	 */
	@Override
	public void compile(IEolCompilationContext context) {
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			// Create resource reference
			ref = DomFactoryImpl.eINSTANCE.createResourceReference();
			// Add the aliases to the resource reference 
			aliases.forEach(a -> ref.getAliases().add(a.getName()));
			// Locate the resource declaration that it comes from
			List<AbstractResource> resources = ctx.getModule().getWorkflow().getResources().parallelStream()
				.filter(m -> m.getName().equalsIgnoreCase(nameExpression.getName())).collect(Collectors.toList());
			
			// Check if resource is pressent
			if (resources.size()==1) {
				// Assign the dom.Resource to the resource reference
				ref.setResource(resources.get(0));
 			} 
			else if (resources.isEmpty() && getName().contains(".")) {
				DerivedResource derivedResource = DomFactoryImpl.eINSTANCE.createDerivedResource();
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

	/**
	 * Execute.
	 *
	 * @param context the context
	 * @return the object
	 * @throws EolRuntimeException the eol runtime exception
	 */
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return null;
	}

	@Override
	public Collection<ResourceReference> getDomElements() {
		return Arrays.asList(ref);
	}

	/**
	 * @param name
	 */
	public void addAlias(NameExpression name) {
		aliases.add(name);
	}

}
