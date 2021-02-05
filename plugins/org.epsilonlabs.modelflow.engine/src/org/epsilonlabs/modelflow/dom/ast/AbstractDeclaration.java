/**
 * 
 */
package org.epsilonlabs.modelflow.dom.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.erl.dom.NamedRule;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

/**
 * @author bea
 *
 */
public abstract class AbstractDeclaration extends NamedRule implements IDeclaration {

	protected NameExpression type;
	protected Map<NameExpression, ModuleElement> parameters;

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

	@Override
	public void compile(IEolCompilationContext context) {
		type.compile(context);
		parameters.entrySet().forEach(e -> {
			e.getKey().compile(context);
			if (e.getValue() instanceof ICompilableModuleElement) {
				((ICompilableModuleElement) e.getValue()).compile(context);
			}
		});
	}
	
	@Override
	public NameExpression getType() {
		return type;
	}
	
	@Override
	public Map<NameExpression, ModuleElement> getParameters() {
		return parameters;
	}
}
