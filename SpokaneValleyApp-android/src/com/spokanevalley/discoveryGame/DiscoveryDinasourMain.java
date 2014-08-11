package com.spokanevalley.discoveryGame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;
import com.spokanevalley.discoveryGame.Screen.MenuSreen;

/**
 * loading objects in game at first , then launch the menu
 * 
 * @author Quyen Ha
 *
 */

public class DiscoveryDinasourMain extends Game{
	@Override
	public void create () {
	// Set Libgdx log level
	Gdx.app.setLogLevel(Application.LOG_DEBUG);
	// Load assets
	Assets.instance.init(new AssetManager());
	// Start game at menu screen
	setScreen(new MenuSreen(this));
	}
}
