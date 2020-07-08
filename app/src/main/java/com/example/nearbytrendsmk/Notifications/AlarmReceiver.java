package com.example.nearbytrendsmk.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.example.nearbytrendsmk.MainActivity;
import com.example.nearbytrendsmk.R;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.nearbytrendsmk.API_interfaces.TwitterServiceProvider.NOTIFICATION_CHANNEL_ID;

public class AlarmReceiver extends BroadcastReceiver {

    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor mEditor;


    @Override
    public void onReceive(Context context, Intent intent) {
        //Get notification manager to manage/send notifications
     //   Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //WelcomeFragment f1= new WelcomeFragment();
        //f1.mToastRunnable.run();
        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        Notification repeatedNotification = buildLocalNotification(context, pendingIntent).build();

        //Send local notification
        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent) {

        mSharedPreferences = context.getSharedPreferences("login_prefs", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        int responsecount = mSharedPreferences.getInt("responsecount",0);


        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService( NOTIFICATION_SERVICE ) ;

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context);

        builder.setContentTitle(Html.fromHtml("<font color='#000'>New Notification</font>") );
        builder.setContentText(Html.fromHtml("<font color='#4295E6'>You got New Tweets</font>")) ;
        //mBuilder.setTicker( "Notification Listener Service Example "+response.getTweets().size() ) ;
        builder.setSmallIcon(R.drawable. ic_twitterwhite ) ;
        builder.setAutoCancel( true ) ;
        builder .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , builder.build()) ;


        return builder;
    }
}
