/**
 * 
 */
package org.epsilonlabs.modelflow.tests.trace;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Paths;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.mmc.core.plugin.CorePlugin;
import org.epsilonlabs.modelflow.registry.ResourceFactoryRegistry;
import org.epsilonlabs.modelflow.registry.TaskFactoryRegistry;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Betty Sanchez
 *
 */
public class ExecutionTraceTest {

	static TaskFactoryRegistry taskFactoryRegistry;
	static ResourceFactoryRegistry resFactoryRegistry;
	protected ModelFlowModule module;


	protected final String code = "task A is core:print {\n" + 
			"	text: \"hi\"\n" + 
			"}";
	protected final File file = Paths.get(System.getProperty("user.dir"),"resources","simple.mflow").toFile();
	
	@Test
	public void testExecutionTraceFromParsedString(){
		parseStr(code);
		execute();
	}
	
	@Test
	public void testExecutionTraceFromParsedFile(){
		parseFile(file);
		execute();
	}
	
	@Test
	public void testContinuousExecutionTraceFromParsedString(){
		parseStr(code);
		execute();
		execute();
	}
	
	@Test
	public void testContinuousExecutionTraceFromParsedFile(){
		parseFile(file);
		execute();
		execute();
	}
	
	@Test
	public void testDisContinuousExecutionTraceFromParsedString(){
		parseStr(code);
		execute();
		populateModule();
		parseStr(code);
		execute();	
	}
	
	@Test
	public void testDisContinuousExecutionTraceFromParsedFile(){
		parseFile(file);
		execute();
		populateModule();
		parseFile(file);
		execute();
	}
	
	@BeforeClass
	public static void before() {
		Injector injector = Guice.createInjector(new CorePlugin());
		taskFactoryRegistry = injector.getInstance(TaskFactoryRegistry.class);
		resFactoryRegistry = injector.getInstance(ResourceFactoryRegistry.class);
	}
	
	@Before
	public void populateModule(){
		module = new ModelFlowModule();
		module.setTaskFactoryRegistry(taskFactoryRegistry);
		module.setResFactoryRegistry(resFactoryRegistry);
		module.getContext().setEndToEndTracing(false);
	}
	
	protected void parseStr(String code) {
		try {
			module.parse(code);
		} catch (Exception e) {
			module.getParseProblems().stream().map(ParseProblem::getReason).forEach(System.out::println);
			fail("parse errors");
		}
	}
	
	protected void parseFile(File file) {
		try {
			module.parse(file);
		} catch (Exception e) {
			module.getParseProblems().stream().map(ParseProblem::getReason).forEach(System.out::println);
			fail("parse errors");
		}
	}

	protected void execute() {
		try {	
			module.execute();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caught exception while executing");
		}
		assertNotNull(module.getContext().getExecutionTrace());
	}
	
}
