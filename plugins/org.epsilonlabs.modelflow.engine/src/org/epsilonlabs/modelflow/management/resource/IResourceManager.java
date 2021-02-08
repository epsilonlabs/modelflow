/**
 * 
 */
package org.epsilonlabs.modelflow.management.resource;

import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

/**
 * @author bea
 *
 */
public interface IResourceManager {
	
	/** 
	 * This method is called when the node has been marked ready for execution.
	 */
	void processResourcesBeforeExecution(ITaskNode taskInstance, IModelFlowContext ctx) throws MFRuntimeException;

	/** 
	 * This method is called after the task has been executed.
	 * @throws MFRuntimeException 
	 */
	void processResourcesAfterExecution(ITaskNode taskInstance, IModelFlowContext ctx) throws MFRuntimeException;

}