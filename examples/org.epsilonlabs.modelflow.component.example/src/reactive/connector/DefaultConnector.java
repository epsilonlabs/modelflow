package reactive.connector;

import java.util.function.Consumer;

public class DefaultConnector implements IConnector {

	private Double value = null;

	@Override
	public void onNext(Double value) {
		System.out.println(getClass().getSimpleName() + value);
		this.value = value;
	}

	@Override
	public void doOnNext(Consumer<Double> object) {
		object.accept(this.value);
	}
	
	public void subscribe(){
	}
	
}
