package reactive.connector;

import java.util.function.Consumer;

import io.reactivex.subjects.PublishSubject;


public class QueueConnector implements IConnector {

	protected PublishSubject<Double> v = PublishSubject.create();
	
	@Override
	public void onNext(Double value) {
		//System.out.println(getClass().getSimpleName() + value);
		this.v.onNext(value);
	}

	@Override
	public void doOnNext(Consumer<Double> object) {
		this.v.subscribe(next-> object.accept(next));
	}
	

}
