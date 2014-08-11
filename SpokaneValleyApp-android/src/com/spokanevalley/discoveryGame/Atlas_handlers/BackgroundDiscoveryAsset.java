package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Define background for game
 * 
 * @author Quyen Ha
 *
 */

public class BackgroundDiscoveryAsset {
	public final AtlasRegion backgroundLeft;
	public final AtlasRegion backgroundRight;
	
	public BackgroundDiscoveryAsset(TextureAtlas atlas){
		backgroundLeft = atlas.findRegion(Constants.BACKGROUND_LEFT);
		backgroundRight = atlas.findRegion(Constants.BACKGROUND_RIGHT);
	}
}
