package reactive.sensor;

import io.reactivex.subjects.PublishSubject;
import reactive.connector.IConnector;
import reactive.connector.IEmmitter;

public class TemperatureSensor implements IEmmitter {
	
	private PublishSubject<Double> value;	

	public TemperatureSensor(IConnector connector){
		value = PublishSubject.create();
		value.subscribe(next -> {
			System.out.println("TemperatureSensor: " + next);
			connector.onNext(next);
		});
	}
	
	@Override
	public void onNext(Double value){
		this.value.onNext(value);	
	}
	
}