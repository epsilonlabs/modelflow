/**
 * 
 */
package org.epsilonlabs.modelflow.management.param;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;

/**
 * @author bea
 *
 */
public interface ITaskParameterManager {

	void processOutputs(ITaskInstance node, IModelFlowContext ctx);

	void processInputs(ITaskInstance node, IModelFlowContext ctx);

	ITaskPropertyHandler getOutputParameterHandler(ITaskInstance task);

	ITaskPropertyHandler getInputParameterHandler(ITaskInstance task);

}