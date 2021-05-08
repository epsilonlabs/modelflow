/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.epsilon.eugenia.EugeniaActionDelegate;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios.EugeniaScenarios;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.BenchmarkUtils;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.ScenarioSource;
import org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util.TestUtils;
import org.epsilonlabs.modelflow.tests.common.ResourceLocator;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * @author Betty Sanchez
 *
 *         This test must be run as a plugin test and the workspace should be
 *         set to ${project_loc}/target/modelflow/test/ Ensure that the
 *         RuntimeConfiguration does not include the original eugenia example
 *         plugin or it will conflict with the one in the target
 *         
 *         Must be run in as Plug In Test in UI THREAD
 */
public class EuGENiaBPMN_UI_Benchmark extends EuGENiaBPMNBenchmark /* implements IHandler */ {

	private SWTWorkbenchBot workbenchBot;
	private SWTBotView explorer;
	private Display display;

	
	@Override
	@ParameterizedTest(name = "Scenario {0} tracing {1} #{2}.")
	@ScenarioSource(value = EugeniaScenarios.class, times = MAX_ITER)
	public void eugeniaExampleTwoExecutionTests(EugeniaScenarios scenario, Boolean tracing, Integer iteration)
			throws Exception {
		setupClass("ui-", true);
		removeProjects();

		preparePackageExplorer();
		
		System.out.printf(">>>>[ EXECUTING UI ] SCENARIO: %s, ITERATION: %d%n", scenario.getName(), iteration);
		
		display = Display.getCurrent();

		display.syncExec(()->{			
			TestUtils.clearExecutionFiles(Paths.get(ResourceLocator.getTestDir()));
		});	
		
		String projectName = String.format("%s.%s", base, metamodelName);
		
		final Path eugeniaSource = Paths.get(ResourceLocator.getUserDir(), "..", "..", "examples", "EuGENia");
		final Path eugeniaOutputProjectPath = TestUtils
				.copyExampleProjectToTempLocation(eugeniaSource.resolve(projectName), projectName);
		
		display.syncExec(()->{			
			try {
				importProject(eugeniaOutputProjectPath);
			} catch (CoreException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		});
		
		// Copy dependent diagram project
		final String diagramProjectName = String.format("%s.%s.diagram.custom", base, metamodelName);
		this.diagramProjectOutputPath = TestUtils
				.copyExampleProjectToTempLocation(eugeniaSource.resolve(diagramProjectName), diagramProjectName);

		display.syncExec(()->{	
			try {
				importProject(diagramProjectOutputPath);
			} catch (CoreException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}			
		});
				
		System.out.println("First Execution");
		execute();

		TimeUnit.SECONDS.sleep(2);
		
		if (scenario.isFirstTimeExecution()) {
			final EuGENIAJobListener listener = EuGENIAJobListener.getInstance();
			saveOverhead(scenario.getName(), iteration, listener.getDuration());
			listener.clear();

			System.out.println("Skipping modifications and second execution");
		} else {

			display.syncExec(()->{
				modifications(scenario, eugeniaOutputProjectPath);
				try {
					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(-1, new NullProgressMonitor());
				} catch (CoreException e) { }
			});

			preparePackageExplorer();

			System.out.println("Second Execution");
			execute();
			
			final EuGENIAJobListener listener = EuGENIAJobListener.getInstance();
			saveOverhead(scenario.getName(), iteration, listener.getDuration());
			listener.clear();
		}

		cleanup(eugeniaOutputProjectPath);
	}

	protected void execute() {
		AtomicReference<IAction> action = new AtomicReference<>();
		AtomicReference<EugeniaActionDelegate> delegate = new AtomicReference<>();
		AtomicBoolean ok = new AtomicBoolean(false);
		
		display.syncExec(()->{			
			try {
				final SWTBotMenu menu = getEugeniaMenu();
				menu.click();	
			} catch (Exception e) {
				fail(e.getMessage());
			}
		});
		while (EuGENIAJobListener.getInstance() == null) {}
		final EuGENIAJobListener listener = EuGENIAJobListener.getInstance();
		listener.register((d, a)-> {
			delegate.set(d);
			action.set(a);
			ok.set(true);
		});

		// Wait for the GmfDiagramCode task to require execution
		while (!ok.get()) {
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				ok.set(true);
			}
		}
		// Dispatch the GmfDiagramCode task
		display.syncExec(()-> {
			try {
				delegate.get().runImpl(action.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// Wait for GmfDiagramCode to finish
		while (!listener.isDone()) {
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				ok.set(true);
			}
		}
	}
	
	protected void saveOverhead(String scenario, Integer iteration, Long duration) {
		Object[] results = new Object[] { 
				scenario, 
				"ui",
				iteration, 
				duration
		};
		try {
			BenchmarkUtils.writeResults(overheadFile, results);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	protected SWTBotMenu getEugeniaMenu() {
		AtomicReference<SWTBotMenu> placeholder = new AtomicReference<>();
		display.syncExec(()->{			
			try {
				final SWTWorkbenchBot bot = new SWTWorkbenchBot();
				while (!explorer.isActive()) {
					explorer.setFocus();
				}
				final Widget explorerWidget = explorer.getWidget();
				TimeUnit.SECONDS.sleep(2);
				
				final Tree explorerTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class),explorerWidget);
				TimeUnit.SECONDS.sleep(2);
				
				final SWTBotTree swtBotTree = new SWTBotTree(explorerTree);
				TimeUnit.SECONDS.sleep(2);
								
				final SWTBotTreeItem project = swtBotTree.getTreeItem("org.eclipse.epsilon.eugenia.simplebpmn");
				if (!project.isExpanded()) project.expand();
				
				final SWTBotTreeItem res = project.getNode("resources");
				if (!res.isExpanded()) res.expand();
				
				final SWTBotTreeItem models = res.getNode("model");
				if (!models.isExpanded()) models.expand();
				
				final SWTBotTreeItem node = models.getNode("simplebpmn.emf");
				node.select();
				
				SWTBotMenu menu = node.contextMenu().menu("Eugenia").menu("Generate GMF editor");
				placeholder.set(menu);
				
			} catch (Exception e) {
				fail(e.getMessage());
			}
		});
		return placeholder.get();
	}

	/**
	 * @throws InterruptedException
	 */
	protected void preparePackageExplorer() throws InterruptedException {
		workbenchBot = new SWTWorkbenchBot();

		try {
			final SWTBotView welcome = workbenchBot.viewByTitle("Welcome");
			if (welcome != null) {			
				welcome.close();
			}
		} catch (Exception e) {
			// Ignore if Welcome view is not present
		}
		
		workbenchBot.perspectiveByLabel("Java").activate();

		explorer = workbenchBot.viewByTitle("Package Explorer");
		explorer.show();
		explorer.setFocus();
	}

	@Override
	protected void cleanup(Path outputPath) {
		super.cleanup(outputPath);
	}
	
	protected void removeProjects() {
		Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).forEach(p->{
			try{
				p.delete(true, new NullProgressMonitor());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unable to delete project: "+p.getName()); 
			}
		});
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(-1, new NullProgressMonitor());
		} catch (CoreException e) { }		
	}

}
