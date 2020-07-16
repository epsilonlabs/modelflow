package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public class EPL extends AbstractExecutableModuleTask {

	public static final String TASK_COMMAND = "epl";
	public static final String TASK_DESCRIPTION = "Executes model pattern matching";

	private Logger log = LogManager.getLogger(EPL.class);

	/** Properties with defaults */

	protected final Property<Integer> maxLoops;
	protected final Property<Boolean> repeatWhileMatches;
	protected final Property<String> exportAs;

	public EPL() {
		maxLoops = objectFactory.property(Integer.class);
		repeatWhileMatches = objectFactory.property(Boolean.class);
		exportAs = objectFactory.property(String.class);
		
		maxLoops.set(-1);
		repeatWhileMatches.set(false);		
		exportAs.set("");
	}
	
	/** CONFIGURABLE */

	@Optional
	@Input
	public Integer getMaxLoops() {
		return maxLoops.get();
	}

	public void setMaxLoops(Integer maxLoops) {
		this.maxLoops.set(maxLoops);
	}

	@Optional
	@Input
	public Boolean isRepeatWhileMatches() {
		return repeatWhileMatches.get();
	}

	public void setRepeatWhileMatches(Boolean repeatWhileMatches) {
		this.repeatWhileMatches.set(repeatWhileMatches);
	}

	@Optional
	@Input
	public String getExportAs() {
		return exportAs.get();
	}

	public void setExportAs(String exportAs) {
		this.exportAs.set(exportAs);
	}

	/** ACTIONS */

	@Override
	protected void initialize() {
		log.info("Initializing EPL");
		EplModule module = (EplModule) this.module;
		module.setMaxLoops(maxLoops.get());
		module.setRepeatWhileMatches(repeatWhileMatches.get());
	}

	protected IEolModule createModule() {
		log.info("Creating EPL module");
		return (IEolModule) new EplModule();
	}

	@Override
	protected void examine() throws Exception {
		log.info("Examining EPL");
		if (exportAs.get().equals("")) {
			PatternMatchModel model = (PatternMatchModel) result;
			model.setName(exportAs.get());
			getProjectModelRepository().addModel(model);
		}
		result = null;
	}

}