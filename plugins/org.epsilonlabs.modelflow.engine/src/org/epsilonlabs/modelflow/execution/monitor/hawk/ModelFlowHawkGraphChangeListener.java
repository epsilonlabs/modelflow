/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.monitor.hawk;

import org.eclipse.hawk.core.IConsole;
import org.eclipse.hawk.core.IModelIndexer;
import org.eclipse.hawk.core.VcsCommitItem;
import org.eclipse.hawk.core.graph.IGraphChangeListener;
import org.eclipse.hawk.core.graph.IGraphNode;
import org.eclipse.hawk.core.model.IHawkClass;
import org.eclipse.hawk.core.model.IHawkObject;
import org.eclipse.hawk.core.model.IHawkPackage;
import org.eclipse.hawk.core.util.DefaultConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFlowHawkGraphChangeListener implements IGraphChangeListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModelFlowHawkGraphChangeListener.class);

	protected IConsole console;

	public ModelFlowHawkGraphChangeListener(){
		this.console = new DefaultConsole();
	}
	
	public ModelFlowHawkGraphChangeListener(IConsole console){
		this.console = console;
	}
	
	/** MODEL ELEMENT */
	
	@Override
	public void modelElementRemoval(VcsCommitItem s, IGraphNode elementNode, boolean isTransient) {
		LOG.debug("Removed model element node with id {} from commit {}",
				elementNode.getId(), s.getCommit().getRevision());
	}

	@Override
	public void modelElementAddition(VcsCommitItem s, IHawkObject element, IGraphNode elementNode,
			boolean isTransient) {
		LOG.debug("Added element of type {} with signature {}",
				element.getType(), element.signature());
	}
	
	@Override
	public void modelElementAttributeUpdate(VcsCommitItem s, IHawkObject eObject, String attrName, Object oldValue,
			Object newValue, IGraphNode elementNode, boolean isTransient) {
		LOG.debug("Updated attribute {} from {} element with signature {}",
				attrName, eObject.getType(), eObject.signature());
	}
	
	@Override
	public void modelElementAttributeRemoval(VcsCommitItem s, IHawkObject eObject, String attrName,
			IGraphNode elementNode, boolean isTransient) {
		LOG.debug("Removed attribute {} from {} element with signature {}",
				attrName, eObject.getType(), eObject.signature());
	}
	
	
	/** METAMODEL */
	
	@Override
	public void metamodelAddition(IHawkPackage pkg, IGraphNode pkgNode) {
		LOG.debug("Added {} metamodel", pkg.getName());
	}
	
	/** METAMODEL CLASS */
	
	@Override
	public void classAddition(IHawkClass cls, IGraphNode clsNode) {
		LOG.debug("Added {} class from {}", cls.getName(), cls.getPackageNSURI());
	}
	
	/** FILES */
	
	@Override
	public void fileRemoval(VcsCommitItem s, IGraphNode fileNode) {
		LOG.debug("Removed {} file", s.getPath());
	}
	
	@Override
	public void fileAddition(VcsCommitItem s, IGraphNode fileNode) {
		LOG.debug("Added {} file", s.getPath());
	}
	
	/** REFERENCE */
	
	@Override
	public void referenceRemoval(VcsCommitItem s, IGraphNode source, IGraphNode destination, String edgelabel,
			boolean isTransient) {
		LOG.debug("Reference {} removed between nodes {} -- {} ", 
				edgelabel, source.getId(), destination.getId());
	}
	
	@Override
	public void referenceAddition(VcsCommitItem s, IGraphNode source, IGraphNode destination, String edgelabel,
			boolean isTransient) {
		LOG.debug("Reference {} added between nodes {} -- {} ", 
				edgelabel, source.getId(), destination.getId());
	}
	
	/** OTHER */
	
	@Override
	public void setModelIndexer(IModelIndexer m) {
		LOG.debug("Model indexer listener for the '{}' index set", m.getName());
	}
	
	@Override
	public void synchroniseStart() {
		LOG.debug("Starting Synchronisation");
	}
	
	@Override
	public void synchroniseEnd() {
		LOG.debug("Synchronisation Ended");		
	}
	
	@Override
	public void changeSuccess() {
		LOG.debug("Change success!");
	}
	
	@Override
	public void changeStart() {
		LOG.debug("Starting change");
	}
	
	@Override
	public void changeFailure() {
		LOG.error("Change failure");
	}

	@Override
	public String getName() {
		return "ModelFlow";
	}
}
