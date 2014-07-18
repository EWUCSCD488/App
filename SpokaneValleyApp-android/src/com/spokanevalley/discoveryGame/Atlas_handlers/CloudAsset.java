package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class CloudAsset {
	public final AtlasRegion cloud1;
	public final AtlasRegion cloud2;
	public final AtlasRegion cloud3;
	
	public CloudAsset(TextureAtlas atlas){
		cloud1 = atlas.findRegion(Constants.CLOUD1);
		cloud2 = atlas.findRegion(Constants.CLOUD2);
		cloud3 = atlas.findRegion(Constants.CLOUD3);
		
	}
}
