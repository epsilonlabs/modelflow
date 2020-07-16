package org.epsilonlabs.gradle.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.epsilonlabs.gradle.artifact.DefaultEpsilonConfigurationContainer;
import org.epsilonlabs.gradle.artifact.DefaultModelContainer;
import org.epsilonlabs.gradle.artifact.EpsilonConfigurationContainer;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.ModelContainer;
import org.epsilonlabs.gradle.extensions.DefaultEpsilonExtension;
import org.epsilonlabs.gradle.extensions.EpsilonExtension;
import org.epsilonlabs.gradle.tasks.ECL;
import org.epsilonlabs.gradle.tasks.EGL;
import org.epsilonlabs.gradle.tasks.EGX;
import org.epsilonlabs.gradle.tasks.EML;
import org.epsilonlabs.gradle.tasks.EOL;
import org.epsilonlabs.gradle.tasks.EPL;
import org.epsilonlabs.gradle.tasks.ETL;
import org.epsilonlabs.gradle.tasks.EVL;
import org.epsilonlabs.gradle.tasks.Flock;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.internal.reflect.Instantiator;

public class EpsilonCorePlugin extends AbstractEpsilonPlugin implements Plugin<Project> {

	@SuppressWarnings("unused")
	private Logger log = LogManager.getLogger(EpsilonCorePlugin.class);

	public static final String EPSILON_MODEL_REPOSITORY = "epsilonModelRepository";
	public static final String EPSILON_DECLARED_MODELS = "epsilonDeclaredModels";
	public static final String EPSILON_EXTENDED_PROPERTIES = "epsilonExtendedProperties";
	public static final String EPSILON_PROJECT_STACK_FRAME = "epsilonProjectStackFrame";
	
	private final Instantiator instantiator;
	private final ObjectFactory objectFactory;
	
	@Inject
	public EpsilonCorePlugin(Instantiator instantiator, ObjectFactory objectFactory) {
		this.instantiator = instantiator;
		this.objectFactory = objectFactory;
	}
	
	@Override
	public void apply(Project project) {
		super.apply(project);
		
		project.getExtensions().getExtraProperties().set(EPSILON_MODEL_REPOSITORY, new ModelRepository());
		project.getExtensions().getExtraProperties().set(EPSILON_EXTENDED_PROPERTIES, new ExtendedProperties());
		project.getExtensions().getExtraProperties().set(EPSILON_PROJECT_STACK_FRAME, new SingleFrame(FrameType.PROTECTED, null));		
		
		ModelContainer modelContainer =  objectFactory.newInstance(DefaultModelContainer.class, instantiator);
        EpsilonConfigurationContainer configuration = instantiator.newInstance(DefaultEpsilonConfigurationContainer.class, instantiator);
        EpsilonExtension extension = project.getExtensions().create(EpsilonExtension.class, EpsilonExtension.NAME, DefaultEpsilonExtension.class,  modelContainer, configuration);
        Map<String, Model> models = new HashMap<String, Model>();
        for (Entry<String, Model> m : modelContainer.getAsMap().entrySet()) {
			System.out.println("Found model: " + m.getKey());
			//models.put(m.getKey(), m.getValue());
		}
        //project.getExtensions().getExtraProperties().set(EPSILON_DECLARED_MODELS, models);
		
		addTasks(project, ECL.class);
		addTasks(project, EGX.class);
		addTasks(project, EGL.class);
		addTasks(project, EML.class);
		addTasks(project, EOL.class);
		addTasks(project, EPL.class);
		addTasks(project, ETL.class);
		//addTasks(project, EUnit.class);
		addTasks(project, EVL.class);
		addTasks(project, Flock.class);
		
		/*project.getTasks().create("genmodel", GenmodelCodegen.class, new Action<Task>() {
			public void execute(Task task) {
				task.setGroup(PLUGIN_GROP);
				project.getExtensions().getExtraProperties().set("genmodel".toUpperCase(),
						GenmodelCodegen.class);
			}
		});*/
	
	}
	
}