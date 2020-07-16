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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.epsilonlabs.modelflow.dom.ModelResource;
import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFUnknownPropertyException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractModelResourceFactory extends AbstactFactory implements IModelResourceFactory {

	private Class<? extends IResource<?>> resourceClass;

	public AbstractModelResourceFactory(Class<? extends IResource<?>> clazz) {
		resourceClass = clazz;
	}
	
	@Override
	public Class<? extends IResource<?>> getInstanceClass(){
		return resourceClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IResource<?> instantiate(IModelResourceNode node, String name, IModelFlowContext ctx) throws MFInstantiationException {
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		IResource<?> instance = injector.getInstance(this.resourceClass);
		instance.setName(name);
		configure(this.resourceClass.cast(instance) , node.getInternal(), ctx);
		instance.configure(node);
		return instance;
	}
	
	/**
	 * This method is responsible of configuring the resource
	 */
	public <T extends IResource<?>> void configure(T iResource, ModelResource resource, IModelFlowContext ctx) throws MFInstantiationException {
		/* Get setters annotated with @Param */
		List<Method> paramMethods = Stream.of(this.resourceClass.getMethods())
				.filter(m->m.getAnnotationsByType(Param.class).length != 0)
				.collect(Collectors.toList());

		/* Iterate over the declaration properties */
		Iterator<Property> iterator = resource.getProperties().iterator();
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
				) 
				.findFirst();
			
			/* If matching method is found assign value */
			if (optionalMethod.isPresent()) {
				Method method = optionalMethod.get();
				assignProperty(iResource, ctx, value, method, prop);
			} else {
				/* Unknown Property */
				throw new MFUnknownPropertyException(key, resource.getName());
			}
		}	
	}

	public <T extends IResource<?>> void assignProperty(T iResource, IModelFlowContext ctx, Object value,
			Method method, Property prop) throws MFInstantiationException {
		Class<?> paramType = method.getParameterTypes()[0];
		Object assignableValue = getAssignableValue(ctx, value, paramType);
		prop.setValue(assignableValue);
		try {
			method.invoke(iResource, assignableValue);
		} catch (Exception e) {
			throw new MFInstantiationException(e);
		}
		
	}

	@Override
	public Object getAssignableValue(IModelFlowContext ctx, Object value, Class<?> paramType) {
		if (File.class.isAssignableFrom(paramType) && value instanceof String) {
			IRelativePathResolver relativePathResolver = ctx.getModule().getCompilationContext().getRelativePathResolver();
			String fileLocation = relativePathResolver.resolve((String) value);
			value = new File(fileLocation);	 
		}
		return value;
	}
	
}
