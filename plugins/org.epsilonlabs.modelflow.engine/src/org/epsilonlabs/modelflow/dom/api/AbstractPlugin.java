/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom.api;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public abstract class AbstractPlugin extends AbstractModule implements IPlugin {
	
	/** FROM ABSTRACT MODULE */
	@Override 
	protected void configure() {
		registerTaskFactories();
		registerResourceFactories();
	}
	
	public void registerResourceFactories() {
		Multibinder<IModelResourceFactory> resourceBinder = Multibinder.newSetBinder(binder(), IModelResourceFactory.class);
		getResourceFactories().stream().forEach(f -> resourceBinder.addBinding().toInstance(()->f));
	}
	
	public void registerTaskFactories() {
		Multibinder<ITaskFactory> taskBinder = Multibinder.newSetBinder(binder(), ITaskFactory.class);
		getTaskFactories().stream().forEach(f -> taskBinder.addBinding().toInstance(()->f));
	}
	
}
