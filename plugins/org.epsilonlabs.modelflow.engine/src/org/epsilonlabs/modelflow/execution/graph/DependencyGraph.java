/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.graph;

import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IDerivedResource;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.exception.MFDependencyGraphException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.edge.DependencyEdge;
import org.epsilonlabs.modelflow.execution.graph.node.DerivedResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IAbstractResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;
import org.epsilonlabs.modelflow.execution.graph.node.ModelResourceNode;
import org.epsilonlabs.modelflow.execution.graph.node.TaskNode;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyGraph extends AbstractDependencyGraph implements IDependencyGraph {

	private static final Logger LOG = LoggerFactory.getLogger(DependencyGraph.class);

	public DependencyGraph() {
		super();
		this.graph = new SimpleDirectedWeightedGraph<>(DependencyEdge.class);
	}	

	@Override
	public DependencyGraph buildImpl(IModelFlowContext ctx) throws MFDependencyGraphException {
		IWorkflow workflow = ctx.getModule().getWorkflow();
		workflow.getResources().forEach(r -> this.addResource(r, ctx));
		workflow.getTasks().forEach(t -> this.addTask(t, ctx));
		//workflow.getResourceDependencies().forEach(this::addResourceDependencies);
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
	protected void processImplicitResources(IWorkflow workflow) {
		workflow.getResources().stream().filter(r -> r instanceof IDerivedResource).forEach(r -> {
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

	protected ITaskNode addTask(ITask t, IModelFlowContext ctx) {
		ITaskNode task = new TaskNode(t);
		task.subscribe(ctx.getPublisher());
		getGraph().addVertex(task);
		this.tasks.put(task.getName(), task);
		t.getConsumes().forEach(r -> addConsumesEdge(task, r));
		t.getModifies().forEach(r -> addModifiesEdge(task, r));
		t.getProduces().forEach(r -> addProducesEdge(task, r));

		return task;
	}

	protected void addResource(IAbstractResource r, IModelFlowContext ctx) {
		IAbstractResourceNode node = null;

		if (r instanceof IDerivedResource) {
			node = new DerivedResourceNode((IDerivedResource) r);
		} else if (r instanceof IModelResource) {
			node = new ModelResourceNode((IModelResource) r);
		}
		if (node != null) {
			node.subscribe(ctx.getPublisher());
			this.resources.put(node.getName(), node);
			getGraph().addVertex(node);
		}
	}

	protected void addTaskDependencies(ITaskDependency d) {
		// Source Node that requires this dependency
		ITaskNode node = getTask(d.getTask());
		// Dependency
		ITaskNode dependency = getTask(d.getDependsOn());
		// source (node) --(dependsOn)--> target (dependency)
		getGraph().addEdge(node, dependency, new DependencyEdge(DependencyEdge.Kind.TASK));
	}

	protected void addProducesEdge(ITaskNode taskNode, IResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(taskNode, resourceNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	protected void addConsumesEdge(ITaskNode taskNode, IResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(resourceNode, taskNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	protected void addModifiesEdge(ITaskNode taskNode, IResourceReference resource) {
		IAbstractResourceNode resourceNode = getResource(resource.getResource());
		getGraph().addEdge(resourceNode, taskNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
		getGraph().addEdge(taskNode, resourceNode,
				new DependencyEdge(DependencyEdge.Kind.TASK_RESOURCE, resource.getAliases()));
	}

	/* GETTERS */

	protected IAbstractResourceNode getResource(IAbstractResource resource) {
		IAbstractResourceNode resourceNode;
		if (resource instanceof IDerivedResource) {
			resourceNode = this.resources.get(resource.getName().replace(".", "_"));
		} else if (resource instanceof IResource) {
			resourceNode = this.resources.get(resource.getName());
		} else {
			return null;
		}

		return resourceNode;
	}

	protected ITaskNode getTask(ITask task) {
		return this.tasks.get(task.getName());
	}

	protected void addEdge(IGraphNode source, IGraphNode target, DependencyEdge dependencyEdge) {
		try {
			getGraph().addEdge(source, target, dependencyEdge);
		} catch (Exception e) {
			LOG.error("Unable to create edge : {} -- {}", source.getName(), target.getName());
		}
	}


}