/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.management.trace;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IResource;
import org.epsilonlabs.modelflow.dom.impl.DomFactory;
import org.epsilonlabs.modelflow.management.trace.impl.ManagementTraceFactoryImpl;

import io.reactivex.annotations.Nullable;

public class ManagementTraceBuilder {

	protected Link link;
	protected Collection<Element> sources;
	protected Collection<Element> targets;
	protected Collection<Property> properties;
	
	public ManagementTraceBuilder() {
		sources = new ArrayList<>();
		targets = new ArrayList<>();
		properties = new ArrayList<>();
	}
	
	public Trace build(){
		Trace trace = ManagementTraceFactoryImpl.eINSTANCE.createTrace();
		if (link != null) trace.setLink(link);
		trace.getLink().getProperties().addAll(properties);
		trace.getSources().addAll(sources);
		trace.getTargets().addAll(targets);
		return trace;
	}
	
	public ManagementTraceBuilder managementLink(String type, String operation){
		if (type!= null || operation!= null) {			
			Link managementOp = ManagementTraceFactoryImpl.eINSTANCE.createLink();
			if (type != null && !type.isEmpty())
				managementOp.setType(type);
			if (operation != null && !operation.isEmpty()) 
				managementOp.setOperation(operation);
			this.link = managementOp;
		}
		
		return this;
	}
	
	public ManagementTraceBuilder link(String type){
		if (type!= null) {			
			link = ManagementTraceFactoryImpl.eINSTANCE.createLink();
			if (!type.isEmpty())
				link.setType(type);
		}
		
		return this;
	}
	
	private ModelElement modelElement(String elementId, @Nullable IAbstractResource container, @Nullable String role) {
		ModelElement element = ManagementTraceFactoryImpl.eINSTANCE.createModelElement();
		if (container instanceof IResource) 			
			element.setResource(container.getName());
		if (elementId != null && !elementId.isEmpty()) 
			element.setElementId(elementId);
		if (role != null && !role.isEmpty()) 
			element.setRole(role);
		return element;
	}
	
	private ModelElement modelElementProperty(String elementId, @Nullable IAbstractResource container, String name, @Nullable String role) {
		ModelElementProperty element = ManagementTraceFactoryImpl.eINSTANCE.createModelElementProperty();
		if (container instanceof IResource) 			
			element.setResource(container.getName());
		if (elementId != null && !elementId.isEmpty()) 
			element.setElementId(elementId);
		if (role != null && !role.isEmpty()) 
			element.setRole(role);
		element.setProperty(name);
		return element;
	}
	
	private FileElement fileElement(@Nullable String file, @Nullable Integer offset, @Nullable Integer length) {
		FileElement element = ManagementTraceFactoryImpl.eINSTANCE.createFileElement();
		if (file != null && !file.isEmpty()) {	
			IResource res = DomFactory.eINSTANCE.createResource();
			res.setDeclared(false);
			res.setName(file);
			element.setResource(res.getName());
		}
		Region region = ManagementTraceFactoryImpl.eINSTANCE.createRegion();
		if (offset != null) 
			region.setOffset(offset);
		if (length != null) 
			region.setLength(length);
		
		if (region != null) {			
			element.setRegion(region);
		}
		return element;
	}
	
	public ManagementTraceBuilder addSourceModelElement(String elementId, @Nullable IAbstractResource container, @Nullable String role) {
		ModelElement source = modelElement(elementId, EcoreUtil.copy(container), role);
		sources.add(source);
		return this;
	}
	
	public ManagementTraceBuilder addTargetModelElement(String elementId, @Nullable IAbstractResource container, @Nullable String role) {
		ModelElement target = modelElement(elementId, EcoreUtil.copy(container), role);
		targets.add(target);
		return this;
	}
	
	public ManagementTraceBuilder addSourceModelElementProperty(String elementId, @Nullable IAbstractResource container, String name, @Nullable String role) {
		ModelElement source = modelElementProperty(elementId, EcoreUtil.copy(container), name, role);
		sources.add(source);
		return this;
	}
	
	public ManagementTraceBuilder addTargetModelElementProperty(String elementId, @Nullable IAbstractResource container, String name, @Nullable String role) {
		ModelElement target = modelElementProperty(elementId, EcoreUtil.copy(container), name,  role);
		targets.add(target);
		return this;
	}
	
	public ManagementTraceBuilder addSourceElement(@Nullable String file, @Nullable Integer offset, @Nullable Integer length) {
		FileElement source = fileElement(file, offset, length);
		sources.add(source);
		return this;
	}
	
	public ManagementTraceBuilder addTargetElement(@Nullable String file, @Nullable Integer offset, @Nullable Integer length) {
		FileElement target = fileElement(file, offset, length);
		targets.add(target);
		return this;
	}
	
	public ManagementTraceBuilder addProperty(String key, Object value){
		if (key!= null && !key.isEmpty() && value != null) {			
			Property prop = ManagementTraceFactoryImpl.eINSTANCE.createProperty();
			prop.setKey(key);
			prop.setValue(value);
			properties.add(prop);
		}
		return this;
	}

}
