/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.TaskDependencyExpression;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

/**
 * @author Betty Sanchez
 *
 */
public class EMFTaskDependencyExpression extends TaskDependencyExpression implements IEMFDomElement<ITaskDependency>  {

	protected ITaskDependency taskDependency;
	protected ITaskModuleElement declaringTaskRule;
	
	/**
	 * Constructor that takes the TaskRule as parent
	 */
	public EMFTaskDependencyExpression(ITaskModuleElement taskRule) {
		this.declaringTaskRule = taskRule;
	}
	
	/**
	 * Compile.
	 *
	 * @param context the context
	 */
	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;
			// Create task dependency element
			taskDependency = DomFactory.eINSTANCE.createTaskDependency();
			// Assign parent as after
			((EMFTaskRule) declaringTaskRule).getDomElements().stream().forEach(taskDependency::setTask);
			// Assign name expression as before 
			List<EMFTaskRule> rules = ctx.getTaskDeclarations().stream()
				.filter(EMFTaskRule.class::isInstance)
				.map(EMFTaskRule.class::cast)
				// Match tasks with dependency name
				.filter(t->t.getName().equals(target.getName()))
				.collect(Collectors.toList());
			// Check that there is only one task that matches this criteria
			if (rules.size() == 1) {
				EMFTaskRule taskRule = rules.get(0);
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

	@Override
	public Collection<ITaskDependency> getDomElements() {
		return Arrays.asList(taskDependency);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(declaringTaskRule, taskDependency);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof EMFTaskDependencyExpression)) {
			return false;
		}
		EMFTaskDependencyExpression other = (EMFTaskDependencyExpression) obj;
		return Objects.equals(declaringTaskRule, other.declaringTaskRule)
				&& Objects.equals(taskDependency, other.taskDependency);
	}

}
