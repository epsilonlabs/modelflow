/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast.emf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
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

	private Collection<ITask> tasks;

	@Override
	public Collection<ITask> getDomElements() {
		return tasks;
	}

	protected HashMap<String, Map<String, Object>> map = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		if (context instanceof IModelFlowCompilationContext) {
			IModelFlowCompilationContext ctx = (IModelFlowCompilationContext) context;

			List<Object> list = new ArrayList<>(1);
			list.add(getName());
			Iterator<Object> iterator = list.iterator();
			if (forEach != null) {
				forEach.compile(ctx);
				try {
					forEach.execute(ctx.getModule().getContext());
					iterator = forEach.getIterator();
				} catch (EolRuntimeException e) {
					ctx.addErrorMarker(forEach, e.getMessage());
				}
			}
			FrameStack frameStack = ctx.getFrameStack();
			for (int loop = 1; iterator.hasNext(); loop++) {
				final Map<String, Object> itMap = new HashMap<>();
				Object next = iterator.next();				
				String name = getName();
				try {
					if (forEach != null){		
						final Variable[] vars = getVariables(ctx.getModule().getContext(), loop, next);
						frameStack.enterLocal(FrameType.UNPROTECTED, this, vars);
						name += String.format("@%d", loop);
						final Map<String, Object> varMap = Arrays.stream(vars).collect(Collectors.toMap(v -> v.getName(), v -> v.getValue()));
						map.put(name, varMap);
					}
					createTask(ctx, name);
					if (forEach != null){	
						frameStack.leaveLocal(this);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}			
			
		}
	}

	private Variable[] getVariables(IEolContext ctx, int loop, Object next) throws EolRuntimeException {
		final Parameter iterator = forEach.getIteratorParameter();
		Variable itemVar = new Variable(iterator.getName(), next, iterator.getType(ctx));
		Variable countVar = new Variable("loopCount", loop, EolPrimitiveType.Integer, true);
		return new Variable[] {itemVar, countVar};
	}
	
	/**
	 * @return the iterator
	 */
	public List<Variable> getVars(String name) {
		return map.get(name).entrySet().stream()
				.map(e->new Variable(e.getKey(), e.getValue(), EolAnyType.Instance, true))
				.collect(Collectors.toList());
	}

	protected void createTask(IModelFlowCompilationContext ctx, String name) {
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
		RuleUtil.setupConfigurableParameters(ctx, task, factory, this);
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
		if (!enabled) {
			task.setEnabled(false);
		}
		if (!trace) {
			task.setTraceable(false);
		}
		if (alwaysExecute) {
			task.setAlwaysExecute(true);
		}
		tasks.add(task);
		task.setModuleElement(this);
	}


}
