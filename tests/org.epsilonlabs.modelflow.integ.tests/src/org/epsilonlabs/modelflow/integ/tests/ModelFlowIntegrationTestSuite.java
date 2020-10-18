/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests;

import org.epsilonlabs.modelflow.integ.tests.examples.ExampleTestSuite;
import org.epsilonlabs.modelflow.integ.tests.integ.TaskTest;
import org.epsilonlabs.modelflow.integ.tests.integ.arch.InjectionTests;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.CompareMergeTransformIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.ComponentOneChangeIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.EglIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.EgxIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.MarkersIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.MarkingMateIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.integ.incremental.TwoTransformationsIncrementalTest;
import org.epsilonlabs.modelflow.integ.tests.unit.ParallelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	//CompareMergeTransformIncrementalTest.class,
	//ExampleTestSuite.class,
	ComponentOneChangeIncrementalTest.class,	
	EglIncrementalTest.class,
	EgxIncrementalTest.class,
	//FullProcessTests.class, 
	InjectionTests.class,
	MarkersIncrementalTest.class,
	MarkingMateIncrementalTest.class,
	TaskTest.class,
	ParallelTest.class,
	//TraceTest.class,	
	TwoTransformationsIncrementalTest.class,
})
public class ModelFlowIntegrationTestSuite {
}
