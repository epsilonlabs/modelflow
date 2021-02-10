/**
 * 
 */
package org.epsilonlabs.modelflow.dom.api;

/**
 * @author bea
 *
 */
public interface IModelResourceFactory extends IFactory{

	@Override
	Class<? extends IModelResourceInstance<?>> getFactoryClass();
}
