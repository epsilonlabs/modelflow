package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;

public class TaskModuleElementNode extends AbstractTaskNode {

	public TaskModuleElementNode(ITaskModuleElement declaration) {
		super(declaration);
	}

	protected TaskModuleElementNode(ITaskModuleElement declaration, String name) {
		super(declaration, name);
	}

	protected Multimap<ResourceKind, IModelResourceNode> models = new Multimap<>();

	protected void resolveModelNodes(IModelFlowContext ctx) {
		taskDeclaration.getInputs().stream().map(m -> new ModelModuleElementNode(m, ctx))
				.forEach(input -> models.put(ResourceKind.INPUT, input));
		taskDeclaration.getInouts().stream().map(m -> new ModelModuleElementNode(m, ctx))
				.forEach(inout -> models.put(ResourceKind.INOUT, inout));
		taskDeclaration.getOutputs().stream().map(m -> new ModelModuleElementNode(m, ctx))
				.forEach(output -> models.put(ResourceKind.OUTPUT, output));
	}

	public Multimap<ResourceKind, IModelResourceNode> getModels() {
		return models;
	}
	
	@Override
	protected ITaskNode createSubNode(ITaskModuleElement declaration, String name) {
		return new TaskModuleElementNode(declaration, name);
	}

	@Override
	public Set<String> getResourceAliases(String resourceNode) {
		final Optional<IModelResourceNode> optional = getModels().entrySet().stream().map(Entry::getValue)
				.flatMap(Collection::stream).filter(m -> m.getName().equals(resourceNode)).findAny();
		if (optional.isPresent()) {
			return optional.get().getAliases();
		}
		return Collections.emptySet();
	}
	
	// TO process
	
	public void attemptIndividualExecution(IModelFlowContext ctx) throws MFRuntimeException {
		resolveModelNodes(ctx);
		super.attemptIndividualExecution(ctx);
	}
	

	protected void safelyDispose(IModelFlowContext ctx) {
		ctx.getTaskRepository().getResourceRepository();
		// declaration.getOutputs().forEach(o->o.getTargetExpression());
		Collection<ModelDeclaration> models = Collections.emptyList();
		for (ModelDeclaration m : models) {
			// if (isLastUse?){
			// dispose
			// }
		}
	}

	@Override
	protected boolean shouldExecuteBasedOnTrace(IModelFlowContext ctx) {
		return true;
	}

	@Override
	protected void noNeedToExecute(IModelFlowContext ctx) {
		// Copy Execution Trace
		conservativeExecutionHelper.copyFromPrevious();

		// Safely dispose no longer used models
		safelyDispose(ctx);
	}

	@Override
	protected List<IModel> getForEachModels(IModelFlowContext ctx) throws MFRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void resolveTask(IModelFlowContext ctx) throws MFRuntimeException {
		// TODO Auto-generated method stub
		
	}

}
