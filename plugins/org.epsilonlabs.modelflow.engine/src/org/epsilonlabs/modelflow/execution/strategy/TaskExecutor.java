package org.epsilonlabs.modelflow.execution.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.ast.ForEachModuleElement;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.AbstractTaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.ConservativeExecutionHelper;
import org.epsilonlabs.modelflow.management.param.ITaskParameterManager;
import org.epsilonlabs.modelflow.management.resource.IResourceManager;
import org.epsilonlabs.modelflow.management.trace.ManagementTrace;
import org.epsilonlabs.modelflow.management.trace.ManagementTraceUpdater;
import org.epsilonlabs.modelflow.management.trace.Trace;


public class TaskExecutor extends AbstractTaskNode {

	protected ITaskModuleElement declaration;

	public TaskExecutor(ITaskModuleElement declaration) {
		this.declaration = declaration; 
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

	/**
	 * @param ctx
	 * @return
	 * @throws MFRuntimeException
	 * @throws EolRuntimeException
	 */
	public Optional<Collection<Trace>> attemptIndividualExecution(IModelFlowContext ctx) throws MFRuntimeException {
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

	public Optional<Collection<Trace>> attemptForEachExecution(IModelFlowContext ctx) throws MFRuntimeException {
		final ForEachModuleElement forEach = declaration.getForEach();
		try {				
			forEach.execute(ctx);
		} catch (Exception e) {
			final String msg = "Exception when evaluating forEach statement of task %s";
			final String formatted = String.format(msg,declaration.getName());
			throw new MFRuntimeException(formatted, forEach);
		} 
		final Iterator<Object> iterator = forEach.getIterator();
		Collection<Trace> traces = new ArrayList<>();
		final Parameter iteratorParameter = forEach.getIteratorParameter();
		for (int loop = 1; iterator.hasNext(); loop++) {
			try {
				final Object next = iterator.next();
				Variable self = new Variable(iteratorParameter.getName(), next, iteratorParameter.getType(ctx));
				Variable loopCount = new Variable("loopCount", loop, EolPrimitiveType.Integer, true);
				Variable taskName = new Variable("taskName", declaration.getName() + "@" + loopCount, EolPrimitiveType.String, true);
				ctx.getFrameStack().enterLocal(FrameType.UNPROTECTED, forEach, self, loopCount, taskName);
				try {
					final TaskExecutor executor = new TaskExecutor(declaration);
					final Optional<Collection<Trace>> optional = executor.attemptIndividualExecution(ctx);
					optional.ifPresent(traces::addAll);
				} finally {
					ctx.getFrameStack().leaveLocal(forEach);
				}
			} catch (EolRuntimeException e) {
				throw new MFRuntimeException(e);
			}
		}
		return Optional.of(traces);
	}
	
	protected Optional<Collection<Trace>> execute(ITaskInstance instance, IModelFlowContext ctx) throws MFRuntimeException {
		
		IResourceManager manager = ctx.getResourceManager(); 
		ITaskParameterManager pManager = ctx.getParamManager();
		
		// Register inputs in execution trace
		pManager.processInputs(instance, ctx);
		
		// Assign Models Before Execution
		manager.processResourcesBeforeExecution(instance, ctx);
		
		// -- EXECUTING --
		// Cleanup if necessary 
		instance.beforeExecute();
		
		// Execute 
		instance.execute(ctx);
		
		// Cleanup if necessary 
		instance.afterExecute();
		
		// -- POST PROCESSING -- 
		
		// Record outputs in execution trace
		pManager.processOutputs(instance, ctx);

		// Traces
		Optional<Collection<Trace>> traces = processManagementTraces(instance, ctx);
		
		// Process Models After Execution
		manager.processResourcesAfterExecution(instance, ctx);
		
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
				ManagementTraceUpdater traceUpdater = new ManagementTraceUpdater(fullTrace, instance.getName());
				traceUpdater.update(traces);
			});
			return trace;
		}
		return Optional.empty();
	}

	@Override
	public String getName() {
		return declaration.getName();
	}

	@Override
	public String getDefinition() {
		return declaration.getType().getName();
	}
	
	@Override
	public ModuleElement getModuleElement() {
		return declaration;
	}
	
	// TODO remove from interface
	@Override
	public ITask getTaskElement() {
		return null;
	}

}
