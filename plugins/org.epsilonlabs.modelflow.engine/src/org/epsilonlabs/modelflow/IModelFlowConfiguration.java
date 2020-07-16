/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow;

import java.io.File;

/**
 * @author Betty Sanchez
 *
 */
public interface IModelFlowConfiguration {

	/**
	 * Sets the execution trace location.
	 *
	 * @param directory the new execution trace location
	 */
	void setExecutionTraceLocation(File directory);

	/**
	 * Gets the execution trace file.
	 *
	 * @return the execution trace file
	 */
	File getExecutionTraceFile();

	/**
	 * Gets the end to end trace file.
	 *
	 * @return the end to end trace file
	 */
	File getEndToEndTraceFile();

	/**
	 * Sets the end to end trace location.
	 *
	 * @param file the new end to end trace location
	 */
	void setEndToEndTraceLocation(File file);

	/**
	 * Sets the end to end trace location.
	 *
	 * @param location the new end to end trace location
	 */
	void setEndToEndTraceLocation(String location);

	/**
	 * Sets the save end to end traces.
	 *
	 * @param save the new save end to end traces
	 */
	void setSaveEndToEndTraces(boolean save);

	/**
	 * Checks if is save end to end traces.
	 *
	 * @return true, if is save end to end traces
	 */
	boolean isSaveEndToEndTraces();


}
