/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.transaction;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;
import org.epsilonlabs.modelflow.dom.Task;

public class TaskResourcesTransactionSupport implements IModelTransactionSupport {

	protected Task task;
	// protected List<IModel> models = new ArrayList<>();

	public TaskResourcesTransactionSupport(Task task) {
		this.task = task;
	}

	public List<IModel> getModels() {
		//return task.getResources();
		return Collections.emptyList();
	}

	/** 
	 * For a store on disposal model : Equivalent to a Save operation
	 * For a non store on disposal model : Equivalent to a update registry entry
	 */
	@Override
	public void commitTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().commitTransaction();
		}
	}

	/** 
	 * For a store on disposal model : Equivalent to a dispose operation without saving and error throwing
	 * For a non store on disposal model : Equivalent to dispose current memory representation and use previous one
	 */
	@Override
	public void rollbackTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().rollbackTransaction();
		}
	}

	/** 
	 * Make an in memory copy
	 */
	@Override
	public void startTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().startTransaction();
		}
	}
	
}
