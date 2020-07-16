/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.monitor.hawk;

import java.io.Closeable;
import java.io.File;

import org.eclipse.hawk.core.IModelIndexer.ShutdownRequestType;
import org.eclipse.hawk.core.graph.IGraphDatabase;
import org.eclipse.hawk.core.security.FileBasedCredentialsStore;
import org.eclipse.hawk.core.util.DefaultConsole;
import org.eclipse.hawk.emf.metamodel.EMFMetaModelResourceFactory;
import org.eclipse.hawk.emf.model.EMFModelResourceFactory;
import org.eclipse.hawk.epsilon.emc.EOLQueryEngine;
import org.eclipse.hawk.graph.updater.GraphMetaModelUpdater;
import org.eclipse.hawk.graph.updater.GraphModelUpdater;
import org.eclipse.hawk.localfolder.LocalFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFlowHawkIndex implements Closeable {
	private static final Logger LOG = LoggerFactory.getLogger(ModelFlowHawkIndex.class);

	protected File indexFolder;
	protected File dbFolder;
	protected FileBasedCredentialsStore credStore;
	protected DefaultConsole console;
	protected IModelFlowModelIndexer indexer;
	protected IGraphDatabase db;
	
	public ModelFlowHawkIndex() {
		/*this.indexFolder = Files.createTempDir();
		this.dbFolder = Files.createTempDir();
		this.console = new DefaultConsole();

		char[] admin = "admin".toCharArray();
		File keystore = new File("keystore");
		this.credStore = new FileBasedCredentialsStore(keystore, admin);*/
		
		this.indexer = new ModelFlowHawkIndexer("mflow");
		
		/** DRIVERS */
		indexer.addMetaModelResourceFactory(new EMFMetaModelResourceFactory());
		indexer.addModelResourceFactory(new EMFModelResourceFactory());
		indexer.setMetaModelUpdater(new GraphMetaModelUpdater());
		indexer.addModelUpdater(new GraphModelUpdater());
		indexer.addQueryEngine(new EOLQueryEngine());
		
		/*db = new OrientDatabase();
		db.run(dbFolder, console);
		indexer.setDB(db, true);*/
		
		try {
			final LocalFolder vcs = new LocalFolder();
			vcs.init(System.getProperty("user.dir") + "/resources/model", indexer);
			vcs.run();
			indexer.addVCSManager(vcs, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** LISTENERS */
		//indexer.addGraphChangeListener(new ModelFlowHawkGraphChangeListener(console));
		//indexer.addStateListener(new ModelFlowHawkStateListener(console));
	}
	
	public void init(){
		try {
			//this.indexer.init(0, 5);
			this.indexer.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sync(){
		try {
			this.indexer.requestImmediateSync();
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	

	@Override
	public void close() {
		try {
			db.delete();
			LOG.info("DB deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.indexer.shutdown(ShutdownRequestType.ALWAYS);
			LOG.info("Index shutdown");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/*public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ModelFlowHawkIndex indexer = new ModelFlowHawkIndex();
		
		indexer.indexer.registerMetamodels(new File(System.getProperty("project.dir")" +  "../../tests/org.epsilonlabs.modelflow.mmc.epsilon.tests/resources/metamodel/ecore/Entity.ecore"), 
				new File(System.getProperty("project.dir")" +  "../../tests/org.epsilonlabs.modelflow.mmc.epsilon.tests/resources/metamodel/ecore/DomainVocabulary.ecore"));
			
		indexer.init();
		
		indexer.sync();
	}*/
	
}

