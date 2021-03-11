/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.VariableDeclaration;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.ErlModule;
import org.epsilonlabs.modelflow.compile.context.EMFModelFlowCompilationContext;
import org.epsilonlabs.modelflow.compile.context.IEMFModelFlowCompilationContext;
import org.epsilonlabs.modelflow.compile.context.IModelFlowCompilationContext;
import org.epsilonlabs.modelflow.compile.context.ModelFlowCompilationContext;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.ast.ForEachModuleElement;
import org.epsilonlabs.modelflow.dom.ast.IModelModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.ModelDeclaration;
import org.epsilonlabs.modelflow.dom.ast.ParameterDeclaration;
import org.epsilonlabs.modelflow.dom.ast.TaskDeclaration;
import org.epsilonlabs.modelflow.dom.ast.TaskDependencyExpression;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFModelCallExpression;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFResourceRule;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFTaskDependencyExpression;
import org.epsilonlabs.modelflow.dom.ast.emf.EMFTaskRule;
import org.epsilonlabs.modelflow.dom.ast.emf.IEMFDomElement;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.context.ModelFlowContext;
import org.epsilonlabs.modelflow.execution.context.ModelFlowEMFContext;
import org.epsilonlabs.modelflow.execution.control.ExecutionStage;
import org.epsilonlabs.modelflow.execution.scheduler.TaskStackScheduler;
import org.epsilonlabs.modelflow.execution.scheduler.TopologicalSequentialScheduler;
import org.epsilonlabs.modelflow.execution.trace.ExecutionTrace;
import org.epsilonlabs.modelflow.execution.trace.impl.ExecutionTraceFactoryImpl;
import org.epsilonlabs.modelflow.management.param.AnnotationTaskParameterManager;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;
import org.epsilonlabs.modelflow.management.resource.ResourceManager;
import org.epsilonlabs.modelflow.management.resource.TaskResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceFactoryImpl;
import org.epsilonlabs.modelflow.parse.ModelFlowLexer;
import org.epsilonlabs.modelflow.parse.ModelFlowParser;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFlowModule extends ErlModule implements IModelFlowModule {

	public static final String INTERACTIVE_ATTRIBUTE = "interactive";
	public static final String PROTECT_OUTPUT_ATTRIBUTE = "protect_outputs";
	public static final String END_TO_END_TRACING_ATTRIBUTE = "end_to_end_trace";
	public static final String END_TO_END_TRACE_LOCATION_ATTRIBUTE = "end_to_end_trace_location";
	public static final String SAVE_END_TO_END_ATTRIBUTE = "save_end_to_end_trace";
	public static final String EXECUTION_TRACE_LOCATION_ATTRIBUTE = "execution_trace_location";
	public static final String BASEDIR = "basedir";
	
	public static final String EXTENSION = "mflow";
	public static final String EXECUITON_TRACE_EXTENSION = "mfexec";
	public static final String MANAGEMENT_TRACE_EXTENSION = "mftrace";
	
	private static final Logger LOG = LoggerFactory.getLogger(ModelFlowModule.class);
	
	protected String hasher;
	protected TaskFactoryRegistry taskFactoryRegistry;
	protected ResourceFactoryRegistry resFactoryRegistry;
	protected IModelFlowCompilationContext compilationCtx;
	protected List<VariableDeclaration> parameterDeclarations;
	protected IWorkflow workflow;
	protected ModulePersistenceHelper traceHelper;
	protected IModelFlowConfiguration config;

	protected boolean isEmf = true;
	/*
	 *******************************************
	 * CONSTRUCTOR
	 *******************************************
	 */

	public ModelFlowModule() {
		this.traceHelper = new ModulePersistenceHelper(this);
		this.config = new ModelFlowConfiguration(this);
	}

	/*
	 *******************************************
	 * CONFIGURATION
	 *******************************************
	 */

	@Override
	public IModelFlowConfiguration getConfiguration() {
		return config;
	}
	
	@Override
	public String getIdentifier() {
		return hasher;
	}

	protected void computeIdentifier(java.net.URI uri, String code, File file) throws IOException {
		if (code != null) {
			hasher = Hasher.computeHash(code.getBytes());
		} else if (file != null) {
			hasher = Hasher.computeHashForFile(file);
		} else if (uri != null) {
			InputStream openStream = uri.toURL().openStream();
			StringBuilder sb = new StringBuilder();
			try (Scanner sc = new Scanner(openStream)){				
				while(sc.hasNext()){
					sb.append(sc.nextLine());
				}
				hasher = Hasher.computeHash(sb.toString().getBytes());
			} catch (Exception e) { }
		}
	}

	@Override
	public Set<String> getConfigurationProperties() {
		HashSet<String> set = new HashSet<>(Arrays.asList(
				INTERACTIVE_ATTRIBUTE, 
				PROTECT_OUTPUT_ATTRIBUTE, 
				END_TO_END_TRACING_ATTRIBUTE,
				SAVE_END_TO_END_ATTRIBUTE,
				END_TO_END_TRACE_LOCATION_ATTRIBUTE,
				BASEDIR,
				EXECUTION_TRACE_LOCATION_ATTRIBUTE
			));
		set.addAll(super.getConfigurationProperties());
		return set;
	}
	
	@Override
	public void configure(Map<String, ?> properties) {
		super.configure(properties);
		if (properties.containsKey(INTERACTIVE_ATTRIBUTE)) {
			getContext().setInteractive((Boolean) properties.get(INTERACTIVE_ATTRIBUTE));
		}
		if (properties.containsKey(PROTECT_OUTPUT_ATTRIBUTE)) {
			getContext().setProtectResources((Boolean) properties.get(PROTECT_OUTPUT_ATTRIBUTE));
		}
		if (properties.containsKey(END_TO_END_TRACING_ATTRIBUTE)) {
			getContext().setEndToEndTracing((Boolean) properties.get(END_TO_END_TRACING_ATTRIBUTE));
		}
		if (properties.containsKey(SAVE_END_TO_END_ATTRIBUTE)) {
			config.setSaveEndToEndTraces((Boolean) properties.get(SAVE_END_TO_END_ATTRIBUTE));
		}		
		if (properties.containsKey(END_TO_END_TRACE_LOCATION_ATTRIBUTE)) {
			Path location = Paths.get((String) properties.get(END_TO_END_TRACE_LOCATION_ATTRIBUTE));
			if (!location.toString().isEmpty() && properties.containsKey(BASEDIR)) {
				Path basedir = Paths.get((String) properties.get(BASEDIR));
				location = location.subpath(1, location.getNameCount());
				location = basedir.resolve(location);
				config.setEndToEndTraceLocation(location.toString());
			}
		}
		if (properties.containsKey(EXECUTION_TRACE_LOCATION_ATTRIBUTE)) {
			String file = (String) properties.get(EXECUTION_TRACE_LOCATION_ATTRIBUTE);
			config.setExecutionTraceLocation(new File(file));
		}
	}

	/*
	 *******************************************
	 * SUPER CLASS
	 *******************************************
	 */

	@Override
	public IModelFlowContext getContext() {
		if (context == null) {
			this.context = isEmf ? new ModelFlowEMFContext() : new ModelFlowContext();
		}
		return (IModelFlowContext) context;
	}

	@Override
	public void setContext(IEolContext context) {
		if (context instanceof IModelFlowContext) {
			this.context = context;
		}
	}

	@Override
	public HashMap<String, Class<?>> getImportConfiguration() {
		HashMap<String, Class<?>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put(EXTENSION, ModelFlowModule.class);
		return importConfiguration;
	}

	/*
	 *******************************************
	 * PARSER
	 *******************************************
	 */
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new ModelFlowLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new ModelFlowParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "modelflowModule";
	}

	@Override
	public boolean parse(String code, File file) throws Exception {
		computeIdentifier(null, code, file);
		return super.parse(code, file);
	}
	@Override
	public boolean parse(java.net.URI uri) throws Exception {
		computeIdentifier(uri, null, null);
		return super.parse(uri);
	}
		
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		// Parameters
		List<AST> paramChildrenAsts = AstUtil.getChildren(cst, ModelFlowParser.PARAMDECLARATION);
		for (AST paramAst : paramChildrenAsts) {
			ParameterDeclaration param = (ParameterDeclaration) module.createAst(paramAst, this);
			getCompilationContext().getParameterDeclarations().add(param);
		}
		
		// Resources
		List<AST> resourceChildrenAsts = AstUtil.getChildren(cst, ModelFlowParser.RESOURCEDECLARATION);
		for (AST resource : resourceChildrenAsts) {
			IModelModuleElement rule = (IModelModuleElement) module.createAst(resource, this);
			getCompilationContext().getResourceDeclarations().add(rule);
		}

		// Tasks
		List<AST> taskChildrenAsts = AstUtil.getChildren(cst, ModelFlowParser.TASKDECLARATION);
		for (AST taskAst : taskChildrenAsts) {
			ITaskModuleElement task = (ITaskModuleElement) module.createAst(taskAst, this);
			getCompilationContext().getTaskDeclarations().add(task);
		}
	}

	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
		
		case ModelFlowParser.RESOURCEDECLARATION:
			return isEmf ? new EMFResourceRule() : new ModelDeclaration();
			
		case ModelFlowParser.TASKDECLARATION:
			return isEmf ? new EMFTaskRule() : new TaskDeclaration();

		case ModelFlowParser.RULETYPE:
			return new NameExpression(cst.getText());	
		
		case ModelFlowParser.GUARD:
			return new ExecutableBlock<>(Boolean.class);
			
		case ModelFlowParser.FOREACH:
			return new ForEachModuleElement();
			
		case ModelFlowParser.PARAMDECLARATION:
			return new ParameterDeclaration();

		case ModelFlowParser.PROPERTY:
			return new NameExpression();
		
		case ModelFlowParser.NAME:
			if (parentAst instanceof ITaskModuleElement) {
				int parentToken = cst.getParent().getType();
				if (parentToken == ModelFlowParser.DEPENDSON) {
					return isEmf ? new EMFTaskDependencyExpression((ITaskModuleElement) parentAst) : new TaskDependencyExpression();
				} else if (parentToken == ModelFlowParser.TASKRESOURCE) {
					return isEmf ? new EMFModelCallExpression() : new ModelCallExpression();
				} else {
					return super.adapt(cst, parentAst);
				}
			}
			break;
		
		case ModelFlowParser.DEPENDSON:
			if (parentAst instanceof ITaskModuleElement) {
				return isEmf ? new EMFTaskDependencyExpression((ITaskModuleElement) parentAst) : new TaskDependencyExpression();
			}
			break;
		
		case ModelFlowParser.TASKRESOURCE:
			if (parentAst instanceof ITaskModuleElement) {
				return isEmf ? new EMFModelCallExpression() : new ModelCallExpression();
			}
			break;
		
		default:
			break;
		}
		return super.adapt(cst, parentAst);
	}

	/*
	 *******************************************
	 * COMPILATION
	 *******************************************
	 */

	@Override
	public IModelFlowCompilationContext getCompilationContext() {
		if (compilationCtx == null) {
			compilationCtx = isEmf ? new EMFModelFlowCompilationContext(this) : new ModelFlowCompilationContext(this);
		}
		compilationCtx.setRuntimeContext(getContext());
		return compilationCtx;
	}

	@Override
	public List<ModuleMarker> compile() {
		IModelFlowCompilationContext context = getCompilationContext();
		workflow = DomFactory.eINSTANCE.createWorkflow();
		for (IModelModuleElement modelDeclaration : context.getResourceDeclarations()) {
			modelDeclaration.compile(context);
			if (modelDeclaration instanceof IEMFDomElement && context instanceof IEMFModelFlowCompilationContext) {				
				Collection<?> resources = ((IEMFDomElement<?>) modelDeclaration).getDomElements();
				resources.stream().filter(IModelResource.class::isInstance).map(IModelResource.class::cast).forEach(r->{
					((IEMFModelFlowCompilationContext) context).registerResourceModelElement(r, modelDeclaration);	
					workflow.getResources().add(r);
				});
			}
		}
		for (ITaskModuleElement taskDeclaration : context.getTaskDeclarations()) {
			taskDeclaration.compile(context);
			if (taskDeclaration instanceof IEMFDomElement) {
				Collection<?> tasks = ((IEMFDomElement<?>) taskDeclaration).getDomElements();
				tasks.stream().filter(ITask.class::isInstance).map(ITask.class::cast).forEach(t->{
					((IEMFModelFlowCompilationContext) context).registerTaskModelElement(t, taskDeclaration);	
					workflow.getTasks().add(t);
				});
			}
		}
		context.getTaskDeclarations().stream().map(ITaskModuleElement::getDependsOn).flatMap(Collection::stream).forEach(dep ->{
			dep.compile(context);
			if (dep instanceof IEMFDomElement) {
				Collection<?> deps = ((IEMFDomElement<?>) dep).getDomElements();
				deps.stream()
					.filter(ITaskDependency.class::isInstance)
					.map(ITaskDependency.class::cast)
					.forEach(t-> workflow.getTaskDependencies().add(t));
			}
		});
		return super.compile();
	}

	/*
	 *******************************************
	 * WORKFLOW
	 *******************************************
	 */

	@Override
	public IWorkflow getWorkflow() {
		return workflow;
	}
	/*
	 *******************************************
	 * REGISTRIES
	 *******************************************
	 */

	@Override
	public TaskFactoryRegistry getTaskFactoryRegistry() {
		if (taskFactoryRegistry == null) {
			taskFactoryRegistry = Setup.getInstance().getTaskFactoryRegistry();
		}
		return taskFactoryRegistry;
	}

	/**
	 * @param taskFactoryRegistry the taskFactoryRegistry to set
	 */
	@Override
	public void setTaskFactoryRegistry(TaskFactoryRegistry taskFactoryRegistry) {
		this.taskFactoryRegistry = taskFactoryRegistry;
	}

	@Override
	public ResourceFactoryRegistry getResFactoryRegistry() {
		if (resFactoryRegistry == null) {
			resFactoryRegistry = Setup.getInstance().getResourceFactoryRegistry();
		}
		return resFactoryRegistry;
	}

	/**
	 * @param resFactoryRegistry the resFactoryRegistry to set
	 */
	@Override
	public void setResFactoryRegistry(ResourceFactoryRegistry resFactoryRegistry) {
		this.resFactoryRegistry = resFactoryRegistry;
	}

	/*
	 *******************************************
	 * EXECUTION
	 *******************************************
	 */

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		
		IModelFlowContext ctx = getContext();		
		
		if (getTaskFactoryRegistry() == null) {
			throw new EolRuntimeException("The Task Factory Registry is null");
		}
		if (getResFactoryRegistry() == null) {
			throw new EolRuntimeException("The Resource Factory Registry is null");
		}
		
		// Task Repository
		TaskRepository taskRepository = new TaskRepository(getTaskFactoryRegistry(), getResFactoryRegistry());
		ctx.setTaskRepository(taskRepository);

		// Executor 
		ctx.setScheduler(isEmf ? new TopologicalSequentialScheduler() : new TaskStackScheduler());
		
		// Model Manager
		if (ctx.getResourceManager() == null) {
			ctx.setResourceManager(isEmf ? new ResourceManager() : new TaskResourceManager());
		}
		
		// Path Resolver
		if (getCompilationContext().getRelativePathResolver() == null) {
			getCompilationContext().setRelativePathResolver(
					relativePath -> {
					if (getFile() != null) {						
						return getFile().getParentFile().toPath().resolve(relativePath).toString();
					}
					return relativePath;
				}
			);
		}

		// Param Manager
		if (ctx.getParamManager() == null) {
			ctx.setParamManager(new AnnotationTaskParameterManager());
		}
		
		// Execution Trace
		traceHelper.prepareExecutionTrace();
		
		// Mamnagement Trace		
		traceHelper.prepareEndToEndTrace();
	}

	/**
	 * Overwriding the super method so that prepareContext is not called twice.
	 * Otherwise EolModule::execute() calls it first and then by
	 * ErlModule::prepareExecution().
	 */
	@Override
	public void prepareExecution() throws EolRuntimeException {
		if (getWorkflow() == null && parser != null) {
			compile();
		}
		IModelFlowContext ctx = getContext();

		// Make model management trace available during execution as "trace"
		Variable traceVariable = Variable.createReadOnlyVariable("trace", ctx.getManagementTrace());
		ctx.getFrameStack().putGlobal(traceVariable);

		// Put parameters in frame stack
		for (ParameterDeclaration param : getCompilationContext().getParameterDeclarations()) {
			param.execute(ctx);
		}
		
		// Execute Pre
		execute(getPre(), ctx);
	}
	
	/**
	 * Main execution logic
	 */
	@Override
	protected ManagementTrace processRules() throws EolRuntimeException {
		IModelFlowContext ctx = getContext();
		ctx.getProfiler().start(ExecutionStage.EXECUTION, null, ctx);
		
		if (ctx.isProfilingEnabled()) {
			ctx.getProfiler().track();
		}
				
		try {
			ctx.getScheduler().build(ctx);
		} catch (Exception e) {
			throw new EolRuntimeException(e);
		}
		
		LOG.debug("Executing");
		try {
			ctx.getScheduler().execute(ctx);
		} finally {
			LOG.debug("Updating Execution Trace");
			traceHelper.updateExecutionTrace();
	
			if (getContext().isEndToEndTracing() && config.isSaveEndToEndTraces()) {
				LOG.debug("Updating Management Trace");
				traceHelper.updateEndToEndTrace();
			}
			ctx.getProfiler().stop(ExecutionStage.EXECUTION, null, ctx);
		}
		
		return ctx.getManagementTrace();
	}

	@Override
	public void postExecution() throws EolRuntimeException {
		super.postExecution();

		final IModelFlowContext ctx = getContext();
		if (ctx.isProfilingEnabled()) {
			ctx.getProfiler().dispose();
		}
		
		ctx.getTaskRepository().getResourceRepository().flush();
	}
	
	@Override
	public void clearCache() {
		super.clearCache();

		IModelFlowContext ctx = getContext();

		// Execution Traces
		ExecutionTrace exTrace = ExecutionTraceFactoryImpl.eINSTANCE.createExecutionTrace();
		ctx.setExecutionTrace(exTrace);

		// Model Management Traces
		ManagementTrace mTrace = ManagementTraceFactoryImpl.eINSTANCE.createManagementTrace();
		ctx.setManagementTrace(mTrace);

		// Model Manager
		ctx.getTaskRepository().getResourceRepository().clear();
		ctx.getTaskRepository().clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(compilationCtx, config, hasher, isEmf, parameterDeclarations,
				resFactoryRegistry, taskFactoryRegistry, traceHelper, workflow);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ModelFlowModule)) {
			return false;
		}
		ModelFlowModule other = (ModelFlowModule) obj;
		return Objects.equals(compilationCtx, other.compilationCtx) && Objects.equals(config, other.config)
				&& Objects.equals(hasher, other.hasher) && isEmf == other.isEmf
				&& Objects.equals(parameterDeclarations, other.parameterDeclarations)
				&& Objects.equals(resFactoryRegistry, other.resFactoryRegistry)
				&& Objects.equals(taskFactoryRegistry, other.taskFactoryRegistry)
				&& Objects.equals(traceHelper, other.traceHelper) && Objects.equals(workflow, other.workflow);
	}

}
