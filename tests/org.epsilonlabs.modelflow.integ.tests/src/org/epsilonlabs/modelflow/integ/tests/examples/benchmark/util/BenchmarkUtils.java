/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Betty Sanchez
 *
 */
public class BenchmarkUtils {

	/**
	 * Appends the results in the output file
	 * @param file
	 * @param record
	 * @throws IOException
	 */
	public static void writeResults(File file, Object... record) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		CSVFormat csvFormat = CSVFormat.EXCEL;
		try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
			csvPrinter.printRecord(record);
		}
	}

	/** 
	 * Computes the results file location with timestamp
	 * @param resultsFile
	 * @return
	 */
	public static File getResultsFile(String prefix, Date date) {
		String pattern = "yyyy-MM-dd-hh:mm:ss";
		String dateOfExperiment = new SimpleDateFormat(pattern).format(date);
		return Paths.get(System.getProperty("user.dir")).resolve("results")
				.resolve(String.format("%s-%s.csv", prefix, dateOfExperiment)).toFile();
	}

	/** 
	 * Intended to be called once per test class in a BeforeClass or BeforeAll annotated method
	 * @param resultsFile
	 * @return
	 */
	public static final void prepareResultFile(File file, String... headers) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(headers);
		try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
			// Do nothing
		}
	}

}
