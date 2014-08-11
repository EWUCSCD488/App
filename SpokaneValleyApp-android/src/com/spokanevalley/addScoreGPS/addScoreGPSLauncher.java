package com.spokanevalley.addScoreGPS;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class addScoreGPSLauncher extends AndroidApplication {

	public static final String HIGH_SCORE = "highScore";

	/***
	 * Initialize the GPS location check game.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new addScoreGPSMain(getApplicationContext()), config);
	}
}
