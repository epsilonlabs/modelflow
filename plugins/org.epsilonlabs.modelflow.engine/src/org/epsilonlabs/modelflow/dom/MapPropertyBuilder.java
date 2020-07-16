/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom;

import java.util.HashMap;
import java.util.Map;

public class MapPropertyBuilder {

	protected Map<String, Object> properties;

	public MapPropertyBuilder() {
		this.properties = new HashMap<>();
	}

	public MapPropertyBuilder put(String key, Object value) {
		this.properties.put(key, value);
		return this;
	}

	public Map<String, Object> build() {
		return this.properties;
	}

}
