/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api.factory;

import org.epsilonlabs.modelflow.dom.api.IResource;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IModelResourceNode;

public interface IModelResourceFactory extends IFactory {
    		
	@Override
	Class<? extends IResource<?>> getInstanceClass();

	<M> IResource<M> instantiate(IModelResourceNode resource, String name, IModelFlowContext ctx) throws MFInstantiationException;
	
	void beforeWorkflow();
	
	void afterWorkflow();
	
	// void watch() throws EolRuntimeException;
    
    //void stop();
    
    //void onChange(IResource resource);
    
}