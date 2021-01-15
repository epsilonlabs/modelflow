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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.epsilonlabs.modelflow.dom.IWorkflow;
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
import org.epsilonlabs.modelflow.tests.common.validator.CompositeValidator;
import org.epsilonlabs.modelflow.tests.common.validator.TaskStateValidator;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CompareMergeTransformIncrementalTest extends IncrementalTest {

	@BeforeClass
	public static void configureModule() {
		Injector injector = Guice.createInjector(new EpsilonPlugin(), new EMFPlugin(), new CorePlugin(), new GMFPlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	/** Validation/Modification Helpers */

	protected void fileModifier(String file) {
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
	
	/** TESTING PROCESS */
	
	@Override
	protected void setupSource() {
		IWorkflow w = ExampleWorkflows.getCompareMergeTransformComponent();
		module.setWorkflow(w);
	}

	/** TESTS */

	//FIXME
	@Test
	public void testThreeExecutionsNoChanges() {
		second = AllTaskStateValidator.SKIPPED;
		modifications = new Runnable() {
			@Override public void run() {}
		};
	}

	@Test
	public void testThreeExecutionsChangesToTaskCompare() {
		modifications = new Runnable() {
			@Override
			public void run() {
				fileModifier("task/Comparison.ecl");
			}
		};
		TaskStateValidator compare = new TaskStateValidator(TaskState.EXECUTED, "compare");
		TaskStateValidator merge = new TaskStateValidator(TaskState.SKIPPED, "merge");
		TaskStateValidator transform = new TaskStateValidator(TaskState.SKIPPED, "transform");
		second = new CompositeValidator(merge, compare, transform);
	}

	@Test
	public void testThreeExecutionsChangesToTaskMerge() {
		modifications = new Runnable() {
			@Override
			public void run() {
				fileModifier("task/Merging.eml");
			}
		};
		TaskStateValidator compare = new TaskStateValidator(TaskState.EXECUTED, "compare");
		TaskStateValidator merge = new TaskStateValidator(TaskState.EXECUTED, "merge");
		TaskStateValidator transform = new TaskStateValidator(TaskState.SKIPPED, "transform");
		second = new CompositeValidator(merge, compare, transform);
	}

	//FIXME
	@Ignore
	@Test
	public void testThreeExecutionsChangesToTaskTransform() {
		modifications = new Runnable() {
			@Override
			public void run() {
				fileModifier("task/entity2node.etl");
			}
		};
		TaskStateValidator compare = new TaskStateValidator(TaskState.SKIPPED, "compare");
		TaskStateValidator merge = new TaskStateValidator(TaskState.SKIPPED, "merge");
		TaskStateValidator transform = new TaskStateValidator(TaskState.EXECUTED, "transform");
		second = new CompositeValidator(merge, compare, transform);
	}
	
	/*TODO

	@Test
	public void testThreeExecutionsChangesToResourceSource() {

	}

	@Test
	public void testThreeExecutionsChangesToResourceVocabulary() {

	}

	@Test
	public void testThreeExecutionsChangesToResourceTarget() {

	}

	@Test
	public void testThreeExecutionsChangesToResourceGraph() {

	}
	*/

}
