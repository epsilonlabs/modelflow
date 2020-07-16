package org.epsilonlabs.gradle.tasks;

import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

public abstract class AbstractExportableModuleTask extends AbstractExecutableModuleTask {

	private Logger log = LogManager.getLogger(AbstractExportableModuleTask.class);

	/** ABSTRACT */

	@Internal
	protected abstract Collection<? extends Object> getObjectsForExportedModel();

	@Internal
	protected abstract Collection<? extends Class<?>> getClassesForExportedModel();

	/** PARAMETER WITH DEFAULTS */

	protected final Property<String> exportAsModel;
	
	public AbstractExportableModuleTask() {
		super();
		
		exportAsModel = objectFactory.property(String.class);
		exportAsModel.set("");
	}

	/** CONFIGURABLE */

	@Optional
	@Input
	public String getExportAsModel() {
		return exportAsModel.get();
	}

	public void setExportAsModel(String exportAsModel) {
		this.exportAsModel.set(exportAsModel);
	}

	protected boolean shouldExportAsModel() {
		return !exportAsModel.get().equals("");
	}

	/** ACTIONS */

	@Override
	protected void examine() throws Exception {
		log.debug("examine");
		if (shouldExportAsModel()) {
			exportJavaModel(getObjectsForExportedModel(), getClassesForExportedModel());
		}
	}

	private void exportJavaModel(Collection<? extends Object> objects,
			Collection<? extends Class<?>> classes) {
		final JavaModel model = new JavaModel(objects, classes);
		model.setName(exportAsModel.get());
		getProjectModelRepository().addModel(model);
	}

}