/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * The Class MemoryTracker.
 *
 * @author Betty Sanchez
 */
public class MemoryTracker extends Thread {

	/** The log file. */
	private File logFile;

	/**
	 * Gets the log file.
	 *
	 * @return the log file
	 */
	public File getLogFile() {
		return logFile;
	}

	/**
	 * Sets the log file.
	 *
	 * @param logFile the new log file
	 */
	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}
	
	/**
	 * Setup.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void setup() throws IOException{
		logFile = Files.createTempFile("track", ".csv").toFile();
		write("time", "memory");
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		long time = System.nanoTime();
		long freeMemory = Runtime.getRuntime().freeMemory();
		write(time, freeMemory);
	}

	/**
	 * Write.
	 *
	 * @param records the records
	 */
	private void write(Object... records) {
		try (FileWriter fileWriter = new FileWriter(logFile, true)) {
			CSVFormat csvFormat = CSVFormat.EXCEL;
			try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
				csvPrinter.printRecord(records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
