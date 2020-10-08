/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.execution.graph.node.AbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskNode;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.DOTExporter;

public class GraphizPrinter<N extends IGraphNode, E extends DefaultWeightedEdge> {
	
	protected final Graph<N, E> graph;
	protected final String dotString;
	
	private GraphizPrinter(Graph<N, E> graph, String dotString) {
		this.graph = graph;
		this.dotString = dotString;
	}
	
	public GraphizPrinter(Graph<N, E> graph) {
		this(graph, "");
	}
	
	public GraphizPrinter<N,E> toDot() {
		if (dotString.isEmpty()) {
			DOTExporter<N, E> exporter = new DOTExporter<>(
					/* Node ID Provider */
					node -> node.getName().replace("://", "_").replace("@","__"), 
					/* Node Label Provider */
					node -> node.getName().replace("://", "-"),
					/* Edge Label Provider */
					null,
					/* Vertex Attribute Provider */
					this::getVertexAttributeProvider, 
					/* Edge Attribute Provider */
					component -> new HashMap<>());
			
			exporter.putGraphAttribute("splines", "polyline");
			Writer writer = new StringWriter();
			exporter.exportGraph(graph, writer);
			return new GraphizPrinter<>(graph, writer.toString());
		}
		return this;
	}
	
	@Override
	public String toString() {
		return dotString;
	}
	
	protected Map<String, Attribute> getVertexAttributeProvider(IGraphNode component) {
		Map<String, StringAttribute> map = new HashMap<>();
		if (component instanceof AbstractResourceNode) {
			map.put(DOTAttribute.SHAPE, StringAttribute.ELLIPSE);
			if (component instanceof DerivedResourceNode) {
				map.put(DOTAttribute.STYLE, StringAttribute.FILLED);
			}
		} else if (component instanceof TaskNode) {
			map.put(DOTAttribute.SHAPE, StringAttribute.BOX);
			map.put(DOTAttribute.COLOR, StringAttribute.TERTIARY_COLOR);
			map.put(DOTAttribute.STYLE, StringAttribute.FILLED);
		}
		return map.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey,e->e.getValue().getAttribute()));
	}

}
