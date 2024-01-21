package com.example.arabicdialectidentificationproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Duration of wait
        int SPLASH_DISPLAY_LENGTH = 2500; // Splash screen will be shown for 1 second

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the main activity.
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    // User is signed in, navigate to MainActivity
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                } else {
                    // No user is signed in, navigate to LoginPage
                    Intent mainIntent = new Intent(SplashActivity.this, FirstPage.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}