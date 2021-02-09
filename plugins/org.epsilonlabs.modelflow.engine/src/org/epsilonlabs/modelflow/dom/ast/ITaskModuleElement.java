/**
 * 
 */
package org.epsilonlabs.modelflow.dom.ast;

import java.util.List;

import org.eclipse.epsilon.eol.dom.ExecutableBlock;

/**
 * @author bea
 *
 */
public interface ITaskModuleElement extends IDeclaration {
	/**
	 * Name of the parameter which indicates how to name multi task instances.
	 */
	public static final String TASK_NAME = "taskName";

	boolean isTrace();

	boolean isAlwaysExecute();

	boolean isEnabled();

	List<IModelCallExpression> getTrans();

	List<IModelCallExpression> getInouts();

	List<IModelCallExpression> getOutputs();

	List<IModelCallExpression> getInputs();

	ForEachModuleElement getForEach();

	List<TaskDependencyExpression> getDependsOn();

	ExecutableBlock<Boolean> getGuard();

	boolean isGenerator();

}
