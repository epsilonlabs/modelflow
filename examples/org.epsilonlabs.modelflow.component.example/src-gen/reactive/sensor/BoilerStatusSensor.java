package reactive.sensor;

import io.reactivex.subjects.PublishSubject;
import reactive.connector.IConnector;
import reactive.connector.IEmmitter;

public class BoilerStatusSensor implements IEmmitter {
	
	private PublishSubject<Double> value;	

	public BoilerStatusSensor(IConnector connector){
		value = PublishSubject.create();
		value.subscribe(next -> {
			System.out.println("BoilerStatusSensor: " + next);
			connector.onNext(next);
		});
	}
	
	@Override
	public void onNext(Double value){
		this.value.onNext(value);	
	}
	
}