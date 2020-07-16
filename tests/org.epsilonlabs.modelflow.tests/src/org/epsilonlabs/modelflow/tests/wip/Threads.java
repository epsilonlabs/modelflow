package org.epsilonlabs.modelflow.tests.wip;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Threads {

	public static void main(String[] args) {
		
		ThreadPoolExecutor executor = 
				  (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		
	    /**
	     * A > 		> D
	     *     	C  
	     * B >		> E
	     */
		MyRunnable a = new MyRunnable("A");
		MyRunnable b = new MyRunnable("B");
		MyRunnable c = new MyRunnable("C", a, b);
		MyRunnable d = new MyRunnable("D", c);
		MyRunnable e = new MyRunnable("E", c);
		executor.submit(a);
		executor.submit(b);
		executor.submit(c);
		executor.submit(d);
		executor.submit(e);
		
		executor.shutdown();
	}	
	
}
