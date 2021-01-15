/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.control;

import java.io.Serializable;


/**
 * @author Betty Sanchez
 *
 */
public class ProfiledStage implements Serializable {

	private static final long serialVersionUID = -2249957038111847953L;
		
	protected MeasureableSnapshot start;
	protected MeasureableSnapshot end;

	public ProfiledStage() {
		this.start = new MeasureableSnapshot();
	}

	public void done() {
		this.end = new MeasureableSnapshot();
	}
	
	/**
	 * @return the start
	 */
	public MeasureableSnapshot getStart() {
		return start;
	}
	
	/**
	 * @return the end
	 */
	public MeasureableSnapshot getEnd() {
		return end;
	}
	
	public void setEnd(MeasureableSnapshot snapshot){
		this.end = snapshot;
	}
	
	public MeasureableSnapshot delta() {
		return end.delta(start);
	}

	@Override
	public String toString() {
		return "ProfiledStage [start=" + start 
				+ ", end=" + end 
				+ ", delta=" + delta()+ "]";
	}

}
