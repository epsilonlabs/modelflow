/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

/**
 * @author Betty Sanchez
 *
 */
public class TaskDependencyExpression extends Expression implements IDomElement<ITaskDependency>  {

	protected ITaskDependency taskDependency;
	protected TaskRule declaringTaskRule;
	protected NameExpression target; 

	/**
	 * Constructor that takes the TaskRule as parent
	 */
	public TaskDependencyExpression(TaskRule taskRule) {
		this.declaringTaskRule = taskRule;
	}
	/**
	 * Builds the AST element
	 *
	 * @param cst    the cst
	 * @param module the module
	 */
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		target = new NameExpression(cst.getText());
		target.setRegion(cst.getRegion());
		target.setUri(cst.getUri());
		target.setModule(cst.getModule());
	}

	/**
	 * Compile.
	 *
	 * @param context the context
	 */
	@Override
	public void compile(IEolCompilationContext context) {
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			// Create task dependency element
			taskDependency = DomFactory.eINSTANCE.createTaskDependency();
			// Assign parent as after
			declaringTaskRule.getDomElements().stream().forEach(taskDependency::setTask);
			// Assign name expression as before 
			List<TaskRule> rules = ctx.getTaskDeclarations().stream()
				// Match tasks with dependency name
				.filter(t->t.getName().equals(target.getName()))
				.collect(Collectors.toList());
			// Check that there is only one task that matches this criteria
			if (rules.size() == 1) {
				TaskRule taskRule = rules.get(0);
				Collection<ITask> domElements = taskRule.getDomElements();
				
				// Assing task as before in the task dependency  
				domElements.forEach(taskDependency::setDependsOn);
			}
			else {
				String msg = String.format("Task with name '%s' could not be found", target.getName()); 
				ctx.addErrorMarker(this, msg);
			}
		}
	}

	/**
	 * Execute.
	 *
	 * @param context the context
	 * @return the object
	 * @throws EolRuntimeException the eol runtime exception
	 */
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		// Does nothing
		return null;
	}

	@Override
	public Collection<ITaskDependency> getDomElements() {
		return Arrays.asList(taskDependency);
	}

}
