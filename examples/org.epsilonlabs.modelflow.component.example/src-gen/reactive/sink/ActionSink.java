package reactive.sink;

import reactive.connector.IConnector;
import reactive.connector.QueueConnector;

public class ActionSink extends QueueConnector {
	
	protected Double value;
	
	public ActionSink(IConnector connector){
		connector.doOnNext(next -> {
			this.value = next;
			this.onNext(next);
		});
	}
	
	public Double getValue() {
		return value;
	}
		
}