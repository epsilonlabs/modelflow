package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.IEolModule;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public class ECL extends AbstractExecutableModuleTask {

	public static final String TASK_COMMAND = "ecl";
	public static final String TASK_DESCRIPTION = "Executes model comparison";

	private Logger log = LogManager.getLogger(ECL.class);

	protected final Property<MatchTrace> matchTrace;
	
	protected String useMatchTrace;
	protected String exportMatchTrace;
	
	public ECL() {
		matchTrace = objectFactory.property(MatchTrace.class);
	}
	/** CONFIGURABLE */

	@Input
	@Optional
	public String getUseMatchTrace() {
		return useMatchTrace;
	}

	public void setUseMatchTrace(String useMatchTrace) {
		this.useMatchTrace = useMatchTrace;
	}

	@Input
	@Optional
	public String getExportMatchTrace() {
		return exportMatchTrace;
	}

	public void setExportMatchTrace(String exportMatchTrace) {
		this.exportMatchTrace = exportMatchTrace;
	}

	/** ACTIONS */

	@Override
	protected void initialize() throws Exception {
		IEclModule eclModule = (IEclModule) module;
		if (useMatchTrace != null) {
			eclModule.getContext().setMatchTrace(
					(MatchTrace) getProjectStackFrame().get(useMatchTrace).getValue());
		}
	}

	@Override
	protected void examine() throws Exception {
		IEclModule eclModule = (IEclModule) module;
		
		MatchTrace reduced = eclModule.getContext().getMatchTrace().getReduced();
		matchTrace.set(reduced);
		
		if (exportMatchTrace != null) {			
			getProjectStackFrame().put(exportMatchTrace, reduced);
		}
	}

	@Override
	protected IEolModule createModule() throws Exception {
		log.info("Creating EPL module");
		return (IEolModule) new EclModule();
	}
}
