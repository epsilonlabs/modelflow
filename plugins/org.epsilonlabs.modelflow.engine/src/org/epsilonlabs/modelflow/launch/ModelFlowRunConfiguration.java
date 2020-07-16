/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.launch;

import org.eclipse.epsilon.erl.launch.ErlRunConfiguration;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.ModelFlowModule;

public class ModelFlowRunConfiguration extends ErlRunConfiguration {
	
	public static class Builder<R extends ModelFlowRunConfiguration, B extends Builder<R, B>> extends ErlRunConfiguration.Builder<R, B> {

		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IModelFlowModule createModule() {
			return new ModelFlowModule();
		}
		
	}
	
	public ModelFlowRunConfiguration(Builder<? extends ModelFlowRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public ModelFlowRunConfiguration(ModelFlowRunConfiguration other) {
		super(other);
	}

	@Override
	public IModelFlowModule getModule() {
		return (IModelFlowModule) super.getModule();
	}

}
