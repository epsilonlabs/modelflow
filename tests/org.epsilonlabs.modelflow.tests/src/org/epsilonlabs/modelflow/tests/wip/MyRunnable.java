package org.epsilonlabs.modelflow.tests.wip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MyRunnable implements Runnable {

	public String name;
	private List<MyRunnable> locks = new ArrayList<MyRunnable>();
	private Semaphore lock;

	public MyRunnable(String name, MyRunnable... locks) {
		this.name = name;
		this.lock = new Semaphore(1);
		this.locks.addAll(Arrays.asList(locks));
	}

	public Semaphore getLock(){
		return this.lock;
	}
	
	@Override
	public void run() {
		System.out.println(String.format("inside %s", name));
		try {
			System.out.println(String.format("acquiring %s", name));
			lock.acquire();
			locks.parallelStream().forEach(l ->{				
				try {
					l.getLock().acquire();
					l.getLock().release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(String.format("in %s trying to acquire %s", name, l.name));
			});
			System.out.println(String.format("running %s", name));
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			System.out.println(String.format("releasing %s", name));
			this.lock.release();
		}
	}
}
