package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class EML extends AbstractExecutableModuleTask {

	public static final String TASK_COMMAND = "eml";
	public static final String TASK_DESCRIPTION = "Executes model merging";

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EML.class);

	protected final Property<String> useMatchTrace;
	protected final Property<String> exportMergeTrace;
	protected final Property<String> exportTransformationTrace;

	public EML() {
		super();
		ObjectFactory objectFactory = getProject().getObjects();

		useMatchTrace = objectFactory.property(String.class);
		useMatchTrace.set("");
		exportMergeTrace = objectFactory.property(String.class);
		exportMergeTrace.set("");
		exportTransformationTrace = objectFactory.property(String.class);
		exportTransformationTrace.set(""); 
	}
	
	public String getExportMergeTrace() {
		return exportMergeTrace.get();
	}

	public void setExportMergeTrace(String exportMergeTrace) {
		this.exportMergeTrace.set(exportMergeTrace);;
	}

	public String getExportTransformationTrace() {
		return exportTransformationTrace.get();
	}

	public void setExportTransformationTrace(String exportTransformationTrace) {
		this.exportTransformationTrace.set(exportTransformationTrace);
	}

	public String getUseMatchTrace() {
		return useMatchTrace.get();
	}

	public void setUseMatchTrace(String useMatchTrace) {
		this.useMatchTrace.set(useMatchTrace);
	}

	@Override
	protected void initialize() throws Exception {
		EmlModule emlModule = (EmlModule) module;

		if (!useMatchTrace.get().equals("")) {
			Variable v = getProjectStackFrame().get(useMatchTrace.get());
			Object matchTraceImpl = v.getValue();
			if (matchTraceImpl instanceof MatchTrace) {
				emlModule.getContext().setMatchTrace(((MatchTrace) matchTraceImpl).getReduced());
			}
		}
	}

	@Override
	protected void examine() throws Exception {
		if (!exportTransformationTrace.get().equals("")) {
			getProjectStackFrame().put(exportTransformationTrace.get(),
					((EmlModule) module).getContext().getTransformationTrace());
		}
		if (!exportMergeTrace.get().equals("")) {
			getProjectStackFrame().put(exportMergeTrace.get(),
					((EmlModule) module).getContext().getMergeTrace());
		}
	}

	@Override
	protected IEolModule createModule() throws Exception {
		return new EmlModule();
	}

}
