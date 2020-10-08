/**
 * 
 */
package org.epsilonlabs.modelflow.execution.control;

/**
 * @author Betty Sanchez
 *
 */
public interface IModelFlowExecutionProfiler extends IModelFlowProfiler {

	StageProfilerMap getProfiledStages();

	default StageProfilerMap getByStage(IMeasurable.Stage stage) {
		return StageProfilerMap.from(getProfiledStages()).getByStage(stage);
	}

	default StageProfilerMap getByNode(String name) {
		return StageProfilerMap.from(getProfiledStages()).getByNode(name);
	}

}
