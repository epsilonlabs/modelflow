/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.node;

import org.epsilonlabs.modelflow.dom.IDerivedResource;

public class DerivedResourceNode extends AbstractResourceNode<IDerivedResource> implements IDerivedResourceNode{

	public DerivedResourceNode(IDerivedResource resource) {
		super(resource);
	}
	
	@Override
	public String getName() {
		return super.getName().replace(".", "_");
	}

	@Override
	public String getType() {
		return "derived";
	}
	
}
