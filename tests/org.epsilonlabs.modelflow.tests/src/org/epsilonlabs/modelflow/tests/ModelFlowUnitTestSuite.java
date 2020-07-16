package org.epsilonlabs.modelflow.tests;

import org.epsilonlabs.modelflow.tests.ast.CompileTest;
import org.epsilonlabs.modelflow.tests.ast.ParsingTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ParsingTest.class, CompileTest.class, /*ExecutionTraceTest.class*/})
public class ModelFlowUnitTestSuite {}
