package com.example.nearbytrendsmk.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nearbytrendsmk.DB_and_SP.MyPreferenceManager;
import com.example.nearbytrendsmk.MainActivity;
import com.example.nearbytrendsmk.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    private MyPreferenceManager myPreferenceManager;
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPreferenceManager = new MyPreferenceManager(this);
        //check if user is already login or not
        if (myPreferenceManager.getUserId() != 0) {
            //if already login then start main activity
            startMainActivity();
            return;
        }

        setContentView(R.layout.login_activity);

        loginButton = findViewById(R.id.login_button);

        //set the callback to Twitter login button
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession twitterSession = result.data;
                //if twitter session is not null then save user data to shared preference
                if (twitterSession != null) {

                    myPreferenceManager.saveUserId(twitterSession.getUserId());//save user id
                    myPreferenceManager.saveScreenName(twitterSession.getUserName());//save user screen name

                    startMainActivity();

                    //show toast
                    Toast.makeText(LoginActivity.this, "Login Sucessfully!", Toast.LENGTH_SHORT).show();
                } else {

                    //if twitter session is null due to some reason then show error toast
                    Toast.makeText(LoginActivity.this, "Failed to do Login. Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LoginActivity.this, "Failed to do Login. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * method to start Main Activity
     */
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);

    }
}
