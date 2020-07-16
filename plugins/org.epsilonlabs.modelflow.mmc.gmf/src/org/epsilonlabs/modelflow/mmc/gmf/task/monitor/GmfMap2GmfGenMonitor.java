package org.epsilonlabs.modelflow.mmc.gmf.task.monitor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmfMap2GmfGenMonitor extends NullProgressMonitor implements IProgressMonitor {

	private final Logger LOG;
	
	public GmfMap2GmfGenMonitor() {
		LOG = LoggerFactory.getLogger(GmfMap2GmfGenMonitor.class);
	}
	@Override
	public void beginTask(String name, int totalWork) {
		LOG.debug("=={} ({})", name, totalWork);
	}

	@Override
	public void done() {
		LOG.debug("Done");		
	}

	@Override
	public void setCanceled(boolean value) {
		LOG.debug("Canceling");
		super.isCanceled();
	}

	@Override
	public void setTaskName(String name) {
		LOG.debug("Naming as {}", name);
	}

	@Override
	public void subTask(String name) {
		LOG.debug("--{}", name);
	}

}
