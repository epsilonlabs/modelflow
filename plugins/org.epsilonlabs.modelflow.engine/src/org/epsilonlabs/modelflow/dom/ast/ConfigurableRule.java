/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.dom.NamedRule;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.api.factory.IFactory;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

/**
 * @author bea
 *
 */
public abstract class ConfigurableRule<T> extends NamedRule
		implements IDomElement<T>, ICompilableModuleElement, IExecutableModuleElement {

	protected Map<NameExpression, ModuleElement> parameters;

	protected NameExpression type;

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		List<AST> child = AstUtil.getChildren(cst, ModelFlowParser.PROPERTY);
		parameters = new HashMap<>(child.size());
		for (AST a : child) {
			NameExpression key = (NameExpression) module.createAst(a, this);
			ModuleElement value = module.createAst(a.getFirstChild(), this);
			parameters.put(key, value);
		}

		type = new NameExpression(AstUtil.getChild(cst, ModelFlowParser.RULETYPE).getFirstChild().getText());
	}

	public NameExpression getType() {
		return type;
	}
	
	/** 
	 * Sets the properties 
	 * @param ctx
	 * @param element
	 * @param factory
	 */
	protected void setupConfigurableParameters(IModelFlowCompilationContext ctx, IConfigurable element, IFactory factory) {
		for (Entry<NameExpression, ModuleElement> p : parameters.entrySet()) {
			IProperty property = DomFactory.eINSTANCE.createProperty();
			property.setKey(p.getKey().getName());
			ModuleElement value = p.getValue();
			if (value instanceof ICompilableModuleElement) {
				ICompilableModuleElement moduleElement = (ICompilableModuleElement) value;
				FrameStack frameStack = ctx.getFrameStack();
				frameStack.enterLocal(FrameType.PROTECTED, (ModuleElement) moduleElement);
				moduleElement.compile(ctx);
				frameStack.leaveLocal((ModuleElement) moduleElement);
			}
			property.setValue(value);
			element.getProperties().add(property);
			if (factory != null && !factory.getParameters().contains(property.getKey())) {
				String msg = String.format("Parameter '%s' does not exist for task type '%s'", p.getKey().getName(), getType().getName());
				ctx.addWarningMarker(p.getKey(), msg);
			}
		}
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		/*for (Property p : getProperties()) {
			ModuleElement e = (ModuleElement) p.getValue();
			if (e instanceof IExecutableModuleElement) {
				Object value = ((IExecutableModuleElement) e).execute(context);
				p.setValue(value);
			}
		}*/
		return null;
	}

}
