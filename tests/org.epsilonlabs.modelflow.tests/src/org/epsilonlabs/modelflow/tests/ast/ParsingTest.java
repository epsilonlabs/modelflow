/**
 * 
 */
package org.epsilonlabs.modelflow.tests.ast;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Betty Sanchez
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParsingTest extends AbstractParsingTest {

	private static final Logger LOG = LoggerFactory.getLogger(ParsingTest.class);

	@Override
	@Test
	public void testMinimalModelWithColonedType() {
		super.testMinimalModelWithColonedType();
	}

	@Override
	@Test
	public void testMinimalModelWithColonEnd() {
		super.testMinimalModelWithColonEnd();
	}

	@Override
	@Test
	public void testModelWithOneParameter() {
		super.testModelWithOneParameter();
	}

	@Override
	@Test
	public void testModelWithMoreThanOneParameter() {
		super.testModelWithMoreThanOneParameter();
	}

	@Override
	@Test
	public void testMinimalTask() {
		super.testMinimalTask();
	}

	@Override
	@Test
	public void testMinimalTaskWithColonEnd() {
		super.testMinimalTaskWithColonEnd();
	}

	@Override
	@Test
	public void testTaskWithOneParameter() {
		super.testTaskWithOneParameter();
	}

	@Override
	@Test
	public void testTaskWithMoreThanOneParameter() {
		super.testTaskWithMoreThanOneParameter();
	}

	@Override
	@Test
	public void testTaskDependency() {
		super.testTaskDependency();
	}

	@Override
	@Test
	public void testTaskDependencies() {
		super.testTaskDependencies();
	}

	@Override
	@Test
	public void testTaskWithGuard() {
		super.testTaskWithGuard();
	}

	@Override
	@Test
	public void testTaskWithOneInputModel() {
		super.testTaskWithOneInputModel();
	}

	@Override
	@Test
	public void testTaskWithOneInputModelWithAlias() {
		super.testTaskWithOneInputModelWithAlias();
	}

	@Override
	@Test
	public void testTaskWithTwoInputModelWithAlias() {
		super.testTaskWithTwoInputModelWithAlias();
	}

	@Test
	@Override
	public void testTaskWithImplicitModelDependency() {
		super.testTaskWithImplicitModelDependency();
	}

	@Test
	@Override
	public void testTaskWithImplicitModelDependencyWithAlias() {
		super.testTaskWithImplicitModelDependencyWithAlias();
	}

	@Override
	@Test
	public void testMinimalDisabledTask() {
		super.testMinimalDisabledTask();
	}

	@Override
	@Test
	public void testMinimalNonTraceableTask() {
		super.testMinimalNonTraceableTask();
	}

	@Override
	@Test
	public void testTaskForEachSequence() {
		super.testTaskForEachSequence();
	}
	
	@Override
	@Test
	public void testParamNoType() {
		super.testParamNoType();
	}

	@Override
	@Test
	public void testParamWithType() {
		super.testParamWithType();
	}

	@Override
	public void evaluate() {
		ModelFlowModule module = new ModelFlowModule();
		boolean ok = true;
		try {
			ok = module.parse(code);

			if (!ok) {
				for (ParseProblem problem : module.getParseProblems()) {
					LOG.error("Parse problem: {}", problem.toString());
				}
				fail("Parse Problems");
			}
		} catch (Exception e) {
			LOG.error("Caught exception: {}. Due to: {}", e.getMessage(), e.getCause());
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertTrue(ok);
	}

}
