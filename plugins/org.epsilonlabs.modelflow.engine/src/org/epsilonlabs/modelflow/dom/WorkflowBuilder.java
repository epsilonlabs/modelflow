/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.dom;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.epsilonlabs.modelflow.dom.api.factory.EMFTaskFactory;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Remembers the latest task or resource in order to pass properties.
 * @author Betty Sanchez
 *
 */
public class WorkflowBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(WorkflowBuilder.class);

	protected Collection<IAbstractResource> resources;
	protected Collection<ITask> tasks;
	protected Collection<ITaskDependency> taskDependencies;

	private static final Pattern PATTERN = EMFTaskFactory.REF_VAR_PATTERN;

	public static void main(String[] args) {
		IWorkflow workflow = new WorkflowBuilder()
		.addTask("myEol", "epsilon:eol")
		.addProperty("src", "Hello") 
		.addModelResource("model", "epsilon:emf")
		.addProperty("src", "myEmf.model")
		.addTask("anotherTask", "epsilon:evl")
		.build();
		LOG.info("{}", workflow);
	}
	
	public WorkflowBuilder(){
		resources = new ArrayList<>();
		tasks = new ArrayList<>();
		taskDependencies = new ArrayList<>();
	}
	
	public IWorkflow build() {
		IWorkflow workflow = DomFactory.eINSTANCE.createWorkflow();
		workflow.getResources().addAll(resources);
		workflow.getTasks().addAll(tasks);
		workflow.getTaskDependencies().addAll(taskDependencies);

		Set<String> names = tasks.stream().map(ITask::getName).collect(Collectors.toSet());
		tasks.forEach(t-> {
			if (t.getGuard() instanceof String) {
				String guard = (String) t.getGuard();
				names.stream().filter(e-> guard.contains(e + ".")).forEach(task -> {
					if (t.getDependencies().stream().noneMatch(d->d.getDependsOn().getName().equals(task))){
						ITaskDependency dep = DomFactory.eINSTANCE.createTaskDependency();
						dep.setTask(t);
						dep.setDependsOn(tasks.stream().filter(a->a.getName().equals(task)).findFirst().get());
						workflow.getTaskDependencies().add(dep);
					}
				});
			}
		});
		return workflow;
	}
	
	public IWorkflow build(String name) {
		IWorkflow build = build();
		build.setName(name);
		return build;
	}
	
	public TaskBuilder addTask(String name, String type){
		ITask task = DomFactory.eINSTANCE.createTask();
		task.setName(name);
		task.setDefinition(type);

		return new TaskBuilder(this, task);
	}
	
	public ModelResourceBuilder addModelResource(String name, String type){
		IModelResource model = DomFactory.eINSTANCE.createModelResource();
		model.setName(name);
		model.setDefinition(type);
		resources.add(model);
		
		return new ModelResourceBuilder(this, model);
	}
	
	public class TaskBuilder extends SubBuilder {
		protected ITask task;
		protected List<String> dependencies = new ArrayList<>();
		
		TaskBuilder(WorkflowBuilder topBuilder, ITask task){
			super(topBuilder);
			this.task = task;
		}
		
		@Override
		protected void save() {
			getBuilder().tasks.add(task);
			dependencies.forEach(d -> {
				Optional<ITask> findFirst = getBuilder().tasks.stream().filter(ta-> ta.getName().equals(d)).findFirst();
				if (findFirst.isPresent()) {					
					ITaskDependency dep = DomFactory.eINSTANCE.createTaskDependency();
					dep.setDependsOn(findFirst.get());
					dep.setTask(task);
					getBuilder().taskDependencies.add(dep);
				} else {
					throw new IllegalStateException(String.format("Task %s has not been created yet", d));
				}
			});
		}
		
		public TaskBuilder skip() {
			task.setEnabled(false);
			
			return this;
		}
		
		public TaskBuilder dontTrace() {
			task.setTraceable(false);
			
			return this;
		}
		
		
		public TaskBuilder addProperty(String key, Object value){
			IProperty property = DomFactory.eINSTANCE.createProperty();
			property.setKey(key);	
			handlePropertyDependency(value);
			if (value instanceof Path) {
				property.setValue(((Path)value).normalize().toAbsolutePath().toString());
			} else {
				property.setValue(value);
			}
			task.getProperties().add(property);
			
			return this;
		}
		
		public TaskBuilder addProperty(String key){
			return addProperty(key, null);
		}
		
		public TaskBuilder addGuard(Object guard) {
			task.setGuard(guard);
			
			return this;
		}
		
		protected IAbstractResource addDerivedResource(String name){
			IDerivedResource model = DomFactory.eINSTANCE.createDerivedResource();
			model.setName(name);
			LOG.debug("Adding derived resource: {}", name);
			resources.add(model);
			return model;
		}
		
		/**
		 * TODO consider aliases
		 * @param value
		 */
		@SuppressWarnings("unchecked")
		protected void handlePropertyDependency(Object value){
			if (value instanceof String) {
				Matcher matcher = PATTERN.matcher((String)value);
				while (matcher.find()) {				
					String taskName = matcher.group(1);
					if (tasks.stream().anyMatch(t-> t.getName().equals(taskName))){
						String resource = matcher.group(2);
						
						IAbstractResource derived = addDerivedResource(taskName + "." +resource);
						
						IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
						ref.setResource(derived);

						task.getConsumes().add(ref);
					} else {
						String msg = "Task %s not found. Ensure it has been declared before this task";
						throw new RuntimeException(String.format(msg, taskName));
					}

				}
			} else if (value instanceof Map) {
				((Map<String,?>) value).values().forEach(this::handlePropertyDependency);
			}
		}
		
		public TaskBuilder addInput(String resourceId, String... alias){
			Optional<IAbstractResource> res = getBuilder().resources.stream().filter(r ->r.getName().equals(resourceId)).findFirst();
			if (res.isPresent()) {
				IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
				ref.setResource(res.get());
				Arrays.asList(alias).forEach(a-> ref.getAliases().add(a));
				task.getConsumes().add(ref);
			} else {	
				String[] split = resourceId.split("\\.");
				if (split.length == 2 && getBuilder().tasks.stream().anyMatch(t-> t.getName().equals(split[0]))){
					
					IAbstractResource derived = addDerivedResource(resourceId);
					IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
					ref.setResource(derived);
					Arrays.asList(alias).forEach(a-> ref.getAliases().add(a));
					task.getConsumes().add(ref);

				} else {
					String msg = "Resource %s not found. Ensure it has been declared before this task";
					throw new RuntimeException(String.format(msg, resourceId));
				}
			}
			
			return this;
		}
	
		public TaskBuilder addInout(String resourceId, String... alias){
			Optional<IAbstractResource> res = getBuilder().resources.stream().filter(r ->r.getName().equals(resourceId)).findFirst();
			if (res.isPresent()) {
				IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
				ref.setResource(res.get());
				Arrays.asList(alias).forEach(a-> ref.getAliases().add(a));

				task.getModifies().add(ref);				
			} else {
				String[] split = resourceId.split("\\.");
				if (split.length == 2 && getBuilder().tasks.stream().anyMatch(t-> t.getName().equals(split[0]))){
					IAbstractResource derived = addDerivedResource(resourceId);
					IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
					ref.setResource(derived);
					Arrays.asList(alias).forEach(a-> ref.getAliases().add(a));
					task.getConsumes().add(ref);
				} else {
					String msg = "Resource %s not found. Ensure it has been declared before this task";
					throw new RuntimeException(String.format(msg, resourceId));
				}
			}
			
			return this;
		}

		public TaskBuilder addOutput(String resourceId, String... alias){
			Optional<IAbstractResource> res = getBuilder().resources.stream().filter(r ->r.getName().equals(resourceId)).findFirst();
			if (res.isPresent()) {
				IResourceReference ref = DomFactory.eINSTANCE.createResourceReference();
				ref.setResource(res.get());
				Arrays.asList(alias).forEach(a-> ref.getAliases().add(a));
				task.getProduces().add(ref);				
			} else {
				String msg = "Resource %s not found. Ensure it has been declared before this task";
				throw new RuntimeException(String.format(msg, resourceId));
			}
			
			return this;
		}
		
		public TaskBuilder dependsOn(String task){
			dependencies.add(task);
			
			return this;
		}
		
	}
	
	public class ModelResourceBuilder extends SubBuilder {
		protected IModelResource resource;
		
		ModelResourceBuilder(WorkflowBuilder topBuilder, IModelResource resource){
			super(topBuilder);
			this.resource = resource;
		}
		
		@Override
		protected void save() {
			getBuilder().resources.add(resource); 
		}
		
		public ModelResourceBuilder addProperty(String key, Object value){
			IProperty property = DomFactory.eINSTANCE.createProperty();
			property.setKey(key);
			if (value instanceof Path) {
				property.setValue(((Path) value).normalize().toAbsolutePath().toString());
			} else {				
				property.setValue(value);
			}
			resource.getProperties().add(property);
			
			return this;
		}
		
		public ModelResourceBuilder addProperty(String key){
			return addProperty(key, null);
		}
	}
	
	abstract class SubBuilder {
		protected WorkflowBuilder builder;
		
		SubBuilder(WorkflowBuilder topBuilder){
			this.builder = topBuilder;
		}
		
		public TaskBuilder addTask(String name, String type){
			save();
			return builder.addTask(name, type);
		}
		
		public ModelResourceBuilder addModelResource(String name, String type){
			save();
			return builder.addModelResource(name, type);
		}
		
		public IWorkflow build() {
			save();
			return builder.build();
		}
		
		public IWorkflow build(String name) {
			save();
			return builder.build(name);
		}
		
		protected WorkflowBuilder getBuilder(){
			return builder;
		}
		
		protected abstract void save();
		
	}
	
}
