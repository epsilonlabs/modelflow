package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import java.util.function.BiConsumer;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.epsilon.eugenia.EugeniaActionDelegate;
import org.eclipse.jface.action.IAction;

public class EuGENIAJobListener implements IJobChangeListener {

	private static EuGENIAJobListener instance = null;
	
	// private static boolean cleared = false;
	// private String label = "";
	private long start;
	private long duration;

	public EuGENIAJobListener() {
		start = 0l;
		duration = 0l;
		instance = this;
	}

	public static EuGENIAJobListener getInstance() {
		return instance;
	}

	//private List<Job> jobs = new ArrayList<Job>();

	private BiConsumer<EugeniaActionDelegate, IAction> consummer;
	
	public void register(BiConsumer<EugeniaActionDelegate, IAction> consummer){
		this.consummer = consummer;
	}

	public void pendingStep(EugeniaActionDelegate delegate, IAction action) {
		if (consummer != null) {			
			consummer.accept(delegate, action);		
		}
	}

	@Override
	public void running(IJobChangeEvent event) {
		//System.out.printf("Job '%s' running%n", event.getJob().getName());
		if (event.getJob().getName().equals("Generating all GMF models")) {			
			start = System.nanoTime();
		}
		//System.out.printf("%s start: %d %n", event.getJob().getName(), start);
	}

	boolean done = false;
	@Override
	public void done(IJobChangeEvent event) {
		final long end = System.nanoTime();
		if (event.getJob().getName().equals("Generate GMF editor")) {
			done = true;
			duration = end - start;
		}
		//System.out.printf("Job '%s' done%n", event.getJob().getName());
		//System.out.printf("Removed '%s' job from queue. Count %d.%n", event.getJob().getName(), jobs.size());
		//jobs.remove(event.getJob());

		//System.out.printf("%s end: %d %n", event.getJob().getName(), end);
		//System.out.printf("%s: %d ns %n", event.getJob().getName(), duration);
	}

	public boolean isDone() {
		return done;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void clear(){
		instance = null;
	}

	@Override
	public void sleeping(IJobChangeEvent event) {
		//System.out.printf("Job '%s' sleeping%n", event.getJob().getName());
	}
	
	@Override
	public void scheduled(IJobChangeEvent event) {
		//System.out.printf("Job '%s' scheduled%n", event.getJob().getName());
		//jobs.add(event.getJob());
		//System.out.printf("Added '%s' job to queue. Count %d.%n", event.getJob().getName(), jobs.size());
	}
	
	@Override
	public void awake(IJobChangeEvent event) {
		//System.out.printf("Job '%s' awake%n", event.getJob().getName());
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {
		//System.out.printf("Job '%s' about to run%n", event.getJob().getName());
	}
}