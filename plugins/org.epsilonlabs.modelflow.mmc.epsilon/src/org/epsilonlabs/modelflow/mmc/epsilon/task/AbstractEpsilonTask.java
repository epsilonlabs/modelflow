/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEpsilonTask implements ITaskInstance {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractEpsilonTask.class);

	// Attributes
	protected IEolModule module;

	// Pre-Execution
	protected Optional<String> code = Optional.empty();
	protected Optional<File> src = Optional.empty();
	protected Boolean profile = false;
	protected Map<String, Object> params = new HashMap<>();

	// Post-Execution
	protected Object result;

	private IModelWrapper[] resources = null;
	protected List<Trace> traces = null; 

	// Constructor
	protected AbstractEpsilonTask() {
		getModule();
	}

	/**
	 * ------------------------------
	 * Post-Execution Methods
	 * ------------------------------
	 */
	@Param(key="code")
	public void setCode(String code) {
		this.code = Optional.ofNullable(code);
	}
	
	@Input(key="code")
	public String getCode() {
		return code.orElse("");
	}

	@Param(key="src")
	public void setSrc(File src) {
		this.src = Optional.ofNullable(src);
	}
	
	@Param(key="src")
	public void setSrc(String src) {
		this.src = Optional.ofNullable(new File(src));
	}
	
	@Input(key="src")
	public File getSrc() {
		return src.orElse(null);
	}
	
	@Input(key="imports")
	public List<File> getImports() {
		return getModule().getImports().stream().map(i->i.getFile()).collect(Collectors.toList());
	}
	
	@Param(key="profile")
	public void setProfile(Boolean profile) {
		this.profile = profile;
	}
	
	public Boolean getProfile() {
		return profile;
	}
	
	@Param(key="params")
	public void setParams(Map<String, Object> params) {
		this.params.putAll(params);
	}
		
	@Input(key="params")
	public Map<String, Object> getParams() {
		return params;
	}
	
	/**
	 * ------------------------------
	 * Abstract Methods
	 * ------------------------------
	 */
	
	public abstract <M extends IEolModule> M getModule();
	
	/**
	 * ------------------------------
	 * Post-Execution Methods
	 * ------------------------------
	 */

	
	@Override
	public Optional<Collection<Trace>> getTrace(){
		return Optional.empty();
	}
	
	public IModelWrapper[] getResources() {
		return resources;
	}
	
	/**
	 * ------------------------------
	 * Model Processing Methods
	 * ------------------------------
	 */
	
	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		this.resources = models;
		Stream.of(models).forEach(model->{
			
			Object m = model.getModel();
			if (m instanceof IModel) {				
				LOG.info("{} as {}",model.getResourceNode().getName(), model.getResourceKind().toString().toLowerCase());
				/*if (model.getExtraLabel().isPresent()) 
					System.out.println(model.getExtraLabel().get());*/
				getModule().getContext().getModelRepository().addModel((IModel) m);
			}
		});
	}
	
	@Override
	public void processModelsAfterExecution(){ }
	
	/**
	 * ------------------------------
	 * Execution Methods
	 * ------------------------------
	 */
	
	@Override
	public void validateParameters() throws MFExecutionException {
		beforeParse();
		try {
			if (src.isPresent() && src.get().exists()) {
				getModule().parse(src.get());
			} else if (code.isPresent() && !code.get().isEmpty()) {
				getModule().parse(code.get());
			} else {
				throw new MFExecutionException("No source nor code to parse");	
			}
		} catch (Exception e) {
			throw new MFExecutionException(e);
		}
		afterParse();
	}
	
	protected void beforeParse() throws MFExecutionException {
		if (getProfile()) {
			getModule().getContext().setProfilingEnabled(getProfile());
		}
		FrameStack frameStack = module.getContext().getFrameStack();
		getParams().entrySet().forEach(e -> 
			frameStack.putGlobal(Variable.createReadOnlyVariable(e))
		);
	}
	
	protected void afterParse() throws MFExecutionException {
		
	}
	
	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		getModule().getContext().setOutputStream(ctx.getOutputStream());
		getModule().getContext().setErrorStream(ctx.getErrorStream());
		if (Platform.isRunning()) {			
			getModule().getContext().getNativeTypeDelegates().add(new ExtensionPointToolNativeTypeDelegate());
		}
		try {
			result = getModule().execute();
		} catch (EolRuntimeException e) {
			throw new MFExecutionException(e.getReason(), e);
		}
	}

	@Override
	public void afterExecute() throws MFExecutionException{}

}
