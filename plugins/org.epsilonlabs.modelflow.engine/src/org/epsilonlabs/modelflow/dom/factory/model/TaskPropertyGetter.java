/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.factory.model;

import java.lang.reflect.Method;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.epsilonlabs.modelflow.dom.api.factory.FactoryIntrospector;

public class TaskPropertyGetter extends AbstractPropertyGetter {

	@Override
	public Boolean hasProperty(Object object, String property) {
		return new FactoryIntrospector(object.getClass()).getParameterNames().contains(property);
	}

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		try {
			Method getter = new FactoryIntrospector(object.getClass()).getGetterFor(property);
			return getter.invoke(object);
		} catch (Exception e) {
			throw new EolRuntimeException(e);
		}
	}

}