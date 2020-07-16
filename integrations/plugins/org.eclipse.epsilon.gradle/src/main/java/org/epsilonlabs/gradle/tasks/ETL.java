package org.epsilonlabs.gradle.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.trace.Transformation;
import org.eclipse.epsilon.etl.trace.TransformationTrace;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public class ETL extends AbstractExportableModuleTask {

	public static final String TASK_COMMAND = "etl";
	public static final String TASK_DESCRIPTION = "Executes model to model transformation";

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(ETL.class);

	protected final Property<String> exportTransformationTrace;

	@Input
	public String getExportTransformationTrace() {
		return exportTransformationTrace.get();
	}

	public void setExportTransformationTrace(String exportTransformationTrace) {
		this.exportTransformationTrace.set(exportTransformationTrace);
	}

	public ETL() {
		super();
		ObjectFactory objectFactory = getProject().getObjects();

		exportTransformationTrace = objectFactory.property(String.class);
		exportTransformationTrace.set("");
	}
	
	/** ACTIONS */
	@Override
	protected void initialize() throws Exception {
	}

	@Override
	protected IEolModule createModule() {
		return (IEolModule) new EtlModule();
	}

	@Override
	protected void examine() throws Exception {
		super.examine();

		final IEtlModule etlModule = (IEtlModule) module;

		if (!exportTransformationTrace.get().equals("")) {
			getProjectStackFrame().put(exportTransformationTrace.get(),
					etlModule.getContext().getTransformationTrace());
		}
	}

	@Override
	protected Collection<? extends Object> getObjectsForExportedModel() {
		final TransformationTrace trace = ((IEtlModule) module).getContext()
				.getTransformationTrace();
		return Collections.singleton(trace);
	}

	@Override
	protected Collection<Class<?>> getClassesForExportedModel() {
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(TransformationTrace.class);
		classes.add(Transformation.class);
		classes.add(TransformationRule.class);
		return classes;
	}

}
