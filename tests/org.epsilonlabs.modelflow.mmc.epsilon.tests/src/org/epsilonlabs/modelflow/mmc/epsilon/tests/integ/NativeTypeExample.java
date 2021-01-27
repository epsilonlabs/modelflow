/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.epsilon.tests.integ;

import java.util.List;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.tools.ITool;

/**
 * @author bea
 *
 */
public class NativeTypeExample implements ITool{

	IEolContext ctx;
	@Override
	public void setContext(IEolContext context) {
		ctx = context;
	}

	@Override
	public IEolContext getContext() {
		return ctx;
	}

	@Override
	public void initialize(List<Object> parameters) {
		// Do nothing
	}
	
	public boolean success(){
		return true;
	}

}
