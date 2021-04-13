/**
 * 
 */
package org.epsilonlabs.modelflow.mmc.core.task;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;

/**
 * @author Betty Sanchez
 *
 */
@Definition(name = "core:sleep")
public class SleepTask implements ITaskInstance {
	
	protected TimeUnit unit = TimeUnit.MILLISECONDS;
	protected Integer timeout = 200;
	
	@Param(key = "unit")
	public void setUnit(String unit){
		this.unit = TimeUnit.valueOf(unit.toUpperCase());
	}
	
	/**
	 * @return the unit
	 */
	public TimeUnit getUnit() {
		return unit;
	}
	
	@Override
	public boolean isAlwaysExecute() {
		return true;
	}
	
	@Param(key = "timeout")
	public void setTimeout(Integer time){
		this.timeout = time;
	}
	
	/**
	 * @return the time
	 */
	public Integer getTimeout() {
		return timeout;
	}
	
	@Override
	public void validateParameters() throws MFRuntimeException {
		// Nothing to validate
	}

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
		// Do nothing
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFRuntimeException {
		try {
			unit.sleep(timeout);
		} catch (InterruptedException e) {
			throw new MFRuntimeException(e);
		}
	}

	@Override
	public void afterExecute() throws MFRuntimeException {
		// Do nothing
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

}