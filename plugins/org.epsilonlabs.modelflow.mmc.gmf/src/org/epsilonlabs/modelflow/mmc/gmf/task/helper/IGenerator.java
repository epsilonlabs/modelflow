/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.gmf.task.helper;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.epsilonlabs.modelflow.mmc.gmf.task.trace.GmfDiagramTrace;

/**
 * @author bea
 *
 */
public interface IGenerator extends Runnable {

	List<GmfDiagramTrace> getTraces();

	Set<File> getFiles();

	/**
	 * Optionally, specify progressMonitor to use. Should be called prior to
	 * {@link #run()}
	 * 
	 * @param progress
	 */
	void setProgressMonitor(IProgressMonitor progress);

	/**
	 * Provides information about success/failures during {@link #run()}
	 * 
	 * @return state of the generator run, or CANCEL if generator was not yet run.
	 */
	IStatus getRunStatus();

	void run(IProgressMonitor progress) throws InterruptedException;

	void clean();

}
