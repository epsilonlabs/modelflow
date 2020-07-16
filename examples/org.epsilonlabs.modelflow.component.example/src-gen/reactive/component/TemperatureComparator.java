package reactive.component;

import reactive.connector.IConnector;

public class TemperatureComparator {
	
	private Double temperature = null; 
	private Double targetTemperature = null; 
	private Double difference = null; 
	private IConnector temperatureConnector = null; 
	private IConnector targetTemperatureConnector = null; 
	private IConnector differenceConnector = null; 
	
	public TemperatureComparator(IConnector temperatureConnector, IConnector targetTemperatureConnector, IConnector differenceConnector) {
		this.temperatureConnector = temperatureConnector;
		this.temperatureConnector.doOnNext(this::setTemperature);
		this.targetTemperatureConnector = targetTemperatureConnector;
		this.targetTemperatureConnector.doOnNext(this::setTargetTemperature);
		this.differenceConnector = differenceConnector;
	}
	
	public void setTemperature(Double next){
		this.temperature = next;
		System.out.println("TemperatureComparator.temperature:" + this.temperature);
		this.compute();
	}
	public void setTargetTemperature(Double next){
		this.targetTemperature = next;
		System.out.println("TemperatureComparator.targetTemperature:" + this.targetTemperature);
		this.compute();
	}
	
	private void compute() {
		if (this.temperature != null && this.targetTemperature != null) {
			/* protected region compute on begin */
			this.difference = this.targetTemperature-this.temperature;
			System.out.println("TemperatureComparator computed " + this.difference);
			this.differenceConnector.onNext(this.difference);
			/* protected region compute end */
		}
	}
	
}