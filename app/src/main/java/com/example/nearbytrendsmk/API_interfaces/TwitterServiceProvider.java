package com.example.nearbytrendsmk.API_interfaces;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.nearbytrendsmk.Arraylist.Data_Model;
import com.example.nearbytrendsmk.Arraylist.TweetList;
import com.example.nearbytrendsmk.Arraylist.TwitterTokenType;
import com.example.nearbytrendsmk.DB_and_SP.Database_Helper;
import com.example.nearbytrendsmk.DB_and_SP.PrefsController;
import com.example.nearbytrendsmk.R;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEvent;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEventFailed;
import com.example.nearbytrendsmk.Search_events.SearchTweetsEventOk;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEvent;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEventFailed;
import com.example.nearbytrendsmk.Search_events.TwitterGetTokenEventOk;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.nearbytrendsmk.API_interfaces.TwitterSearchApplication.getAppContext;
import static com.example.nearbytrendsmk.util.Util.getBase64String;


public class TwitterServiceProvider {
	private static final String TAG = TwitterServiceProvider.class.getName();

	private TwitterApiService mApi;
	private Bus mBus;
	Database_Helper database;
	Context incCtx;

	Handler handler = new Handler();
	Runnable runnable;
	int delay = 10000;

	public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
	private final static String default_notification_channel_id = "default" ;

    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor mEditor;


	public TwitterServiceProvider(TwitterApiService api, Bus bus, Context incCtx) {
		this.mApi = api;
		this.mBus = bus;
		this.incCtx = incCtx;
	}



	@Subscribe
	public void onLoadTweets(final SearchTweetsEvent event) {

		mApi.getTweetList("Bearer " + event.twitterToken, event.hashtag,event.geolocation,new Callback<TweetList>() {

			@Override
			public void success(TweetList response, Response rawResponse) {
					mBus.post(new SearchTweetsEventOk(response));

					for(int i=0;i<response.getTweets().size();i++) {

					database = new Database_Helper(incCtx);

					String getName = response.getTweets().get(i).getUser().getName().toString();
					String getdes = response.getTweets().get(i).getText().toString();

					System.out.println("setTweetList" + getName + getdes);

					database.insertData(new Data_Model(getName, getdes));


					}

				NotificationManager mNotificationManager = (NotificationManager)incCtx.getSystemService( NOTIFICATION_SERVICE ) ;
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(incCtx ,default_notification_channel_id ) ;


				mBuilder.setContentTitle(Html.fromHtml("<font color='#000'>New Notification</font>")) ;
				mBuilder.setContentText(Html.fromHtml(response.getTweets().size()+"<font color='#4295E6'> New Tweets added in local database</font>")) ;
				//mBuilder .setContentIntent(pendingIntent);
				//mBuilder.setTicker( "Notification Listener Service Example "+response.getTweets().size() ) ;
				mBuilder.setSmallIcon(R.drawable. ic_twitterwhite ) ;
				mBuilder.setAutoCancel( true ) ;
				if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
					int importance = NotificationManager. IMPORTANCE_HIGH ;
					NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
					mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
					assert mNotificationManager != null;
					mNotificationManager.createNotificationChannel(notificationChannel) ;
				}
				assert mNotificationManager != null;
				mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;

			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(TAG, error.toString(), error);
				mBus.post(new SearchTweetsEventFailed());
			}
		});



	}

	@Subscribe
	public void onGetToken(TwitterGetTokenEvent event) {
		try {
			mApi.getToken("Basic " + getBase64String(ApiConstants.BEARER_TOKEN_CREDENTIALS), "client_credentials", new Callback<TwitterTokenType>() {
				@Override
				public void success(TwitterTokenType token, Response response) {
					PrefsController.setAccessToken(getAppContext(), token.accessToken);
					PrefsController.setTokenType(getAppContext(), token.tokenType);
					mBus.post(new TwitterGetTokenEventOk());
				}

				@Override
				public void failure(RetrofitError error) {
					Log.e(TAG, error.toString(), error);
					mBus.post(new TwitterGetTokenEventFailed());
				}
			});
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.toString(), e);
		}
	}

	public void createNotification () {
		NotificationManager mNotificationManager = (NotificationManager)incCtx.getSystemService( NOTIFICATION_SERVICE ) ;
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(incCtx ,default_notification_channel_id ) ;
		mBuilder.setContentTitle( "New Notification" ) ;
		mBuilder.setContentText("Notification Listener Service Example" ) ;
		mBuilder.setTicker( "Notification Listener Service Example" ) ;
		mBuilder.setSmallIcon(R.drawable. ic_twitterwhite ) ;
		mBuilder.setAutoCancel( true ) ;
		if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
			int importance = NotificationManager. IMPORTANCE_HIGH ;
			NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
			mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
			assert mNotificationManager != null;
			mNotificationManager.createNotificationChannel(notificationChannel) ;
		}
		assert mNotificationManager != null;
		mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
	}








}
