package org.epsilonlabs.gradle.tasks;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.CommandLineFixer;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

public class EVL extends AbstractExportableModuleTask {
	
	public static final String TASK_COMMAND = "evl";
	public static final String TASK_DESCRIPTION = "Executes model validation";
	
	private Logger log = LogManager.getLogger(EVL.class);

	/** PROPERTIES WITH DEFAULT VALUES */
	
	protected String exportConstraintTrace;
	
	/** CONFIGURABLE */
	
	@Optional
	@Input
	public String getExportConstraintTrace() {
		return exportConstraintTrace;
	}

	public void setExportConstraintTrace(String exportConstraintTrace) {
		this.exportConstraintTrace = exportConstraintTrace;
	}

	/** ACTIONS */
	
	@Override
	protected IEolModule createModule() {
		return (IEolModule) new EvlModule();
	}

	@Override
	protected void initialize() throws Exception {
		IEvlModule evlModule = (IEvlModule) module;
		CommandLineFixer clf = new CommandLineFixer();
		clf.setFix(false);
		evlModule.setUnsatisfiedConstraintFixer(clf);
	}

	@Override
	protected void examine() throws Exception {
		super.examine();
		
		IEvlModule evlModule = (IEvlModule) module;
		int errors = 0;
		int warnings = 0;
		for (UnsatisfiedConstraint unsatisfiedConstraint : evlModule.getContext().getUnsatisfiedConstraints()){
			if (unsatisfiedConstraint.getConstraint().isCritique()) {
				if (!unsatisfiedConstraint.getConstraint().isInfo()) {
					warnings ++;
				}
			}
			else {
				errors ++;
			}
		}
		log.info("Errors : " + errors);
		
		final String message = errors + " error(s) and " + warnings + " warning(s) found during validation";
		if (errors > 0) {
			fail(message, null);
		}
		else if (warnings > 0) {
			warn(message);
		}
		
		if (exportConstraintTrace != null) {
			getProjectStackFrame().put(exportConstraintTrace, 
					evlModule.getContext().getConstraintTrace());
		}
	}
	
	@Override
	public Object getResult() {
		if (result !=null) {
			result = ((IEvlModule) module).getContext().getUnsatisfiedConstraints().size() == 0;
			System.out.println("result: " + result);
		}
		return result;
	}

	@Override
	protected Collection<Class<?>> getClassesForExportedModel() {
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(UnsatisfiedConstraint.class);
		return classes;
	}

	@Override
	protected ArrayList<Object> getObjectsForExportedModel() {
		return new ArrayList<Object>(((EvlModule)module).getContext().getUnsatisfiedConstraints());
	}
	
}
