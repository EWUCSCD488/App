package com.spokanevalley.farm;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class FarmGameLauncher extends AndroidApplication {

	public static final String HIGH_SCORE = "highScore";

	/***
	 * Initializes the farm game.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Farm(getApplicationContext()), config);
	}
}
