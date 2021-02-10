/**
 * 
 */
package org.epsilonlabs.modelflow.dom.api.factory;

import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.IGraphNode;

/**
 * @author bea
 *
 */
public interface IInstanceFactory<T, N extends IGraphNode> {

	//T create(IModelFlowContext ctx) throws MFRuntimeException;
	
	T create(Class<? extends T> factory, N node, IModelFlowContext ctx) throws MFRuntimeException;

}
