package com.spokanevalley.discoveryGame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.spokanevalley.Screen.MenuSreen;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

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
