/**
 * 
 */
package org.epsilonlabs.modelflow.execution.control;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
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

	protected Map<MeasurableObject, ProfiledStage> profiledStages = new ConcurrentHashMap<>();
	protected MemoryTracker memoryTracker = new MemoryTracker();
	protected long timeout = 100;
	protected TimeUnit unit = TimeUnit.NANOSECONDS;
	
	@Override
	public StageProfilerMap getProfiledStages() {
		return StageProfilerMap.from(profiledStages);
	}

	@Override
	public void start(IMeasurable.Stage stage, IGraphNode ast, IModelFlowContext context) {
		// Must be the first line invoked
		ProfiledStage profiledStageDiagnostic = new ProfiledStage();

		MeasurableObject measurableObject = new MeasurableObject(ast, stage);
		profiledStages.put(measurableObject, profiledStageDiagnostic);
	}
	
	@Override
	public void stop(IMeasurable.Stage stage, IGraphNode ast, IModelFlowContext context) {
		// Must be the first line invoked
		MeassureSnapshot meassureSnapshot = new MeassureSnapshot();
		
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
		profiledStages.clear();
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
		try {
			if (memoryTracker.getLogFile()==null) {				
				memoryTracker.setup();
			}
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(memoryTracker, 0, timeout, unit);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public MemoryTracker getMemoryTracker() {
		return memoryTracker;
	}

}
