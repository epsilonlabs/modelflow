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
	
	public static String locateInTarget(String location){
		return System.getProperty("user.dir") + "/target/"+ location;
	}

	public static String locateAndCopy(String location) {
		String l = location;
		if (location.startsWith("/")){
			l = location.split("/resources/")[1];
		}
		String destination = System.getProperty("user.dir") + "/target/" +	l;
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
			}
			FileUtils.copyDirectory(source, target);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
