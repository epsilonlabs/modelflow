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
import java.util.Set;

import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author Betty Sanchez
 *
 */
public abstract class AbstactFactory implements IFactory {
	
	protected Set<String> parameters;
	
	@Override
	public Collection<String> getParameters() {
		if (this.parameters == null) {
			this.parameters = new FactoryIntrospector(getInstanceClass()).getParameterNames();
		}
		return this.parameters;
	}

	/**
	 * @param ctx
	 * @param value
	 * @param paramType
	 * @return
	 * @throws MFInstantiationException
	 */
	protected abstract Object getAssignableValue(IModelFlowContext ctx, Object value, Class<?> paramType) throws MFInstantiationException;

	/**
	 * @param value
	 * @return
	 */
	protected Boolean assignableFrom(Method m, Object value, IModelFlowContext ctx) {
		if (value instanceof IExecutableModuleElement) {
			IExecutableModuleElement me = (IExecutableModuleElement) value;
			//ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, me, variables)
			try {
				value = me.execute(ctx);
				if (value instanceof Return) {
					value = ((Return) value).getValue();
				}
			} catch (EolRuntimeException e) {
				e.printStackTrace();
			}
			//ctx.getFrameStack().leaveLocal(me);
		} 
		return m.getParameterTypes()[0].isAssignableFrom(value.getClass()) || ( m.getParameterTypes()[0].equals(File.class) && value.getClass().equals(String.class) );
	}

}
