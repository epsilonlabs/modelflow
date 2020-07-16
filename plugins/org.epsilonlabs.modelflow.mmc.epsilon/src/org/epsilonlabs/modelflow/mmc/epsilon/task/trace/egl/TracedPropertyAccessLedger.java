/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace.egl;

import java.util.Collection;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;

public class TracedPropertyAccessLedger {
	
	private final Multimap<EglTemplate, TracedPropertyAccess> accessesByTemplate = new Multimap<>();
	
	/** Puts the output file */
	protected void associate(IPropertyAccess access, Region region, EglTemplate template) {
		accessesByTemplate.put(template, new TracedPropertyAccess(access, region));
	}
 
	protected Collection<TracedPropertyAccess> get(EglTemplate template) {
		return accessesByTemplate.get(template);
	}
}
