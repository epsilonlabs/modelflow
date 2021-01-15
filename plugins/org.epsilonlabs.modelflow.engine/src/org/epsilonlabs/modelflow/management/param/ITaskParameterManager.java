/**
 * 
 */
package org.epsilonlabs.modelflow.management.param;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

/**
 * @author bea
 *
 */
public interface ITaskParameterManager {

	void processOutputs(ITaskNode node, IModelFlowContext ctx);

	void processInputs(ITaskNode node, IModelFlowContext ctx);

	ITaskPropertyHandler getOutputParameterHandler(ITaskInstance task);

	ITaskPropertyHandler getInputParameterHandler(ITaskInstance task);

}