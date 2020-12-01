/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.scenarios;

import java.nio.file.Path;

import org.epsilonlabs.modelflow.tests.common.validator.IValidate;

/**
 * @author bea
 *
 */
public interface IScenario {

	default Runnable getModifications(Path basedir) {
		return getModifications(basedir.toString());
	}
	
	/**
	 * @param basedir
	 * @return
	 */
	Runnable getModifications(String basedir);

	/**
	 * @return
	 */
	IValidate getValidator();

	/**
	 * @return
	 */
	boolean isProtect();

	/**
	 * @return
	 */
	String getName();


}
