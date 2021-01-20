/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.CommandLineFixer;
import org.eclipse.epsilon.evl.trace.ConstraintTrace;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.task.trace.EvlTaskTrace;

@Definition(name = "epsilon:evl")
public class EpsilonEvlTask extends AbstractEpsilonTask implements ITaskInstance {

	@SuppressWarnings("unchecked")
	@Override
	public IEvlModule getModule() {
		if (module == null) {
			this.module = new EvlModule();
		}
		return (IEvlModule) this.module;
	}
	
	protected Boolean fix = false;
	protected Boolean shortCircuit = false;
	protected Boolean optimizedTrace = false;
	protected Boolean failOnError = false;

	@Param(key="fix")
	public void setFix(Boolean fix) {
		this.fix = fix;
	}
	
	@Param(key="failOnError")
	public void setFailOnError(Boolean failOnError) {
		this.failOnError = failOnError;
	}
	
	@Param(key="shortCircuit")
	public void setShortCircuit(Boolean shortCircuit) {
		this.shortCircuit = shortCircuit;
	}
	
	public Boolean getShortCircuit() {
		return shortCircuit;
	}
	
	public Boolean getFix() {
		return fix;
	}
	
	public Boolean getFailOnError() {
		return failOnError;
	}
	
	@Override
	public void validateParameters() throws MFExecutionException {
		super.validateParameters();
		CommandLineFixer fixer = new CommandLineFixer();
		fixer.setFix(this.fix);
		getModule().setUnsatisfiedConstraintFixer(fixer);
		getModule().getContext().setOptimizeConstraintTrace(this.optimizedTrace);
		getModule().getContext().setShortCircuit(this.shortCircuit);
	}
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		super.execute(ctx);
		long errors = getModule().getContext().getUnsatisfiedConstraints().stream().filter(c->!(c.getConstraint().isCritique() || c.getConstraint().isInfo())).count();
		if (failOnError && errors>0) {
			throw new MFExecutionException("Failed Constraints");
		}
	}
	
	@Override
	public Optional<Collection<Trace>> getTrace() {
		if (traces == null) {			
			ConstraintTrace trace = getModule().getContext().getConstraintTrace();
			traces = trace.stream()
					.map(constraint -> new EvlTaskTrace(constraint, this).init().getTrace())
					.collect(Collectors.toList());
		}
		return Optional.of(traces);
	}
	
}