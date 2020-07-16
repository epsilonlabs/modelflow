package reactive;

/* protected region imports on begin */
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
/* protected region imports end */
import java.util.function.Consumer;

import reactive.component.*;
import reactive.sensor.*;
import reactive.sink.*;
import reactive.connector.*;

public class BoilerControllerReactive {
	
	protected TemperatureSensor temperatureSensor;
	protected TargetTemperatureSensor targetTemperatureSensor;
	protected BoilerStatusSensor boilerStatusSensor;
	protected ActionSink actionSink;
	
	public BoilerControllerReactive(){
		IConnector boilerControllerTargetTemperatureToTemperatureComparatorTargetTemperature = new QueueConnector();
		IConnector boilerControllerBoilerStatusToBoilerActuatorBoilerStatus = new QueueConnector();
		IConnector temperatureComparatorDifferenceToBoilerActuatorDiff = new QueueConnector();
		IConnector boilerActuatorActionToBoilerControllerAction = new QueueConnector();
		IConnector boilerControllerTemperatureToTemperatureToleranceFilterFilter = new QueueConnector();
		IConnector temperatureToleranceFilterResultToTemperatureComparatorTemperature = new QueueConnector();
		this.temperatureSensor = new TemperatureSensor(boilerControllerTemperatureToTemperatureToleranceFilterFilter);
		this.targetTemperatureSensor = new TargetTemperatureSensor(boilerControllerTargetTemperatureToTemperatureComparatorTargetTemperature);
		this.boilerStatusSensor = new BoilerStatusSensor(boilerControllerBoilerStatusToBoilerActuatorBoilerStatus);
		new TemperatureComparator(temperatureToleranceFilterResultToTemperatureComparatorTemperature, boilerControllerTargetTemperatureToTemperatureComparatorTargetTemperature, temperatureComparatorDifferenceToBoilerActuatorDiff);
		new BoilerActuator(temperatureComparatorDifferenceToBoilerActuatorDiff, boilerControllerBoilerStatusToBoilerActuatorBoilerStatus, boilerActuatorActionToBoilerControllerAction);
		new Filter(0.5d,boilerControllerTemperatureToTemperatureToleranceFilterFilter, temperatureToleranceFilterResultToTemperatureComparatorTemperature);
		this.actionSink =  new ActionSink(boilerActuatorActionToBoilerControllerAction);
	}
	
	public void setTemperature(Double temperature){
		this.temperatureSensor.onNext(temperature);
	}
	public void setTargetTemperature(Double targetTemperature){
		this.targetTemperatureSensor.onNext(targetTemperature);
	}
	public void setBoilerStatus(Double boilerStatus){
		this.boilerStatusSensor.onNext(boilerStatus);
	}
	public Double getAction(){
		return this.actionSink.getValue();
	}
	public void onResult(Consumer<Double> action){
		this.actionSink.doOnNext(action);
	}
	
	/* protected region main on begin */
	public static void main(String[] args) {
	    BoilerControllerReactive multiplierAdderReactive = new BoilerControllerReactive();
	    final AtomicLong status = new AtomicLong(-1);
	    multiplierAdderReactive.onResult(result -> {
	        System.out.println("Result: " + result);
	        if (status.get() == -1l) {
	            status.set(result.longValue()); 
	        } 
	        if (result.longValue() != status.get()) {
	            System.out.println("Feedback, setting boiler status to:" + result);
	            multiplierAdderReactive.setBoilerStatus(result);
	        }
	    });
	    Scanner s = new Scanner(System.in);
	    while (s.hasNext()) {
	        try {
	        String[] split = s.next().split("=");
	        String property = split[0];
	        Double value = Double.valueOf(split[1]);
	        switch (property) {
	        case "target":
	            multiplierAdderReactive.setTargetTemperature(value);
	            break;
	        case "boiler":
	            multiplierAdderReactive.setBoilerStatus(value);
	            break;
	        case "temp":
	            multiplierAdderReactive.setTemperature(value);
	            break;
	        default:
	            break;
	        } 
	        } catch (RuntimeException e) {
	            
	        }
	    }
	    /*
	    BoilerControllerReactive multiplierAdderReactive = new BoilerControllerReactive();
	    multiplierAdderReactive.onResult(result -> {
	        System.out.println("Result: " + result);
	    }
	    multiplierAdderReactive.setTargetTemperature(20d);
	    multiplierAdderReactive.setBoilerStatus(0d);
	    multiplierAdderReactive.setTemperature(16d);
	    multiplierAdderReactive.setTemperature(16.3d);
	    multiplierAdderReactive.setTemperature(16.4d);
	    multiplierAdderReactive.setTemperature(16.6d);
	    multiplierAdderReactive.setTemperature(21d);
	    */
	}
	/* protected region main end */
	
}