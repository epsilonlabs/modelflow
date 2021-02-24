/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.compile.context.IEolCompilationContext;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFTaskDependencyExpression;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;

public class TaskDeclaration extends AbstractDeclaration implements ITaskModuleElement {
	
	protected ExecutableBlock<Boolean> guard;	
	protected ForEachModuleElement forEach;
	protected List<IModelCallExpression> inputs = new ArrayList<>();
	protected List<IModelCallExpression> outputs = new ArrayList<>();
	protected List<IModelCallExpression> inouts = new ArrayList<>();
	protected List<IModelCallExpression> trans = new ArrayList<>();
	protected List<TaskDependencyExpression> dependsOn = new ArrayList<>();
	protected List<TaskDependencyExpression> requires = new ArrayList<>();

	// Annotations
	protected boolean enabled = true;
	protected boolean alwaysExecute = false;
	protected boolean trace = true;

	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		// FOREACH
		forEach = (ForEachModuleElement) module.createAst(AstUtil.getChild(cst, ModelFlowParser.FOREACH), this);
		
		// Guard
		guard = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, ModelFlowParser.GUARD), this);

		// MODELS
		process(cst, ModelFlowParser.INPUTS, inputs, module);
		process(cst, ModelFlowParser.OUTPUTS, outputs, module);
		process(cst, ModelFlowParser.INOUTS, inouts, module);
		//process(cst, ModelFlowParser.TRANS, inouts, module); // TRANS
		
		// Dependencies
		AST dependencies = AstUtil.getChild(cst, ModelFlowParser.DEPENDSON);
		for (AST dependency : AstUtil.getChildren(dependencies)) {
			EMFTaskDependencyExpression exp = (EMFTaskDependencyExpression) module.createAst(dependency, this);
			dependsOn.add(exp);
		}
				
		setupAnnotations();
	}
	
	// TODO: Check that task type is known
	// TODO: Check that parameters keys are valid
	// trans.forEach(m->m.compile(context));
	// requires.forEach(d->d.compile(context));
	@Override
	public void compile(IEolCompilationContext context) {
		super.compile(context);
		
		if (forEach != null) {
			forEach.compile(context);
		}
		if (guard != null) {			
			guard.compile(context);
		}
		inputs.forEach(m->m.compile(context));
		outputs.forEach(m->m.compile(context));
		inouts.forEach(m->m.compile(context));
		dependsOn.forEach(d->d.compile(context));
	}

	protected void process(AST cst, Integer token, List<IModelCallExpression> destination, IModule module){
		AST elements = AstUtil.getChild(cst, token);
		if (elements != null) {			
			AST list = elements.getFirstChild();
			for (AST modelRef : list.getChildren()) {
				IModelCallExpression element = null;
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
	
	protected void setupAnnotations() {
		// @disabled 
		if (getAnnotation("disabled") != null) {
			enabled = false;
		}
		if (getAnnotation("skip") != null) {
			enabled = false;
		}
		if (getAnnotation("enabled") != null) {
			enabled = true;
		}

		// @noTrace 
		if (getAnnotation("noTrace") != null) {
			trace = false;
		}
		if (getAnnotation("trace") != null) {
			trace = true;
		}
		
		// @always
		if (getAnnotation("always") != null) {
			alwaysExecute = true;
		}
		if (getAnnotation("never") != null) {
			alwaysExecute = false; 
			enabled = false;
		}
	}
	
	@Override
	public boolean isGenerator() {
		return forEach != null;
	}

	@Override
	public ExecutableBlock<Boolean> getGuard() {
		return guard;
	}

	@Override
	public List<TaskDependencyExpression> getDependsOn() {
		return dependsOn;
	}

	@Override
	public ForEachModuleElement getForEach() {
		return forEach;
	}

	@Override
	public List<IModelCallExpression> getInputs() {
		return inputs;
	}

	@Override
	public List<IModelCallExpression> getOutputs() {
		return outputs;
	}

	@Override
	public List<IModelCallExpression> getInouts() {
		return inouts;
	}

	@Override
	public List<IModelCallExpression> getTrans() {
		return trans;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public boolean isAlwaysExecute() {
		return alwaysExecute;
	}

	@Override
	public boolean isTrace() {
		return trace;
	}	

	@Override
	public String toString() {
		return "TaskDeclaration [type=" + type + ", parameters=" + parameters + ", guard=" + guard + ", forEach="
				+ forEach + ", inputs=" + inputs + ", outputs=" + outputs + ", inouts=" + inouts + ", dependsOn="
				+ dependsOn + ", enabled=" + enabled + ", alwaysExecute=" + alwaysExecute + ", trace=" + trace + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(alwaysExecute, dependsOn, enabled, forEach, guard, inouts, inputs,
				outputs, requires, trace, trans);
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
		if (!(obj instanceof TaskDeclaration)) {
			return false;
		}
		TaskDeclaration other = (TaskDeclaration) obj;
		return alwaysExecute == other.alwaysExecute && Objects.equals(dependsOn, other.dependsOn)
				&& enabled == other.enabled && Objects.equals(forEach, other.forEach)
				&& Objects.equals(guard, other.guard) && Objects.equals(inouts, other.inouts)
				&& Objects.equals(inputs, other.inputs) && Objects.equals(outputs, other.outputs)
				&& Objects.equals(requires, other.requires) && trace == other.trace
				&& Objects.equals(trans, other.trans);
	}	

}
