package com.spokanevalley.plantesferry;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/*
 * @author Kevin Borling
 * Activity set to launch GameSetup class
 * GLES2 is currently used: (true)
 */
public class PlantesFerryActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		AndroidApplicationConfiguration localAndroidApplicationConfiguration = new AndroidApplicationConfiguration();
		localAndroidApplicationConfiguration.useGL20 = true;
		initialize(new GameSetup(getApplicationContext()),
				localAndroidApplicationConfiguration);
	} // End onCreate
}// End MainActivity