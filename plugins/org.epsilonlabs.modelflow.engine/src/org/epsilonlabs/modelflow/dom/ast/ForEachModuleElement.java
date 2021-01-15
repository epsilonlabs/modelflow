/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ForStatement;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;

/**
 * @author Betty Sanchez
 *
 */
public class ForEachModuleElement extends ForStatement {

	protected Iterator<Object> it;

	@Override
	public void build(AST cst, IModule module) {
		iteratorParameter = (Parameter) module.createAst(cst.getFirstChild(), this);
		iteratedExpression = (Expression) module.createAst(cst.getSecondChild(), this);
	}

	@Override
	public void compile(IEolCompilationContext context) {
		iteratedExpression.compile(context);
		context.getFrameStack().enterLocal(FrameType.UNPROTECTED, bodyStatementBlock,
				new Variable("loopCount", EolPrimitiveType.Integer), new Variable("hasMore", EolPrimitiveType.Boolean));

		if (iteratedExpression.hasResolvedType()
				&& !(iteratedExpression.getResolvedType() instanceof EolCollectionType)) {
			context.addErrorMarker(iteratedExpression,
					"Collection expected instead of " + iteratedExpression.getResolvedType());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Return execute(IEolContext context) throws EolRuntimeException {
		Object iteratedObject = this.iteratedExpression.execute(context);

		Collection<Object> iteratedCol = null;

		if (iteratedObject instanceof Collection<?>) {
			iteratedCol = (Collection<Object>) iteratedObject;
		}
		else if (iteratedObject instanceof Iterable) {
			iteratedCol = CollectionUtil.iterate((Iterable<Object>) iteratedObject);
		} else if (iteratedObject instanceof EolModelElementType) {
			iteratedCol = CollectionUtil.createDefaultList();
			iteratedCol.addAll(((EolModelElementType) iteratedObject).all());
		} else if (iteratedObject instanceof Iterator) {
			it = (Iterator<Object>) iteratedObject;
		} else {
			iteratedCol = CollectionUtil.createDefaultList();
			iteratedCol.add(iteratedObject);
		}

		if (it == null) {			
			it = iteratedCol.iterator();
		}

		return null;
	}

	public Iterator<Object> getIterator() {
		return it;
	}

}
