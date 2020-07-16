/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.monitor.hawk;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.hawk.core.IVcsManager;
import org.eclipse.hawk.core.VcsCommitItem;
import org.eclipse.hawk.core.graph.IGraphDatabase;
import org.eclipse.hawk.core.runtime.BaseModelIndexer;
import org.eclipse.hawk.core.security.FileBasedCredentialsStore;
import org.eclipse.hawk.core.util.DefaultConsole;
import org.eclipse.hawk.epsilon.emc.EOLQueryEngine;
import org.eclipse.hawk.graph.updater.GraphMetaModelUpdater;
import org.eclipse.hawk.graph.updater.GraphModelUpdater;
import org.eclipse.hawk.localfolder.LocalFile;
import org.eclipse.hawk.localfolder.LocalFolder;
import org.eclipse.hawk.orientdb.OrientDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFlowHawkIndexer extends BaseModelIndexer implements IModelFlowModelIndexer {
	private static final Logger LOG = LoggerFactory.getLogger(ModelFlowHawkIndexer.class);

	protected static final String UNKNOWN_REMOTE_REVISION = "-4";
	protected static final String UNKNOWN_LOCAL_REVISION = "-3";
	protected final Map<String, String> currLocalTopRevisions = new HashMap<>();
	protected final Map<String, String> currReposTopRevisions = new HashMap<>();

	protected boolean hasBeenSyncd = false;
	protected IGraphDatabase db;

	public ModelFlowHawkIndexer(String name) {
		super(null, null, new FileBasedCredentialsStore(new File("keystore"), "admin".toCharArray()), null);
		
		this.name = name;
		this.parentfolder = Paths.get(System.getProperty("user.dir"),"hawk").toFile();
		this.console = new DefaultConsole();

		db = new OrientDatabase();
		setDB(db, true);
	}
	
	public void init() throws Exception {
		this.setMetaModelUpdater(new GraphMetaModelUpdater());
		this.addModelUpdater(new GraphModelUpdater());
		this.addQueryEngine(new EOLQueryEngine());
		
		File dbFolder = Paths.get(System.getProperty("user.dir") , "hawk", "db").toFile();		
		db.run(dbFolder, console);
		LOG.debug("{}", getCompositeGraphChangeListener().size());
		/*getCompositeGraphChangeListener().forEach(l -> {
			LOG.debug("{} - {} - {}" , l.getClass(), l.getCategory(), l.getHumanReadableName());
		});*/
		//getCompositeGraphChangeListener().clear();
		addGraphChangeListener(new ModelFlowHawkGraphChangeListener(console));
		//addStateListener(new ModelFlowHawkStateListener(console));
		init(0, 0); // Manual sync
	}
	
	public boolean addFile(File file){
		try {
			IVcsManager vcs = null;
			if (file.isDirectory()){				
				vcs = new LocalFolder();
			} else if (file.isFile()) {
				vcs = new LocalFile();
			}
			vcs.init(file.getAbsolutePath(), this);
			vcs.run();
			this.addVCSManager(vcs, true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	protected boolean synchronise(IVcsManager vcsManager) throws Exception {
		boolean success = true;

		String currentRevision = getCurrentRevision(vcsManager, success);
	
		if (!currentRevision.equals(currLocalTopRevisions.get(vcsManager.getLocation()))) {
			final Collection<VcsCommitItem> files = vcsManager.getDelta(currLocalTopRevisions.get(vcsManager.getLocation()));
			latestUpdateFoundChanges = true;
			boolean updatersOK = synchroniseFiles(currentRevision, vcsManager, files);
	
			if (updatersOK) {
				currLocalTopRevisions.put(vcsManager.getLocation(),
						currReposTopRevisions.get(vcsManager.getLocation()));
			} else {
				success = false;
				currLocalTopRevisions.put(vcsManager.getLocation(), UNKNOWN_LOCAL_REVISION);
			}
		}
	
		return success;
	}
	
	protected String getCurrentRevision(IVcsManager vcsManager, boolean success){
		String currentRevision = currReposTopRevisions.get(vcsManager.getLocation());
		try {
			/*
			 * Try to fetch the current revision from the VCS, if not, keep the latest seen
			 * revision.
			 */
			currentRevision = vcsManager.getCurrentRevision();
			currReposTopRevisions.put(vcsManager.getLocation(), currentRevision);
		} catch (Exception e) {
			console.printerrln(e);
			success = false;
		}
		return currentRevision;		
	}

	@Override
	public void addVCSManager(IVcsManager vcs, boolean persist) {
		currLocalTopRevisions.put(vcs.getLocation(), UNKNOWN_LOCAL_REVISION);
		currReposTopRevisions.put(vcs.getLocation(), UNKNOWN_REMOTE_REVISION);

		super.addVCSManager(vcs, persist);
	}

	@Override
	public void removeVCSManager(IVcsManager vcs) throws Exception {
		currLocalTopRevisions.remove(vcs.getLocation());
		currReposTopRevisions.remove(vcs.getLocation());
		super.removeVCSManager(vcs);
	}

	@Override
	protected void resetRepository(String repoURL) {
		System.err.println("reseting local top revision of repository: " + repoURL
				+ "\n(as elements in it were removed or new metamodels were added to Hawk)");
		currLocalTopRevisions.put(repoURL, UNKNOWN_LOCAL_REVISION);
	}

}
