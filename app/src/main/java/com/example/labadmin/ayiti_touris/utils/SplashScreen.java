package com.example.labadmin.ayiti_touris.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.labadmin.ayiti_touris.activities.MainActivity;
import com.example.labadmin.ayiti_touris.R;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);


        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

               // Intent i = new Intent(SplashScreen.this, MainActivity.class);
              //  startActivity(i);

                // close this activity

                doWork();
                startApp();

                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=30) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();


            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
