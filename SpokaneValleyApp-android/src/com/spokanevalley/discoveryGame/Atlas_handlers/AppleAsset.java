package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Define AtlasRegion for apple
 * 
 * @author Quyen Ha
 *
 */

public class AppleAsset {
	public final AtlasRegion apple;
	
	public AppleAsset(TextureAtlas atlas){
		apple = atlas.findRegion(Constants.APPLE_NAME);
	}
}
