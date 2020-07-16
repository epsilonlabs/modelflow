package org.epsilonlabs.modelflow.mmc.gmf.task.monitor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagramGeneratorMonitor extends NullProgressMonitor implements IProgressMonitor {

	private final Logger LOG;
	
	public DiagramGeneratorMonitor() {
		LOG = LoggerFactory.getLogger(DiagramGeneratorMonitor.class);
	}
	protected Integer totalWork;
	protected Double worked = 0d;
	
	@Override
	public void beginTask(String name, int totalWork) {
		this.totalWork = totalWork;
		LOG.debug("== {} ==", name);
	}

	@Override
	public void done() {
		LOG.debug("Done 100%");		
	}

	@Override
	public void setCanceled(boolean value) {
		LOG.debug("Canceling");
		super.isCanceled();
	}

	@Override
	public void setTaskName(String name) {
		double progress = (worked/totalWork)*100;
		LOG.debug("{} ({}%)", name, Math.round(progress));
	}

	@Override
	public void internalWorked(double work) {
		this.worked += work;
		super.internalWorked(work);
	}
	
	@Override
	public void subTask(String name) {
		if (name != null && !name.isEmpty()) {			
			LOG.debug(">>{}", name);
		}
	}	
	@Override
	public void worked(int work) {
		this.worked += work;
		super.worked(work);
	}

}
