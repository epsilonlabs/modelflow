package org.epsilonlabs.modelflow.tests.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ResourceLocator {

	public static String locate(String location){
		if (location.startsWith("/")){
			return location;
		} else {
			return System.getProperty("user.dir") + "/resources/"+ location;
		}
	}
	
	public static String locateInTestDir(String location){
		return System.getProperty("user.dir") + "/target/modelflow/test/"+ location;
	}
	
	public static String getTestDir(){
		return System.getProperty("user.dir") + "/target/modelflow/test/";
	}

	public static String locateAndCopyToTestDir(String location) {
		String l = location;
		if (location.startsWith("/")){
			l = location.split("/resources/")[1];
		}
		String destination = locateInTestDir(l);
		File target = new File(destination);
		if (target.exists()) {
			target.delete();
		}

		String fullLocation = locate(location);
		File source = new File(fullLocation);
		
		if (source.exists()) {
			try {				
				FileUtils.copyFile(source, target);
			} catch (Exception e) {
				String msg = "Unable to copy %s to destination %s";
				String fullMsg = String.format(msg, source.getAbsoluteFile(), target.getAbsoluteFile());
				throw new RuntimeException(fullMsg);
			}
		}
		return destination;
	}

	public static void copyDir(File source, File target) {
		try {
			if (target.exists()) {
				FileUtils.deleteDirectory(target);
				target.mkdir();
			}
			FileUtils.copyDirectory(source, target);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
