/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.param.hash;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileHasher implements IHasher<Collection<File>, Map<String, Object>>{

	@Override
	public Map<String, Object> fromExecutionTrace(Map<String, Object> trace) {
		HashMap<String, Object> hashMap = new HashMap<>();
		trace.keySet().forEach(f-> hashMap.put(f, Hasher.computeHashForFile(new File(f))));
		return hashMap;
	}

	@Override
	public Map<String, Object> fromEvaluatedParameter(Collection<File> taskParameterReturnType) {
		HashMap<String, Object> hashMap = new HashMap<>();
		taskParameterReturnType.forEach(f-> hashMap.put(f.getAbsolutePath(), Hasher.computeHashForFile(f)));
		return hashMap;
	}

}
