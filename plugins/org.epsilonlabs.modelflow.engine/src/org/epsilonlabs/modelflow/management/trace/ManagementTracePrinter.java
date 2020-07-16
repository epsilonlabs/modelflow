/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.trace;

import org.epsilonlabs.modelflow.dom.Resource;

public class ManagementTracePrinter {

	protected final TaskTrace tTrace;
	
	public ManagementTracePrinter(TaskTrace tTrace) {
		this.tTrace = tTrace;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("==========================\n");
		builder.append(String.format("==Traces for %s== %n", tTrace.getTask().getName()));
		builder.append("==========================\n");
		for (Trace trace : tTrace.getTraces()) {
			builder.append("- Link: \n");
			builder.append(String.format("  (type: %s", trace.getLink().getLink()));
			if (trace.getLink() instanceof ManagementOperation) {			
				builder.append(String.format(", op: %s)%n", ((ManagementOperation)trace.getLink()).getManagementOperation()));
			} else {
				builder.append(")\n");
			}
			builder.append("- Sources: \n");
			for (Element source : trace.getSources()) {
				element(builder, source);
			}
			builder.append("- Targets: \n");
			for (Element target : trace.getTargets()) {
				element(builder, target);
			}
			builder.append("- Properties: \n");
			for (Property prop : trace.getProperties()) {
				builder.append(String.format("  (%s: %s)%n", prop.getKey(), prop.getValue()));
			}
			builder.append("----------------\n");
		}
		return builder.toString();
		
	}

	protected void element(StringBuilder builder, Element element) {
		if (element instanceof ModelElement) {
			builder.append(String.format("  (model : %s, ", getContainer(element)));
			builder.append(String.format("id : %s, ", ((ModelElement)element).getElementId()));
			builder.append(String.format("as : %s", ((ModelElement)element).getRole()));
			if (element instanceof ModelElementProperty) {
				builder.append(String.format(", property : %s", ((ModelElementProperty)element).getProperty()));
			}
		} else if (element instanceof FileElement){
			builder.append(String.format("  (file : %s, ", getContainer(element)));
			Region region = ((FileElement)element).getRegion();
			builder.append(String.format("region : %s:%s", region.getOffset(), region.getLength()));
		}
		builder.append(")\n");
		
	}

	protected String getContainer(Element element){
		Resource container = element.getContainer();
		return (container != null) ? container.getName() : "";
	}
	
}
