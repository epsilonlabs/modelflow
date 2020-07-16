package reactive.connector;

import java.util.function.Consumer;

public interface ISubscriber {

	void doOnNext(Consumer<Double>  object);

}
