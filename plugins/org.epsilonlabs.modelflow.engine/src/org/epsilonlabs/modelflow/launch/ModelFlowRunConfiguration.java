/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.launch;

import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.epsilonlabs.modelflow.IModelFlowModule;
import org.epsilonlabs.modelflow.ModelFlowModule;

public class ModelFlowRunConfiguration extends EolRunConfiguration {
	
	public static class MFBuilder	<
							R extends ModelFlowRunConfiguration, 
							B extends Builder<R, B>
								> 
						extends Builder<R, B> {

		protected MFBuilder() {
			super();
		}
		
		protected MFBuilder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IModelFlowModule createModule() {
			return new ModelFlowModule();
		}
		
		protected boolean protectOutputs;
		protected boolean interactive;
		protected boolean endToEnd;

		
		public MFBuilder protectOutput() {
			protectOutputs = true;
			return this;
		}
		
		public MFBuilder noOutputProtection() {
			protectOutputs = false;
			return this;
		}
		
		public MFBuilder interactive() {
			interactive = true;
			return this;
		}
		
		public MFBuilder batch() {
			interactive = false;
			return this;
		}
		
		public MFBuilder endToEndTracing() {
			endToEnd = true;
			return this;
		}
		
		public MFBuilder noEndToEndTracing() {
			endToEnd = false;
			return this;
		}
	
		
		@Override
		public R build() {
			R build = super.build();
			return build;
		}
		
		// Additions
		
		
	}
	
	public ModelFlowRunConfiguration(Builder<? extends ModelFlowRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public ModelFlowRunConfiguration(ModelFlowRunConfiguration other) {
		super(other);
	}
	
	public static MFBuilder<? extends ModelFlowRunConfiguration, ?> Builder() {
		return new MFBuilder<>(ModelFlowRunConfiguration.class);
	}

	@Override
	public IModelFlowModule getModule() {
		return (IModelFlowModule) super.getModule();
	}

}
