/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDefinitionValidator {

	private static final Logger LOG = LoggerFactory.getLogger(TaskDefinitionValidator.class);

	private static final String UNNAMED = "unnamed";
	
	protected Class<? extends ITask> clazz;
	protected boolean valid = true;
	protected List<String> messages = new ArrayList<>();

	private FactoryIntrospector introspector;
	
	public TaskDefinitionValidator(Class<? extends ITask> clazz) {
		this.clazz = clazz;
		this.introspector = new FactoryIntrospector(clazz);
	}
	
	public boolean isValid(){
		/** Valid Methods, with no more than one annotation of each type */
		Stream.of(clazz.getMethods()).forEach(this::validMethod);
		
		if (!valid) {
			String msg = String.join("\n >> ", messages);
			LOG.error("\n==Task {} invalid== \n >> {}", this.clazz.getSimpleName(), msg);
		}
		return valid;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	/**
	 * Checks the wellformedness of each relevant method
	 */
	protected void validMethod(Method m) {
		int paramCount = m.getAnnotationsByType(Param.class).length;
		int outputCount = m.getAnnotationsByType(Output.class).length;
		int inputCount = m.getAnnotationsByType(Input.class).length;
		
		/** If it is annotated with @Param, @Output or @Input */
		if (paramCount > 0 || outputCount > 0 || inputCount > 0) {
			/** Must be public */
			boolean isPublic = introspector.isPublic(m);
			if (!isPublic) {
				messages.add(String.format("%s is not public",m.getName()));
			}
			validate(isPublic);
		
			/** Getter method */
			if (introspector.isGetter(m)) {
				validateGetter(m, paramCount, outputCount, inputCount);
				
			}
			/** Setter method */
			else if (introspector.isSetter(m)) {
				validateSetter(m, paramCount, outputCount, inputCount);
			}
		}
	}

	protected void validateSetter(Method m, int paramCount, int outputCount, int inputCount) {
		boolean wellformedSeter = paramCount == 1 && no(inputCount) && no(outputCount);
		if (!wellformedSeter){
			messages.add(m.getName() + " invalid configuration");
		}
		validate(wellformedSeter);
		
		/** has key */
		if (paramCount==1) {
			boolean hasValidKey = !m.getAnnotation(Param.class).key().equals(UNNAMED);
			if (!hasValidKey) {
				messages.add(m.getName() + " does not have a @Param key");
			}
			validate(hasValidKey);
		}
		
		correspondingGetterExist(m);
	}

	protected void validateGetter(Method m, int paramCount, int outputCount, int inputCount) {
		boolean wellformedGetter = or(inputCount, outputCount) && no(paramCount)
								&& atMostOnce(inputCount) && atMostOnce(outputCount);
		if (!wellformedGetter){
			messages.add(m.getName() + " invalid configuration");
		}
		validate(wellformedGetter);
		
		/** Output has valid key */
		if (outputCount==1) {
			boolean hasValidKey = !m.getAnnotation(Output.class).key().equals(UNNAMED);
			if (!hasValidKey) {
				messages.add(m.getName() + " does not have an @Output key");
			}
			validate(hasValidKey);
		}
		
		/** Input has valid key */
		if (inputCount==1) {
			boolean hasValidKey = !m.getAnnotation(Input.class).key().equals(UNNAMED);
			if (!hasValidKey) {
				messages.add(m.getName() + " does not have an @Input key");
			}
			validate(hasValidKey);
		}
	}
	
	/** 
	 * Checks that here is a corresponding getter method 
	 * which may not be annotated 
	 */
	protected void correspondingGetterExist(Method m) {
		String key = m.getAnnotation(Param.class).key();
		/** 
		 * There must be one possible corresponding getter for the param 
		 * which follows one of the following configurations:
		 * - setX(Object..) => getX()
		 * - @Param(key=x) setW(Object..) => getX()
		 */
		List<Method> corresponding = introspector.getGetterMethods().stream()
				.filter(me -> introspector.isCorrespondingGetter(m, me))
				.collect(Collectors.toList());
		
		int count = corresponding.size();  
		boolean correspondingGetterExists = (count == 1);
		if (!correspondingGetterExists) {
			messages.add(m.getName() + "'s param (" + key + ") does not have a getter");
		}
		validate(correspondingGetterExists);
		
		/** 
		 * If getter has @Input annotation 
		 * it must have the same key as @Param 
		 */
		if (correspondingGetterExists) {
			Input[] inAnnotations = corresponding.get(0).getAnnotationsByType(Input.class);
			if (inAnnotations.length==1) {
				boolean validKey = inAnnotations[0].key().equals(key);
				if (!validKey) {
					messages.add(m.getName() + " corresponding getter does not have the same @Input key as @Param");
				}
				validate(validKey);
			}
		}
		
		/**
		 * if another setter has same key, the name of setter must be the same.
		 * This scenario happens when we may accept different types e.g. String or File 
		 * to set the same param
		 */
		boolean equivalentSetter = introspector.getParameterSetterMethods().stream()
				.filter(me -> !me.equals(m) && me.getAnnotation(Param.class).key().equals(m.getAnnotation(Param.class).key()))
				.allMatch(me -> me.getName().equals(m.getName()));
		
		if (!equivalentSetter) {
			String msg = "%s has setters with same param (%s) but not matching signatures";
			messages.add(String.format(msg, m.getName() , key));
		}
		validate(equivalentSetter);		

	}
	
	protected void validate(boolean feature) {
		this.valid = valid && feature;
	}
	protected static Boolean no(Integer i){
		return i == 0;
	}

	protected static Boolean atMostOnce(Integer i){
		return i == 0 || i == 1;
	}
	
	protected static Boolean xor(Integer a, Integer b){
		return (a == 0 && b == 1) || (a == 1 && b == 0);
	}
	
	protected static Boolean or(Integer a, Integer b){
		return (a == 1 || b == 1) ;
	}
	
}
