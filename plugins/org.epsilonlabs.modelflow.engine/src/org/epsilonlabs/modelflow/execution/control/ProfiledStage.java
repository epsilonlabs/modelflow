/**
 * 
 */
package org.epsilonlabs.modelflow.execution.control;

import java.io.Serializable;


/**
 * @author Betty Sanchez
 *
 */
public class ProfiledStage implements Serializable {

	private static final long serialVersionUID = -2249957038111847953L;
		
	protected MeassureSnapshot start;
	protected MeassureSnapshot end;

	public ProfiledStage() {
		this.start = new MeassureSnapshot();
	}

	public void done() {
		this.end = new MeassureSnapshot();
	}
	
	/**
	 * @return the start
	 */
	public MeassureSnapshot getStart() {
		return start;
	}
	
	/**
	 * @return the end
	 */
	public MeassureSnapshot getEnd() {
		return end;
	}
	
	public void setEnd(MeassureSnapshot snapshot){
		this.end = snapshot;
	}
	
	public MeassureSnapshot delta() {
		return end.delta(start);
	}

	@Override
	public String toString() {
		return "ProfiledStage [start=" + start 
				+ ", end=" + end 
				+ ", delta=" + delta()+ "]";
	}

}
