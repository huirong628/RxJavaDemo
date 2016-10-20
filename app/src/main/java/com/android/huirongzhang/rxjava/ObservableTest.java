package com.android.huirongzhang.rxjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by HuirongZhang on 2016/10/20.
 */

public class ObservableTest extends Activity {
	private ListView mListView;
	private MyAdapter mAdapter;
	private List<AppInfo> mAppInfos = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.observable_test);
		mListView = (ListView) findViewById(R.id.app_list);
		mAdapter = new MyAdapter(mAppInfos);
		mListView.setAdapter(mAdapter);
		refreshTheList();
	}

	private Observable<List<AppInfo>> getApps() {

		return Observable.create(new Observable.OnSubscribe<List<AppInfo>>() {
			@Override
			public void call(Subscriber<? super List<AppInfo>> subscriber) {
				final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

				List<ResolveInfo> infos = getPackageManager().queryIntentActivities(mainIntent, 0);

				List<AppInfo> appInfos = new ArrayList<AppInfo>();
				for (ResolveInfo info : infos) {
					appInfos.add(new AppInfo("test"));
				}
				subscriber.onNext(appInfos);
			}
		});
		/*return Observable.create(new Observable.OnSubscribe<AppInfo>() {
			@Override
			public void call(Subscriber<? super AppInfo> subscriber) {
				final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

				List<ResolveInfo> infos = getPackageManager().queryIntentActivities(mainIntent, 0);

				for (ResolveInfo appInfo : infos) {
					String name = "test";
				*//*	if (subscriber.isUnsubscribed()) {
						return;
					}*//*
					subscriber.onNext(new AppInfo(name));
				}
				*//*if (!subscriber.isUnsubscribed()) {
					subscriber.onCompleted();
				}*//*
			}
		});*/
	}

	private void refreshTheList() {
		getApps().subscribe(new Observer<List<AppInfo>>() {

					@Override
					public void onCompleted() {
						Toast.makeText(getActivity(), "Here is the list!", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onError(Throwable e) {
						Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNext(List<AppInfo> appInfos) {
						System.out.println("onNext()");
						mAdapter.setData(appInfos);
					}
				});
	}

	public Context getActivity() {
		return ObservableTest.this;
	}

	class MyAdapter extends BaseAdapter {
		List<AppInfo> mAppInfos;

		public  MyAdapter(List<AppInfo> appInfos) {
			mAppInfos = appInfos;
		}

		@Override
		public int getCount() {
			return mAppInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mAppInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AppInfo app = (AppInfo) getItem(position);
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.appin_item, parent, false);
				TextView appName = (TextView) convertView.findViewById(R.id.app_name);
				appName.setText(app.getName());
			}
			return convertView;
		}

		public void setData(List<AppInfo> appInfos) {
			mAppInfos.clear();
			mAppInfos.addAll(appInfos);
			notifyDataSetChanged();
		}
	}
}
