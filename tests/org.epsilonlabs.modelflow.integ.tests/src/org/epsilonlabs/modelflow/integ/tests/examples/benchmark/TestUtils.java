/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

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

/**
 * @author Betty Sanchez
 *
 */
public class TestUtils {
	
	protected static Path getProject(String projectName) {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "examples", projectName).normalize();
	}

	public static Path getOutputPath(String projectName) {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "testOutput", projectName).normalize();
	}

	public static void copyExampleProjectToTempLocation(String projectName) {
		Path sourceProject = getProject(projectName);
		Path destinationProject = getOutputPath(projectName);
		FileFilter filter = getExampleFileFilter();
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
	public static File getBuildScript(String projectName, String scriptFile) {
		return getOutputPath(projectName).resolve(scriptFile).toFile();
	}

	private static FileFilter getExampleFileFilter() {
		return pathname -> {
			String absolutePath = pathname.getAbsolutePath();
			boolean binOrTarget = absolutePath.contains("/bin/") || absolutePath.contains("/target/");
			return !pathname.isHidden() && !binOrTarget;
		};
	}

	public static void clearExecutionFiles(String projectName) {
		try {
			FileUtils.deleteDirectory(getOutputPath(projectName).toFile());
		} catch (IOException e1) {
			fail("Unable to clear files");
		}
	}
}
