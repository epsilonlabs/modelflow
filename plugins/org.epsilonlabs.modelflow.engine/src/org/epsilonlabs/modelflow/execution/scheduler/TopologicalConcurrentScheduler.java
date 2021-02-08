/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.scheduler;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.DependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.ExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.IDependencyGraph;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePrinter;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TopologicalSequentialScheduler is an executor that sequentially iterates
 * over the tasks in the execution graph. The iteration is carried in
 * topological order.
 */
public class TopologicalConcurrentScheduler extends AbstractScheduler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TopologicalConcurrentScheduler.class);
		
	protected ConcurrentExecutorManager manager = new ConcurrentExecutorManager();
	
	protected IDependencyGraph dg;
	protected IExecutionGraph eg;
	
	public TopologicalConcurrentScheduler () {
		dg = new DependencyGraph();
		eg = new ExecutionGraph();
	}
	
	@Override
	public IDependencyGraph getDependencyGraph(){
		return dg;
	}
	
	@Override
	public IExecutionGraph getExecutionGraph() {
		return eg;
	}
	
	@Override
	public void build(IModelFlowContext ctx) throws Exception {
		dg.build(ctx);
		eg.build(ctx);
	}
	
	/**
	 * Execute.
	 *
	 * @param ctx the ctx
	 * @return the workflow execution
	 * @throws MFExecutionException the MF execution exception
	 */
	@Override
	protected WorkflowExecution executeImpl(IModelFlowContext ctx) throws MFExecutionException {
		ctx.validate();

		// Locate Full Execution Trace
		IWorkflow wf = ctx.getModule().getWorkflow();
		WorkflowExecution currentWorkflowExecution = updater.createWorkflowExecution(wf);


		// Check workflow is populated
		if (eg.getState().isPopulated()) {

			// Get Iterator
			TopologicalOrderIterator<ITaskNode, ExecutionEdge> iterator = new TopologicalOrderIterator<>(eg.getGraph());
			
			/* Iterate over Tasks */
			while (iterator.hasNext()) {

				// Next Task on iterator
				ITaskNode task = iterator.next();
				
				new ConcurrentTaskExecutor(task, ctx).init();
				
			} // END WHILE
			
			manager.await();
			
			LOG.debug("\n{}", new ExecutionTracePrinter(updater.getTrace()));

		} else {
			/* Throw because workflow is not populated */
			throw new MFExecutionException("Execution graph is not populated");
		}
		LOG.info("Finished Execution");
		/* Return this execution */
		return currentWorkflowExecution;
	}
	
	/**
	 * @return the manager
	 */
	public ConcurrentExecutorManager getManager() {
		return manager;
	}
	
	class ConcurrentTaskExecutor {
		
		private ITaskNode task;
		private Deque<ITaskNode> que = new ConcurrentLinkedDeque<>();
		private IModelFlowContext ctx;

		public ConcurrentTaskExecutor(ITaskNode task, IModelFlowContext ctx) {
			this.task = task;
			this.ctx = ctx;
		}

		public void init() {
			Collection<ITaskNode> dependencies = eg.getPredecessorTasks(task);
			this.que.addAll(dependencies);
			int count = manager.increment();
			LOG.debug("Thread Count: {}", count);
			if (dependencies.isEmpty()) {
				pop();
			} else {
				dependencies.stream().forEach(
						t -> t.getObservable()
						.doOnError(manager::handleException)
						.doOnComplete(this::pop)
						.subscribe());
			}
		}

		protected void pop() {
			if (!que.isEmpty()) {
				que.pop();
			}
			if (que.isEmpty()) {
				manager.submit(() -> {
					LOG.debug("Executing {}", task.getName());
					try {
						executeTask(ctx, task);
					} catch (MFRuntimeException e) {
						manager.handleException(e);
					}
				});
			}
		}
	}
}
