package org.epsilonlabs.modelflow.dom.ast;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.eol.types.EolUndefined;

public class ParameterDeclaration extends Expression {
	
	protected NameExpression nameExpression;
	protected TypeExpression typeExpression;
	
	public ParameterDeclaration() {}
	
	public ParameterDeclaration(NameExpression nameExpression, TypeExpression typeExpression) {
		this.nameExpression = nameExpression;
		this.typeExpression = typeExpression;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		nameExpression = (NameExpression) module.createAst(cst.getFirstChild(), this);
		AST typeExpressionAst = cst.getSecondChild();
		typeExpression = (TypeExpression) module.createAst(typeExpressionAst, this);
	}

	@Override
	public void compile(IEolCompilationContext context) {
		EolType type;
		
		if (typeExpression != null) {
			typeExpression.compile(context);
			type = typeExpression.getCompilationType();
		}
		else {
			type = EolAnyType.Instance;
		}

		if (context.getFrameStack().getTopFrame().contains(getName())) {
			if (type != EolAnyType.Instance) {				
				Variable param = context.getFrameStack().getTopFrame().get(getName());
				if (!param.getType().equals(type)) {
					String msg = String.format("Param '%s' expected type '%s' not '%s'", 
							getName(), getTypeExpression().getName(), param.getType().getName());
					context.addErrorMarker(this, msg);
				}
			}
		}
		else {
			String msg = String.format("Param '%s' has not been defined", getName());
			context.addErrorMarker(this, msg);
		}
	}
	
	@Override
	public Variable execute(IEolContext context) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		Variable variable = null;

		EolType variableType = typeExpression == null ? EolAnyType.Instance :
			(EolType) context.getExecutorFactory().execute(typeExpression, context);
		
		if (!frameStack.contains(getName())){
			variable = Variable.createReadOnlyVariable(getName(), EolUndefined.INSTANCE);
			frameStack.put(variable);
		} else {
			variable = frameStack.get(getName());
		}
		
		return variable;
	}
	
	
	public String getName() {
		return nameExpression.getName();
	}
	
	public TypeExpression getTypeExpression() {
		return typeExpression;
	}
	
	public void setTypeExpression(TypeExpression typeExpression) {
		this.typeExpression = typeExpression;
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}

}
