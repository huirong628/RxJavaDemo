package com.android.huirongzhang.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;


public class MainActivity extends AppCompatActivity {

	private TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTv = (TextView) findViewById(R.id.tv);
		mTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//createObservableByCreate();
				//createObservableByFrom();
				createObservableByJust();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * new Observable<T>(RxJavaHooks.onCreate(f))
	 */
	private void createObservableByCreate() {
		/*Observable.create(new Observable.OnSubscribe<Object>() {

			@Override
			public void call(Subscriber<? super Object> subscriber) {

			}
		});*/

		Observable<Integer> observableString = Observable.create(new Observable.OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				for (int i = 0; i < 5; i++) {
					subscriber.onNext(i);
				}
				subscriber.onCompleted();
			}
		});

		Subscription subscriptionPrint = observableString.subscribe(new Observer<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("onCompleted()");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError()");
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("onNext(),integer = " + integer);
			}
		});
	}

	/**
	 * create(new OnSubscribeFromIterable<T>(iterable))
	 * <p>
	 * new Observable<T>(RxJavaHooks.onCreate(f));
	 */
	private void createObservableByFrom() {
		List<Integer> items = new ArrayList<Integer>();
		items.add(1);
		items.add(10);
		items.add(100);
		items.add(200);
		Observable.from(items).subscribe(new Observer<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("onCompleted()");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError()");
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("onNext(),integer = " + integer);
			}
		});
	}

	/**
	 * ScalarSynchronousObservable.create(value);
	 * new ScalarSynchronousObservable<T>(t)
	 * super(RxJavaHooks.onCreate(new JustOnSubscribe<T>(t)));
	 */
	private void createObservableByJust() {
		Observable.just("Hello Word").subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("onCompleted()");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("onError()");
			}

			@Override
			public void onNext(String str) {
				System.out.println("onNext(),str = " + str);
			}
		});
	}
}
