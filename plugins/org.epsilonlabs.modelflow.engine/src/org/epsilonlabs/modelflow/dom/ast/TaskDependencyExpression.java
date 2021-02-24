/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.Objects;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.IEolVisitor;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * @author Betty Sanchez
 *
 */
public class TaskDependencyExpression extends Expression  {

	protected NameExpression target; 

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		target = new NameExpression(cst.getText());
		target.setRegion(cst.getRegion());
		target.setUri(cst.getUri());
		target.setModule(cst.getModule());
	}

	@Override
	public void compile(IEolCompilationContext context) {
		target.compile(context);
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		// Does nothing
		return null;
	}

	@Override
	public void accept(IEolVisitor visitor) {
		//TODO
	}
	
	public NameExpression getTarget() {
		return target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(target);
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
		if (!(obj instanceof TaskDependencyExpression)) {
			return false;
		}
		TaskDependencyExpression other = (TaskDependencyExpression) obj;
		return Objects.equals(target, other.target);
	}

}
