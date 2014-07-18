package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class DinasourAsset {
	public final AtlasRegion cuteDinasour;
	public DinasourAsset(TextureAtlas atlas){
		cuteDinasour  = atlas.findRegion(Constants.DINASOUR_NAME);
	}
}
