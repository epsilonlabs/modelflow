/**
 * 
 */
package org.epsilonlabs.modelflow.dom.api;

/**
 * @author bea
 *
 */
public interface ITaskFactory extends IFactory{

	@Override
	Class<? extends ITaskInstance> getFactoryClass();
}
