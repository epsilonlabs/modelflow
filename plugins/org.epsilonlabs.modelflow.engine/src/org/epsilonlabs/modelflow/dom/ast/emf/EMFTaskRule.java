/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.ast.IModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.TaskDeclaration;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;

/**
 * The Class TaskRule.
 */
public class EMFTaskRule extends TaskDeclaration implements IEMFDomElement<ITask>{

	private Collection<ITask> tasks = new ArrayList<>();

	@Override
	public Collection<ITask> getDomElements() {
		return tasks;
	}

	protected HashMap<String, Map<String, Object>> map = new HashMap<>();
	
	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		if (context instanceof IModelFlowCompilationContext) {
			IModelFlowCompilationContext ctx = (IModelFlowCompilationContext) context;

			List<Object> list = new ArrayList<>(1);
			list.add(getName());
			if (forEach != null) {
				forEach.compile(ctx);
			}
			createTask(ctx, getName());
		}
	}
	
	/**
	 * @return the iterator
	 */
	public List<Variable> getVars(String name) {
		return map.get(name).entrySet().stream()
				.map(e->new Variable(e.getKey(), e.getValue(), EolAnyType.Instance, true))
				.collect(Collectors.toList());
	}

	public void createTask(IModelFlowCompilationContext ctx, String name) {
		ITask task = DomFactory.eINSTANCE.createTask();
		task.setName(name);
		task.setDefinition(getType().getName());

		Class<ITaskInstance> factory = null;
		try {
			// Factory exists?
			factory = ctx.getModule().getTaskFactoryRegistry().getFactory(getType().getName());
		} catch (Exception e) {
			String msg = String.format("Unkown task factory '%s'", getType().getName());
			ctx.addWarningMarker(getType(), msg);
		}
		if (forEach == null) {			
			RuleUtil.setupConfigurableParameters(ctx, task, factory, this);
		} // wait for runtime to setup
		
		for (IModelCallExpression p : inputs) {
			p.compile(ctx);
			final EList<IResourceReference> consumes = task.getConsumes();
			if (p instanceof IEMFDomElement) {
				((IEMFDomElement<?>) p).getDomElements().stream()
					.filter(IResourceReference.class::isInstance)
					.map(IResourceReference.class::cast)
					.forEach(consumes::add);
			}
		}
		for (IModelCallExpression p : inouts) {
			p.compile(ctx);
			final EList<IResourceReference> modifies = task.getModifies();
			if (p instanceof IEMFDomElement) {
				((IEMFDomElement<?>) p).getDomElements().stream()
					.filter(IResourceReference.class::isInstance)
					.map(IResourceReference.class::cast)
					.forEach(modifies::add);
			}
		}
		for (IModelCallExpression p : outputs) {
			p.compile(ctx);
			final EList<IResourceReference> produces = task.getProduces();
			if (p instanceof IEMFDomElement) {
				((IEMFDomElement<?>) p).getDomElements().stream()
					.filter(IResourceReference.class::isInstance)
					.map(IResourceReference.class::cast)
					.forEach(produces::add);
			}
		}
		
		if (guard != null) {
			guard.compile(ctx);
			task.setGuard(guard);
		}
		task.setEnabled(enabled);
		task.setTraceable(trace);
		task.setAlwaysExecute(alwaysExecute);
		tasks.add(task);
		task.setModuleElement(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(map, tasks);
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
		if (!(obj instanceof EMFTaskRule)) {
			return false;
		}
		EMFTaskRule other = (EMFTaskRule) obj;
		return Objects.equals(map, other.map) && Objects.equals(tasks, other.tasks);
	}
	
}
