/**
 * 
 */
package org.epsilonlabs.modelflow.tests.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.epsilonlabs.modelflow.IModelFlowConfiguration;

/**
 * @author Betty Sanchez
 *
 */
public abstract class ExampleProjectTest extends IncrementalTest {

	protected abstract String getBuildFileName();

	protected abstract String getProjectName();

	@Override
	protected void setupModule() {
		File endToEndTraceFile = getOutputPath().resolve("endToEndTrace.mftrace").toFile();
		File executionTraceFile = getOutputPath().resolve("executionTrace.mfexec").toFile();

		super.setupModule();

		module.getContext().setProfilingEnabled(true);
		IModelFlowConfiguration configuration = module.getConfiguration();
		configuration.setSaveEndToEndTraces(true);
		configuration.setEndToEndTraceLocation(endToEndTraceFile);
		configuration.setExecutionTraceLocation(executionTraceFile);
	}

	protected Path getProject() {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "examples", getProjectName()).normalize();
	}

	protected Path getOutputPath() {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "testOutput", getProjectName()).normalize();
	}

	protected void copyToTempLocation() {
		Path sourceProject = getProject();
		FileFilter filter = getFileFilter();
		Path destinationProject = getOutputPath();
		try (Stream<Path> stream = Files.walk(sourceProject)) {
			stream.filter(f -> filter.accept(f.toFile())).forEach(source -> {
				Path dest = destinationProject.resolve(sourceProject.relativize(source));
				File file = dest.toFile();
				if (file.isDirectory() && file.exists()) {
					try {
						FileUtils.cleanDirectory(file);
					} catch (IOException e) {
						fail(e.getMessage());
					}
				} else {					
					file.mkdirs();
				}
				try {
					Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					fail("Unable to copy files to test directory");
				}
			});
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * @return the buildScript as file
	 */
	public File getBuildScript() {
		return getOutputPath().resolve(getBuildFileName()).toFile();
	}

	protected FileFilter getFileFilter() {
		return pathname -> {
			String absolutePath = pathname.getAbsolutePath();
			boolean binOrTarget = absolutePath.contains("/bin/") || absolutePath.contains("/target/");
			return !pathname.isHidden() && !binOrTarget;
		};
	}

	protected void clearExecutionFiles() {
		try {
			FileUtils.deleteDirectory(getOutputPath().toFile());
		} catch (IOException e1) {
			fail("Unable to clear files");
		}
	}

	@Override
	protected void setupSource() {
		try {
			assertNotNull("Code is null", getBuildFileName());
			module.parse(getBuildScript());
		} catch (Exception e) {
			fail("Parse failure");
		}
	}

	/**
	 * Instead of reusing the module, we create a new one.
	 */
	@Override
	public void reExecute() throws Exception {
		execute();
	}

	@Override
	public void preprocess() {
		super.preprocess();
		copyToTempLocation();
	}

	@Override
	public void cleanup() {
		clearExecutionFiles();
	}

}
