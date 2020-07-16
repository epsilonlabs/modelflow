package org.epsilonlabs.gradle.tasks;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.internal.ModelFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskExecutionException;

public abstract class AbstractExecutableModuleTask extends AbstractEpsilonTask {

	protected IEolModule module;
	protected Object result;

	protected final Property<File> src;// = null; // FIXME
	protected final Property<String> ref;
	protected final Property<String> code;
	protected final Property<String> uri;
	
	protected final Property<Boolean> assertions;
	protected final Property<Boolean> isGUI;
	protected final Property<Boolean> isDebug;
	protected final Property<Boolean> setBeans;
	protected final Property<Boolean> fine;
	protected Set<File> modelOutputFiles;
	protected Set<File> modelInputFiles;

	protected final ListProperty<String> input;
	protected final ListProperty<String> output;
	
	private Logger log = LogManager.getLogger(AbstractExecutableModuleTask.class);

	public AbstractExecutableModuleTask() {
		super();
		src = objectFactory.property(File.class);
		ref = objectFactory.property(String.class);
		code = objectFactory.property(String.class);
		uri = objectFactory.property(String.class);

		assertions = objectFactory.property(Boolean.class);
		isGUI = objectFactory.property(Boolean.class);
		isDebug = objectFactory.property(Boolean.class);
		setBeans = objectFactory.property(Boolean.class);
		fine = objectFactory.property(Boolean.class);
		input = getProject().getObjects().listProperty(String.class);
		output = getProject().getObjects().listProperty(String.class);
		modelOutputFiles = new HashSet<File>();
		modelInputFiles = new HashSet<File>();
		
		// DEFAULTS
		code.set("\"please add an epsilon file or code\".println;");
		ref.set("");
		uri.set("");
		
		assertions.set(true);
		isGUI.set(true);
		isDebug.set(false);
		setBeans.set(false);
		fine.set(true); // FIXME		
	}

	/** ABSTRACTS */
	
	protected abstract void initialize() throws Exception;

	protected abstract void examine() throws Exception;

	protected abstract IEolModule createModule() throws Exception;

	/** CONFIGURABLE */

	@Optional
	@Input
	public String getRef() {
		return ref.get();
	}

	public void setRef(String ref) {
		this.ref.set(ref);
	}

	@Optional
	@InputFile
	public File getSrc() {
		return src.get();
	}

	public void setSrc(File src) {
		this.src.set(src);
	}

	@Optional
	@Input
	public String getUri() {
		return uri.get();
	}

	public void setUri(String uri) {
		this.uri.set(uri);
	}

	@Optional
	@Input
	public String getCode() {
		return code.get();
	}

	public void setCode(String code) {
		this.code.set(code);
	}
	
	@Optional
	@Input
	public Boolean getAssertions() {
		return this.assertions.get();
	}

	public void setAssertions(Boolean assertions) {
		this.assertions.set(assertions);
	}
	
	@Optional
	@Input
	public List<String> getInput() {
		return this.input.get();
	}
	
	public void setInput(String in) {
		this.input.add(in);
	}
	
	@Optional
	@Input
	public List<String> getOutput() {
		return this.output.get();
	}
	
	public void setOutput(String in) {
		this.output.add(in);
	}
	
	@Optional
	@Input
	public Boolean getIsGUI() {
		return this.isGUI.get();
	}

	public void setIsGUI(Boolean isGUI) {
		this.isGUI.set(isGUI);
	}
	
	@Optional
	@Input
	public Boolean getIsDebug() {
		return this.isDebug.get();
	}

	public void setIsDebug(Boolean isDebug) {
		this.isDebug.set(isDebug);
	}
	
	@Optional
	@Input
	public Boolean getSetBeans() {
		return setBeans.get();
	}

	public void setSetBeans(Boolean setBeans) {
		this.setBeans.set(setBeans);
	}

	@Optional
	@Input
	public Boolean getFine() {
		return fine.get();
	}
	
	public void setFine(Boolean fine) {
		this.fine.set(fine);
	}

	public Object getResult() {
		return result;
	}
	
	@OutputFiles
	public Set<File> getModelOutputFiles() {
		SortedMap<String, Model> models = getExtension().getModels().getAsMap();
		
		this.output.get().forEach(name -> {
			if (models.containsKey(name)) {
				Model model = models.get(name);
				modelOutputFiles.add(model.getModelFile());

			} else {
			}
		});
	//	System.out.println(getName());
	//	modelOutputFiles.forEach(o->System.out.println("Outputasdas: " + o.getAbsolutePath()));
		return modelOutputFiles;	
	}
	
	@InputFiles
	public Set<File> getModelInputFiles() {
		SortedMap<String, Model> models = getExtension().getModels().getAsMap();
	
		this.input.get().forEach(name -> {
			if (models.containsKey(name)) {
				Model model = models.get(name);
				modelInputFiles.add(model.getModelFile());

			} else {
			}
		});
	//	modelInputFiles.forEach(o->System.out.println("Inputs: " + o.getAbsolutePath()));
		return modelInputFiles;
	}
	
	/** EXECUTION */

	protected void parseModule() throws Exception {
		module = createModule();
		if (src.get() != null) {
			module.parse(src.get());
		} else if (!uri.get().equals("")) {
			module.parse(URI.create(uri.get()));
		} else {
			String codeMsg = (code.get().length() > 20) ? code.get().substring(0, 19) : code.get();
			module.parse(code.get());
		}
		if (module.getParseProblems().size() > 0) {
			for (ParseProblem problem : module.getParseProblems()) {
				log.error(problem.toString());
			}
		}
	}

	@Override
	public void performImpl() {
		//askForInput();
		try {
			parseModule();

			if (src.get() != null && profile.get()) {
				//Profiler.INSTANCE.start(src.get().getName(), "", module);
			}
			populateWithModels();
			initialize();

			result = module.execute();

			useResults();
			//if (src.get() != null && profile.get())
				//Profiler.INSTANCE.stop(src.get().getName());

			module.getContext().getModelRepository().dispose();
		} catch (Throwable t) {
			if (profile.get())
				//Profiler.INSTANCE.stop(src.get().getName());
			if (t instanceof TaskExecutionException) {
				throw (TaskExecutionException) t;
			} else {
				StringWriter sw = new StringWriter();
				t.printStackTrace(new PrintWriter(sw));
				log.error("EXCEPTION: " + sw.toString());
				throw new TaskExecutionException(this, t);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void configureModule()
			throws EolModelNotFoundException, TaskExecutionException, EolModelLoadingException {
		module.getContext().setExtendedProperties(getExtendedProperties());
		module.getContext().setAssertionsEnabled(getAssertions());
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("null", null));
		if (getSetBeans()) {
			module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("project", getProject()));
			addVariables(module.getContext(), getProject().getProperties());
		}
	}
	
	@SuppressWarnings("unchecked") 
	protected void addVariables(IEolContext context, Map<String, ?>... variableMaps) {
		for (Map<String, ?> variableMap : variableMaps) {
			for (String key : variableMap.keySet()) {
				module.getContext().getFrameStack().put(Variable.createReadOnlyVariable(key, variableMap.get(key)));				
			}
		}
	}

		
	protected void populateWithModels() throws EolModelLoadingException {

		ModelRepository moduleModelRepository = module.getContext().getModelRepository();		
		ModelRepository projectModelRepository = getProjectModelRepository();
		ModelFactory factories = ModelFactory.getInstance();
		
		SortedMap<String, Model> models = getExtension().getModels().getAsMap();
		this.input.get().forEach(name -> {
			if (models.containsKey(name)) {
				IModel model = factories.create(models.get(name));
				model.setReadOnLoad(true);
				model.setStoredOnDisposal(false);
				try {
					model.load();
					moduleModelRepository.addModel(model);
				} catch (EolModelLoadingException e) {
					e.printStackTrace();
				}
			} else {
				Exception exception = new Exception("Model "+ name + " not found");
				//throw new EolModelLoadingException(exception, null);
			}
		});
		this.output.get().forEach(name -> {
			if (models.containsKey(name)) {
				IModel model ;
				model = factories.create(models.get(name));
				modelOutputFiles.add(new File(((EmfModel) model).getModelFile()));
				model.setReadOnLoad(false);
				model.setStoredOnDisposal(true);
				try {
					model.load();
					moduleModelRepository.addModel(model);
				} catch (EolModelLoadingException e) {
					e.printStackTrace();
				}
			} else {
				Exception exception = new Exception("Model "+ name + " not found");
				//throw new EolModelLoadingException(exception, null);
			}
		});
		//modelOutputFiles.forEach(o->System.out.println("Ouputs: " + o.getAbsolutePath()));
		
		//moduleModelRepository.getModels().stream().forEach(m -> System.out.println("Model:" + m.getName()));
		//modelOutputFiles.forEach(f-> System.out.println(f.getAbsoluteFile()));
		/*for (Class c : factories.getFactories().keySet()) {
			Iterator iterator = taskModels.withType(c).iterator();
			while (iterator.hasNext()) {
				RuntimeModel runtimeModel = (RuntimeModel) iterator.next();
				Model model = epsilonModels.getByName(runtimeModel.getName());
				IModel iModel = factories.get(c).get(model, runtimeModel, getProject());
				iModel.load();
				moduleModelRepository.addModel(iModel);
			}
		}*/
	}

	protected void useResults() throws Exception {
		// exportVariables();
		examine();
	}
	
	

}
