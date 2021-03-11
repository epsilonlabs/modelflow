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
public class DefaultMemoryTracker implements IMemoryTracker{

	@Override
	public void run() {
		// Do nothing
	}

	@Override
	public void setup() throws IOException {
		// Do nothing
	}

	@Override
	public void setLogFile(File logFile) {
		// Do nothing
	}

	@Override
	public File getLogFile() {
		return null;
	}

}
