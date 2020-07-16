/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.task;

import java.io.File;

import org.apache.tools.ant.ComponentHelper;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntTask {

	public static void main(String[] args) {
		
		File buildFile = new File("build.xml");
		Project p = new Project();
		
		
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
		
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		p.addBuildListener(consoleLogger);
		
		p.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		p.addReference("ant.projectHelper", helper);
		helper.parse(p, buildFile);
		ComponentHelper componentHelper = ComponentHelper.getComponentHelper(p);
		componentHelper.getTaskDefinitions().forEach((k,v) -> {
			if (!v.isAnnotationPresent(Deprecated.class)) {				
				System.out.println(k);
				System.out.println(v.getCanonicalName());
			}
		});
		p.executeTarget(p.getDefaultTarget());
	}
}
