/**
 * 
 */
package org.epsilonlabs.modelflow.execution.graph.node;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.execution.IModelFlowPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * @author bea
 *
 */
public abstract class AbstractTaskNode implements ITaskNode {
	
	private static final Logger LOG = LoggerFactory.getLogger(ITaskNode.class);

	protected ITaskInstance taskInstance;
	protected TaskState state;

	protected final CompletableSubject completable = CompletableSubject.create();
	protected final PublishSubject<TaskState> statusUpdater = PublishSubject.create();
	
	public AbstractTaskNode() {
		setState(TaskState.CREATED);
	}
	
	@Override
	public Completable getObservable() {
		return completable;
	}
	
	@Override
	public void subscribe(IModelFlowPublisher pub){
		statusUpdater.subscribe(status -> pub.taskState(getName(), status));
	}
	
	protected synchronized void setState(TaskState state){
		LOG.debug("Task {} is {}", getTaskElement().getName(), state.name());
		this.statusUpdater.onNext(state);
		this.state = state;
		switch (state) {
		case EXECUTED:
		case SKIPPED:
			this.statusUpdater.onComplete();
			this.completable.onComplete();
			break;
		default: 
			break;
		}
	}
	
	@Override
	public synchronized TaskState getState() {
		return this.state;
	}
	
	public ITaskInstance getTaskInstance() {
		return this.taskInstance;
	}

	public void setInstance(ITaskInstance instance) {
		this.taskInstance = instance;
	}
}
