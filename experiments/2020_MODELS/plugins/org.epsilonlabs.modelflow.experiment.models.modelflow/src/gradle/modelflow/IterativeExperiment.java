package gradle.modelflow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

public abstract class IterativeExperiment {

	protected static final int WARMUP_ITERATIONS = 5;
	protected static final int MAX_ITERATIONS = 20;

	protected static File resultsFile;
	protected Long duration, egx, evl, etl;

	@Rule
	public TestName name = new TestName();
	
	@Parameters(name = "Test_{index}: ScenarioId={0},Iteration={1}")
	public static Iterable<Object[]> parameters() {
		ArrayList<Object[]> list = new ArrayList<>();
		for (int scenarioId=1;scenarioId <= Scenario.values().length; scenarioId++) {
			for (int iteration= - WARMUP_ITERATIONS;iteration < MAX_ITERATIONS; iteration++) {
				list.add(new Object[] {scenarioId, iteration});
			}
		}
		return list;
	}
	
	@Parameter(0)
	public Integer scenarioId;
	
	@Parameter(1)
	public Integer iteration;
	
	public static File getResultsFile() {
		if (resultsFile == null) {
			String pattern = "yyyy-MM-dd-hh:mm:ss";
			String dateOfExperiment = new SimpleDateFormat(pattern).format(new Date());
			resultsFile = Paths.get(System.getProperty("user.dir")).resolve("results").resolve(String.format("results-%s.csv", dateOfExperiment)).toFile();
		}
		return resultsFile;
	}

	
	abstract protected Path getTestProjectRoot();

	abstract protected String getApproachName();

	protected static final void prepareResultFile(File file, String...headers) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(headers);
		try(CSVPrinter csvPrinter = new CSVPrinter(fileWriter,csvFormat)) {}
	}
	
	protected static final String[] getHeaders() {
		return Arrays.asList("approach", "scenarioId", "iteration", "duration(nanosec)", "evl", "etl", "egx").toArray(new String[0]);
	}
	
	protected Long lapse;
	
	protected void startTimer() {
		duration = 0l;
		continueTimer();
	}
	
	protected void stopTimer() {
		duration = duration + (lapse > 0l ? (System.nanoTime() - lapse) : 0l);
		lapse = 0l;
	}
	
	protected void continueTimer(){
		lapse = System.nanoTime();
	}
	
	abstract protected void beforeTestExecution() throws Exception;
	
	@BeforeClass
	public static void setupClass() throws Exception{
		prepareResultFile(getResultsFile(), getHeaders());
	}
	
	
	@Before
	public void setup() throws Exception{
		System.out.println(name.getMethodName());
		
		
		/** Prepare */
		beforeTestExecution();

		
	}
	
	protected void writeResults(File file, Object... record) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		CSVFormat csvFormat = CSVFormat.EXCEL;
		try(CSVPrinter csvPrinter = new CSVPrinter(fileWriter,csvFormat)) {
			csvPrinter.printRecord(record);
		}
	}
	
	abstract protected void afterTestExecution();

	@After
	public void postExecution() throws IOException {
		/** Cleanup */
		afterTestExecution();
		
		/** Results */
		if (iteration >= 0) {			
			writeResults(resultsFile, getApproachName(), scenarioId, iteration, duration, evl, etl, egx);
		}
	}
	
	abstract protected void execute();

	@Test
	public void runIteration() {
		/** First Execution */
		if (scenarioId == 0) {
			startTimer();			
		}
		execute();
		
		if (scenarioId == 0) {
			stopTimer();			
		}
		/** Invoke Scenario Modifications */
		Scenario.get(scenarioId).getModifications(getTestProjectRoot()).run();
		
		/** Second Execution */
		if (scenarioId != 0) {
			startTimer();			
		}
		execute();
		if (scenarioId != 0) {
			stopTimer();			
		}
	}
	
}
