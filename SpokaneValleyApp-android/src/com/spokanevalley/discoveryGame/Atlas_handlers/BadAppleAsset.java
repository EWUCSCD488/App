package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Define atlasRegion for bad apple
 * 
 * @author Quyen Ha
 * 
 */

public class BadAppleAsset {
	public final AtlasRegion badApple;

	public BadAppleAsset(TextureAtlas atlas) {
		badApple = atlas.findRegion(Constants.BAD_APPLE_NAME);
	}
}
