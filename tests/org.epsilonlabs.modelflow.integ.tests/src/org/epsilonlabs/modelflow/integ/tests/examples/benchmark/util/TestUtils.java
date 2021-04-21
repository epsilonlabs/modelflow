/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util;

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
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;

/**
 * @author Betty Sanchez
 *
 */
public class TestUtils {
	
	protected static Path getExampleProject(String projectName) {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "examples", projectName).normalize();
	}
	
	protected static Path getExampleProject(Path navigateFromExamplesFolder, String projectName) {
		return Paths.get(System.getProperty("user.dir"), "..", "..", "examples", navigateFromExamplesFolder.toString(), projectName).normalize();
	}

	public static Path getOutputPath(String projectName) {
		return Paths.get(ResourceLocator.locateInTestDir(projectName)).normalize();
	}
	
	public static Path copyExampleProjectToTempLocation(String projectName) {
		Path sourceProject = getExampleProject(projectName);
		return copyExampleProjectToTempLocation(sourceProject, projectName);
	}

	public static Path copyExampleProjectToTempLocation(Path sourceProject, String projectName) {
		Path destinationProject = getOutputPath(projectName);
		if (destinationProject.toFile().exists()) {
			try {
				FileUtils.cleanDirectory(destinationProject.toFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
		return destinationProject;
	}

	/**
	 * @return the buildScript as file
	 */
	public static File getFileInPath(Path outputPath, String file) {
		return outputPath.resolve(file).toFile();
	}

	private static FileFilter getExampleFileFilter() {
		return pathname -> {
			String absolutePath = pathname.getAbsolutePath();
			boolean binOrTarget = absolutePath.contains("/bin/") || absolutePath.contains("/target/");
			return !binOrTarget; //!pathname.isHidden()
		};
	}

	public static void clearExecutionFiles(Path output) {
		if (output.toFile().exists()) {
			try {
				FileUtils.deleteDirectory(output.toFile());
			} catch (IOException e1) {
				e1.printStackTrace();
				fail("Unable to clear files");
			}
		}
	}
}
