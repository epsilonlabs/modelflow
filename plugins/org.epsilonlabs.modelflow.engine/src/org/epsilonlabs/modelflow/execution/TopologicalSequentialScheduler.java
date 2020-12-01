/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePrinter;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TopologicalSequentialScheduler is an executor that sequentially iterates
 * over the tasks in the execution graph. The iteration is carried in
 * topological order.
 */
public class TopologicalSequentialScheduler extends AbstractScheduler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TopologicalSequentialScheduler.class);

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

		IExecutionGraph eg = ctx.getExecutionGraph();

		// Locate Full Execution Trace
		Workflow wf = ctx.getModule().getWorkflow();
		ExecutionTraceUpdater updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		WorkflowExecution currentWorkflowExecution;
		synchronized (updater) {
			currentWorkflowExecution = updater.createWorkflowExecution(wf);
		}

		// Check workflow is populated
		if (eg.getState().isPopulated()) {

			// Get Iterator
			TopologicalOrderIterator<ITaskNode, ExecutionEdge> iterator = new TopologicalOrderIterator<>(eg.getGraph());

			/* Iterate over Tasks */
			while (iterator.hasNext()) {

				// Next Task on iterator
				ITaskNode task = iterator.next();
				
				synchronized (updater) {
					try {
						executeTask(ctx, updater, task);
					} catch (Exception e) {
						throw e;
					}
				}

			} // END WHILE
			
			LOG.debug("\n{}", new ExecutionTracePrinter(updater.getTrace()));

		} else {
			/* Throw because workflow is not populated */
			throw new MFExecutionException("Execution graph is not populated");
		}
		
		/* Clean in-memory repository */
		ctx.getTaskRepository().clear();
		
		LOG.info("Finished Execution");
		/* Return this execution */
		return currentWorkflowExecution;
	}
	

}
