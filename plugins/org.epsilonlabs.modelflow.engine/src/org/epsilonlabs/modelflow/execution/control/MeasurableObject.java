/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author Betty Sanchez
 *
 */
public class MeasurableObject implements IMeasurable {

	protected final IMeasurable.Stage stage;
	protected final String name;
	protected final Class<? extends IGraphNode> type;
	protected IGraphNode node;

	public MeasurableObject(IGraphNode node, IMeasurable.Stage stage) {
		this.stage = stage;
		if (node != null) {
			this.node = node;
			this.name = node.getName();
			this.type = node.getClass();
		} else {
			this.name = null;
			this.type = null;
		}
	}
	
	@Override
	public IMeasurable getParent() {
		return null;
	}	

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Stage getStage() {
		return this.stage;
	}
	@Override
	public IGraphNode getNode() {
		return node;
	}
	@Override
	public Class<? extends IGraphNode> getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stage == null) ? 0 : stage.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeasurableObject)) {
			return false;
		}
		MeasurableObject other = (MeasurableObject) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (stage != other.stage) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MeasurableObject [stage=" + stage + ", node=" + name + ", type=" + type + "]";
	}
	
}
