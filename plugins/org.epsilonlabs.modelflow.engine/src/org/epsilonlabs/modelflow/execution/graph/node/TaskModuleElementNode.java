package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.ast.ForEachModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.param.ITaskParameterManager;
import org.epsilonlabs.modelflow.management.resource.IResourceManager;
import org.epsilonlabs.modelflow.management.resource.ResourceKind;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceUpdater;
import org.epsilonlabs.modelflow.management.trace.Trace;

import com.google.common.collect.Maps;


public class TaskModuleElementNode extends AbstractTaskNode {

	protected ITaskModuleElement declaration;
	protected String name;
	
	protected Map<String, TaskModuleElementNode> subNodes = Maps.newHashMap();

	public TaskModuleElementNode(ITaskModuleElement declaration) {
		this(declaration, declaration.getName());
	}
	protected TaskModuleElementNode(ITaskModuleElement declaration, String name) {
		this.declaration = declaration; 
		this.name = name;
	}
	
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		//Optional<Collection<Trace>> traces = Optional.empty();
		
		// TODO Evaluate dependencies?
		
		if (declaration.isGenerator()) {
			// Evaluate forEach
			attemptForEachExecution(ctx);
		}
		if (declaration.isEnabled()) {
			attemptIndividualExecution(ctx);
		}
		// Skip (copy traces)
		
	}

	public Map<String, TaskModuleElementNode> getSubNodes() {
		return subNodes;
	}

	/**
	 * @param ctx
	 * @return
	 * @throws MFRuntimeException
	 * @throws EolRuntimeException
	 */
	public Optional<Collection<Trace>> attemptIndividualExecution(IModelFlowContext ctx) throws MFRuntimeException {
		resolveModelNodes(ctx);
		
		Optional<Collection<Trace>> traces;
		// Preparation for any situation 
		ITaskInstance instance = ctx.getTaskRepository().create(this, ctx);
		instance.validateParameters();

		// TODO: Check that dependencies have executed
		
		if (!declaration.isAlwaysExecute()) {
			// Check if inputs have changed
			
		} else {				
			// Go straight to the execution
		}
		// Execution
		traces = execute(instance, ctx);
		
		// Post processing
		return traces;
	}

	protected Multimap<ResourceKind, IModelResourceNode> models = new Multimap<>();
	
	protected void resolveModelNodes(IModelFlowContext ctx) {
		declaration.getInputs().stream().map(m->new ModelModuleElementNode(m, ctx)).forEach(input-> models.put(ResourceKind.INPUT, input));
		declaration.getInouts().stream().map(m->new ModelModuleElementNode(m, ctx)).forEach(inout-> models.put(ResourceKind.INOUT, inout));
		declaration.getOutputs().stream().map(m->new ModelModuleElementNode(m, ctx)).forEach(output-> models.put(ResourceKind.OUTPUT, output));
	}
	
	public Multimap<ResourceKind, IModelResourceNode> getModels(){
		return models;
	}

	public Optional<Collection<Trace>> attemptForEachExecution(IModelFlowContext ctx) throws MFRuntimeException {
		final ForEachModuleElement forEachModuleElement = declaration.getForEach();
		try {				
			forEachModuleElement.execute(ctx);
		} catch (Exception e) {
			final String msg = "Exception when evaluating forEach statement of task %s";
			final String formatted = String.format(msg,declaration.getName());
			throw new MFRuntimeException(formatted, forEachModuleElement);
		} 
		final Iterator<Object> iterator = forEachModuleElement.getIterator();
		Collection<Trace> traces = new ArrayList<>();
		final Parameter iteratorParameter = forEachModuleElement.getIteratorParameter();
		for (int loop = 1; iterator.hasNext(); loop++) {
			try {
				final Object next = iterator.next();
				Variable selfVar = new Variable(iteratorParameter.getName(), next, iteratorParameter.getType(ctx));
				Variable loopCountVar = new Variable("loopCount", loop, EolPrimitiveType.Integer, true);
				final String taskName = declaration.getName() + "@" + loopCountVar;
				Variable taskNameVar = new Variable("taskName", taskName, EolPrimitiveType.String, true);
				ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, forEachModuleElement, selfVar, loopCountVar, taskNameVar);
				try {
					final TaskModuleElementNode node = new TaskModuleElementNode(declaration, taskName);
					subNodes.put(taskName, node);
					final Optional<Collection<Trace>> optional = node.attemptIndividualExecution(ctx);
					optional.ifPresent(traces::addAll);
				} finally {
					ctx.getFrameStack().leaveLocal(forEachModuleElement);
				}
			} catch (EolRuntimeException e) {
				throw new MFRuntimeException(e);
			}
		}
		return Optional.of(traces);
	}
	
	protected Optional<Collection<Trace>> execute(ITaskInstance instance, IModelFlowContext ctx) throws MFRuntimeException {
		
		IResourceManager modelManager = ctx.getResourceManager(); 
		ITaskParameterManager pManager = ctx.getParamManager();
		
		// Register inputs in execution trace
		pManager.processInputs(this, ctx);
		
		// Assign Models Before Execution
		modelManager.processResourcesBeforeExecution(this, ctx);
		
		// -- EXECUTING --
		// Cleanup if necessary 
		instance.beforeExecute();
		
		// Execute 
		instance.execute(ctx);
		
		// Cleanup if necessary 
		instance.afterExecute();
		
		// -- POST PROCESSING -- 
		
		// Record outputs in execution trace
		pManager.processOutputs(this, ctx);

		// Traces
		Optional<Collection<Trace>> traces = processManagementTraces(this.getTaskInstance(), ctx);
		
		// Process Models After Execution
		modelManager.processResourcesAfterExecution(this, ctx);
		
		return traces;
	}
	
	protected Collection<Trace> skip() {
		// TODO Update end to end traces to ignore 
		return Collections.emptyList();
	}
	
	protected void noNeedToExecute(IModelFlowContext ctx, ConservativeExecutionHelper incremental) {
		// Copy Execution Trace 
		incremental.copyFromPrevious();
		
		//Safely dispose no longer used models
		safelyDispose(ctx);
	}
	
	protected void safelyDispose(IModelFlowContext ctx) {
		ctx.getTaskRepository().getResourceRepository();
		//declaration.getOutputs().forEach(o->o.getTargetExpression());
		Collection<ModelDeclaration> models = Collections.emptyList();
		for (ModelDeclaration m : models) {
			// 	if (isLastUse?){
			// 		dispose
			// 	}
		}
	}
	
	protected Boolean isGuardOk(IModelFlowContext ctx) throws MFRuntimeException {
  		ExecutableBlock<Boolean> guard = this.declaration.getGuard();
  		if (guard != null) {
  			Variable self = Variable.createReadOnlyVariable("self", declaration);
  			ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, guard, self);
  			try {				
  				return guard.execute(ctx);
  			} catch (Exception e) {
  				final String msg = "Exception when evaluating guard of task %s";
				final String formatted = String.format(msg,declaration.getName());
				throw new MFRuntimeException(formatted, guard);
  			} finally {				
  				ctx.getFrameStack().leaveLocal(guard);
			}
  		}
		return true;
	}
	
	protected Optional<Collection<Trace>> processManagementTraces(ITaskInstance instance, IModelFlowContext ctx) {
		boolean endToEndTracing = ctx.isEndToEndTracing();
		boolean traceable = this.declaration.isTrace();
		if (endToEndTracing && traceable) {
			// Check if task produced traces
			final Optional<Collection<Trace>> trace = instance.getTrace();
			trace.ifPresent(traces -> {
				ManagementTrace fullTrace= ctx.getManagementTrace();
				ManagementTraceUpdater traceUpdater = new ManagementTraceUpdater(fullTrace, getName());
				traceUpdater.update(traces);
			});
			return trace;
		}
		return Optional.empty();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDefinition() {
		return declaration.getType().getName();
	}
	
	@Override
	public ITaskModuleElement getModuleElement() {
		return declaration;
	}
	
	@Override
	public Set<String> getResourceAliases(String resourceNode) {
		final Optional<IModelResourceNode> optional = getModels().entrySet().stream()
				.map(Entry::getValue)
				.flatMap(Collection::stream)
				.filter(m->m.getName().equals(resourceNode)).findAny();
		if (optional.isPresent()) {
			return optional.get().getAliases();
		}
		return Collections.emptySet();
	}

}
