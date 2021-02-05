/**
 * 
 */
package org.epsilonlabs.modelflow.dom.ast;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;

/**
 * @author bea
 *
 */
public interface IModelCallExpression extends ModuleElement, ICompilableModuleElement, IExecutableModuleElement{

	NameExpression getModel();

	List<NameExpression> getAliases();

	/**
	 * @param name
	 */
	void addAlias(NameExpression name);

}
