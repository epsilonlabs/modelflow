package gradle.modelflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProgressEvent;
import org.gradle.tooling.ProgressListener;
import org.gradle.tooling.ProjectConnection;

public class GradleInvoker {	
	
	protected static final String GRADLE_TASK = "workflow";

	protected GradleConnector connector;
	protected BuildLauncher build;
	protected ProjectConnection projectConnection;
	protected String projectName, gradleInstallationDir;
	protected String[] tasks;
	protected Path rootPath, outPath;
	protected Boolean production, started;
	protected ArrayList<ProgressEvent> events = new ArrayList<ProgressEvent>();
	private Integer scenario;

	public GradleInvoker(String gradleInstallationDir, String projectName, Boolean production, String... tasks) {
		this.gradleInstallationDir = gradleInstallationDir;
		this.projectName = projectName;
		this.tasks = tasks;
		this.production = production;
		this.started = false;
	}

	public GradleInvoker(String gradleInstallationDir, String projectName, String... tasks) {
		this(gradleInstallationDir, projectName, true, tasks);
	}

	public void prepareForExecution(IterativeExperiment exp) throws IOException {
		rootPath = Paths.get(System.getProperty("user.dir"), "..").normalize();
		outPath = rootPath.resolve("out").resolve(projectName);

		connector = GradleConnector.newConnector();
		connector.useInstallation(new File(gradleInstallationDir));
		connector.forProjectDirectory(getTempLocation());

		projectConnection = connector.connect();

		build = projectConnection.newBuild();
		build.forTasks(tasks);
		
		build.addProgressListener(new ProgressListener() {
			@Override
			public void statusChanged(ProgressEvent arg0) {
				if ("Run tasks".equalsIgnoreCase(arg0.getDescription())) {
					if (scenario != null) {
						exp.startTimer();
						scenario = null;
						started = true;
						if (!production) {
							System.out.println(arg0.getDescription());
							System.out.println("Started");
						}
					}
				}
				
				if (started && "Run build".equalsIgnoreCase(arg0.getDescription())) {
					exp.stopTimer();
					started = false;
				}
			}
		});
		if (!production) {
			build.setStandardError(System.err);
			build.setStandardOutput(System.out);
			build.setStandardInput(System.in);
		}
		
	}
	
	public void setScenario(Integer scenario){
		this.scenario = scenario;
	}

	public void executeTask() {
		build.run();
	}

	public List<ProgressEvent> getEvents() {
		return events;
	}

	public Path getOutPath() {
		return outPath;
	}

	protected File getTempLocation() throws IOException {
		if (outPath.toFile().exists()) {
			FileUtils.deleteDirectory(outPath.toFile());
		}
		outPath.toFile().mkdirs();

		Path baseProjectPath = rootPath.resolve(projectName);
		FileUtils.copyDirectory(baseProjectPath.toFile(), outPath.toFile());

		// Ensure there is no cache from previous executions
		if (outPath.resolve(".gradle").toFile().exists()) {
			FileUtils.deleteDirectory(outPath.resolve(".gradle").toFile());
		}

		return outPath.toFile();
	}

	public void postExecution() throws IOException {
		projectConnection.close();
		events.clear();
		FileUtils.deleteDirectory(outPath.toFile());
	}

	public static void main(String[] args) throws IOException {

		Properties properties = new Properties();
		properties.load(new FileInputStream("experiment.properties"));
	    String installation = (String) properties.getOrDefault("gradle", "/usr/local/opt/gradle/libexec");
		GradleInvoker invoker = new GradleInvoker(installation, "gradle.workflow", GRADLE_TASK);
		invoker.prepareForExecution(null);
		invoker.executeTask();
		invoker.postExecution();
	}
}
