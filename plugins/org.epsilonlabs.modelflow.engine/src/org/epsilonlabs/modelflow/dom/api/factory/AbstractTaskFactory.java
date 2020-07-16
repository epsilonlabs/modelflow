/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFUnknownPropertyException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.repository.ResourceRepository;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractTaskFactory extends AbstactFactory implements ITaskFactory {

	private Class<? extends ITask> taskClass;

	/** 
	 * REGEX that identifies implicit references in parameters
	 * E.g. ${taskName.outputProperty}
	 */
	public static final String REFERENCE_VARIABLE = "\\$\\{([a-zA-Z]*)\\.([a-zA-Z]*)\\}";
	
	
	private static final Pattern PATTERN = Pattern.compile(AbstractTaskFactory.REFERENCE_VARIABLE);

	public AbstractTaskFactory(Class<? extends ITask> taskClazz) {
		this.taskClass = taskClazz;
	}

	@Override
	public Class<? extends ITask> getInstanceClass() {
		return this.taskClass;
	}

	@Override
	public ITask create(ITaskNode node, String name, IModelFlowContext ctx) throws MFInstantiationException {
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		ITask instance = injector.getInstance(this.taskClass);
		injector.injectMembers(instance);
		instance.configure(node);
		configure(this.taskClass.cast(instance), node.getTaskDefinition(), ctx); // How to add info of the factory in the task
		return instance;
	}

	/** 
	 * This method is responsible of configuring the task
	 */
	protected <T extends ITask> void configure(T iTask, Task task, IModelFlowContext ctx) throws MFInstantiationException {
		/* Get setters annotated with @Param */
		List<Method> paramMethods = Stream.of(this.taskClass.getMethods())
				.filter(m->m.getAnnotationsByType(Param.class).length != 0)
				.collect(Collectors.toList());
		
		/* Iterate over the declaration properties */
		Iterator<Property> iterator = task.getProperties().iterator();
		while (iterator.hasNext()) {
			
			Property prop = iterator.next();
			String key = prop.getKey();
			Object value = new ParameterHelper(prop, ctx).getEvaluatedValue();
	
			/*
			 * Look within the @Param annotated methods for one 
			 * with the same key as the current property key/value pair 
			 */
			Optional<Method> optionalMethod = paramMethods.stream()
				.filter(m -> 
							/* same key */ 		
							m.getAnnotationsByType(Param.class)[0].key().equals(key) 
							/* one argument */
							&& m.getParameterTypes().length == 1 
							/* assignable from value */ 
							&& assignableFrom(m, value, ctx)
				).findFirst();
			/* If matching method is found assign value */
			if (optionalMethod.isPresent()) {
				Method method = optionalMethod.get();
				assignProperty(iTask, ctx, value, method, prop);
			} else {
				/* Unknown Property */
				throw new MFUnknownPropertyException(key, task.getName());
			}
		}
	}	

	public <T extends ITask> void assignProperty(T iTask, IModelFlowContext ctx, Object value, Method method, Property prop)
			throws MFInstantiationException {
		Class<?> paramType = method.getParameterTypes()[0];
		Object assignableValue = getAssignableValue(ctx, value, paramType); 
		prop.setValue(assignableValue);
		try {
			method.invoke(iTask, assignableValue);
		} catch (Exception e) {
			throw new MFInstantiationException(e);
		}	
	}

	@Override
	protected Object getAssignableValue(IModelFlowContext ctx, Object value, Class<?> paramType) throws MFInstantiationException {
		ResourceRepository repo = ctx.getTaskRepository().getResourceRepository();
		if (value instanceof IExecutableModuleElement) {
			try {
				value = ((IExecutableModuleElement) value).execute(ctx);
				if (value instanceof Return) {
					value = ((Return) value).getValue();
				}
			} catch (EolRuntimeException e) {
				e.printStackTrace();
			}
		} 
		if (Map.class.isAssignableFrom(paramType)) {
			if (value instanceof Map) {
				Map<?,?> mapVal = (Map<?,?>) value;
				if (mapVal.entrySet().stream().allMatch(e->e.getKey() instanceof String)) {
					@SuppressWarnings("unchecked")
					Map<String,Object> mapVal2 = (Map<String,Object>) mapVal;
					mapVal2.entrySet().stream().map(e-> {
						if (e.getValue() instanceof String) {
							e.setValue(handleImplicitDependencyViaProperty((String)e.getValue(), repo));
						} 	
						return e;
					}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
				}
				
			}
		} else if (File.class.isAssignableFrom(paramType)){
			if (value instanceof String) {
				IRelativePathResolver relativePathResolver = ctx.getModule().getCompilationContext().getRelativePathResolver();
				String fileLocation = relativePathResolver.resolve((String) value);
				value = new File(fileLocation);	 
			}
		} else if (value instanceof String) {
			value = handleImplicitDependencyViaProperty((String) value, repo);
		}
		
		return value;
	}

	protected static Object handleImplicitDependencyViaProperty(String value, ResourceRepository repo){
		Matcher matcher = PATTERN.matcher(value);
		while (matcher.find()) {				
			String taskName = matcher.group(1);
			String resource = matcher.group(2);
			return repo.getDerived(taskName + "_" + resource);
		}
		return value;
	}
	

}
