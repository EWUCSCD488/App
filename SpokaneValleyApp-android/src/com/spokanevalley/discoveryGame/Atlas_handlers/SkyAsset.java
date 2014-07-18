package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class SkyAsset {
	public final AtlasRegion sky;
	
	public SkyAsset(TextureAtlas atlas){
		sky = atlas.findRegion(Constants.SKY);
	}
}
