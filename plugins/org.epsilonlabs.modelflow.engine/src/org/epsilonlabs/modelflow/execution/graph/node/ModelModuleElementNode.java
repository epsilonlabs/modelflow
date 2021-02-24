/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.eol.dom.NameExpression;
import org.epsilonlabs.modelflow.dom.ast.IModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.IModelModuleElement;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author bea
 *
 */
public class ModelModuleElementNode implements IModelResourceNode {

	protected IModelCallExpression modelRef;
	protected IModelModuleElement model;
	
	public ModelModuleElementNode(IModelCallExpression callExpression, IModelFlowContext ctx) {
		modelRef = callExpression;
		ctx.getModule().getCompilationContext().getResourceDeclarations().stream()
			.filter(m->m.getNameExpression().getName().equals(getName()))
			.findAny().ifPresent(m -> model = m);
	}
	
	@Override
	public Set<String> getAliases() {
		return modelRef.getAliases().stream().map(NameExpression::getName).collect(Collectors.toSet());
	}

	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getDefinition() {
		return model.getType().getName();
	}

	
	// Useless
	
	@Override
	public void subscribe(IModelFlowPublisher pub) {
		// do nothing
	}

	@Override
	public IModelModuleElement getModuleElement() {
		return model;
	}

}
