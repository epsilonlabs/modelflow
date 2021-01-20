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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.epsilonlabs.modelflow.dom.IConfigurable;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFUnknownPropertyException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstractFactory {

	protected final IModelFlowContext ctx;
	protected final Class<?> clazz;

	protected AbstractFactory(IModelFlowContext ctx, Class<?> clazz) {
		this.ctx = ctx;
		this.clazz = clazz;
	}

	protected abstract Object getIObject();

	protected abstract IConfigurable getConfigurable();

	/**
	 * This method is responsible of configuring the element
	 */
	public void configure() throws MFInstantiationException {
		/* Get setters annotated with @Param */
		List<Method> paramMethods = Stream.of(clazz.getMethods())
				.filter(m -> m.getAnnotationsByType(Param.class).length != 0).collect(Collectors.toList());

		/* Iterate over the declaration properties */
		Iterator<IProperty> iterator = getConfigurable().getProperties().iterator();
		while (iterator.hasNext()) {

			IProperty prop = iterator.next();
			String key = prop.getKey();
			Object value = new ParameterHelper(prop, ctx).getEvaluatedValue();

			/*
			 * Look within the @Param annotated methods for one with the same key as the
			 * current property key/value pair
			 */
			Collection<Method> potentialMatchingMethods = paramMethods.stream().filter(m -> {
				/* same key */
				return m.getAnnotationsByType(Param.class)[0].key().equals(key)
						/* one argument */
						&& m.getParameterTypes().length == 1
						/* assignable from value */
						&& (isAssignableFrom(m, value) || value instanceof IExecutableModuleElement)
						;
			}).collect(Collectors.toSet());
			
			if (potentialMatchingMethods.isEmpty()) {
				/* Unknown Property */
				throw new MFUnknownPropertyException(key, getConfigurable().getName());
			}
			
			tryToAssignProperty(prop, value, potentialMatchingMethods);
		}
	}

	protected void tryToAssignProperty(IProperty prop, Object value, Collection<Method> potentialMatchingMethods)
			throws MFInstantiationException {
		boolean ok = false;
		for (Method method : potentialMatchingMethods) {
			Class<?> paramType = method.getParameterTypes()[0];
			Object assignableValue =  getAssignableValue(value, paramType);
			if (isAssignableFrom(method, assignableValue)) {
				prop.setValue(assignableValue);
				try {
					method.invoke(getIObject(), assignableValue);
				} catch (Exception e) {
					throw new MFInstantiationException(e);
				}
				ok = true;
				break;
			}
		}
		if (!ok) {
			String msg = "Unable to assign property %s to task %s";
			throw new MFInstantiationException(String.format(msg, prop, getConfigurable().getName()));
		}
	}

	protected boolean isAssignableFrom(Method m, Object value) {
		return m.getParameterTypes()[0].isAssignableFrom(value.getClass())
				|| (m.getParameterTypes()[0].equals(File.class) && value.getClass().equals(String.class));
	}
	
	protected Object getAssignableValue(Object value, Class<?> paramType) throws MFInstantiationException {

		// Evaluate ModuleElements
		if (value instanceof IExecutableModuleElement) {
			ModuleElement me = prepareFrameStack();
			try {
				value = ((IExecutableModuleElement) value).execute(ctx);
				if (value instanceof Return) {
					value = ((Return) value).getValue();
				}
			} catch (EolRuntimeException e) {
				throw new MFInstantiationException(e);
			} finally {
				try {					
					ctx.getFrameStack().leaveLocal(me);
				} catch (NullPointerException e) {
					System.err.println("ERROR");
				}
			}
		} 
		
		// Process their value
		if (Map.class.isAssignableFrom(paramType)) {
			if (value instanceof Map) {
				handleMapProperty((Map<?,?>) value);
			}
		} else if (File.class.isAssignableFrom(paramType)){
			if (value instanceof String) {
				IRelativePathResolver relativePathResolver = ctx.getModule().getCompilationContext().getRelativePathResolver();
				String fileLocation = relativePathResolver.resolve((String) value);
				value = new File(fileLocation);	 
			}
		} else if (value instanceof String) {
			value = handleStringProperty((String) value);
		}
		
		return value;
	}
	
	protected abstract ModuleElement prepareFrameStack();
	protected abstract Object handleMapProperty(Map<?,?> mapVal);
	protected abstract Object handleStringProperty(String value);
	

	

}
