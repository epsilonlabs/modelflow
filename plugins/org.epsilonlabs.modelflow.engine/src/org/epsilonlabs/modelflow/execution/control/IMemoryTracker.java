/**
 * 
 */
package org.epsilonlabs.modelflow.execution.control;

import java.io.File;
import java.io.IOException;

/**
 * @author bea
 *
 */
public interface IMemoryTracker extends Runnable {

	/**
	 * Setup.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void setup() throws IOException;

	/**
	 * Sets the log file.
	 *
	 * @param logFile the new log file
	 */
	void setLogFile(File logFile);

	/**
	 * Gets the log file.
	 *
	 * @return the log file
	 */
	File getLogFile();

}
