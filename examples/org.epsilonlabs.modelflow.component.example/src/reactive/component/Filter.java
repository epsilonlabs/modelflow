package reactive.component;

import reactive.connector.IConnector;

public class Filter {

	private Double filter = null;
	private Double result = null;
	private Double tolerance = 0d;
	private IConnector filterConnector = null;
	private IConnector resultConnector = null;

	public Filter(Double tolerance, IConnector filterConnector, IConnector resultConnector) {
			this.filterConnector = filterConnector;
			this.filterConnector.doOnNext(this::setFilter);
			this.resultConnector = resultConnector;
			this.tolerance = tolerance;
		}

	public void setFilter(Double next) {
		this.filter = next;
		System.out.println("Filter:" + this.filter);
		this.compute();
	}

	private void compute() {
		if (this.filter != null && this.tolerance != null) {
			if (result == null) {
				this.result = this.filter;
				System.out.println("Filter computed " + this.result);
				this.resultConnector.onNext(this.result);
			} else if (Math.abs(this.filter-this.result) > this.tolerance) {
				this.result = this.filter;
				System.out.println("Filter computed " + this.result);
				this.resultConnector.onNext(this.result);
			} else {
				//System.out.println("Filtering value");				
			}
				
		}
	}

}
