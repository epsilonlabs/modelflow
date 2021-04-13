/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Definition;
import org.epsilonlabs.modelflow.dom.api.annotation.Input;
import org.epsilonlabs.modelflow.dom.api.annotation.Output;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.exception.MFExecutionException;
import org.epsilonlabs.modelflow.exception.MFInvalidModelException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.management.resource.IModelWrapper;
import org.epsilonlabs.modelflow.management.trace.Trace;

@Definition(name = "core:fileReader")
public class FileReaderTask implements ITaskInstance {

	protected File file;
	protected String contents;

	@Param(key = "src")
	public void setFile(File file) {
		this.file = file;
	}
	
	@Input(key="src")
	public File getFile() {
		return this.file;
	}
	
	@Override
	public boolean isAlwaysExecute() {
		return true;
	}

	@Override
	public void validateParameters() throws MFExecutionException {
		if (!getFile().exists()) {
			throw new MFExecutionException("File does not exist");
		}
	}

	@Override
	public void execute(IModelFlowContext ctx) throws MFExecutionException {
		try (FileReader reader = new FileReader(file)) {
			try (BufferedReader bis = new BufferedReader(reader)) {
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = bis.readLine()) != null) {
					sb.append(line);
				}
				this.contents = sb.toString();
			} catch (Exception e) {
				throw new MFExecutionException(e);
			}
		} catch (FileNotFoundException e1) {
			throw new MFExecutionException(e1);
		} catch (IOException e1) {
			throw new MFExecutionException(e1);
		}
	}

	@Output(key = "contents") 
	public String getContents() {
		return contents;
	}

	@Override
	public Optional<Collection<Trace>> getTrace() {
		return Optional.empty();
	}

	@Override
	public void acceptModels(IModelWrapper[] models) throws MFInvalidModelException {
	}

	@Override
	public void afterExecute() {
	}

}