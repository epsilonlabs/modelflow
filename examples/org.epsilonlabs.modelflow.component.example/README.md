## BoilerControllerReactive.java

``` 
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

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
```

## BoilerActuator.java

```
if (this.diff > 0) {
	if (this.action == null || this.action == 0d) {				
		this.action = 1d;
		System.out.println("BoilerActuator computed " + this.action);
		this.actionConnector.onNext(this.action);
	}
} else { // need to turn off
	if (this.action == null || this.action != 0d) {					
		this.action = 0d;
		System.out.println("BoilerActuator computed " + this.action);
		this.actionConnector.onNext(this.action);
	}
}
```

## TemperatureComparator.java

```
this.difference = this.targetTemperature-this.temperature;
System.out.println("TemperatureComparator computed " + this.difference);
this.differenceConnector.onNext(this.difference);
```