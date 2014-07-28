package com.spokanevalley.app;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.spokanevalley.minigames.plantesferry.GameSetup;

public class PlantesFerryActivity extends AndroidApplication {
  @Override
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    AndroidApplicationConfiguration localAndroidApplicationConfiguration = new AndroidApplicationConfiguration();
    localAndroidApplicationConfiguration.useGL20 = true;
    initialize(new GameSetup(), localAndroidApplicationConfiguration);
  }
}// End MainActivity