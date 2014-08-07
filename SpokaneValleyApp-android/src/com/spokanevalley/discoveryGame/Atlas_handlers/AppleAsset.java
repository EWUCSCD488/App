package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class AppleAsset {
	public final AtlasRegion coin;
	
	public AppleAsset(TextureAtlas atlas){
		coin = atlas.findRegion(Constants.APPLE_NAME);
	}
}
