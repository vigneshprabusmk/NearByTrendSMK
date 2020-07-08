package com.example.nearbytrendsmk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;

import com.example.nearbytrendsmk.Notifications.NotificationHelper;
import com.example.nearbytrendsmk.Screens.WelcomeFragment;
import com.example.nearbytrendsmk.util.ActivityHelper;

import static com.example.nearbytrendsmk.util.Util.makeToast;


public class MainActivity extends Activity implements FragmentManager.OnBackStackChangedListener {

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityHelper.navigateTo(this, new WelcomeFragment(), false, R.id.container_main);
		getFragmentManager().addOnBackStackChangedListener(this);
		//Handle when activity is recreated like on orientation Change
		shouldDisplayHomeUp();

    }


	public void transactFragment(Fragment fragment, boolean reload) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if (reload) {
			getFragmentManager().popBackStack();
		}
		transaction.replace(R.id.container_main, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onBackStackChanged() {
		shouldDisplayHomeUp();
	}

	public void shouldDisplayHomeUp() {
		//Enable Up button only  if there are entries in the back stack

		boolean canback = getFragmentManager().getBackStackEntryCount() > 0;
		getActionBar().setDisplayHomeAsUpEnabled(canback);
	}

	@Override
	public boolean onNavigateUp() {
		//This method is called when the up button is pressed. Just the pop back stack.
		getFragmentManager().popBackStack();
		return true;
	}


	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		builder.setMessage(Html.fromHtml("<font color='#4295E6'>Are you sure want to logout from your profile ?</font>"));
		builder.setPositiveButton(Html.fromHtml("<font color='#000'>Yes</font>"),
				new DialogInterface.OnClickListener() {

			@TargetApi(11)
			public void onClick(DialogInterface dialog, int id) {

					Logout();
			}
		});
		builder.setNegativeButton(Html.fromHtml("<font color='#000'>No</font>"),
				new DialogInterface.OnClickListener() {

			@TargetApi(11)
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		AlertDialog theAlertDialog = builder.create();
		theAlertDialog.show();

	}

	public void Logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(Html.fromHtml("<font color='#000'>WARNING</font>"));
		builder.setMessage(Html.fromHtml("<font color='#4295E6'>If you logout from your profile,\nYou won't get any notification from twitter,\nAre you sure want to logout ?</font>"));
		builder.setPositiveButton(Html.fromHtml("<font color='#000'>Yes</font>"),
				new DialogInterface.OnClickListener() {

			@TargetApi(11)
			public void onClick(DialogInterface dialog, int id) {

				makeToast(MainActivity.this, "Logout sucessfully!");
                sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                SharedPreferences.Editor mEditor = sharedPreferences.edit();
                mEditor.remove("user_id");
                mEditor.remove("screen_name");

                mEditor.apply();
                mEditor.commit();
				finishAffinity();

			}

		});
		builder.setNegativeButton(Html.fromHtml("<font color='#000'>No</font>"),
				new DialogInterface.OnClickListener() {

			@TargetApi(11)
			public void onClick(DialogInterface dialog, int id) {

				//finish();
				finishAffinity();

				NotificationHelper.scheduleRepeatingElapsedNotification(MainActivity.this);
				NotificationHelper.enableBootReceiver(MainActivity.this);
                //NotificationHelper.scheduleRepeatingRTCNotification(MainActivity.this,"00","01");
                //NotificationHelper.enableBootReceiver(MainActivity.this);


			}
		});

		AlertDialog theAlertDialog = builder.create();
		theAlertDialog.show();

	}

}
