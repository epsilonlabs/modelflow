package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;

public class EOL extends AbstractExecutableModuleTask {

	public static final String TASK_COMMAND = "eol";
	public static final String TASK_DESCRIPTION = "Executes model navigation and manipulation";
	
	private Logger log = LogManager.getLogger(EOL.class);
	
	public EOL() {
		super();
	}
	
	/** ACTIONS */
	
	@Override
	protected void initialize() {
		log.debug("Initializing EOL");
	}

	@Override
	protected IEolModule createModule() {
		log.debug("Creating EOL module");
		return new EolModule();
	}

	@Override
	protected void examine() throws Exception {
	}
	
}
