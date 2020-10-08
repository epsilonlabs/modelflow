package org.epsilonlabs.modelflow.execution.control;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MeassureSnapshot implements Serializable{
	
	private static final long serialVersionUID = 312306623488191951L;

	private static final Runtime RT = Runtime.getRuntime();

	protected long time;
	protected long freeMemory;
	protected long totalMemory;
	protected long maxMemory;
	protected int availableProcessors ;
	protected int threads ;
	protected boolean delta;
	
	public MeassureSnapshot() {
		this.time = System.nanoTime();
		this.freeMemory = RT.freeMemory();
		this.maxMemory = RT.maxMemory();
		this.totalMemory = RT.totalMemory();
		this.availableProcessors = RT.availableProcessors();
		this.threads = Thread.activeCount();
	}
	
	public MeassureSnapshot(long time, long freeMemmory, long maxMemory, long totalMemory, int available, int threads) {
		this.time = time;
		this.freeMemory = freeMemmory;
		this.maxMemory = maxMemory;
		this.totalMemory = totalMemory;
		this.availableProcessors = available;
		this.threads = threads;
		this.delta = true;
	}
	
	public MeassureSnapshot delta(MeassureSnapshot start) {
		MeassureSnapshot end = this;
		return new MeassureSnapshot(
				end.time 				- start.time, 
				end.freeMemory			- start.freeMemory, 
				end.maxMemory 			- start.maxMemory,
				end.totalMemory 		- start.totalMemory,
				end.availableProcessors - start.availableProcessors,
				end.threads				- start.threads);
	}
	
	public long getMemory(){
		return totalMemory - freeMemory;
	}
	
	public long getTime(TimeUnit unit) {
		return unit.convert(this.time, TimeUnit.NANOSECONDS);
	}
	
	public long getFreeMemory(MemoryUnit unit) {
		return unit.convert(this.freeMemory, MemoryUnit.BYTES);
	}
	
	public long getTotalMemory(MemoryUnit unit) {
		return unit.convert(this.totalMemory, MemoryUnit.BYTES);
	}
	
	public long getMaxMemory(MemoryUnit unit) {
		return unit.convert(this.maxMemory, MemoryUnit.BYTES);
	}
	
	public int getAvailableProcessors() {
		return availableProcessors;
	}
	
	public int getThreads() {
		return threads;
	}
	
	public boolean isDelta() {
		return delta;
	}
	
	@Override
	public String toString() {
		String format = "%s : %d %s";
		String noUnit = "%s : %d";
		String timeStr = String.format(format, delta ? "duration" : "time", this.time, TimeUnit.NANOSECONDS);
		String freeMemoryStr = String.format(format, delta ? "delta free memory" : "free memory", this.freeMemory, MemoryUnit.BYTES);
		String maxMemoryStr = String.format(format, delta ? "delta max memory" : "max memory", this.maxMemory, MemoryUnit.BYTES);
		String totalMemoryStr = String.format(format, delta ? "delta total memory" : "total memory", this.totalMemory, MemoryUnit.BYTES);
		String availableStr = "";
		String threadsStr = "";
		if (!delta) {			
			availableStr = String.format(noUnit, "available processors", this.availableProcessors);
			threadsStr = String.format(noUnit, "thread count", this.threads);
		}
		return Arrays.asList(timeStr, freeMemoryStr, maxMemoryStr, totalMemoryStr, availableStr, threadsStr).stream().collect(Collectors.joining(","));
	}
	
}

