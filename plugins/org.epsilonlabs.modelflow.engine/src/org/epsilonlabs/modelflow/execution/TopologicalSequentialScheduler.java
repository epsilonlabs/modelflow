/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.IExecutionGraph;
import org.epsilonlabs.modelflow.execution.graph.edge.ExecutionEdge;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTracePrinter;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
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
	public WorkflowExecution execute(IModelFlowContext ctx) throws MFExecutionException {
		ctx.validate();

		IExecutionGraph eg = ctx.getExecutionGraph();

		// Locate Full Execution Trace
		Workflow wf = ctx.getModule().getWorkflow();
		ExecutionTraceUpdater updater = new ExecutionTraceUpdater(ctx.getExecutionTrace());
		WorkflowExecution currentWorkflowExecution = updater.createWorkflowExecution(wf);

		// Check workflow is populated
		if (eg.getState().isPopulated()) {
			// Start Workflow Execution Timer
			currentWorkflowExecution.setStart(System.nanoTime());

			// Get Iterator
			TopologicalOrderIterator<ITaskNode, ExecutionEdge> iterator = new TopologicalOrderIterator<>(eg.getGraph());

			/* Iterate over Tasks */
			while (iterator.hasNext()) {

				// Next Task on iterator
				ITaskNode task = iterator.next();

				// Execute
				executeTask(ctx, updater, task);

			} // END WHILE

			/* Stop Workflow Execution Timer */
			currentWorkflowExecution.setEnd(System.nanoTime());
			/* Stop Workflow Execution Timer */
			currentWorkflowExecution.setEndState("Success");

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
	 * 
	 * The execution process of a single task.
	 *
	 * @param ctx                      the ModelFlow module context
	 * @param currentWorkflowExecution the current workflow execution trace to keep
	 *                                 updating
	 * @param task                     the task to be executed
	 * @throws MFExecutionException the MF execution exception
	 */
	protected void executeTask(IModelFlowContext ctx, ExecutionTraceUpdater updater, ITaskNode task)
			throws MFExecutionException {
		/* Notify about to execute */
		for (IExecutionListener listener : executionListeners) {
			listener.preparingForExecution(this, task);
		}

		/* New Task Execution Record */
		TaskExecution currentTaskExecution = updater.createTaskExecution(task.getTaskDefinition());
		
		/* Notify executing */
		for (IExecutionListener listener : executionListeners) {
			listener.executing(this, task);
		}

		LOG.info("Executing: {}", task.getTaskDefinition().getName());
		ctx.getOutputStream().printf("%n>>Executing: %s%n", task.getTaskDefinition().getName());
		
		/* Start Task Execution Record Timer */
		currentTaskExecution.setStart(System.nanoTime());

		/* Execute Task */
		try {
			task.execute(ctx);
		} catch (Exception e) {
			processException(ctx, task, currentTaskExecution, updater.getCurrentWorkflowExecution(), e);
		}

		/* Stop Task Timer */
		currentTaskExecution.setEnd(System.nanoTime());

		/* Successful Execution Status */
		currentTaskExecution.setEndState(task.getState().toString());
		LOG.info("Status {}", currentTaskExecution.getEndState());
		ctx.getOutputStream().printf("%n--Status: %s%n", currentTaskExecution.getEndState());

		/* Notify finished execution */
		for (IExecutionListener listener : executionListeners) {
			listener.finishedExecution(this, task);
		}
	}

	/**
	 * Processes the exception, i.e. saves the trace and notifies listeners
	 *
	 * @param node the node
	 * @param taskExecution the task execution
	 * @param workflowExecution the workflow execution
	 * @param exception the exception
	 * @throws MFExecutionException the ModelFlow execution exception
	 */
	protected void processException(IModelFlowContext ctx, ITaskNode node, TaskExecution taskExecution, WorkflowExecution workflowExecution, Throwable exception)
			throws MFExecutionException {
		long endTime = System.nanoTime();
		taskExecution.setEnd(endTime);
		workflowExecution.setEnd(endTime);
		taskExecution.setEndState(exception.getClass().getSimpleName());
		workflowExecution.setEndState("Exception");
		ctx.getErrorStream().printf("%n--Status: %s%n", "Exception");
		/* Notify finished with exception */
		for (IExecutionListener listener : executionListeners) {
			listener.executionThrew(this, node, exception);
		}
		ModuleElement moduleElement = ctx.getModule().getCompilationContext().getTaskModuleElement(node.getTaskDefinition());
		ctx.getErrorStream().println(exception.getMessage());
		throw new MFExecutionException(moduleElement,exception);
	}

}
