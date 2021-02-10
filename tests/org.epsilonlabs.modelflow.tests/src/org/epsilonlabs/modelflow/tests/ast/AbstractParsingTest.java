/**
 * 
 */
package org.epsilonlabs.modelflow.tests.ast;

import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.junit.After;
import org.junit.Before;

/**
 * @author bea
 *
 */
public abstract class AbstractParsingTest {

	protected interface Assertable {
		Boolean evaluate(IWorkflow w);
	}

	protected String code;

	public void testMinimalModel() {
		code = "model ModelA is EMF {}";
	}

	public void testMinimalModelWithColonedType() {
		code = "model ModelA is epsilon:emf {}";
	}

	public void testMinimalModelWithColonEnd() {
		code = "model ModelA is EMF;";
	}

	public void testModelWithOneParameter() {
		code = "model ModelA is EMF {\n" 
				+ "  src: \"a.model\"\n" 
				+ "}";
	}

	public void testModelWithMoreThanOneParameter() {
		code = "model ModelA is EMF {\n" 
				+ "  mm: \"a.ecore\"\n" 
				+ "  src: \"a.model\"\n" 
				+ "}";
	}

	public void testMinimalTask() {
		code = "task Task1 is EOL {}";
	}

	public void testMinimalDisabledTask() {
		code = "@disabled \n"
				+ "task Task1 is EOL {}";
	}
	

	public void testMinimalNonTraceableTask() {
		code = "@noTrace \n"
				+ "task Task1 is EOL {}";
	}
	
	public void testTaskDependency() {
		code = "model X is EMF; \n"
				+ "task Task2 is EOL in X dependsOn Task1 {} \n"
				+ "task Task1 is EOL {} \n";
	}
	
	public void testTaskDependencies() {
		code = "model X is EMF; \n"
				+ "task Task2 is EOL in X dependsOn Task1, Task3 {} \n"
				+ "task Task3 is EOL; \n"
				+ "task Task1 is EOL {} \n";
	}
	
	public void testMinimalTaskWithColonEnd() {
		code = "task Task1 is EOL;";
	}

	public void testTaskWithOneParameter() {
		code = "task Task1 is EOL {\n" 
				+ "	src: \"eolFile.eol\"\n" 
				+ "}";
	}

	public void testTaskWithMoreThanOneParameter() {
		code = "task Task1 is EOL {\n" 
				+ "	src: \"eolFile.eol\"\n" 
				+ "	src2 { return \"eolFile.eol\"; } \n" 
				+ "}";
	}

	public void testTaskWithGuard() {
		code = "task Task1 is EOL {\n" 
				+ "	guard: true\n" 
				+ "	src: \"eolFile.eol\"\n" 
				+ "}";
	}

	public void testTaskWithOneInputModel() {
		code = "model ModelA is EMF; \n" 
				+ "task Task1 is EOL in ModelA;";
	}

	public void testTaskWithOneInputModelWithAlias() {
		code = "model ModelA is EMF ;\n" 
				+ "task Task1 is EOL in ModelA as Src;";
	}
	
	public void testTaskWithImplicitModelDependency() {
		code = "task compare is ECL;"
				+ "task Task1 is EOL in compare.comparison;"; 
	}
	
	public void testTaskWithImplicitModelDependencyWithAlias() {
		code = "task compare is ECL;"
				+ "task Task1 is EOL in compare.comparison as Compare;";
	}

	public void testTaskWithTwoInputModelWithAlias() {
		code = "model ModelA is EMF; \n" 
				+ "model ModelB is EMF ; \n" 
				+ "task Task1 is EOL in ModelA as Src and ModelB;";
	}
	
	public void testTaskWithOneOutputModel() {
		code = "model ModelA is EMF;\n" 
				+ "task Task1 is EOL out ModelA;";
	}
	
	public void testTaskWithOneInoutModel() {
		code = "model ModelA is EMF;\n" 
				+ "task Task1 is EOL inout ModelA;";
	}
	
	public void testTaskForEachSequence() {
		code = 	" task A is EOL forEach b in : Sequence{1..5}{\n"
				+ "	  src: b + '.eol' \n"
				+ "}";
	}
	
	public void testParamNoType() {
		code = "param tada;";
	}
	
	public void testParamWithType() {
		code = "param tada : String;";
	}

	@Before
	public void reset() {
		code = "";
	}
	
	@After
	public abstract void evaluate();

}
