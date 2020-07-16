package org.epsilonlabs.gradle.tasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.epsilonlabs.gradle.artifact.ModelContainer;
import org.epsilonlabs.gradle.extensions.EpsilonExtension;
import org.epsilonlabs.gradle.plugins.EpsilonCorePlugin;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.internal.tasks.userinput.UserInputHandler;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.provider.Property;
import org.gradle.api.specs.AndSpec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import groovy.lang.Closure;

public abstract class AbstractEpsilonTask extends DefaultTask {

	public static final String TASK_COMMAND = "TASK_COMMAND";
	public static final String TASK_DESCRIPTION = "TASK_DESCRIPTION";

	protected final Property<Boolean> profile;
	protected final Property<Boolean> failOnErrors;
	protected final Property<Boolean> failOnWarnings;

	protected Logger log = LogManager.getLogger(AbstractEpsilonTask.class);
	protected ObjectFactory objectFactory;
	protected ExtraPropertiesExtension extraProperties;

	protected EpsilonExtension epsilonExtension;
	protected ModelContainer epsilonModels;
	protected Runnable guardRun;
	protected AndSpec<Task> guard;
	
	/** ABSTRACT */

	public abstract void performImpl() throws TaskExecutionException;

	/** CONSTRUCTOR */
	
	public AbstractEpsilonTask() {
		super();
		extraProperties = getProject().getExtensions().getExtraProperties();
		epsilonExtension = getProject().getExtensions().getByType(EpsilonExtension.class);
		objectFactory = getProject().getObjects();
		
		profile = objectFactory.property(Boolean.class);
		profile.set(false);
		failOnErrors = objectFactory.property(Boolean.class);
		failOnErrors.set(true);
		failOnWarnings = objectFactory.property(Boolean.class);
		failOnWarnings.set(false);
	}

	/** CONFIGURABLE */
	
	@Optional
	@Input
	public Boolean isGuard() { // guard ok?
		if (guardRun != null) {
			guardRun.run();
			return guard.isSatisfiedBy(this);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void setGuard(final Closure<Boolean> closure) {
		System.out.println("closure "+ closure.call());
		guardRun = new Runnable() {
			@Override
			public void run() {
				guard = ((AndSpec<Task>)getOnlyIf()).and(closure);
			}
		};	
	}
	
	@Optional
	@Input
	public Boolean isProfile() {
		return profile.get();
	}

	public void setProfile(Boolean profile) {
		this.profile.set(profile);
	}
	
	@Optional
	@Input
	public Boolean isFailOnErrors() {
		return failOnErrors.get();
	}

	public void setFailOnErrors(Boolean failOnErrors) {
		this.failOnErrors.set(failOnErrors);
	}

	@Optional
	@Input
	public Boolean isFailOnWarnings() {
		return failOnWarnings.get();
	}

	public void setFailOnWarnings(Boolean failOnWarnings) {
		this.failOnWarnings.set(failOnWarnings);
	}

	public ModelRepository getProjectModelRepository() {
		return (ModelRepository) extraProperties.get(EpsilonCorePlugin.EPSILON_MODEL_REPOSITORY);
	}

	public ExtendedProperties getExtendedProperties() {
		return (ExtendedProperties) extraProperties.get(EpsilonCorePlugin.EPSILON_EXTENDED_PROPERTIES);
	}

	public Frame getProjectStackFrame() {
		return (Frame) extraProperties.get(EpsilonCorePlugin.EPSILON_PROJECT_STACK_FRAME);
	}

	/** ACTIONS */

	protected void fail(String message, Exception exception) throws TaskExecutionException {
		if (failOnErrors.get()) {
			throw new TaskExecutionException(this, exception);
		} else {
			log.error(message);
		}
	}

	protected void warn(String message) throws TaskExecutionException {
		if (failOnWarnings.get()) {
			throw new TaskExecutionException(this, new Exception(message));
		} else {
			log.warn(message);
		}
	}

	@TaskAction
	public void perform() {
		try {
			performImpl();
		} catch (TaskExecutionException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}
	
	protected EpsilonExtension getExtension() {
		if (epsilonExtension == null) {			
			epsilonExtension = getProject().getExtensions().getByType(EpsilonExtension.class);	
		}
		return epsilonExtension;
	}

	protected void askForInput(){
        UserInputHandler inputHandler = getServices().get(UserInputHandler.class);
		Boolean askYesNoQuestion = inputHandler.askYesNoQuestion("Would you like to continue: ");
		log.debug("Responded " + askYesNoQuestion);
	}
}
