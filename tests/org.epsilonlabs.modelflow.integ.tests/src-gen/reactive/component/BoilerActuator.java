package reactive.component;

import reactive.connector.IConnector;

public class BoilerActuator {
	
	private Double diff = null; 
	private Double boilerStatus = null; 
	private Double action = null; 
	private IConnector diffConnector = null; 
	private IConnector boilerStatusConnector = null; 
	private IConnector actionConnector = null; 
	
	public BoilerActuator(IConnector diffConnector, IConnector boilerStatusConnector, IConnector actionConnector) {
		this.diffConnector = diffConnector;
		this.diffConnector.doOnNext(this::setDiff);
		this.boilerStatusConnector = boilerStatusConnector;
		this.boilerStatusConnector.doOnNext(this::setBoilerStatus);
		this.actionConnector = actionConnector;
	}
	
	public void setDiff(Double next){
		this.diff = next;
		System.out.println("BoilerActuator.diff:" + this.diff);
		this.compute();
	}
	public void setBoilerStatus(Double next){
		this.boilerStatus = next;
		System.out.println("BoilerActuator.boilerStatus:" + this.boilerStatus);
		this.compute();
	}
	
	private void compute() {
		if (this.diff != null && this.boilerStatus != null) {
			/* protected region compute on begin */
			// TODO logic
			System.out.println("BoilerActuator computed " + this.action);
			this.actionConnector.onNext(this.action);
			/* protected region compute end */
		}
	}
	
}