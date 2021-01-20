/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import java.util.Collections;
import java.util.Set;

import org.epsilonlabs.modelflow.dom.IResource;

public class ResourceNode extends AbstractResourceNode<IResource> implements IResourceNode {
	protected Set<String> aliases = Collections.emptySet();

	public ResourceNode(IResource resource) {
		super(resource);
	}
	
	@Override
	public String getType() {
		return getModelElement().getDefinition();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ResourceNode && ((ResourceNode) obj).getName().equals(getName());
	}
	
	@Override
	public Set<String> getAliases() {
		return aliases;
	}
	
}
