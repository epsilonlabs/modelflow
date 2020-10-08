/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.epsilonlabs.modelflow.dom.Property;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author Betty Sanchez
 *
 */
public class ParameterHelper {

	protected final Property property;
	protected final IModelFlowContext ctx;
	
	public ParameterHelper(Property property, IModelFlowContext ctx) {
		this.property = property;
		this.ctx = ctx;
	}
	
	public Object getEvaluatedValue() throws MFInstantiationException {
		Object value = this.property.getValue();
		if (value instanceof Callable) {
			try {
				return ((Callable<?>) value).call();
			} catch (Exception e) {
				throw new MFInstantiationException("Unable to retrieve value from callable", e);
			}
		} else if (value instanceof Supplier) {
			return ((Supplier<?>) value).get();
		}
		return value;
	}
}
