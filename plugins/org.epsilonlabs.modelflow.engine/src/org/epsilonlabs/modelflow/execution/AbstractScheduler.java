/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.control.IMeasurable;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTraceUpdater;
import org.epsilonlabs.modelflow.execution.trace.TaskExecution;
import org.epsilonlabs.modelflow.execution.trace.WorkflowExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractScheduler implements IScheduler {

	/** The Constant LOG. */
	static final Logger LOG = LoggerFactory.getLogger(AbstractScheduler.class);
	
	protected List<IExecutionListener> executionListeners = new ArrayList<>();
	
	@Override
	public void addExecutionListener(IExecutionListener listener) {
		final IExecutionListener lst = listener;
		this.executionListeners.add(lst);
	}

	@Override
	public List<IExecutionListener> getExecutionListeners() {
		return executionListeners;
	}
	
	@Override
	public WorkflowExecution execute(IModelFlowContext ctx) throws MFExecutionException {
		WorkflowExecution executeImpl = null; 
		try {
			ctx.getProfiler().start(IMeasurable.Stage.EXECUTION_PROCESS, null, ctx);
			executeImpl = executeImpl(ctx);
		} finally {
			ctx.getProfiler().stop(IMeasurable.Stage.EXECUTION_PROCESS, null, ctx);
		}
		return executeImpl;
	}
	
	protected abstract WorkflowExecution executeImpl(IModelFlowContext ctx) throws MFExecutionException;
	
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
	protected void executeTask(IModelFlowContext ctx, ExecutionTraceUpdater updater, ITaskNode task) throws MFExecutionException {

		/* Notify about to execute */
		executionListeners.parallelStream().forEach(l->l.preparingForExecution(this, task));
	
		/* New Task Execution Record */
		TaskExecution currentTaskExecution = updater.createTaskExecution(task.getTaskDefinition());
		
		/* Notify executing */
		executionListeners.parallelStream().forEach(l->l.executing(this, task));

		ctx.getOutputStream().printf("%n>>Executing: %s%n", task.getTaskDefinition().getName());

		/* Profiler start */
		ctx.getProfiler().start(IMeasurable.Stage.TASK_EXECUTION_PROCESS, task, ctx);
	
		/* Execute Task */
		try {
			task.execute(ctx);
		} catch (Exception e) {
			processException(ctx, task, currentTaskExecution, updater.getCurrentWorkflowExecution(), e);
		} finally {
			/* Profiler end */
			ctx.getProfiler().stop(IMeasurable.Stage.TASK_EXECUTION_PROCESS, task, ctx);
		}
	
		/* Successful Execution Status */
		currentTaskExecution.setEndState(task.getState().toString());

		ctx.getOutputStream().printf("%n--Status: %s%n", currentTaskExecution.getEndState());
	
		/* Notify finished execution */
		executionListeners.parallelStream().forEach(l->l.finishedExecution(this, task));		
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

		/* End state */
		taskExecution.setEndState(exception.getClass().getSimpleName());
		workflowExecution.setEndState("Exception");

		/* Notify finished with exception */
		executionListeners.parallelStream().forEach(l->l.executionThrew(this, node, exception));

		/* Print error */
		ctx.getErrorStream().printf("%n--Status: %s%n", "Exception");
		exception.printStackTrace(ctx.getErrorStream());
		ctx.getErrorStream().println(exception.getMessage());
		
		/* Clean in-memory repository */
		ctx.getTaskRepository().clear();

		ModuleElement moduleElement = ctx.getModule().getCompilationContext().getTaskModuleElement(node.getTaskDefinition());
		throw new MFExecutionException(moduleElement,exception);
	}

}
