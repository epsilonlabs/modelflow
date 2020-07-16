/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource.hash;

import java.util.Map;

import org.epsilonlabs.modelflow.management.param.hash.IHasher;

/** Can only handle protected regions, not controlled ones */
/**
 * TODO Fix the protected regions issue where these may be removed
 */
public class EglHasher implements IHasher<ProtectedFiles,Map<String, Object>> {
	
	@Override
	public Map<String, Object> fromTaskPopulatedParameter(ProtectedFiles protectedFiles){
		return protectedFiles.compute();

	}
	
	@Override
	public Map<String, Object> fromExecutionTrace(Map<String, Object> trace){
		ProtectedFiles protectedFiles = new ProtectedFiles(trace);
		return protectedFiles.compute();
	}

}
