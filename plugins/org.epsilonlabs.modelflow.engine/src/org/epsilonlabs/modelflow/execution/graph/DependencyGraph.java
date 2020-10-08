/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import org.epsilonlabs.modelflow.dom.AbstractResource;
import org.epsilonlabs.modelflow.dom.DerivedResource;
import org.epsilonlabs.modelflow.dom.ModelResource;
import org.epsilonlabs.modelflow.dom.Resource;
import org.epsilonlabs.modelflow.dom.ResourceDependency;
import org.epsilonlabs.modelflow.dom.ResourceReference;
import org.epsilonlabs.modelflow.dom.Task;
import org.epsilonlabs.modelflow.dom.TaskDependency;
import org.epsilonlabs.modelflow.dom.Workflow;
import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.ModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskNode;
import org.epsilonlabs.modelflow.execution.graph.util.GraphizPrinter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyGraph extends AbstractDependencyGraph implements IDependencyGraph {

	private static final Logger LOG = LoggerFactory.getLogger(DependencyGraph.class);

	public DependencyGraph() {
		this.graph = new SimpleDirectedWeightedGraph<>(DependencyEdge.class);
		updateStatus(GraphState.CREATED);
	}

	protected void updateStatus(GraphState status) {
		this.status = status;
		this.statusUpdater.onNext(this.status);
		if (this.status.equals(GraphState.POPULATED)) {
			this.statusUpdater.onComplete();
		}
	}

	@Override
	public DependencyGraph buildImpl(IModelFlowContext ctx) throws MFDependencyGraphException {
		Workflow workflow = ctx.getModule().getWorkflow();
		workflow.getResources().forEach(r -> this.addResource(r, ctx));
		workflow.getTasks().forEach(t -> this.addTask(t, ctx));
		workflow.getResourceDependencies().forEach(this::addResourceDependencies);
		workflow.getTaskDependencies().forEach(this::addTaskDependencies);
		processImplicitResources(workflow);
		updateStatus(GraphState.POPULATED);
		LOG.info("DependencyGraph populated: \n{}", this);
		return this;
	}

	/**
	 * This method is used to help with the execution order
	 * 
	 * @param workflow
	 */
	protected void processImplicitResources(Workflow workflow) {
		workflow.getResources().stream().filter(r -> r instanceof DerivedResource).forEach(r -> {
			// already added,
			String[] split = r.getName().split("\\.");
			IAbstractResourceNode resourceNode = getResource(r);
			if (split.length == 2 && workflow.getTasks().stream().anyMatch(t -> t.getName().equals(split[0]))) {
				ITaskNode taskNode = tasks.get(split[0]);
				addEdge(taskNode, resourceNode, new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE));
			} else {
				LOG.error("invalid resource {}", r.getName());
				getGraph().removeVertex(resourceNode);
			}
		});
	}

	protected ITaskNode addTask(Task t, IModelFlowContext ctx) {
		ITaskNode task = new TaskNode(t);
		task.subscribe(ctx.getPublisher());
		getGraph().addVertex(task);
		this.tasks.put(task.getName(), task);
		t.getConsumes().forEach(r -> addConsumesEdge(task, r));
		t.getModifies().forEach(r -> addModifiesEdge(task, r));
		t.getProduces().forEach(r -> addProducesEdge(task, r));

		return task;
	}

	protected void addResource(AbstractResource r, IModelFlowContext ctx) {
		IAbstractResourceNode node = null;

		if (r instanceof DerivedResource) {
			node = new DerivedResourceNode((DerivedResource) r);
		} else if (r instanceof ModelResource) {
			node = new ModelResourceNode((ModelResource) r);
		}
		if (node != null) {
			node.subscribe(ctx.getPublisher());
			this.resources.put(node.getName(), node);
			getGraph().addVertex(node);
		}
	}

	protected void addTaskDependencies(TaskDependency d) {
		// Source Node that requires this dependency
		ITaskNode node = getTask(d.getAfter());
		// Dependency
		ITaskNode dependency = getTask(d.getBefore());
		// source (node) --(dependsOn)--> target (dependency)
		getGraph().addEdge(node, dependency, new DependencyEdge(DependencyEdge.Kind.TASK));
	}

	protected void addResourceDependencies(ResourceDependency rd) {
		IAbstractResourceNode source = getResource(rd.getSource());
		IAbstractResourceNode target = getResource(rd.getTarget());
		getGraph().addEdge(source, target, new DependencyEdge(DependencyEdge.Kind.RESOURCE));
	}

	protected void addProducesEdge(ITaskNode taskNode, ResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(taskNode, resourceNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	protected void addConsumesEdge(ITaskNode taskNode, ResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(resourceNode, taskNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	protected void addModifiesEdge(ITaskNode taskNode, ResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(resourceNode, taskNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
		getGraph().addEdge(taskNode, resourceNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	/* GETTERS */

	protected IAbstractResourceNode getResource(AbstractResource resource) {
		IAbstractResourceNode resourceNode;
		if (resource instanceof DerivedResource) {
			resourceNode = this.resources.get(resource.getName().replace(".", "_"));
		} else if (resource instanceof Resource) {
			resourceNode = this.resources.get(resource.getName());
		} else {
			return null;
		}

		return resourceNode;
	}

	protected ITaskNode getTask(Task task) {
		return this.tasks.get(task.getName());
	}

	protected void addEdge(IGraphNode source, IGraphNode target, DependencyEdge dependencyEdge) {
		try {
			getGraph().addEdge(source, target, dependencyEdge);
		} catch (Exception e) {
			LOG.error("Unable to create edge : {} -- {}", source.getName(), target.getName());
		}
	}

	/**
	 * Prints the graph in graphviz-dot notation
	 */
	@Override
	public String toString() {
		GraphizPrinter<IGraphNode, DependencyEdge> printer = new GraphizPrinter<>(getGraph());
		return printer.toDot().toString();
	}

	// FIXME return new instead of reseting
	@Override
	public void reset() {
		updateStatus(GraphState.CREATED);
		this.graph = new SimpleDirectedWeightedGraph<>(DependencyEdge.class);
		tasks.clear();
		resources.clear();
	}

}