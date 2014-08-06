package com.spokanevalley.apples;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.spokanevalley.ski.Ski;

public class AppleActivity extends AndroidApplication {

	 @Override
	  public void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    AndroidApplicationConfiguration localAndroidApplicationConfiguration = new AndroidApplicationConfiguration();
	    localAndroidApplicationConfiguration.useGL20 = true;
	    initialize(new Apple(getApplicationContext()), localAndroidApplicationConfiguration);
	  } // End onCreate
}
