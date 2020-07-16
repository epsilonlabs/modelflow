package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockModule;
import org.gradle.api.Named;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class Flock extends AbstractExecutableModuleTask {

	public static final String TASK_COMMAND = "flock";
	public static final String TASK_DESCRIPTION = "Executes model migration";
	
	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EML.class);

	private final Property<String> originalModel; 
	private final Property<String> migratedModel;

	public Flock() {
		super();
		ObjectFactory objectFactory = getProject().getObjects();

		originalModel = objectFactory.property(String.class);
		originalModel.set("");
		migratedModel = objectFactory.property(String.class);
		migratedModel.set("");
	}
	
	public String getOriginalModel() {
		return originalModel.get();
	}

	public void setOriginalModel(String originalModel) {
		this.originalModel.set(originalModel);
	}
	
	public void setOriginalModel(Named originalModel) {
		this.originalModel.set(originalModel.getName());
	}

	public String getMigratedModel() {
		return migratedModel.get();
	}

	public void setMigratedModel(String migratedModel) {
		this.migratedModel.set(migratedModel);
	}
	
	public void setMigratedModel(Named migratedModel) {
		this.migratedModel.set(migratedModel.getName());
	}
	
	@Override
	protected void initialize() throws Exception {
		final IFlockModule migrator = (IFlockModule) module;
		
		migrator.getContext().setOriginalModel(migrator.getContext().getModelRepository().getModelByName(originalModel.get()));
		migrator.getContext().setMigratedModel(migrator.getContext().getModelRepository().getModelByName(migratedModel.get()));
	}

	@Override
	protected void examine() throws Exception {	}

	@Override
	protected IEolModule createModule() throws Exception {
		return new FlockModule();
	}
	
}
