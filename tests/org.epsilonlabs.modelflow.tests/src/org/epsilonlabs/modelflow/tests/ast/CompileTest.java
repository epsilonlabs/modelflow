/**
 * 
 */
package org.epsilonlabs.modelflow.tests.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.epsilonlabs.modelflow.ModelFlowModule;
import org.epsilonlabs.modelflow.dom.IAbstractResource;
import org.epsilonlabs.modelflow.dom.IModelResource;
import org.epsilonlabs.modelflow.dom.IProperty;
import org.epsilonlabs.modelflow.dom.IResourceReference;
import org.epsilonlabs.modelflow.dom.ITask;
import org.epsilonlabs.modelflow.dom.ITaskDependency;
import org.epsilonlabs.modelflow.dom.IWorkflow;
import org.epsilonlabs.modelflow.execution.context.ModelFlowContext;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Betty Sanchez
 *
 */
public class CompileTest extends AbstractParsingTest {

	private static final Logger LOG = LoggerFactory.getLogger(CompileTest.class);
	protected IWorkflow producedWorkflow;
	protected Assertable result;

	@Override
	@Test
	public void testMinimalModel() {
		super.testMinimalModel();
		result = w -> {
			assertEquals(1,w.getResources().size());
			IAbstractResource res = w.getResources().get(0);
			assertTrue(res instanceof IModelResource);
			assertEquals("ModelA", res.getName());
			assertEquals("EMF", ((IModelResource)res).getDefinition());
			return true;
		};
	}

	@Override
	@Test
	public void testMinimalModelWithColonedType() {
		super.testMinimalModelWithColonedType();
		result = w -> {
			assertEquals(1,w.getResources().size());
			IAbstractResource res = w.getResources().get(0);
			assertTrue(res instanceof IModelResource);
			assertEquals("ModelA", res.getName());
			assertEquals("epsilon:emf", ((IModelResource)res).getDefinition());
			return true;
		};
	}

	@Override
	@Test
	public void testMinimalModelWithColonEnd() {
		super.testMinimalModelWithColonEnd();
		result = w -> {
			assertEquals(1,w.getResources().size());
			IAbstractResource res = w.getResources().get(0);
			assertTrue(res instanceof IModelResource);
			assertEquals("ModelA", res.getName());
			assertEquals("EMF", ((IModelResource)res).getDefinition());
			return true;
		};
		
	}
	

	@Override
	@Test
	public void testModelWithOneParameter() {
		super.testModelWithOneParameter();
		result = new Assertable() {
			@Override
			public Boolean evaluate(IWorkflow w) {
				IAbstractResource resource = w.getResources().get(0);
				assertTrue(resource != null);
				assertTrue(resource instanceof IModelResource);
				IModelResource model = (IModelResource) resource;
				assertEquals("ModelA", model.getName());
				assertEquals("EMF", model.getDefinition());
				assertEquals(1, model.getProperties().size());
				IProperty prop = model.getProperties().get(0);
				assertEquals("src", prop.getKey());
				Object value = prop.getValue();
				assertTrue(value instanceof StringLiteral);
				String result;
				try {
					result = ((StringLiteral) value).execute(null);
					assertEquals("a.model", result);
				} catch (EolRuntimeException e) {
					fail(e.getMessage());
				}
				return true;
			}
		};
	}

	@Override
	@Test
	public void testModelWithMoreThanOneParameter() {
		super.testModelWithMoreThanOneParameter();
		result = w -> {
				IAbstractResource resource = w.getResources().get(0);
				assertTrue(resource != null);
				assertTrue(resource instanceof IModelResource);
				IModelResource model = (IModelResource) resource;
				assertEquals("ModelA", model.getName());
				assertEquals("EMF", model.getDefinition());
				assertEquals(2, model.getProperties().size());
				for (IProperty prop : model.getProperties()) {
					String key = prop.getKey();
					assertTrue(Arrays.asList("src", "mm").contains(key));
					Object value = prop.getValue();
					assertTrue(value instanceof StringLiteral);
					try {
						String result = ((StringLiteral) value).execute(null);
						if ("src".equalsIgnoreCase(key)){							
							assertEquals("a.model", result);
						} else if ("mm".equalsIgnoreCase(key)) {							
							assertEquals("a.ecore", result);
						}
					} catch (EolRuntimeException e) {
						fail(e.getMessage());
					}
				}
				return true;
			};
	}

	@Override
	@Test
	public void testMinimalTask() {
		super.testMinimalTask();
		result = w -> {
			assertEquals(1,w.getTasks().size());
			ITask task = w.getTasks().get(0);
			assertEquals("Task1", task.getName());
			assertEquals("EOL", task.getDefinition());
			return true;
		};
	}
	
	@Override
	@Test
	public void testMinimalDisabledTask() {
		super.testMinimalDisabledTask();
		result = new Assertable() {
			@Override
			public Boolean evaluate(IWorkflow w) {
				ITask task = w.getTasks().get(0);
				assertTrue(task != null);
				assertFalse(task.getEnabled());
				return true;
			}
		};
	}
	
	@Override
	@Test
	public void testMinimalNonTraceableTask() {
		super.testMinimalNonTraceableTask();
		result = new Assertable() {
			@Override
			public Boolean evaluate(IWorkflow w) {
				ITask task = w.getTasks().get(0);
				assertTrue(task != null);
				assertFalse(task.getTraceable());
				return true;
			}
		};
	}


	@Override
	@Test
	public void testMinimalTaskWithColonEnd() {
		super.testMinimalTaskWithColonEnd();
		result = w -> {
			assertEquals(1,w.getTasks().size());
			ITask task = w.getTasks().get(0);
			assertEquals("Task1", task.getName());
			assertEquals("EOL", task.getDefinition());
			return true;
		};
	}

	@Override
	@Test
	public void testTaskDependency() {
		super.testTaskDependency();
		result = w -> {
			EList<ITaskDependency> dependencies = w.getTaskDependencies();
			assertTrue("One dependency", dependencies.size()==1);
			ITaskDependency dep = dependencies.get(0);
			assertTrue("Task1 should be before", dep.getDependsOn().getName().equals("Task1"));
			assertTrue("Task2 should go after", dep.getTask().getName().equals("Task2"));
			return true;
		};
	}
	
	@Override
	@Test
	public void testTaskDependencies() {
		super.testTaskDependencies();
		result = w -> {
			EList<ITaskDependency> dependencies = w.getTaskDependencies();
			assertTrue("Two dependencies expected", dependencies.size()==2);
			ITaskDependency dep = dependencies.get(0);
			assertTrue("Task1 should be before", dep.getDependsOn().getName().equals("Task1"));
			assertTrue("Task2 should go after", dep.getTask().getName().equals("Task2"));
			dep = dependencies.get(1);
			assertTrue("Task3 should be before", dep.getDependsOn().getName().equals("Task3"));
			assertTrue("Task2 should go after", dep.getTask().getName().equals("Task2"));
			return true;
		};
	}

	@Override
	@Test
	public void testTaskWithOneParameter() {
		super.testTaskWithOneParameter();
		result = new Assertable() {
			@Override
			public Boolean evaluate(IWorkflow w) {
				ITask task = w.getTasks().get(0);
				assertTrue(task != null);
				assertEquals("Invalid Task Name", "Task1", task.getName());
				assertEquals("Invalid Task type", "EOL", task.getDefinition());
				assertEquals("Invalid Number of properties", 1, task.getProperties().size());
				IProperty prop = task.getProperties().get(0);
				assertEquals("Invalid Parameter key", "src", prop.getKey());
				Object value = prop.getValue();
				assertTrue("Invalid property value type", value instanceof StringLiteral);
				String result;
				try {
					result = ((StringLiteral) value).execute(null);
					assertEquals("Invalid expected property value", "eolFile.eol", result);
				} catch (EolRuntimeException e) {
					e.getMessage();
					fail("Unable to get the property value from expression");
				}
				return true;
			}
		};
	}

	@Override
	@Test
	public void testTaskWithMoreThanOneParameter() {
		super.testTaskWithMoreThanOneParameter();
		result = new Assertable() {
			@Override
			public Boolean evaluate(IWorkflow w) {
				ITask task = w.getTasks().get(0);
				assertTrue(task != null);
				assertEquals("Invalid Task Name", "Task1", task.getName());
				assertEquals("Invalid Task type", "EOL", task.getDefinition());
				assertEquals("Invalid Number of properties", 2, task.getProperties().size());
				for (IProperty prop : task.getProperties()) {
					String key = prop.getKey();
					assertTrue("Property key unknown", Arrays.asList("src", "src2").contains(key));
					try {
						if ("src".equalsIgnoreCase(key)) {							
							Object value = prop.getValue();
							String result = ((StringLiteral) value).execute(null);
							assertTrue("Invalid property value type", value instanceof StringLiteral);
							assertEquals("Invalid expected src property value", "eolFile.eol", result);
						} else if ("".equalsIgnoreCase(key)) {
							Object value = prop.getValue();
							assertTrue("Invalid property value type", value instanceof StatementBlock);
							String result = (String) ((StatementBlock) value).execute(null).getValue();
							assertEquals("Invalid expected src2 property value", "eolFile.eol", result);
						}
					} catch (EolRuntimeException e) {
						e.getMessage();
						fail("Unable to get the property value from expression");
					}
				}
				return true;
			}
		};
	}

	@Override
	@Test
	public void testTaskWithGuard() {
		super.testTaskWithGuard();
		result = (w) -> {
			ITask task = w.getTasks().get(0);
			assertTrue(task != null);
			assertEquals("Invalid Task Name", "Task1", task.getName());
			assertEquals("Invalid Task type", "EOL", task.getDefinition());
			assertEquals("Invalid Number of properties", 1, task.getProperties().size());
			IProperty prop = task.getProperties().get(0);
			assertEquals("Invalid Parameter key", "src", prop.getKey());
			Object value = prop.getValue();
			assertTrue("Invalid property value type", value instanceof StringLiteral);
			try {
				String result = ((StringLiteral) value).execute(null);
				assertEquals("Invalid expected property value", "eolFile.eol", result);
			} catch (EolRuntimeException e) {
				e.getMessage();
				fail("Unable to get the property value from expression");
			}
			Object guard = task.getGuard();
			assertTrue("Invalid guard type", guard instanceof ExecutableBlock);
			try {
				@SuppressWarnings("unchecked")
				Boolean result = ((ExecutableBlock<Boolean>) guard).execute(new ModelFlowContext());
				assertTrue("Invalid expected guard value", result);
			} catch (EolRuntimeException e) {
				e.getMessage();
				fail("Unable to get the guard value from expression");
			}

			return true;
		};
	}

	/*
	@Override
	@Test
	public void testTaskWithOneInputModel() {
		super.testTaskWithOneInputModel();
		expectedWorkflow = new WorkflowBuilder()
				.addModelResource("ModelA", "EMF")
				.addTask("Task1", "EOL")
				.addInput("ModelA")
				.build();
	}

	@Override
	@Test
	public void testTaskWithOneInputModelWithAlias() {
		super.testTaskWithOneInputModelWithAlias();
		expectedWorkflow = new WorkflowBuilder()
				.addModelResource("ModelA", "EMF")
				.addTask("Task1", "EOL")
				.addInput("ModelA", "Src")
				.build();
	}

	@Override
	@Test
	public void testTaskWithTwoInputModelWithAlias() {
		super.testTaskWithTwoInputModelWithAlias();
		expectedWorkflow = new WorkflowBuilder()
				.addModelResource("ModelA", "EMF")
				.addModelResource("ModelB", "EMF")
				.addTask("Task1", "EOL")
				.addInput("ModelA", "Src")
				.addInput("ModelB")
				.build();
	}
	
	@Override
	@Test
	public void testTaskWithOneInoutModel() {
		super.testTaskWithOneInoutModel();
		expectedWorkflow = new WorkflowBuilder()
				.addModelResource("ModelA", "EMF")
				.addTask("Task1", "EOL")
				.addInout("ModelA")
				.build();
	}
	
	@Override
	@Test
	public void testTaskWithOneOutputModel() {
		super.testTaskWithOneOutputModel();
		expectedWorkflow = new WorkflowBuilder()
				.addModelResource("ModelA", "EMF")
				.addTask("Task1", "EOL")
				.addOutput("ModelA")
				.build();
	}
	*/
	
	@Override
	@Test
	public void testTaskForEachSequence() {
		super.testTaskForEachSequence();
		result = w -> {
			assertEquals(5, w.getTasks().size());
			for (int i=1; i == 5; i++) {				
				String name = String.format("A@%d", i);
				ITask task = w.getTasks().get(i-1);
				assertEquals(name, task.getName());
			}
			return true;
		};
	}
	
	@Ignore
	@Override
	@Test
	public void testParamNoType() {
		super.testParamNoType();
		// TODO decide how to include in the model
		result = w -> {
			return false;
		};
	}
	
	@Ignore
	@Override
	@Test
	public void testParamWithType() {
		super.testParamWithType();
		// TODO decide how to include in the model
		result = w -> {
			return false;
		};
	}
	
	@Test
	@Override
	public void testTaskWithImplicitModelDependency() {
		super.testTaskWithImplicitModelDependency();
		result = w -> {
			EList<ITask> tasks = w.getTasks();
			EList<IAbstractResource> resources = w.getResources();
			assertEquals(2,tasks.size());
			assertEquals(1,resources.size());
			IAbstractResource resource = resources.get(0);
			assertEquals("compare.comparison", resource.getName());
			ITask eolTask = tasks.get(1);
			assertEquals(1, eolTask.getConsumes().size());
			assertEquals(resource, eolTask.getConsumes().get(0).getResource());
			
			return true;
		};
	}

	@Test
	@Override
	public void testTaskWithImplicitModelDependencyWithAlias() {
		super.testTaskWithImplicitModelDependencyWithAlias();
		result = w -> {
			EList<ITask> tasks = w.getTasks();
			EList<IAbstractResource> resources = w.getResources();
			assertEquals(2,tasks.size());
			assertEquals(1,resources.size());
			IAbstractResource resource = resources.get(0);
			assertEquals("compare.comparison", resource.getName());
			ITask eolTask = tasks.get(1);
			assertEquals(1, eolTask.getConsumes().size());
			IResourceReference consumed = eolTask.getConsumes().get(0);
			assertEquals(resource, consumed.getResource());
			assertTrue(consumed.getAliases().contains("Compare"));
			
			return true;
		};
	}
	
	@Override
	public void reset() {
		super.reset();
		result = null;
		producedWorkflow = null;
	}

	@Override
	public void evaluate() {
		ModelFlowModule module = new ModelFlowModule();
		try {
			boolean ok = module.parse(code);
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
		module.compile();
		List<ModuleMarker> markers = module.getCompilationContext().getMarkers();
		long markerSize = markers.stream().filter(m->!m.getMessage().trim().startsWith("Unkown")).count();
		if (markerSize>0) {
			for (ModuleMarker m : markers) {
				LOG.error(m.getMessage());
			}
			fail("Compilation problems");
		}
		
		producedWorkflow = module.getWorkflow();
		if (result != null) {
			assertTrue(result.evaluate(producedWorkflow));
		} else {
			fail("No expected result was provided");
		}

	}
}
