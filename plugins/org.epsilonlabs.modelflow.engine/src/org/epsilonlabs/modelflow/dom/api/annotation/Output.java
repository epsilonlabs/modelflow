/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.epsilonlabs.modelflow.management.param.hash.IHasher;
import org.epsilonlabs.modelflow.management.param.hash.NoHasher;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Output {
	
	static final class DEFAULT { }

	Class<?> type() default DEFAULT.class;

	String key() default "unnamed";
	
	Class<? extends IHasher<?,?>> hasher() default NoHasher.class;
		
}
