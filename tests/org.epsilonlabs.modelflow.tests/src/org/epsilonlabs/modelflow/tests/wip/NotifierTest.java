package org.epsilonlabs.modelflow.tests.wip;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

public class NotifierTest {

	public static void main(String[] args) {
		PublishSubject<String> publisher = PublishSubject.create();
		Observer<String> observer = new Observer<String>() {
			@Override
			public void onNext(String t) {
				System.out.println(t);
			}

			@Override
			public void onSubscribe(Disposable d) {
				
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Finished observing");
				
			}
		};
		publisher.subscribe(observer);
				
		publisher.onNext("message");
		publisher.onNext("2");
		publisher.onNext("3");
		publisher.onNext("4");
			
		publisher.onComplete();

		System.out.println("done");
		
		
		

		Observable<String> users = Observable.just("a", "b", "c");
		users.subscribe(observer);
		users.subscribe(observer);
		users.subscribe(observer);
		
		ConnectableObservable<String> usersC = Observable.just("a", "b", "c").publish();
		usersC.subscribe(observer);
		usersC.subscribe(observer);
		usersC.subscribe(observer);
		usersC.connect(); // it's important!!
	}
	

}
