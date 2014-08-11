/*
 * This Is the class that controls the splash screen for the app. It uses the animation drawable class to create a frame animation that plays for 6 seconds when the app starts.
 * The animation itself is stored in the splashani.xml and the splash.xml so you must update the files there if you wish to modify the animation.
 * 
 */



package com.spokanevalley.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
 
public class SplashActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.splash);
     // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        final ImageView img = (ImageView)findViewById(R.id.imgLogo);
        img.setBackgroundResource(R.drawable.splashani);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	System.gc();//calls the garbage collector to remove the frames from memory
                Intent i = new Intent(SplashActivity.this, MapView.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}