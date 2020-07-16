/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.monitor.hawk;

import org.eclipse.hawk.core.IConsole;
import org.eclipse.hawk.core.IStateListener;
import org.eclipse.hawk.core.util.DefaultConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFlowHawkStateListener implements IStateListener {

	private static final Logger LOG = LoggerFactory.getLogger(ModelFlowHawkStateListener.class);

	protected IConsole console;

	public ModelFlowHawkStateListener(){
		this.console = new DefaultConsole();
	}
	
	public ModelFlowHawkStateListener(IConsole console){
		this.console = console;
	}
	
	@Override
	public void state(HawkState state) {
		LOG.info("{}",state);		
	}

	@Override
	public void info(String s) {
		LOG.info(s);
	}

	@Override
	public void error(String s) {
		LOG.error(s);
	}

	@Override
	public void removed() {
		LOG.info("Removed");
	}
	
}
