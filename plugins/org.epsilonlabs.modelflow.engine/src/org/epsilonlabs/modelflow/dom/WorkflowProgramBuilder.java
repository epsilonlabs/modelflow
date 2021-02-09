/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom;

import java.util.stream.Collectors;

public class WorkflowProgramBuilder {

	protected IWorkflow workflow;
	
	public WorkflowProgramBuilder(IWorkflow workflow){
		this.workflow = workflow;
	}
	
	public String build() {
		IWorkflow w= workflow;
		StringBuilder str = new StringBuilder();
		final String NL = System.lineSeparator();
		for (ITask t : w.getTasks()) {
			if (t.getAlwaysExecute()) {
				str.append("@always");
				str.append(NL);
			}
			if (!t.getTraceable()) {
				str.append("@noTrace");
				str.append(NL);
			}
			if (!t.getEnabled()) {
				str.append("@skip");
				str.append(NL);
			}
			final String main = String.format("task %s is %s", t.getName(), t.getDefinition());
			str.append(main);
			str.append(NL);
			// Models
			final String input = t.getConsumes().stream().map(this::models).collect(Collectors.joining(" and "));
			if (!input.isEmpty()) str.append("in "+input+ NL);
			final String inout = t.getModifies().stream().map(this::models).collect(Collectors.joining(" and "));
			if (!inout.isEmpty()) str.append("inout "+inout+ NL);
			final String output = t.getProduces().stream().map(this::models).collect(Collectors.joining(" and "));
			if (!output.isEmpty()) str.append("out "+output + NL);
			str.append("{" + NL);
			// TODO Guard
			// Properties
			final String properties = t.getProperties().stream().map(p->p.getKey() + ": " + propValue(p.getValue())).collect(Collectors.joining(NL));
			str.append(properties);
			str.append(NL + "}" + NL);
		}
		w.getResources().stream().filter(IModelResource.class::isInstance).map(IModelResource.class::cast).forEach(m ->{
			final String main = String.format("model %s is %s", m.getName(), m.getDefinition());
			str.append(main);
			str.append(NL);
			str.append("{" + NL);
			// Properties
			final String properties = m.getProperties().stream().map(p->p.getKey() + ": " + propValue(p.getValue())).collect(Collectors.joining(NL));
			str.append(properties);
			str.append(NL + "}" + NL);
		});
		return str.toString();
	}
	protected String propValue(Object val){
		if (val instanceof String) {
			return String.format("\"%s\"", val);
		} else {
			// TODO other scenarios
			return val.toString();
		}
		
	}

	protected String models(IResourceReference m) {
		final String name = m.getResource().getName();
		if (m.getAliases().isEmpty()) {
			return name;
		} else {
			return name + " as " + m.getAliases().stream().collect(Collectors.joining(", "));
		}
	}
	
	@Override
	public String toString() {
		return build();
	}
	
}
