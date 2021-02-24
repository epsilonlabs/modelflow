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
import java.util.Objects;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.IEolVisitor;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;

/**
 * @author Betty Sanchez
 *
 */
public class ForEachModuleElement extends Statement {

	protected Iterator<Object> it;
	protected IExecutableModuleElement iterationBlock;
	protected IExecutableModuleElement labelBlock;
	protected Parameter iteratorParameter;


	@Override
	public void build(AST cst, IModule module) {
		iteratorParameter = (Parameter)  module.createAst(cst.getFirstChild(), this);
		AST iteration = (cst.getThirdChild()!=null)? cst.getThirdChild(): cst.getSecondChild();
		AST label = (cst.getThirdChild()!=null)? cst.getSecondChild() : null;
		iterationBlock = (IExecutableModuleElement) module.createAst(iteration, this);
		if (label != null) {			
			labelBlock = (IExecutableModuleElement) module.createAst(label, this);
		}
	}

	@Override
	public void compile(IEolCompilationContext context) {
		iteratorParameter.compile(context);
		if (iterationBlock instanceof ICompilableModuleElement) {
			((ICompilableModuleElement)iterationBlock).compile(context);
		}
		context.getFrameStack().enterLocal(FrameType.UNPROTECTED, iterationBlock,
				new Variable("loopCount", EolPrimitiveType.Integer), new Variable("hasMore", EolPrimitiveType.Boolean));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Return execute(IEolContext context) throws EolRuntimeException {
		Object iteratedObject = this.iterationBlock.execute(context);
		if (iteratedObject instanceof Return) {
			iteratedObject = ((Return) iteratedObject).getValue();
		}
		
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

	@Override
	public void accept(IEolVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	public IExecutableModuleElement getIterationBlock() {
		return iterationBlock;
	}
	
	public Parameter getIteratorParameter() {
		return iteratorParameter;
	}	
	
	public IExecutableModuleElement getLabelBlock() {
		return labelBlock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(it, iterationBlock, iteratorParameter, labelBlock);
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
		if (!(obj instanceof ForEachModuleElement)) {
			return false;
		}
		ForEachModuleElement other = (ForEachModuleElement) obj;
		return Objects.equals(it, other.it) && Objects.equals(iterationBlock, other.iterationBlock)
				&& Objects.equals(iteratorParameter, other.iteratorParameter)
				&& Objects.equals(labelBlock, other.labelBlock);
	}

}
