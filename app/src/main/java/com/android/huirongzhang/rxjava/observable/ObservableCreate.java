package com.android.huirongzhang.rxjava.observable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;

/**
 * Created by HuirongZhang on 2016/10/22.
 * <p>
 * create an Observable from scratch by means of a function
 */

public class ObservableCreate {

	private void create1() {
		Observable observable = Observable.create(new Observable.OnSubscribe() {
			@Override
			public void call(Object o) {

			}
		});
	}

	/**
	 * call(T)的参数类型由来：public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {}
	 * <p>
	 * 参数类型：Subscriber-> Observer
	 * <p>
	 * OnSubscribe -> Action1 -> Action -> Function
	 */
	private void create2() {
		Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try {
					//先判断是否有订阅
					if (!subscriber.isUnsubscribed()) {

						subscriber.onNext("a");

						subscriber.onCompleted();
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	private void create3() {
		Observable<String> observable = Observable.create(new SyncOnSubscribe<Object, String>() {
			@Override
			protected Object generateState() {
				return null;
			}

			@Override
			protected Object next(Object state, Observer<? super String> observer) {
				return null;
			}
		});
	}

	private void create4() {
		Observable<String> observable = Observable.create(new AsyncOnSubscribe<Object, String>() {
			@Override
			protected Object generateState() {
				return null;
			}

			@Override
			protected Object next(Object state, long requested, Observer<Observable<? extends String>> observer) {
				return null;
			}
		});
	}

	private void create5() {
		Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						for (int i = 1; i < 5; i++) {
							observer.onNext(i);
						}
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onNext(Integer item) {
				System.out.println("Next: " + item);
			}

			@Override
			public void onError(Throwable error) {
				System.err.println("Error: " + error.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Sequence complete.");
			}
		});
	}
}
