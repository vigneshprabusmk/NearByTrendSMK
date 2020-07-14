package com.example.nearbytrendsmk.API_interfaces;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.nearbytrendsmk.util.BusProvider;
import com.squareup.otto.Bus;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import retrofit.RestAdapter;

import static com.example.nearbytrendsmk.API_interfaces.ApiConstants.CONSUMER_KEY;
import static com.example.nearbytrendsmk.API_interfaces.ApiConstants.CONSUMER_SECRET;

public class TwitterSearchApplication extends Application {
	private static TwitterSearchApplication mInstance;
	private static Context mAppContext;

	private TwitterServiceProvider mTwitterService;
	private Bus bus = BusProvider.getInstance();

	@Override
	public void onCreate() {
		super.onCreate();

		//initiate Twitter config
		TwitterConfig config = new TwitterConfig.Builder(this)
				.logger(new DefaultLogger(Log.DEBUG))
				.twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY,CONSUMER_SECRET))//pass Twitter API Key and Secret
				.debug(true)
				.build();

		Twitter.initialize(config);
		mInstance = this;
		this.setAppContext(getApplicationContext());
		mTwitterService = new TwitterServiceProvider(buildApi(), bus,this);
		bus.register(mTwitterService);
		bus.register(this); //listen to "global" events
	}


	private TwitterApiService buildApi() {
		return new RestAdapter.Builder()
				.setEndpoint(ApiConstants.TWITTER_SEARCH_URL)
				.build()
				.create(TwitterApiService.class);
	}



	public static TwitterSearchApplication getInstance(){
		return mInstance;
	}
	public static Context getAppContext() {
		return mAppContext;
	}
	public void setAppContext(Context mAppContext) {
		this.mAppContext = mAppContext;
	}


}
