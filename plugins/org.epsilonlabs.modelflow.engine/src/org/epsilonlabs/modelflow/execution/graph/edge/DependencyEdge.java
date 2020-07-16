/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.edge;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.google.common.collect.Sets;

public class DependencyEdge extends DefaultWeightedEdge {

	private static final long serialVersionUID = 8331886846634503486L;
	
	private Kind kind;

	private Set<String> aliases;

	public enum Kind {
		RESOURCE(1), TASK_RESOURCE(2), TASK(3);

		private double weight;

		Kind(double weight) {
			this.weight = weight;
		}
	}

	public DependencyEdge(Kind kind) {
		super();
		this.kind = kind;
	}

	
	public DependencyEdge(Kind kind, EList<String> aliases) {
		super();
		this.kind = kind;
		this.aliases = Sets.newHashSet(aliases.toArray(new String[0]));
	}

	public Kind getKind() {
		return kind;
	}

	@Override
	protected double getWeight() {
		return kind.weight;
	}
	
	@Override
	public IGraphNode getSource() {
		return (IGraphNode) super.getSource();
	}
	
	@Override
	public IGraphNode getTarget() {
		return (IGraphNode) super.getTarget();
	}
	
	public Set<String> getAliases(){
		return aliases;
	}
		
}
