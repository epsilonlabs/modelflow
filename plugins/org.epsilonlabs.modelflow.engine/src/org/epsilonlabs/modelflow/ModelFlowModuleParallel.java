/**
 * 
 */
package org.epsilonlabs.modelflow;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.epsilonlabs.modelflow.execution.concurrent.TopologicalConcurrentScheduler;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author Betty Sanchez
 *
 */
public class ModelFlowModuleParallel extends ModelFlowModule {

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		IModelFlowContext ctx = getContext();
		ctx.setExecutor(new TopologicalConcurrentScheduler());
	}
}
