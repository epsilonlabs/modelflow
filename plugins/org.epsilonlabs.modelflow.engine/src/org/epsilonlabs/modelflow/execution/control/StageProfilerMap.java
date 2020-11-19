/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StageProfilerMap extends ConcurrentHashMap<MeasurableObject, ProfiledStage> implements Map<MeasurableObject, ProfiledStage> {

	private static final long serialVersionUID = 561135571648588358L;

	public static StageProfilerMap from(Map<MeasurableObject, ProfiledStage> old) {
		StageProfilerMap map = new StageProfilerMap();
		map.putAll(old);
		return map;
	}

	public StageProfilerMap getByStage(IMeasurable.Stage stage) {
		return entrySet().stream().filter(e -> e.getKey().getStage().equals(stage))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, StageProfilerMap::new));
	}

	public StageProfilerMap getByNode(String name) {
		return entrySet().stream().filter(e -> e.getKey().getNode().equals(name))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, StageProfilerMap::new));
	}
	
}
