/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
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
