/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.epsilonlabs.modelflow.dom.Configurable;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.ast.TaskRule;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.repository.ResourceRepository;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class TaskFactoryImpl extends AbstractFactoryImpl {

	/** 
	 * REGEX that identifies implicit references in parameters
	 * E.g. ${taskName.outputProperty}
	 */
	protected static final String REFERENCE_VARIABLE = "\\$\\{([a-zA-Z]*)\\.([a-zA-Z]*)\\}";
	
	public static final Pattern REF_VAR_PATTERN = Pattern.compile(REFERENCE_VARIABLE);
	
	protected final ITaskNode node;
	protected final String name;
	protected Task task;
	protected ITask iTask;

	public TaskFactoryImpl(ITaskFactory factory, ITaskNode node, String name, IModelFlowContext ctx) {
		super(ctx, factory.getInstanceClass());
		this.node = node;
		this.task = node.getTaskDefinition();
		this.name = name;
	}
	
	public ITask create() throws MFInstantiationException {
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		ITask instance = (ITask) injector.getInstance(clazz);
		injector.injectMembers(instance);
		instance.configure(node);
		iTask = (ITask) clazz.cast(instance); // Is this necessary?
		configure(); // How to add info of the factory in the task
		return iTask;
	}

	@Override
	protected Object getIObject() {
		return iTask;
	}

	@Override
	protected Configurable getConfigurable(){
		return task;
	}

	protected static Object handleImplicitDependencyViaProperty(String value, ResourceRepository repo){
		Matcher matcher = REF_VAR_PATTERN.matcher(value);
		if (matcher.find()) {				
			String taskName = matcher.group(1);
			String resource = matcher.group(2);
			return repo.getDerived(taskName + "_" + resource);
		}
		return value;
	}
	
	@Override
	protected ModuleElement prepareFrameStack() {
		Variable[] variables = new Variable[0];
		Configurable configurable = getConfigurable();
		TaskRule me = (TaskRule) configurable.getModuleElement();
		if (me.isGenerator()) {
			variables = me.getVars(configurable.getName());
		}
		ctx.getFrameStack().enterLocal(FrameType.PROTECTED, me, variables);
		return me;
	}
	
	@Override
	protected Object handleMapProperty(Map<?,?> mapVal) {
		ResourceRepository repo = ctx.getTaskRepository().getResourceRepository();
		if (mapVal.entrySet().stream().allMatch(e->e.getKey() instanceof String)) {
			@SuppressWarnings("unchecked")
			Map<String,Object> mapVal2 = (Map<String,Object>) mapVal;
			mapVal2.entrySet().stream().map(e-> {
				if (e.getValue() instanceof String) {
					e.setValue(handleImplicitDependencyViaProperty((String)e.getValue(), repo));
				} 	
				return e;
			}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
			return mapVal2;
		}
		return mapVal;
	}
	
	@Override
	protected Object handleStringProperty(String value) {
		ResourceRepository repo = ctx.getTaskRepository().getResourceRepository();
		return handleImplicitDependencyViaProperty(value, repo);
	}
		
}
