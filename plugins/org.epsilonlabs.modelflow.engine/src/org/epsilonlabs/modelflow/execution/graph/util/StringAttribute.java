/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.util;

import org.jgrapht.io.Attribute;
import org.jgrapht.io.AttributeType;

public enum StringAttribute  {

	FILLED("filled"), PRIMARY_COLOR("white"), SECONDARY_COLOR("black"), TERTIARY_COLOR("gray"), BOX("box"),
	DIAMOND("diamond"), ELLIPSE("ellipse");

	String value;

	StringAttribute(String value) {
		this.value = value;
	}

	public Attribute getAttribute() {
		return new Attribute() {
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public AttributeType getType() {
				return AttributeType.STRING;
			}
		};
	}

}