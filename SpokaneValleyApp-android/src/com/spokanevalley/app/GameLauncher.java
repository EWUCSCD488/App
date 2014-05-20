package com.spokanevalley.app;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.spokanevalley.apples.Apple;

public class GameLauncher extends AndroidApplication {

	public static final String HIGH_SCORE = "highScore";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new Apple( getApplicationContext() ), config);
	}
}
