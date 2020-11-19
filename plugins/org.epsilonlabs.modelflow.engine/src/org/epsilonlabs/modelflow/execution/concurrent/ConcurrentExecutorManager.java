/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.execution.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentExecutorManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConcurrentExecutorManager.class);
	
	protected final ExecutorService exec = Executors.newFixedThreadPool(10);

	protected List<Future<?>> futures = new ArrayList<>();
	
	protected AtomicInteger count = new AtomicInteger(0);
	
	public void submit(Runnable task){
		LOG.info("Executing {}", count.get());
		Future<?> submit = exec.submit(task);
		futures.add(submit);
	}
	
	public void await(){
		int i = 0;
		while (i != count.get() - 1) {
			Future<?> f = futures.get(i);
			try {
				f.get();
			} catch (InterruptedException e) {
				exec.shutdownNow();
			} catch (ExecutionException e) {
				break;
			}
			i += 1;
		}
		exec.shutdown();
		try {				
			while (!exec.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				LOG.debug("Awaiting completion of threads.");
			}
		} catch (Exception e) {
			exec.shutdownNow();
		}
	}
	
	public void handleException(Throwable ex) {
		LOG.error(ex.getMessage());
		exec.shutdown();
	}
	
	protected int increment(){
		return count.incrementAndGet();
	}
	
}
