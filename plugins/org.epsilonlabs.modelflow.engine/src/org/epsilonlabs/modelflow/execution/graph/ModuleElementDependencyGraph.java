/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph;

import java.util.Collection;

import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskModuleElementNode;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bea
 *
 */
public class ModuleElementDependencyGraph extends AbstractDependencyGraph {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModuleElementDependencyGraph.class);
	
	public ModuleElementDependencyGraph() {
		super();
		this.graph = new SimpleDirectedGraph<>(DependencyEdge.class);		
	}
	
	@Override
	protected IDependencyGraph buildImpl(IModelFlowContext ctx) throws MFDependencyGraphException {
		final IModelFlowCompilationContext compilationCtx = ctx.getModule().getCompilationContext();
		final Collection<ITaskModuleElement> taskDeclarations = compilationCtx.getTaskDeclarations();
		taskDeclarations.stream().forEach(t->addTask(t, ctx));
		return null;
	}
	
	protected ITaskNode addTask(ITaskModuleElement t, IModelFlowContext ctx) {
		ITaskNode task = new TaskModuleElementNode(t);
		task.subscribe(ctx.getPublisher());
		getGraph().addVertex(task);
		this.tasks.put(task.getName(), task);
		/*t.getInputs().forEach(r -> addConsumesEdge(task, r));
		t.getModifies().forEach(r -> addModifiesEdge(task, r));
		t.getProduces().forEach(r -> addProducesEdge(task, r));*/
		return task;
	}

}
