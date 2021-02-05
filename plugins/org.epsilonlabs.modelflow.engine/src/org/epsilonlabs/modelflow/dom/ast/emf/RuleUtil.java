/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.Map.Entry;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;
import org.epsilonlabs.modelflow.dom.ast.AbstractDeclaration;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

/**
 * @author bea
 *
 */
public abstract class RuleUtil  {
	
	protected static void setupConfigurableParameters(IModelFlowCompilationContext ctx, IConfigurable element, Class<?> factory, AbstractDeclaration declaration) {
		for (Entry<NameExpression, ModuleElement> p : declaration.getParameters().entrySet()) {
			IProperty property = DomFactory.eINSTANCE.createProperty();
			property.setKey(p.getKey().getName());
			ModuleElement value = p.getValue();
			if (value instanceof ICompilableModuleElement) {
				ICompilableModuleElement moduleElement = (ICompilableModuleElement) value;
				FrameStack frameStack = ctx.getFrameStack();
				frameStack.enterLocal(FrameType.UNPROTECTED, (ModuleElement) moduleElement);
				moduleElement.compile(ctx);
				frameStack.leaveLocal((ModuleElement) moduleElement);
			}
			property.setValue(value);
			element.getProperties().add(property);
			if (factory != null && !new FactoryIntrospector(factory).getParameterNames().contains(property.getKey())) {
				String msg = String.format("Parameter '%s' does not exist for task type '%s'", p.getKey().getName(), declaration.getType().getName());
				ctx.addWarningMarker(p.getKey(), msg);
			}
		}
	}


}
