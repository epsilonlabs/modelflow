/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

/**
 * The Class TaskRule.
 */
public class TaskRule extends ConfigurableRule<ITask> {

	/** The task. */
	protected Collection<ITask> tasks = new ArrayList<>();

	/** The guard. */
	protected ExecutableBlock<Boolean> guard;
	
	/** For Each */
	protected ForEachModuleElement forEach;

	/** The inputs. */
	protected List<ModelCallExpression> inputs = new ArrayList<>();
	
	protected List<ModelCallExpression> outputs = new ArrayList<>();
	
	protected List<ModelCallExpression> inouts = new ArrayList<>();
	
	protected List<TaskDependencyExpression> dependsOn = new ArrayList<>();

	protected boolean enabled = true;
	
	protected boolean alwaysExecute = false;
	
	protected boolean trace = true;

	/**
	 * Builds the.
	 *
	 * @param cst    the cst
	 * @param module the module
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		// FOREACH
		forEach = (ForEachModuleElement) module.createAst(AstUtil.getChild(cst, ModelFlowParser.FOREACH), this);
		
		// Guard
		guard = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, ModelFlowParser.GUARD), this);

		// Inputs
		process(cst, ModelFlowParser.INPUTS, inputs, module);
		// Outputs
		process(cst, ModelFlowParser.OUTPUTS, outputs, module);
		// Inouts
		process(cst, ModelFlowParser.INOUTS, inouts, module);

		// Inputs
		AST depends = AstUtil.getChild(cst, ModelFlowParser.DEPENDSON);
		for (AST child : AstUtil.getChildren(depends)) {
			TaskDependencyExpression exp = (TaskDependencyExpression) module.createAst(child, this);
			dependsOn.add(exp);
		}
				
		// @disabled 
		if (getAnnotation("disabled") != null) enabled = false;
		// @noTrace 
		if (getAnnotation("noTrace") != null) trace = false;
		// @always
		if (getAnnotation("always") != null) alwaysExecute = true;
	}
	
	protected void process(AST cst, Integer token, List<ModelCallExpression> destination, IModule module){
		AST elements = AstUtil.getChild(cst, token);
		if (elements != null) {			
			AST list = elements.getFirstChild();
			for (AST modelRef : list.getChildren()) {
				ModelCallExpression element = null;
				if (modelRef.getToken().getType() == ModelFlowParser.TASKRESOURCE) {
					element = (ModelCallExpression) module.createAst(modelRef, this);
				} else {					
					AST ref = AstUtil.getChild(modelRef, ModelFlowParser.TASKRESOURCE);
					element = (ModelCallExpression) module.createAst(ref, this);
					
					List<AST> aliases = AstUtil.getChildren(modelRef, ModelFlowParser.NAME);
					for (AST alias : aliases) {
						NameExpression name = (NameExpression) module.createAst(alias, this);
						element.addAlias(name);
					}				
				}
				if (element != null) {					
					destination.add(element);
				}
			}
		}
	}
	
	public boolean isGenerator() {
		return forEach != null;
	}

	/**
	 * Gets the guard.
	 *
	 * @return the guard
	 */
	public ExecutableBlock<Boolean> getGuard() {
		return guard;
	}

	/**
	 * Sets the guard.
	 *
	 * @param guard the new guard
	 */
	public void setGuard(ExecutableBlock<Boolean> guard) {
		this.guard = guard;
	}

	/**
	 * @return the dependsOn
	 */
	public List<TaskDependencyExpression> getDependsOn() {
		return dependsOn;
	}
	
	/**
	 * Gets the emf element.
	 *
	 * @return the emf element
	 */
	@Override
	public Collection<ITask> getDomElements() {
		return tasks;
	}

	protected HashMap<String, Map<String, Object>> map = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void compile(IEolCompilationContext context) {
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
		setupConfigurableParameters(ctx, task, factory);
		for (ModelCallExpression p : inputs) {
			p.compile(ctx);
			task.getConsumes().addAll(p.getDomElements());
		}
		for (ModelCallExpression p : inouts) {
			p.compile(ctx);
			task.getModifies().addAll(p.getDomElements());
		}
		for (ModelCallExpression p : outputs) {
			p.compile(ctx);
			task.getProduces().addAll(p.getDomElements());
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



	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return null;
	}

}
