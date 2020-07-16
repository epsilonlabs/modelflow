/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;

/** 
 * TODO: modelflowTrace variable empty to be populated by the execution
 * if not populated return empty
 */
public class EpsilonEolTask extends AbstractEpsilonTask implements ITask {

	@SuppressWarnings("unchecked")
	@Override
	public IEolModule getModule() {
		if (module == null) {
			this.module = new EolModule();
		}
		return this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEolTask.class);
		}

		@Override
		public String getName() {
			return "eol";
		}

	}

}