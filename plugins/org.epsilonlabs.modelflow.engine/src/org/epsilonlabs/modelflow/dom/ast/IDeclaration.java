/**
 * 
 */
package org.epsilonlabs.modelflow.dom.ast;

import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.ICompilableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;

/**
 * @author bea
 *
 */
public interface IDeclaration extends ModuleElement, ICompilableModuleElement {

	NameExpression getType();
	
	Map<NameExpression, ModuleElement> getParameters();
	
	NameExpression getNameExpression();
	
	default String getName() {
		return getNameExpression().getName();
	}

}
