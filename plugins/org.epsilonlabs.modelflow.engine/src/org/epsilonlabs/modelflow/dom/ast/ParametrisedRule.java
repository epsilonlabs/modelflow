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

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.dom.NamedRule;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

/**
 * @author bea
 *
 */
public abstract class ParametrisedRule<T> extends NamedRule
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

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		for (Property p : getProperties()) {
			ModuleElement e = (ModuleElement) p.getValue();
			if (e instanceof IExecutableModuleElement) {
				Object value = ((IExecutableModuleElement) e).execute(context);
				p.setValue(value);
			}
		}
		return null;
	}

	protected abstract EList<Property> getProperties();

}
