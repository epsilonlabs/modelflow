/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.integ.incremental;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.dom.WorkflowProgramBuilder;
import org.epsilonlabs.modelflow.execution.graph.node.TaskState;
import org.epsilonlabs.modelflow.integ.tests.common.workflow.ExampleWorkflows;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.mmc.emf.plugin.EMFPlugin;
import org.epsilonlabs.modelflow.mmc.epsilon.plugin.EpsilonPlugin;
import org.epsilonlabs.modelflow.mmc.gmf.plugin.GMFPlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.epsilonlabs.modelflow.tests.common.IncrementalTest;
import org.epsilonlabs.modelflow.tests.common.validator.AllTaskStateValidator;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EgxIncrementalTest extends IncrementalTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	/** Validation/Modification Helpers */

	protected void addComment(String file) {
		String fileName = DIR + file;
		File file2 = new File(fileName);
		assertTrue("File not found", file2.exists());
		String hash1 = Hasher.computeHashForFile(file2);
		try (FileWriter fw = new FileWriter(fileName, true)) {
			BufferedWriter out = new BufferedWriter(fw);
			out.newLine();
			out.write("//explicit modification");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		String hash2 = Hasher.computeHashForFile(new File(fileName));
		assertNotEquals("hashes are the same", hash1, hash2);
	}
	
	protected void protectedRegionChange(Boolean inside, String fileRelativePath) {
		String filePath = DIR + fileRelativePath;
		File file = new File(filePath);
		assertTrue("File not found", file.exists());
		String hash1 = Hasher.computeHashForFile(file);
	
		BufferedReader reader;
		StringBuffer inputBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			boolean first = true;
			while ((line = reader.readLine()) != null) {
				if (inside && line.trim() != null && line.trim().equals("<style type=\"text/css\">")) {
					line = line.replace("<style type=\"text/css\">", "<style type=\"text/css\">h1 { font-size: 12px; }");
				} else if (!inside && line.trim() != null && line.trim().equals("<h1>Get up</h1>")) {
					line = line.replace("Get up", "Modified Title!");
				}
				inputBuffer.append((!first?"\n":"") + line);

				first=false;
			}
			reader.close();
			String modifiedString = inputBuffer.toString();
			FileOutputStream fileOut = new FileOutputStream(file);
	        fileOut.write(modifiedString.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String hash2 = Hasher.computeHashForFile(new File(filePath));
		assertNotEquals("hashes are the same", hash1, hash2);
	}
	
	protected void modifySourceModel(String fileRelativePath) {
		String filePath = DIR + fileRelativePath;
		File file = new File(filePath);
		assertTrue("File not found", file.exists());
		String hash1 = Hasher.computeHashForFile(file);
	
		BufferedReader reader;
		StringBuffer inputBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Yes")) {
					line = line.replace("Yes", "Definitely");
				}
				inputBuffer.append(line + "\n");
			}
			reader.close();
			String modifiedString = inputBuffer.toString();
			FileOutputStream fileOut = new FileOutputStream(file);
	        fileOut.write(modifiedString.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String hash2 = Hasher.computeHashForFile(new File(filePath));
		assertNotEquals("hashes are the same", hash1, hash2);
	}
	
	/** TESTING PROCESS */

	@Override
	protected void setupSource() {
		IWorkflow w = ExampleWorkflows.getEgxWorkflow();
		final String program = new WorkflowProgramBuilder(w).build();
		try {
			//module.setWorkflow(w);
			module.parse(program);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/** TESTS */

	@Test
	public void testThreeExecutions3TNoChanges() {
		second = AllTaskStateValidator.SKIPPED;
		modifications = new Runnable() {
			@Override public void run() {}
		};
	}

	@Test
	public void testTwoExecutionsChangesToTaskSource() {
		modifications = new Runnable() {
			@Override
			public void run() {
				addComment("task/egx/protected/main.egx");
			}
		};
		second = new TaskStateValidator(TaskState.EXECUTED, "generateCode");
	}
	
	@Test
	public void testTwoExecutionsChangesToTaskTemplate() {
		modifications = new Runnable() {
			@Override
			public void run() {
				addComment("task/egx/protected/action2page.egl");
			}
		};
		second = new TaskStateValidator(TaskState.EXECUTED, "generateCode");
	}

	@Test 
	public void testTwoExecutionsChangesToOutputInProtected() {
		modifications = new Runnable() {
			@Override
			public void run() {
				protectedRegionChange(true,"task/egx/output/website/Get up.html"); 
			}
		};
		second = new TaskStateValidator(TaskState.SKIPPED, "generateCode");
	}
	
	@Test 
	public void testTwoExecutionsChangesToOutputOutsideProtected() {
		protect = true;
		modifications = new Runnable() {
			@Override
			public void run() {
				protectedRegionChange(false, "task/egx/output/website/Get up.html"); 
			}
		};
		second = new TaskStateValidator(TaskState.SKIPPED, "generateCode");
	}
	
	@Test 
	public void testTwoExecutionsChangesToOutputOutsideProtectedIgnored() {
		protect = false;
		modifications = new Runnable() {
			@Override
			public void run() {
				protectedRegionChange(false, "task/egx/output/website/Get up.html"); 
			}
		};
		second = new TaskStateValidator(TaskState.EXECUTED, "generateCode");
	}
	
	@Test
	public void testTwoExecutionsChangesToSourceModel() {
		modifications = new Runnable() {
			@Override
			public void run() {
				modifySourceModel("m/wakeup.model");
			}
		};
		second = new TaskStateValidator(TaskState.EXECUTED, "generateCode");
	}

}
