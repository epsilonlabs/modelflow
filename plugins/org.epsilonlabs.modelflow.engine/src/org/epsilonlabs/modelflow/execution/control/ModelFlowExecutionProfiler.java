/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowExecutionProfiler implements IModelFlowExecutionProfiler {

	protected Map<MeasurableObject, ProfiledStage> profiledStages =  new HashMap<>();
	protected IMemoryTracker memoryTracker;
	protected long timeout = 100;
	protected TimeUnit unit = TimeUnit.NANOSECONDS;
	protected ScheduledExecutorService exec;
	
	@Override
	public StageProfilerMap getProfiledStages() {
		return StageProfilerMap.from(profiledStages);
	}

	@Override
	public void start(ExecutionStage stage, IGraphNode ast, IModelFlowContext context) {
		// Must be the first line invoked
		ProfiledStage profiledStageDiagnostic = new ProfiledStage();

		MeasurableObject measurableObject = new MeasurableObject(ast, stage);
		profiledStages.put(measurableObject, profiledStageDiagnostic);
	}
	
	@Override
	public void stop(ExecutionStage stage, IGraphNode ast, IModelFlowContext context) {
		// Must be the first line invoked
		MeasureableSnapshot meassureSnapshot = new MeasureableSnapshot();
		
		MeasurableObject measurableObject = new MeasurableObject(ast, stage);
		ProfiledStage profiledStageDiagnostic = profiledStages.get(measurableObject);
		profiledStageDiagnostic.setEnd(meassureSnapshot);
	}
	
	@Override
	public void report(IModelFlowContext context) {
		context.getErrorStream().println(this.toString());
	}
	
	@Override
	public void dispose() {
		if (exec != null) {			
			exec.shutdownNow();
		}
	}
	
	@Override
	public String toString() {
		return getProfiledStages()
				.entrySet()
				.stream()
				.map(entry -> String.format("--Stage %s : %n %s", entry.getKey(), entry.getValue().delta()))
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	@Override
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public void setTimeUnit(TimeUnit unit) {
		this.unit = unit;
	}

	@Override
	public TimeUnit getTimeUnit() {
		return unit;
	}

	
	@Override
	public void track() {
		
		if (memoryTracker == null){
			memoryTracker = new DefaultMemoryTracker();
		}/*
		try {
			if (memoryTracker.getLogFile()==null) {				
				memoryTracker.setup();
			}
			exec = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					final Thread t = new Thread(r);
					t.setName("ModelFlowProfiler");
					return t;
				}
			});
			exec.scheduleAtFixedRate(memoryTracker, 0, timeout, unit);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}	
	
	@Override
	public IMemoryTracker getMemoryTracker() {
		return memoryTracker;
	}

}
