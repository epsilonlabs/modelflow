/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task.trace.egl;

import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.egl.execute.control.DefaultTemplateExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;


public class TraceLinkCreatorExecutionListener extends DefaultTemplateExecutionListener {

	private final Trace trace;
	private final TracedPropertyAccessLedger ledger;

	public TraceLinkCreatorExecutionListener(Trace trace, TracedPropertyAccessLedger ledger) {
		this.trace = trace;
		this.ledger = ledger;
	}

	/** Called by EglPersistingTemplate::generate() just after executin doGenerate()*/
	@Override
	public void finishedGenerating(EglPersistentTemplate template, String path) {
		for (TracedPropertyAccess access : ledger.get(template)) {
			trace.getTraceLinks().add(createTraceLink(path, access));
		}
	}

	protected TraceLink createTraceLink(String path, TracedPropertyAccess access) {
		return new TraceLink(createSource(access), createDestination(access.getRegion(), path));
	}

	protected ModelLocation createSource(IPropertyAccess access) {
		return new ModelLocation(access.getModelElement(), access.getPropertyName());
	}

	protected TextLocation createDestination(Region region, String path) {
		return new TextLocation(region, path);
	}

}
