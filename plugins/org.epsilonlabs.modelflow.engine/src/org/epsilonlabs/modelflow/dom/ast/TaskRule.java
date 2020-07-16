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
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.factory.ITaskFactory;
import org.epsilonlabs.modelflow.dom.impl.DomFactoryImpl;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

/**
 * The Class TaskRule.
 */
public class TaskRule extends ParametrisedRule<Task> {

	/** The task. */
	protected Task task;

	/** The guard. */
	protected ExecutableBlock<Boolean> guard;

	/** The inputs. */
	protected List<ModelCallExpression> inputs = new ArrayList<>();
	
	protected List<ModelCallExpression> outputs = new ArrayList<>();
	
	protected List<ModelCallExpression> inouts = new ArrayList<>();
	
	protected List<TaskDependencyExpression> dependsOn = new ArrayList<>();

	protected boolean enabled = true;
	
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
		// @volatile 
		if (getAnnotation("noTrace") != null) trace = false;
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
	public Collection<Task> getDomElements() {
		return Arrays.asList(task);
	}

	@Override
	public void compile(EolCompilationContext context) {
		if (context instanceof ModelFlowCompilationContext) {
			ModelFlowCompilationContext ctx = (ModelFlowCompilationContext) context;

			task = DomFactoryImpl.eINSTANCE.createTask();
			task.setName(getName());
			task.setDefinition(getType().getName());
	
			ITaskFactory factory = null;
			try {
				// Factory exists?
				factory = ctx.getModule().getTaskFactoryRegistry().getFactory(getType().getName());
			} catch (Exception e) {
				String msg = String.format("Unkown task factory '%s'", getType().getName());
				ctx.addWarningMarker(getType(), msg);
			}
			for (Entry<NameExpression, ModuleElement> p : parameters.entrySet()) {
				Property property = DomFactoryImpl.eINSTANCE.createProperty();
				property.setKey(p.getKey().getName());
				property.setValue(p.getValue());
				task.getProperties().add(property);
				if (factory != null && !factory.getParameters().contains(property.getKey())) {
					String msg = String.format("Parameter '%s' does not exist for task type '%s'", p.getKey().getName(), getType().getName());
					ctx.addWarningMarker(p.getKey(), msg);
				}
			}
			for (ModelCallExpression p : inputs) {
				p.compile(context);
				task.getConsumes().addAll(p.getDomElements());
			}
			for (ModelCallExpression p : inouts) {
				p.compile(context);
				task.getModifies().addAll(p.getDomElements());
			}
			for (ModelCallExpression p : outputs) {
				p.compile(context);
				task.getProduces().addAll(p.getDomElements());
			}
			if (guard != null) {
				guard.compile(context);
				task.setGuard(guard);
			}
			if (!enabled) {
				task.setEnabled(false);
			}
			if (!trace) {
				task.setTraceable(false);
			}
		}
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object object = super.execute(context);
		if (guard != null) {
			// TODO fix guard
			// task.setGuard(guard.execute(context));
		}
		return object;
	}

	@Override
	protected EList<Property> getProperties() {
		return task.getProperties();
	}

}
