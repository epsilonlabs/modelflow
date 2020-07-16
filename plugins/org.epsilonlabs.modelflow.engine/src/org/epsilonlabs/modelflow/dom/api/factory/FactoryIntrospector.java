/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;

/**
 * @author Betty Sanchez
 *
 */
public class FactoryIntrospector {

	private final Class<?> clazz;
	
	public FactoryIntrospector(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Set<String> getParameterNames() {
		return getParameterSetterMethods().stream()
				.map(m -> m.getAnnotation(Param.class).key())
				.collect(Collectors.toSet());
	}
	
	public Set<Method> getInputMethods() {
		return getAnnotatedGetterMethods(Input.class);
	}
	
	public Set<Method> getOutputMethods() {
		return getAnnotatedGetterMethods(Output.class);
	}
	
	public Set<Method> getAnnotatedGetterMethods(Class<? extends Annotation> annotation) {
		return getGetterMethods().stream()
				.filter(m-> m.isAnnotationPresent(annotation))
				.collect(Collectors.toSet());
	}
	
	public Method getGetterFor(String property) throws NoSuchMethodException {
		/** As a input/output annotated @getter */
		Optional<Method> getter = getGetterMethods().stream()
			.filter(m->{
				if (m.isAnnotationPresent(Input.class)) {
					return m.getAnnotation(Input.class).key().equalsIgnoreCase(property);
				} else if (m.isAnnotationPresent(Output.class)) {
					return m.getAnnotation(Output.class).key().equalsIgnoreCase(property);
				} else {
					return false;
				}
			}).findFirst();
		if (getter.isPresent()) {
			return getter.get();
		} else {			
			/** Look for getter derived from a @param setter */
			try {
				for (Method setter : getSetterFor(property)) {
					getter = getGetterMethods().stream()
						.filter(g -> isCorrespondingGetter(setter, g))
						.findFirst();
				}
				if (getter.isPresent()) {
					return getter.get();
				}
			} catch (NoSuchMethodException e) {
				// Ignore setter exception, although 
				// it indirectly tells us about the exception below
			}
		}
		String msg = "No getter method found for property %s in task %s";
		throw new NoSuchMethodException(String.format(msg, property, clazz.getSimpleName()));
	}
	
	public Set<Method> getSetterFor(String property) throws NoSuchMethodException  {
		Set<Method> setters = getParameterSetterMethods().stream()
				.filter(m -> m.getAnnotation(Param.class).key().equalsIgnoreCase(property)).collect(Collectors.toSet());
		if (!setters.isEmpty()) {
			return setters;
		} else {			
			String msg = "No setter method found for property %s in task %s";
			throw new NoSuchMethodException(String.format(msg, property, clazz.getSimpleName()));
		}
	}
	
	public List<Method> getParameterSetterMethods() {
		return Stream.of(clazz.getMethods())
				.filter(m -> 
					isSetter(m) && m.getAnnotationsByType(Param.class).length == 1 
				).collect(Collectors.toList());
	}
	
	public List<Method> getSetterMethods() {
		return Stream.of(clazz.getMethods())
				.filter(this::isSetter)
				.collect(Collectors.toList());
	}
	
	public List<Method> getGetterMethods() {
		return Stream.of(clazz.getMethods())
					.filter(this::isGetter)
					.collect(Collectors.toList());
	}
	
	public boolean isGetter(Method m){
		return isPublic(m) && (m.getName().startsWith("get") || m.getName().startsWith("is"))  
				&& m.getParameterCount() == 0 && !m.getReturnType().equals(Void.TYPE);
	}
	
	public boolean isSetter(Method m){
		return isPublic(m) && m.getName().startsWith("set")
				&& m.getParameterCount() == 1 && m.getReturnType().equals(Void.TYPE);
	}
	
	public boolean isPublic(Method m){
		return Modifier.isPublic(m.getModifiers());
	}
	
	public boolean isCorrespondingGetter(Method setter, Method getter){
		String getterName = "";
		if (getter.getName().startsWith("get")) {
			getterName = getter.getName().substring("get".length());
		} else if (getter.getName().startsWith("is")) {
			getterName = getter.getName().substring("is".length());
		}
		String setterName = setter.getName().substring("set".length());
		return getterName.equalsIgnoreCase(setterName) || getterName.equalsIgnoreCase(setter.getAnnotation(Param.class).key()); 
		
	}
}
