/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.FeatureCallExpression;
import org.eclipse.epsilon.eol.dom.IEolVisitor;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * The Class ModelCallExpression.
 *
 * @author bea
 */
public class ModelCallExpression extends FeatureCallExpression implements IModelCallExpression {

	protected List<NameExpression> aliases = new ArrayList<>();

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		nameExpression = new NameExpression(cst.getText());
		nameExpression.setRegion(cst.getRegion());
		nameExpression.setUri(cst.getUri());
		nameExpression.setModule(cst.getModule());
	}

	@Override
	public void compile(IEolCompilationContext context) {
		nameExpression.compile(context);
		aliases.forEach(a->a.compile(context));
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return null;
	}

	@Override
	public void accept(IEolVisitor visitor) {
		
	}

	@Override
	public List<NameExpression> getAliases() {
		return aliases;
	}
	
	@Override
	public void addAlias(NameExpression name) {
		this.aliases.add(name);
	}
	
	@Override
	public NameExpression getModel() {
		return super.getNameExpression();
	}
}
