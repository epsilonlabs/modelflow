/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.IEUnitModule;
import org.epsilonlabs.modelflow.dom.api.ITask;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.management.trace.Trace;
import org.epsilonlabs.modelflow.mmc.epsilon.factory.AbstractEpsilonTaskFactory;

public class EpsilonEunitTask extends AbstractEpsilonTask implements ITask {

	@SuppressWarnings("unchecked")
	@Override
	public IEUnitModule getModule() {
		if (module == null) {
			this.module = new EUnitModule();
		}
		return (IEUnitModule) this.module;
	}

	/** FACTORY */

	public static class Factory extends AbstractEpsilonTaskFactory {

		public Factory() {
			super(EpsilonEunitTask.class);
		}

		@Override
		public String getName() {
			return "eunit";
		}

	}

	protected String packageName = "default";
	protected Optional<File> dir = Optional.empty();
	protected Boolean report = false;
	protected List<String> operations = new ArrayList<>();

	@Param(key = "package")
	public void setPackage(String packageName) {
		this.packageName = packageName;
	}
	
	public String getPackage() {
		return packageName;
	}

	@Param(key = "dir")
	public void setReportDir(File dir) {
		this.dir = Optional.of(dir);
	}
	
	public Optional<File> getDir() {
		return dir;
	}
	
	@Param(key = "report")
	public void setReport(Boolean report) {
		this.report = report;
	}
	
	public Boolean getReport() {
		return report;
	}

	@Param(key = "selectedOperations")
	public void setSelectedOperations(List<String> operations) {
		this.operations.addAll(operations);
	}
	
	@Param(key = "selectedOperations")
	public void addSelectedOperation(String operation) {
		this.operations.add(operation);
	}
	
	public List<String> getSelectedOperations() {
		return operations;
	}

	@Override
	public void validateParameters() throws MFExecutionException {
		super.validateParameters();
		getModule().setPackage(this.packageName);
		if (getDir().isPresent()) {
			getModule().setReportDirectory(getDir().get());			
		} else if (this.report) {
			getModule().setReportDirectory(this.src.get().getParentFile());
		} else {
			getModule().setReportDirectory(null);
		}
		try {
			getModule().setSelectedOperations(this.operations);
		} catch (EolRuntimeException e) {
			throw new MFExecutionException(e);
		}
	}
	
	@Override
	public Optional<Object> getResult() {
		try {
			return Optional.of(getModule().getSuiteRoot().getResult());
		} catch (EolRuntimeException e) {
			return Optional.empty();
		}
	}

	//FIXME Manage Traces in EUnit
	@Override
	public Optional<Collection<Trace>> getTrace() {
		/*EUnitTest suiteRoot = null;
		try {
			suiteRoot = getModule( ).getSuiteRoot();
		} catch (Exception e) {
		}
		return Optional.of(Collections.emptyList());*/
		return Optional.empty();
	}

}