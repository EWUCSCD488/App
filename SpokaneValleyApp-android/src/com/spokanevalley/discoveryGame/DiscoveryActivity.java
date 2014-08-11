package com.spokanevalley.discoveryGame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Android Libgdx Activity hosting the game
 * 
 * @author Quyen Ha
 *
 */

public class DiscoveryActivity extends AndroidApplication {
	
	public static boolean drawDebugOutLine = true;
	public static boolean rebuiltAtlas = true;
	
	/**
	 * onCreate in Activity , define game properties and lauch the game
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        cfg.hideStatusBar = true;
        
        
        initialize(new DiscoveryDinasourMain(), cfg);
    }
}